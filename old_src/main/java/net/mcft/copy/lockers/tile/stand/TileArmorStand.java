package io.github.tehstoneman.lockers.tile.stand;

import io.github.tehstoneman.lockers.common.block.BlockContainerlockers;
import io.github.tehstoneman.lockers.tile.TileContainerlockers;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileArmorStand extends TileContainerlockers
{
	public TileArmorStand()
	{
		super( Material.ROCK );

		setHardness( 2.5f );
		// setBlockBounds(2 / 16.0F, 0, 2 / 16.0F, 14 / 16.0F, 2, 14 / 16.0F);

		setHarvestLevel( "pickaxe", 0 );
	}

	@Override
	public Class< ? extends ItemBlock > getItemClass()
	{
		return ItemArmorStand.class;
	}

	/*
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT)
	 * public void registerBlockIcons(IIconRegister iconRegister) {
	 * blockIcon = iconRegister.registerIcon("stone_slab_top");
	 * }
	 */

	/*
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT)
	 * public String getItemIconName() { return Constants.modId + ":armorStand"; }
	 */

	/*
	 * @Override
	 * public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
	 * int metadata = world.getBlockMetadata(x, y, z);
	 * if (metadata == 0) setBlockBounds(2 / 16.0F, 0, 2 / 16.0F, 14 / 16.0F, 2, 14 / 16.0F);
	 * else setBlockBounds(2 / 16.0F, -1, 2 / 16.0F, 14 / 16.0F, 1, 14 / 16.0F);
	 * }
	 */

	@Override
	public boolean isOpaqueCube( IBlockState state )
	{
		return false;
	}

	@Override
	public boolean isFullBlock( IBlockState state )
	{
		return false;
	}

	@Override
	@SideOnly( Side.CLIENT )
	public EnumBlockRenderType getRenderType( IBlockState state )
	{
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;// return ClientProxy.armorStandRenderId;
	}

	/*
	 * @Override
	 * public int quantityDropped(int meta, int fortune, Random random) {
	 * return ((meta == 0) ? 1 : 0);
	 * }
	 */

	@Override
	public void onBlockPlacedBy( World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack )
	{
		super.onBlockPlacedBy( world, pos, state, player, stack );
		// Set block above to armor stand with metadata 1.
		// world.setBlockState( pos.up(), state );
		// world.setBlock(x, y + 1, z, this, 1, SetBlockFlag.DEFAULT);
	}

	@Override
	public boolean onBlockActivated( World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem,
			EnumFacing side, float hitX, float hitY, float hitZ )
	{
		if( world.isRemote )
			return true;
		// if (world.getBlockMetadata(x, y, z) > 0) { y -= 1; hitY += 1; }
		return super.onBlockActivated( world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ );
	}

	@Override
	public ItemStack getPickBlock( IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player )
	{
		// if (world.getBlockMetadata(x, y, z) > 0) { y -= 1; }
		return super.getPickBlock( state, target, world, pos, player );
	}

	@Override
	public boolean removedByPlayer( IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean hillHarvest )
	{
		return world.setBlockToAir( pos );
	}

	@Override
	public void breakBlock( World world, BlockPos pos, IBlockState state )
	{
		// if (meta > 0) return;
		super.breakBlock( world, pos, state );
	}

	@Override
	public void onNeighborChange( IBlockAccess world, BlockPos pos, BlockPos neighbor )
	{
		/*
		 * int metadata = world.getBlockMetadata(x, y, z);
		 * int targetY = y + ((metadata == 0) ? 1 : -1);
		 * int targetMeta = ((metadata == 0) ? 1 : 0);
		 * if ((world.getBlock(x, targetY, z) == this) &&
		 * (world.getBlockMetadata(x, targetY, z) == targetMeta)) return;
		 * world.setBlockToAir(x, y, z);
		 * if (metadata == 0)
		 * dropBlockAsItem(world, x, y, z, metadata, 0);
		 */
	}

	@Override
	public TileEntity createNewTileEntity( World world, int metadata )
	{
		return metadata == 0 ? new TileEntityArmorStand() : null;
	}

	@Override
	public boolean hasComparatorInputOverride( IBlockState state )
	{
		return true;
	}
}
