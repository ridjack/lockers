package io.github.tehstoneman.lockers.container;

import io.github.tehstoneman.lockers.common.inventory.ContainerCraftingStation;
import io.github.tehstoneman.lockers.inventory.InventoryCraftingStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotStationOutput extends Slot
{
	public final InventoryCraftingStation startingInventory;

	public SlotStationOutput( ContainerCraftingStation container, IInventory inventory, int index, int x, int y )
	{
		super( inventory, index, x, y );
		startingInventory = container.inventoryStation;
	}

	@Override
	public boolean isItemValid( ItemStack stack )
	{
		return false;
	}

	@Override
	public boolean canTakeStack( EntityPlayer player )
	{
		return super.canTakeStack( player ) && startingInventory.canTake( player );
	}
}
