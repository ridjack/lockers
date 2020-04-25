package io.github.tehstoneman.lockers.inventory;

import io.github.tehstoneman.lockers.utils.StackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InventoryItem extends InventoryStacks
{

	private final String			title;

	protected final EntityPlayer	player;
	protected final int				slot;

	protected final ItemStack		stack;

	public InventoryItem( EntityPlayer player, int size, String title, boolean localized )
	{
		super( StackUtils.getStackContents( player.getHeldItemMainhand(), size ) );
		this.title = title;
		this.player = player;
		slot = player.inventory.currentItem;
		stack = player.getHeldItemMainhand().copy();
	}

	@Override
	public String getName()
	{
		return title;
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer player )
	{
		return true;
	}

	/*
	 * @Override
	 * public void closeInventory()
	 * {
	 * updateStack();
	 * }
	 */

	protected void updateStack()
	{
		StackUtils.setStackContents( stack, allContents[0] );
		player.inventory.setInventorySlotContents( slot, stack );
	}

}
