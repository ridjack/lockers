package io.github.tehstoneman.lockers.common.inventory;

import java.util.logging.Logger;

import io.github.tehstoneman.lockers.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class CraftingStackHandler extends ItemStackHandler
{
	public CraftingStackHandler()
	{
		super();
	}

	public CraftingStackHandler( int size )
	{
		super( size );
	}

	public CraftingStackHandler( ItemStack[] stacks )
	{
		super( stacks );
	}

	@Override
	public ItemStack insertItem( int slot, ItemStack stack, boolean simulate )
	{
		//Logger.getLogger( ModInfo.modId ).info( slot + " : " + stack.toString() + " : " + simulate );
		if( simulate )
			return super.insertItem( slot, stack, simulate );

		if( stack == null || stack.stackSize == 0 )
			return null;

		validateSlotIndex( slot );

		stacks[slot] = stack.copy();
		stacks[slot].stackSize = 1;
		onContentsChanged( slot );

		return stack;
	}

	@Override
	public ItemStack extractItem( int slot, int amount, boolean simulate )
	{
		if( simulate )
			return super.extractItem( slot, amount, simulate );

		if( amount == 0 )
			return null;

		validateSlotIndex( slot );

		final ItemStack existing = stacks[slot];

		if( existing == null )
			return null;

		if( !simulate )
		{
			stacks[slot] = null;
			onContentsChanged( slot );
		}
		return null;
	}

	@Override
	protected int getStackLimit( int slot, ItemStack stack )
	{
		return 1;
	}

	public InventoryCrafting getCraftingInventory()
	{
		final InventoryCrafting crafting = new InventoryCrafting( new DummyContainer(), 3, 3 );
		for( int i = 0; i < crafting.getSizeInventory(); i++ )
			crafting.setInventorySlotContents( i, stacks[i] );
		return crafting;
	}

	private class DummyContainer extends Container
	{
		@Override
		public boolean canInteractWith( EntityPlayer playerIn )
		{
			return false;
		}
	}
}
