package io.github.tehstoneman.betterstorage.inventory;

import io.github.tehstoneman.betterstorage.api.crafting.BetterStorageCrafting;
import io.github.tehstoneman.betterstorage.api.crafting.ContainerInfo;
import io.github.tehstoneman.betterstorage.api.crafting.CraftingSourceTileEntity;
import io.github.tehstoneman.betterstorage.api.crafting.IRecipeInput;
import io.github.tehstoneman.betterstorage.api.crafting.StationCrafting;
import io.github.tehstoneman.betterstorage.common.tileentity.TileEntityCraftingStation;
import io.github.tehstoneman.betterstorage.config.GlobalConfig;
import io.github.tehstoneman.betterstorage.item.recipe.VanillaStationCrafting;
import io.github.tehstoneman.betterstorage.misc.FakePlayer;
import io.github.tehstoneman.betterstorage.utils.StackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryCraftingStation extends ItemStackHandler
{
	public TileEntityCraftingStation	entity					= null;

	public final ItemStackHandler		crafting;
	public final ItemStackHandler		output;
	public final ItemStackHandler		inventory;

	public StationCrafting				currentCrafting			= null;
	public boolean						outputIsReal			= false;
	public int							progress				= 0;

	private boolean						hasRequirements			= false;
	private boolean						checkHasRequirements	= true;

	public InventoryCraftingStation( ItemStackHandler crafting, ItemStackHandler output, ItemStackHandler inventory )
	{
		this.crafting = crafting;
		this.output = output;
		this.inventory = inventory;
	}

	public void update()
	{
		if( !outputIsReal && currentCrafting != null )
			if( progress >= Math.max( currentCrafting.getCraftingTime(), GlobalConfig.stationAutocraftDelaySetting.getValue() ) )
			{
				if( entity != null /* && entity.isRedstonePowered() */ && currentCrafting.getRequiredExperience() == 0 && hasItemRequirements() )
					craft( null );
			}
			else
				progress++;
	}

	/** Called whenever the crafting input changes. */
	public void inputChanged()
	{
		progress = 0;
		currentCrafting = BetterStorageCrafting.findMatchingStationCrafting( crafting );
		if( currentCrafting == null )
			currentCrafting = VanillaStationCrafting.findVanillaRecipe( this );
		updateGhostOutput();
	}

	public void updateGhostOutput()
	{
		if( outputIsReal )
			return;
		if( currentCrafting != null )
		{
			final ItemStack[] craftingOutput = currentCrafting.getOutput();
			for( int i = 0; i < output.getSlots(); i++ )
				output.setStackInSlot( i, i < craftingOutput.length ? ItemStack.copyItemStack( craftingOutput[i] ) : null );
		}
		// else Arrays.fill( output, null );
	}

	/**
	 * Called when an item is removed from the output slot while it doesn't
	 * store any real items. Returns if the recipe can be crafted again.
	 */
	private boolean craft( EntityPlayer player, boolean simulate )
	{
		/*
		 * final ItemStack[] contents = simulate ? this.contents.clone() : this.contents;
		 * final ItemStack[] crafting = simulate ? this.crafting.clone() : this.crafting;
		 * if( simulate )
		 * for( int i = 0; i < crafting.length; i++ )
		 * crafting[i] = ItemStack.copyItemStack( crafting[i] );
		 *
		 * if( currentCrafting instanceof VanillaStationCrafting )
		 * {
		 * boolean unset = false;
		 * if( player == null )
		 * {
		 * player = FakePlayer.get( entity );
		 * unset = true;
		 * }
		 *
		 * final ItemStack craftOutput = simulate ? output[4].copy() : output[4];
		 * final IInventory craftMatrix = new InventoryStacks( crafting );
		 * FMLCommonHandler.instance().firePlayerCraftingEvent( player, craftOutput, craftMatrix );
		 * new CustomSlotCrafting( player, craftOutput );
		 *
		 * if( unset )
		 * {
		 * FakePlayer.unset();
		 * player = null;
		 * }
		 * }
		 *
		 * final ICraftingSource source = new CraftingSourceTileEntity( entity, player );
		 * currentCrafting.craft( source );
		 *
		 * final IRecipeInput[] requiredInput = currentCrafting.getCraftRequirements();
		 * for( int i = 0; i < crafting.length; i++ )
		 * if( crafting[i] != null )
		 * crafting[i] = craftSlot( crafting[i], requiredInput[i], player, simulate );
		 *
		 * final boolean pulled = pullRequired( contents, crafting, requiredInput );
		 *
		 * if( !simulate )
		 * {
		 * final int requiredExperience = currentCrafting.getRequiredExperience();
		 * if( requiredExperience != 0 && player != null && !player.capabilities.isCreativeMode )
		 * player.experienceLevel -= requiredExperience;
		 *
		 * outputIsReal = !outputEmpty();
		 * progress = 0;
		 * inputChanged();
		 * checkHasRequirements = true;
		 * }
		 *
		 * return pulled;
		 */
		return false;
	}

	public void craft( EntityPlayer player )
	{
		craft( player, false );
	}

	public boolean simulateCraft()
	{
		final boolean canCraftAgain = craft( FakePlayer.get( entity ), true );
		FakePlayer.unset();
		return canCraftAgain;
	}

	private static class CustomSlotCrafting extends SlotCrafting
	{
		public CustomSlotCrafting( EntityPlayer player, ItemStack stack )
		{
			super( player, null, null, 0, 0, 0 );
			onCrafting( stack );
		}
	}

	private ItemStack craftSlot( ItemStack stack, IRecipeInput requiredInput, EntityPlayer player, boolean simulate )
	{
		if( simulate )
			stack = stack.copy();
		final ContainerInfo containerInfo = new ContainerInfo();
		requiredInput.craft( stack, containerInfo );
		ItemStack containerItem = ItemStack.copyItemStack( containerInfo.getContainerItem() );

		boolean removeStack = false;
		if( stack.stackSize <= 0 )
			// Item stack is depleted.
			removeStack = true;
		else
			if( stack.getItem().isDamageable() && stack.getItemDamage() > stack.getMaxDamage() )
			{
				// Item stack is destroyed.
				removeStack = true;
				if( player != null )
					MinecraftForge.EVENT_BUS.post( new PlayerDestroyItemEvent( player, stack, EnumHand.MAIN_HAND ) );
			}

		// If the stack has been depleted, set it
		// to either null, or the container item.
		if( removeStack )
			if( !containerInfo.doesLeaveCrafting() )
			{
				stack = containerItem;
				containerItem = null;
			}
			else
				stack = null;

		/*
		 * if( containerItem != null && !simulate )
		 * // Try to add the container item to the internal storage, or spawn the item in the world.
		 * if( !InventoryUtils.tryAddItemToInventory( containerItem, new InventoryStacks( contents ), true ) && entity != null )
		 * WorldUtils.spawnItem( entity.getWorld(), entity.getPos().getX() + 0.5, entity.getPos().getY() + 0.5, entity.getPos().getZ() + 0.5,
		 * containerItem );
		 */
		return stack;
	}

	/** Pull items required for the recipe from the internal inventory. Returns if successful. */
	public boolean pullRequired( ItemStack[] contents, ItemStack[] crafting, IRecipeInput[] requiredInput )
	{
		boolean success = true;
		craftingLoop:
		for( int i = 0; i < crafting.length; i++ )
		{
			ItemStack stack = crafting[i];
			final IRecipeInput required = requiredInput[i];
			if( required != null )
			{
				int currentAmount = 0;
				if( stack != null )
				{
					if( !required.matches( stack ) )
						return false;
					currentAmount = stack.stackSize;
				}
				int requiredAmount = required.getAmount() - currentAmount;
				if( requiredAmount <= 0 )
					continue;
				for( int j = 0; j < contents.length; j++ )
				{
					final ItemStack contentsStack = contents[j];
					if( contentsStack == null )
						continue;
					if( stack == null ? required.matches( contentsStack ) : StackUtils.matches( stack, contentsStack ) )
					{
						final int amount = Math.min( contentsStack.stackSize, requiredAmount );
						crafting[i] = stack = StackUtils.copyStack( contentsStack, currentAmount += amount );
						contents[j] = StackUtils.copyStack( contentsStack, contentsStack.stackSize - amount );
						if( ( requiredAmount -= amount ) <= 0 )
							continue craftingLoop;
					}
				}
			}
			else
				if( stack == null )
					continue;
			success = false;
		}
		return success;
	}

	/** Returns if items can be taken out of the output slots. */
	public boolean canTake( EntityPlayer player )
	{
		return outputIsReal || player != null && currentCrafting != null && currentCrafting.canCraft( new CraftingSourceTileEntity( entity, player ) )
				&& progress >= currentCrafting.getCraftingTime() && hasRequiredExperience( player );
	}

	private boolean hasRequiredExperience( EntityPlayer player )
	{
		final int requiredExperience = currentCrafting.getRequiredExperience();
		return requiredExperience == 0 || player != null && ( player.capabilities.isCreativeMode || player.experienceLevel >= requiredExperience );
	}

	/**
	 * Returns if the crafting station has the items
	 * required in its inventory to craft the recipe again.
	 */
	public boolean hasItemRequirements()
	{
		if( checkHasRequirements )
		{
			hasRequirements = simulateCraft();
			checkHasRequirements = false;
		}
		return hasRequirements;
	}

	// IInventory implementation

	/*
	 * @Override
	 * public int getSizeInventory()
	 * {
	 * return crafting.length + output.length + contents.getSlots();
	 * }
	 */

	@Override
	public ItemStack getStackInSlot( int slot )
	{
		if( slot < crafting.getSlots() )
			return crafting.getStackInSlot( slot );
		else
			if( slot < crafting.getSlots() + output.getSlots() )
				return output.getStackInSlot( slot - crafting.getSlots() );
			else
				return inventory.getStackInSlot( slot - ( crafting.getSlots() + output.getSlots() ) );
	}

	/*
	 * @Override
	 * public void setInventorySlotContents( int slot, ItemStack stack )
	 * {
	 * if( slot < crafting.length )
	 * crafting[slot] = stack;
	 * else
	 * if( slot < crafting.length + output.length )
	 * output[slot - crafting.length] = stack;
	 * //else contents[slot - ( crafting.length + output.length )] = stack;
	 * markDirty();
	 * }
	 */

	/*
	 * @Override
	 * public boolean isUseableByPlayer( EntityPlayer player )
	 * {
	 * return true;
	 * }
	 */

	/*
	 * @Override
	 * public void openInventory( EntityPlayer player )
	 * {}
	 */

	/*
	 * @Override
	 * public void closeInventory( EntityPlayer player )
	 * {}
	 */

	/*
	 * @Override
	 * public void markDirty()
	 * {
	 * if( entity != null )
	 * entity.markDirtySuper();
	 * if( outputEmpty() )
	 * {
	 * outputIsReal = false;
	 * updateGhostOutput();
	 * }
	 * checkHasRequirements = true;
	 * }
	 */

	// Utility functions

	/*
	 * private boolean outputEmpty()
	 * {
	 * for( final ItemStack element : output )
	 * if( element != null )
	 * return false;
	 * return true;
	 * }
	 */

	/*
	 * @Override
	 * public ItemStack removeStackFromSlot( int index )
	 * {
	 * // TODO Auto-generated method stub
	 * return null;
	 * }
	 */

	/*
	 * @Override
	 * public int getField( int id )
	 * {
	 * // TODO Auto-generated method stub
	 * return 0;
	 * }
	 */

	/*
	 * @Override
	 * public void setField( int id, int value )
	 * {
	 * // TODO Auto-generated method stub
	 *
	 * }
	 */

	/*
	 * @Override
	 * public int getFieldCount()
	 * {
	 * // TODO Auto-generated method stub
	 * return 0;
	 * }
	 */

	/*
	 * @Override
	 * public void clear()
	 * {
	 * // TODO Auto-generated method stub
	 *
	 * }
	 */

	/*
	 * @Override
	 * public ITextComponent getDisplayName()
	 * {
	 * // TODO Auto-generated method stub
	 * return null;
	 * }
	 */

	/*
	 * @Override
	 * public String getName()
	 * {
	 * // TODO Auto-generated method stub
	 * return null;
	 * }
	 */

	/*
	 * @Override
	 * public boolean hasCustomName()
	 * {
	 * // TODO Auto-generated method stub
	 * return false;
	 * }
	 */

	/*
	 * @Override
	 * public ItemStack decrStackSize( int index, int count )
	 * {
	 * // TODO Auto-generated method stub
	 * return null;
	 * }
	 */

	/*
	 * @Override
	 * public int getInventoryStackLimit()
	 * {
	 * // TODO Auto-generated method stub
	 * return 0;
	 * }
	 */

	/*
	 * @Override
	 * public boolean isItemValidForSlot( int index, ItemStack stack )
	 * {
	 * // TODO Auto-generated method stub
	 * return false;
	 * }
	 */
}
