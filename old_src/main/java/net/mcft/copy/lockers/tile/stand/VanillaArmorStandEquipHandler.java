package io.github.tehstoneman.lockers.tile.stand;

import io.github.tehstoneman.lockers.api.stand.ArmorStandEquipHandler;
import io.github.tehstoneman.lockers.api.stand.EnumArmorStandRegion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class VanillaArmorStandEquipHandler extends ArmorStandEquipHandler
{

	public static final String	ID			= "Vanilla";
	public static final int		PRIORITY	= 0;

	public final int			armorType;

	public VanillaArmorStandEquipHandler( EnumArmorStandRegion region )
	{
		super( ID, region, PRIORITY );
		armorType = 3 - region.ordinal();
	}

	@Override
	public boolean isValidItem( EntityPlayer player, ItemStack item )
	{
		return false;
		// return item.getItem().isValidArmor(item, armorType, player);
	}

	@Override
	public ItemStack getEquipment( EntityPlayer player )
	{
		return null;
		// return player.getCurrentArmor(region.ordinal());
	}

	@Override
	public boolean canSetEquipment( EntityPlayer player, ItemStack item )
	{
		return true;
	}

	@Override
	public void setEquipment( EntityPlayer player, ItemStack item )
	{
		// player.setCurrentItemOrArmor(region.ordinal() + 1, item);
		// Shouldn't this be done automatically?
		// ((EntityPlayerMP)player).playerNetServerHandler.sendPacket( new SPacketSetSlot(player.openContainer.windowId, 8 - region.ordinal(), item));
	}

}
