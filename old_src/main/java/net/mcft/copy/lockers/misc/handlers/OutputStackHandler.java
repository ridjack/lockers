package io.github.tehstoneman.lockers.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class OutputStackHandler extends ItemStackHandler
{
	ItemStack[] ghostStacks;
	public OutputStackHandler()
	{
		super();
	}

	public OutputStackHandler( int size )
	{
		super( size );
		ghostStacks = new ItemStack[size];
	}

	public OutputStackHandler( ItemStack[] stacks )
	{
		super( stacks );
		ghostStacks = new ItemStack[stacks.length];
	}

    public void setSize(int size)
    {
    	super.setSize( size );
    	ghostStacks = new ItemStack[size];
    }

	public void setStackGhosted(int slot, ItemStack stack)
    {
        validateSlotIndex(slot);
        if (ItemStack.areItemStacksEqual(this.ghostStacks[slot], stack))
            return;
        this.ghostStacks[slot] = stack;
    }
}
