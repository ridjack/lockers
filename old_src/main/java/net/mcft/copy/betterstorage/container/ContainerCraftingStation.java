package io.github.tehstoneman.betterstorage.common.inventory;

import java.util.logging.Logger;

import io.github.tehstoneman.betterstorage.ModInfo;
import io.github.tehstoneman.betterstorage.api.crafting.StationCrafting;
import io.github.tehstoneman.betterstorage.common.tileentity.TileEntityCraftingStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

//@InventoryContainer
public class ContainerCraftingStation extends Container
{
	// public InventoryCraftingStation inventoryStation;
	private final IInventory				inventoryPlayer;

	private final ItemStack[]				crafting;
	private final ItemStackHandler			output;
	private final ItemStackHandler			inventory;

	private final TileEntityCraftingStation	tileCraftingStation;

	private final int						lastOutputIsReal	= 0;
	private int								lastProgress, lastMaxProgress;

	public int								indexInput, indexOutput, indexContents, indexPlayer, indexHotbar;

	public ContainerCraftingStation( EntityPlayer player, TileEntityCraftingStation tileCraftingStation )
	{
		inventoryPlayer = player.inventory;
		crafting = tileCraftingStation.crafting;
		output = tileCraftingStation.output;
		inventory = tileCraftingStation.inventory;

		this.tileCraftingStation = tileCraftingStation;

		indexInput = 0;
		indexOutput = indexInput + crafting.length;
		indexContents = indexOutput + output.getSlots();
		indexHotbar = indexContents + inventory.getSlots();
		indexPlayer = indexHotbar + 9;

		for( int i = 0; i < 9; i++ )
		{
			final int x = i % 3 * 18 + 17;
			final int y = 17 + i / 3 * 18;
			addSlotToContainer( new SlotStationCrafting( tileCraftingStation, i, x, y ) );
		}

		for( int i = 0; i < 9; i++ )
		{
			final int x = i % 3 * 18 + 107;
			final int y = 17 + i / 3 * 18;
			addSlotToContainer( new SlotOutputHandler( output, i, x, y ) );
		}

		for( int i = 0; i < 18; i++ )
		{
			final int x = i % 9 * 18 + 8;
			final int y = 76 + i / 9 * 18;
			addSlotToContainer( new SlotItemHandler( inventory, i, x, y ) );
		}

		for( int i = 0; i < 27; i++ )
		{
			final int x = i % 9 * 18 + 8;
			final int y = 126 + i / 9 * 18;
			addSlotToContainer( new Slot( inventoryPlayer, i + 9, x, y ) );
		}

		for( int i = 0; i < 9; i++ )
		{
			final int x = i % 9 * 18 + 8;
			final int y = 184;
			addSlotToContainer( new Slot( inventoryPlayer, i, x, y ) );
		}
	}

	@Override
	public boolean canInteractWith( EntityPlayer playerIn )
	{
		return true;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for( int j = 0; j < listeners.size(); ++j )
		{
			if( tileCraftingStation.progress != lastProgress )
				listeners.get( j ).sendProgressBarUpdate( this, 1, tileCraftingStation.progress );
			if( tileCraftingStation.maxProgress != lastMaxProgress )
				listeners.get( j ).sendProgressBarUpdate( this, 2, tileCraftingStation.maxProgress );
		}
		lastProgress = tileCraftingStation.progress;
		lastMaxProgress = tileCraftingStation.maxProgress;
	}

	@Override
	@SideOnly( Side.CLIENT )
	public void updateProgressBar( int id, int val )
	{
		switch( id )
		{
		case 0:
			tileCraftingStation.outputIsReal = val != 0;
			break;
		case 1:
			tileCraftingStation.progress = val;
			break;
		case 2:
			tileCraftingStation.maxProgress = val;
			break;
		}
	}

