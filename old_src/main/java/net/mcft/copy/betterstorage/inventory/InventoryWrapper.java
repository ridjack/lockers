package io.github.tehstoneman.betterstorage.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class InventoryWrapper implements IInventory
{

	public final IInventory	base;

	private boolean			overwriteName	= false;
	private String			title;
	private boolean			localized;

	public InventoryWrapper( IInventory base )
	{
		this.base = base;
	}

	public InventoryWrapper( IInventory base, String title, boolean localized )
	{
		this( base );
		overwriteName = true;
		this.title = title;
		this.localized = localized;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return overwriteName ? new TextComponentString( title ) : base.getDisplayName();
	}

	@Override
	public boolean hasCustomName()
	{
		return overwriteName ? localized : base.hasCustomName();
	}

	@Override
	public int getSizeInventory()
	{
		return base.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot( int slot )
	{
		return base.getStackInSlot( slot );
	}

	@Override
	public ItemStack decrStackSize( int slot, int amount )
	{
		return base.decrStackSize( slot, amount );
	}

	@Override
	public void setInventorySlotContents( int slot, ItemStack stack )
	{
		base.setInventorySlotContents( slot, stack );
	}

	/*
	 * @Override
	 * public ItemStack getStackInSlotOnClosing(int slot) { return base.getStackInSlotOnClosing(slot); }
	 */

	@Override
	public int getInventoryStackLimit()
	{
		return base.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer player )
	{
		return base.isUseableByPlayer( player );
	}

	@Override
	public boolean isItemValidForSlot( int slot, ItemStack stack )
	{
		return base.isItemValidForSlot( slot, stack );
	}

	@Override
	public void markDirty()
	{
		base.markDirty();
	}

	@Override
	public void openInventory( EntityPlayer player )
	{
		base.openInventory( player );
	}

	@Override
	public void closeInventory( EntityPlayer player )
	{
		base.closeInventory( player );
	}

	@Override
	public boolean equals( Object obj )
	{
		if( !( obj instanceof InventoryWrapper ) )
			return false;
		final InventoryWrapper inv = (InventoryWrapper)obj;
		return base.equals( inv.base );
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot( int index )
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getField( int id )
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField( int id, int value )
	{
		// TODO Auto-generated method stub
	}

	@Override
	public int getFieldCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear()
	{
		// TODO Auto-generated method stub
	}
}
