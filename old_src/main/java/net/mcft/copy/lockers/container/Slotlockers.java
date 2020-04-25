package io.github.tehstoneman.lockers.container;

import io.github.tehstoneman.lockers.common.inventory.Containerlockers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Slotlockers extends Slot {

	private final Containerlockers container;
	private boolean isProtected = false;
	
	public Slotlockers(Containerlockers container,
	                         IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
		this.container = container;
	}
	
	/** Prevents the slot from being interacted with. */
	public Slotlockers setProtected() {
		isProtected = true;
		return this;
	}
	/** Returns if the slot is protected. */
	public boolean isProtected() { return isProtected; }
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return (!isProtected && inventory.isItemValidForSlot(slotNumber, stack));
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player) {
		// Not sure if isItemValidForSlot can / should be used to check
		// if items can be taken out, but I'll just use it anyway.
		return (!isProtected && inventory.isItemValidForSlot(slotNumber, null));
	}
	
	@Override
	public void onSlotChanged() {
		container.onSlotChanged(slotNumber);
		super.onSlotChanged();
	}
	
}
