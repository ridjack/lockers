package io.github.tehstoneman.lockers.tile.stand;

import io.github.tehstoneman.lockers.api.stand.ClientArmorStandPlayer;
import io.github.tehstoneman.lockers.api.stand.IArmorStand;
import io.github.tehstoneman.lockers.api.stand.IArmorStandRenderHandler;
import net.minecraft.tileentity.TileEntity;

public class VanillaArmorStandRenderHandler implements IArmorStandRenderHandler
{
	@Override
	public <T extends TileEntity & IArmorStand> void onPreRender( T armorStand, ClientArmorStandPlayer player )
	{
		// player.setCurrentItemOrArmor(EquipmentSlot.HEAD, armorStand.getItem(lockersArmorStand.helmet));
		// player.setCurrentItemOrArmor(EquipmentSlot.CHEST, armorStand.getItem(lockersArmorStand.chestplate));
		// player.setCurrentItemOrArmor(EquipmentSlot.LEGS, armorStand.getItem(lockersArmorStand.leggins));
		// player.setCurrentItemOrArmor(EquipmentSlot.FEET, armorStand.getItem(lockersArmorStand.boots));
	}

	@Override
	public <T extends TileEntity & IArmorStand> void onPostRender( T armorStand, ClientArmorStandPlayer player )
	{}
}
