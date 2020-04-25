package io.github.tehstoneman.betterstorage.common.tileentity;

import io.github.tehstoneman.betterstorage.ModInfo;
import io.github.tehstoneman.betterstorage.api.ConnectedType;
import io.github.tehstoneman.betterstorage.common.block.BlockConnectableContainer;
import io.github.tehstoneman.betterstorage.common.block.BlockLocker;
import io.github.tehstoneman.betterstorage.common.inventory.ContainerLocker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityLocker extends TileEntityConnectable implements IChestLid, ITickableTileEntity
{
	protected int ticksSinceSync;

	public TileEntityLocker( TileEntityType< ? > tileEntityTypeIn )
	{
		super( tileEntityTypeIn );
	}

	public TileEntityLocker()
	{
		super( BetterStorageTileEntityTypes.LOCKER.get() );
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB( pos.add( -1, 0, -1 ), pos.add( 2, 2, 2 ) );
	}

	@Override
	protected String getConnectableName()
	{
		return ModInfo.CONTAINER_LOCKER_NAME;
	}

	@Override
	public BlockPos getConnected()
	{
		if( isConnected() )
			if( isMain() )
				return pos.up();
			else
				return pos.down();
		return null;
	}

	@Override
	public Container createMenu( int windowID, PlayerInventory playerInventory, PlayerEntity player )
	{
		if( isMain() )
			return new ContainerLocker( windowID, playerInventory, world, pos );
		else
			return getMainTileEntity().createMenu( windowID, playerInventory, player );
	}

	@Override
	public void tick()
	{
		final int x = pos.getX();
		final int y = pos.getY();
		final int z = pos.getZ();
		++ticksSinceSync;
		if( !world.isRemote && numPlayersUsing != 0 && ( ticksSinceSync + x + y + z ) % 200 == 0 )
		{
			numPlayersUsing = 0;
			final float f = 5.0F;

			for( final PlayerEntity entityplayer : world.getEntitiesWithinAABB( PlayerEntity.class,
					new AxisAlignedBB( x - 5.0F, y - 5.0F, z - 5.0F, x + 1 + 5.0F, y + 1 + 5.0F, z + 1 + 5.0F ) ) )
				if( entityplayer.openContainer instanceof ContainerLocker )
					++numPlayersUsing;
		}

		prevLidAngle = lidAngle;
		final float f1 = 0.1F;
		if( numPlayersUsing > 0 && lidAngle == 0.0F )
			playSound( SoundEvents.BLOCK_CHEST_OPEN );

		if( numPlayersUsing == 0 && lidAngle > 0.0F || numPlayersUsing > 0 && lidAngle < 1.0F )
		{
			final float f2 = lidAngle;
			if( numPlayersUsing > 0 )
				lidAngle += 0.1F;
			else
				lidAngle -= 0.1F;

			if( lidAngle > 1.0F )
				lidAngle = 1.0F;

			final float f3 = 0.5F;
			if( lidAngle < 0.5F && f2 >= 0.5F )
				playSound( SoundEvents.BLOCK_CHEST_CLOSE );

			if( lidAngle < 0.0F )
				lidAngle = 0.0F;
		}
	}

	protected void playSound( SoundEvent soundIn )
	{
		final ConnectedType chesttype = getBlockState().get( BlockConnectableContainer.TYPE );
		double x = pos.getX() + 0.5D;
		final double y = pos.getY() + 0.5D;
		double z = pos.getZ() + 0.5D;
		if( chesttype != ConnectedType.SINGLE )
		{
			final Direction enumfacing = BlockLocker.getDirectionToAttached( getBlockState() );
			x += enumfacing.getXOffset() * 0.5D;
			z += enumfacing.getZOffset() * 0.5D;
		}

		world.playSound( (PlayerEntity)null, x, y, z, soundIn, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F );
	}

	@Override
	public float getLidAngle( float partialTicks )
	{
		return prevLidAngle + ( lidAngle - prevLidAngle ) * partialTicks;
	}
}
