package io.github.tehstoneman.lockers.inventory;

import io.github.tehstoneman.lockers.utils.StackUtils;
import net.minecraft.item.ItemStack;

public class InventoryCardboardBox extends InventoryStacks
{
	public InventoryCardboardBox( ItemStack[] contents )
	{
		super( contents );
	}

	@Override
	public boolean isItemValidForSlot( int slot, ItemStack stack )
	{
		return StackUtils.canBeStoredInContainerItem( stack );
	}
}