	@Override
	public ItemStack transferStackInSlot( EntityPlayer player, int index )
	{
		final Slot slot = inventorySlots.get( index );
		ItemStack returnStack = null;

		if( slot != null && slot.getHasStack() )
		{
			final ItemStack itemStack = slot.getStack();
			returnStack = itemStack.copy();

			if( index < indexOutput )
			{
				// Empty slot contents
				slot.putStack( null );
				return null;
			}
			else
				if( index < indexHotbar )
				{
					// Try to transfer from output to player
					if( !mergeItemStack( itemStack, indexHotbar, inventorySlots.size(), true ) )
						return null;
				}
				else
					if( !mergeItemStack( itemStack, indexContents, indexHotbar, false ) )
						return null;

			if( itemStack == null || itemStack.stackSize == 0 )
				slot.putStack( null );
			else
				slot.onSlotChanged();
		}

		return returnStack;
	}

	@Override
	public ItemStack slotClick( int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player )
	{
		final InventoryPlayer inventoryplayer = player.inventory;

		if( clickTypeIn == ClickType.PICKUP_ALL && slotId >= 0 )
		{
			final Slot slot = inventorySlots.get( slotId );
			final ItemStack itemstack = inventoryplayer.getItemStack();

			if( itemstack != null && ( slot == null || !slot.getHasStack() || !slot.canTakeStack( player ) ) )
			{
				final int i1 = dragType == 0 ? 0 : inventorySlots.size() - 1;
				final int j1 = dragType == 0 ? 1 : -1;

				for( int i3 = 0; i3 < 2; ++i3 )
					for( int j3 = i1; j3 >= 0 && j3 < inventorySlots.size() && itemstack.stackSize < itemstack.getMaxStackSize(); j3 += j1 )
					{
						final Slot slot8 = inventorySlots.get( j3 );

						if( j3 >= indexOutput && slot8.getHasStack() && canAddItemToSlot( slot8, itemstack, true ) && slot8.canTakeStack( player )
								&& canMergeSlot( itemstack, slot8 )
								&& ( i3 != 0 || slot8.getStack().stackSize != slot8.getStack().getMaxStackSize() ) )
						{
							final int l = Math.min( itemstack.getMaxStackSize() - itemstack.stackSize, slot8.getStack().stackSize );
							final ItemStack itemstack2 = slot8.decrStackSize( l );
							itemstack.stackSize += l;

							if( itemstack2.stackSize <= 0 )
								slot8.putStack( (ItemStack)null );

							slot8.onPickupFromSlot( player, itemstack2 );
						}
					}
			}
			detectAndSendChanges();
			return itemstack;
		}

		if( slotId >= 0 && slotId < indexOutput && clickTypeIn == ClickType.PICKUP && ( dragType == 0 || dragType == 1 ) )
		{
			final ItemStack itemstack = inventoryplayer.getItemStack();
			final Slot slot = inventorySlots.get( slotId );

			if( slot != null )
			{

				if( itemstack != null )
				{
					if( slot.isItemValid( itemstack ) )
						slot.putStack( new ItemStack( itemstack.getItem() ) );

				}
				else
					slot.putStack( null );
				tileCraftingStation.onCraftMatrixChanged();
				slot.onSlotChanged();
				return itemstack;
			}
		}

		if( slotId >= indexOutput && slotId < indexContents && tileCraftingStation.currentCrafting != null
				&& output.getStackInSlot( slotId - 9 ) != null )
		{
			final ItemStack craftingStack = output.getStackInSlot( slotId - 9 );
			final int amount = craftingStack.stackSize;
			// if( clickTypeIn == ClickType.QUICK_MOVE )

			final ItemStack holding = player.inventory.getItemStack();
			if( holding == null || ItemStack.areItemsEqual( holding, craftingStack ) && holding.stackSize + amount <= holding.getMaxStackSize() )
				tileCraftingStation.craft( player );
		}
		return super.slotClick( slotId, dragType, clickTypeIn, player );
	}

	@Override
	public boolean canDragIntoSlot( Slot slotIn )
	{
		return slotIn.slotNumber >= indexContents;
	}

	public int getMaxProgress()
	{
		return Math.max( 1, tileCraftingStation.maxProgress );
	}

	public int getCraftingProgress()
	{
		return tileCraftingStation.progress;
	}

	public StationCrafting getCurrentCrafting()
	{
		return tileCraftingStation.currentCrafting;
	}
}
