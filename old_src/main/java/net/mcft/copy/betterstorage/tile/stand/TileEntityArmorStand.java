package io.github.tehstoneman.betterstorage.tile.stand;

import java.util.HashMap;
import java.util.Map;

import io.github.tehstoneman.betterstorage.ModInfo;
import io.github.tehstoneman.betterstorage.api.stand.ArmorStandEquipHandler;
import io.github.tehstoneman.betterstorage.api.stand.BetterStorageArmorStand;
import io.github.tehstoneman.betterstorage.api.stand.EnumArmorStandRegion;
import io.github.tehstoneman.betterstorage.api.stand.IArmorStand;
import io.github.tehstoneman.betterstorage.common.tileentity.TileEntityContainer;
import io.github.tehstoneman.betterstorage.utils.WorldUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityArmorStand extends TileEntityContainer implements IArmorStand
{
	private final Map< EnumArmorStandRegion, Map< ArmorStandEquipHandler, ItemStack > >	equipment	= new HashMap<>();

	public int																			rotation	= 0;

	public TileEntityArmorStand()
	{
		for( final EnumArmorStandRegion region : EnumArmorStandRegion.values() )
			equipment.put( region, new HashMap< ArmorStandEquipHandler, ItemStack >() );
	}

	private void clearItems()
	{
		for( final EnumArmorStandRegion region : EnumArmorStandRegion.values() )
			equipment.get( region ).clear();
	}

	// IArmorStand implementation

	@Override
	public ItemStack getItem( ArmorStandEquipHandler handler )
	{
		return equipment.get( handler.region ).get( handler );
	}

	@Override
	public void setItem( ArmorStandEquipHandler handler, ItemStack item )
	{
		final Map< ArmorStandEquipHandler, ItemStack > items = equipment.get( handler.region );
		if( item == null )
			items.remove( handler );
		else
			items.put( handler, item );

		if( worldObj != null )
		{
			markForUpdate();
			markDirty();
		}
	}

	// TileEntity stuff

	@Override
	@SideOnly( Side.CLIENT )
	public AxisAlignedBB getRenderBoundingBox()
	{
		return WorldUtils.getAABB( this, 0, 0, 0, 0, 1, 0 );
	}

	// TileEntityContainer stuff

	@Override
	public String getName()
	{
		return ModInfo.containerArmorStand;
	}

	@Override
	public boolean canSetCustomTitle()
	{
		return false;
	}

	@Override
	protected int getSizeContents()
	{
		return 0;
	}

	@Override
	public void onBlockPlaced( EntityLivingBase player, ItemStack stack )
	{
		super.onBlockPlaced( player, stack );
		rotation = Math.round( ( player.rotationYawHead + 180 ) * 16 / 360 );
	}

	@Override
	public boolean onBlockActivated( EntityPlayer player, int side, float hitX, float hitY, float hitZ )
	{
		if( worldObj.isRemote )
			return true;

		final int slot = Math.max( 0, Math.min( 3, (int)( hitY * 2 ) ) );
		final EnumArmorStandRegion region = EnumArmorStandRegion.values()[slot];

		for( final ArmorStandEquipHandler handler : BetterStorageArmorStand.getEquipHandlers( region ) )
		{
			final ItemStack item = getItem( handler );
			if( player.isSneaking() )
			{

				// Swap player's equipped armor with armor stand's.
				final ItemStack equipped = handler.getEquipment( player );
				if( item == null && equipped == null || item != null && !handler.isValidItem( player, item )
						|| equipped != null && !handler.isValidItem( player, equipped ) || !handler.canSetEquipment( player, item ) )
					continue;

				setItem( handler, equipped );
				handler.setEquipment( player, item );

			}
			else
			{

				// Swap player's held item with armor stand's.
				final ItemStack holding = player.getHeldItemMainhand();
				if( item == null && holding == null || holding != null && !handler.isValidItem( player, holding ) )
					continue;

				setItem( handler, holding );
				// player.setCurrentItemOrArmor(EquipmentSlot.HELD, item);
				break;

			}
		}

		return true;

	}

	@Override
	public ItemStack onPickBlock( ItemStack block, RayTraceResult target )
	{
		final int slot = Math.max( 0, Math.min( 3, (int)( ( target.hitVec.yCoord - pos.getY() ) * 2 ) ) );
		final EnumArmorStandRegion region = EnumArmorStandRegion.values()[slot];

		ItemStack item;
		for( final ArmorStandEquipHandler handler : BetterStorageArmorStand.getEquipHandlers( region ) )
			if( ( item = getItem( handler ) ) != null )
				return item;

		return block;
	}

	@Override
	public void dropContents()
	{
		ItemStack item;
		for( final EnumArmorStandRegion region : EnumArmorStandRegion.values() )
			for( final ArmorStandEquipHandler handler : BetterStorageArmorStand.getEquipHandlers( region ) )
				if( ( item = getItem( handler ) ) != null )
					WorldUtils.dropStackFromBlock( worldObj, pos.getX(), pos.getY(), pos.getZ(), item );
		clearItems();
	}

	@Override
	protected int getComparatorSignalStengthInternal()
	{
		int count = 0;
		for( final EnumArmorStandRegion region : EnumArmorStandRegion.values() )
			for( final ArmorStandEquipHandler handler : BetterStorageArmorStand.getEquipHandlers( region ) )
				if( getItem( handler ) != null )
					count++;
		return count;
	}

	// TileEntity synchronization

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		final NBTTagCompound compound = new NBTTagCompound();
		write( compound );
		return new SPacketUpdateTileEntity( pos, 0, compound );
	}

	@Override
	public void onDataPacket( NetworkManager net, SPacketUpdateTileEntity packet )
	{
		read( packet.getNbtCompound() );
	}

	// Reading from / writing to NBT

	@Override
	public void readFromNBT( NBTTagCompound compound )
	{
		super.readFromNBT( compound );
		read( compound );
	}

	@Override
	public NBTTagCompound writeToNBT( NBTTagCompound compound )
	{
		super.writeToNBT( compound );
		write( compound );
		return compound;
	}

	public void read( NBTTagCompound compound )
	{
		rotation = compound.getByte( "rotation" );

		clearItems();
		final NBTBase itemsTag = compound.getTag( "Items" );
		if( itemsTag instanceof NBTTagList )
		{

			// Backward compatibility.
			final NBTTagList items = (NBTTagList)itemsTag;
			for( int i = 0; i < items.tagCount(); i++ )
			{
				final NBTTagCompound item = items.getCompoundTagAt( i );
				final int slot = item.getByte( "Slot" ) & 255;
				if( slot < 0 || slot >= EnumArmorStandRegion.values().length )
					continue;
				final EnumArmorStandRegion region = EnumArmorStandRegion.values()[slot];
				final ArmorStandEquipHandler handler = BetterStorageArmorStand.getEquipHandler( region, VanillaArmorStandEquipHandler.ID );
				if( handler != null )
					setItem( handler, ItemStack.loadItemStackFromNBT( item ) );
			}

		}
		/*
		 * else final NBTTagCompound items = (NBTTagCompound)itemsTag;
		 * for (final EnumArmorStandRegion region : EnumArmorStandRegion.values()) NBTTagCompound regionItems = items.getCompoundTag(region.toString());
		 * for (String id : (Set<String>)regionItems.func_150296_c()) {
		 * ItemStack item = ItemStack.loadItemStackFromNBT(regionItems.getCompoundTag(id));
		 * ArmorStandEquipHandler handler = BetterStorageArmorStand.getEquipHandler(region, id);
		 * setItem(handler, item);
		 * }
		 */
	}

	public void write( NBTTagCompound compound )
	{
		compound.setByte( "rotation", (byte)rotation );
		final NBTTagCompound items = new NBTTagCompound();
		for( final EnumArmorStandRegion region : EnumArmorStandRegion.values() )
		{
			final NBTTagCompound regionCompound = new NBTTagCompound();

			ItemStack item;
			for( final ArmorStandEquipHandler handler : BetterStorageArmorStand.getEquipHandlers( region ) )
				if( ( item = getItem( handler ) ) != null )
					regionCompound.setTag( handler.id, item.writeToNBT( new NBTTagCompound() ) );

			items.setTag( region.toString(), regionCompound );
		}
		compound.setTag( "Items", items );
	}
}
