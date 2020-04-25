package io.github.tehstoneman.betterstorage.common.tileentity;

import java.util.logging.Logger;

import io.github.tehstoneman.betterstorage.ModInfo;
import io.github.tehstoneman.betterstorage.api.crafting.BetterStorageCrafting;
import io.github.tehstoneman.betterstorage.api.crafting.ContainerInfo;
import io.github.tehstoneman.betterstorage.api.crafting.CraftingSourceTileEntity;
import io.github.tehstoneman.betterstorage.api.crafting.ICraftingSource;
import io.github.tehstoneman.betterstorage.api.crafting.IRecipeInput;
import io.github.tehstoneman.betterstorage.api.crafting.StationCrafting;
import io.github.tehstoneman.betterstorage.client.gui.BetterStorageGUIHandler.EnumGui;
import io.github.tehstoneman.betterstorage.common.inventory.OutputStackHandler;
import io.github.tehstoneman.betterstorage.config.GlobalConfig;
import io.github.tehstoneman.betterstorage.inventory.InventoryStacks;
import io.github.tehstoneman.betterstorage.item.recipe.VanillaStationCrafting;
import io.github.tehstoneman.betterstorage.misc.FakePlayer;
import io.github.tehstoneman.betterstorage.utils.InventoryUtils;
import io.github.tehstoneman.betterstorage.utils.StackUtils;
import io.github.tehstoneman.betterstorage.utils.WorldUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCraftingStation extends TileEntity implements ITickable, IInventory
{
	public ItemStack[]			crafting	= new ItemStack[9];
	public OutputStackHandler	output;
	public ItemStackHandler		inventory;

	public StationCrafting		currentCrafting;
	public int					progress, maxProgress;
	public boolean				outputIsReal;
	private boolean				checkHasRequirements;

	public TileEntityCraftingStation()
	{
		output = new OutputStackHandler( 9 )
		{
			@Override
			protected void onContentsChanged( int slot )
			{
				TileEntityCraftingStation.this.markDirty();
			}
		};
		inventory = new ItemStackHandler( 18 )
		{
			@Override
			protected void onContentsChanged( int slot )
			{
				TileEntityCraftingStation.this.markDirty();
			}
		};
	}

	@Override
	public boolean hasCapability( Capability< ? > capability, EnumFacing facing )
	{
		if( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && GlobalConfig.enableStationAutoCraftingSetting.getValue() )
			if( facing != EnumFacing.DOWN || currentCrafting != null )
				return true;
		return super.hasCapability( capability, facing );
	}

	@Override
	public <T> T getCapability( Capability< T > capability, EnumFacing facing )
	{
		if( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && GlobalConfig.enableStationAutoCraftingSetting.getValue() )
			if( facing == EnumFacing.DOWN )
			{
				if( currentCrafting == null )
					return (T)output;
			}
			else
				return (T)inventory;
		return super.getCapability( capability, facing );
	}

	// @Override
	@Override
	public String getName()
	{
		return ModInfo.containerCraftingStation;
	}

	public boolean onBlockActivated( EntityPlayer player, int side, float hitX, float hitY, float hitZ )
	{
		if( worldObj.isRemote )
			return true;
		// if( !canPlayerUseContainer( player ) ) return true;
		player.openGui( ModInfo.modId, EnumGui.CRAFTING.getGuiID(), worldObj, pos.getX(), pos.getY(), pos.getZ() );
		return true;
	}

	// IInventory implementation

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString( getName() );
	}

	@Override
	public void update()
	{
		if( GlobalConfig.enableStationAutoCraftingSetting.getValue() )
			if( currentCrafting != null && worldObj.isBlockPowered( pos ) )
			{
				// if( progress >= Math.max( currentCrafting.getCraftingTime(), GlobalConfig.stationAutocraftDelaySetting.getValue() ) )
				maxProgress = GlobalConfig.stationAutocraftDelaySetting.getValue();
				if( progress >= maxProgress )
				{
					// if( entity != null /* && entity.isRedstonePowered() */ && currentCrafting.getRequiredExperience() == 0 && hasItemRequirements() )
					output.setStackInSlot( 4, currentCrafting.getOutput()[4] );
					currentCrafting = null;
					progress = 0;
				}
				else
					progress++;
			}
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	public void onCraftMatrixChanged()
	{
		currentCrafting = BetterStorageCrafting.findMatchingStationCrafting( crafting );
		if( currentCrafting == null )
			currentCrafting = VanillaStationCrafting.findVanillaRecipe( crafting, worldObj );

		progress = 0;

		if( currentCrafting != null )
		{
			final ItemStack[] craftingOutput = currentCrafting.getOutput();
			for( int i = 0; i < output.getSlots(); i++ )
				output.setStackInSlot( i, i < craftingOutput.length ? ItemStack.copyItemStack( craftingOutput[i] ) : null );
		}
		else
			for( int i = 0; i < output.getSlots(); i++ )
				output.setStackInSlot( i, null );
	}

	// Reading from / writing to NBT

	@Override
	public NBTTagCompound writeToNBT( NBTTagCompound compound )
	{
		super.writeToNBT( compound );

		final NBTTagList nbttaglist = new NBTTagList();
		for( int i = 0; i < crafting.length; ++i )
			if( crafting[i] != null )
			{
				final NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte( "Slot", (byte)i );
				crafting[i].writeToNBT( nbttagcompound );
				nbttaglist.appendTag( nbttagcompound );
			}
		compound.setTag( "Crafting", nbttaglist );

		compound.setTag( "Output", output.serializeNBT() );
		compound.setTag( "Inventory", inventory.serializeNBT() );

		compound.setInteger( "Progress", progress );
		// if( currentCrafting != null ) compound.setTag( "Current", currentCrafting.serializeNBT() );

		return compound;
	}

	@Override
	public void readFromNBT( NBTTagCompound compound )
	{
		super.readFromNBT( compound );

		final NBTTagList nbttaglist = compound.getTagList( "Crafting", 10 );
		crafting = new ItemStack[getSizeInventory()];

		for( int i = 0; i < nbttaglist.tagCount(); ++i )
		{
			final NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt( i );
			final int j = nbttagcompound.getByte( "Slot" );

			if( j >= 0 && j < crafting.length )
				crafting[j] = ItemStack.loadItemStackFromNBT( nbttagcompound );
		}

		output.deserializeNBT( compound.getCompoundTag( "Output" ) );
		inventory.deserializeNBT( compound.getCompoundTag( "Inventory" ) );

		progress = compound.getInteger( "Progress" );
		// if( compound.hasKey( "Current" ) ) currentCrafting.deserializeNBT( compound.getCompoundTag( "Current" ) );
	}

	public void craft( EntityPlayer player )
	{
		craft( player, false );
	}

	/**
	 * Called when an item is removed from the output slot while it doesn't
	 * store any real items. Returns if the recipe can be crafted again.
	 */
	private boolean craft( EntityPlayer player, boolean simulate )
	{
		final ItemStack[] contents = new ItemStack[inventory.getSlots()];
		final ItemStack[] result = new ItemStack[output.getSlots()];

		for( int i = 0; i < contents.length; i++ )
			contents[i] = inventory.getStackInSlot( i );

		for( int i = 0; i < result.length; i++ )
			if( simulate )
				result[i] = ItemStack.copyItemStack( output.getStackInSlot( i ) );
			else
				result[i] = output.getStackInSlot( i );

		if( currentCrafting instanceof VanillaStationCrafting )
		{
			boolean unset = false;
			if( player == null )
			{
				player = FakePlayer.get( this );
				unset = true;
			}

			final ItemStack craftOutput = simulate ? output.getStackInSlot( 4 ).copy() : output.getStackInSlot( 4 );
			final IInventory craftMatrix = new InventoryStacks( result );
			FMLCommonHandler.instance().firePlayerCraftingEvent( player, craftOutput, craftMatrix );
			new CustomSlotCrafting( player, craftOutput );

			if( unset )
			{
				FakePlayer.unset();
				player = null;
			}
		}

		final ICraftingSource source = new CraftingSourceTileEntity( this, player );
		currentCrafting.craft( source );

		final IRecipeInput[] requiredInput = currentCrafting.getCraftRequirements();
		for( int i = 0; i < crafting.length; i++ )
		{
			if( crafting[i] != null )
				result[i] = craftSlot( crafting[i], requiredInput[i], player, simulate );
		}

		final boolean pulled = pullRequired( contents, result, requiredInput );

		if( !simulate )
		{
			final int requiredExperience = currentCrafting.getRequiredExperience();
			if( requiredExperience != 0 && player != null && !player.capabilities.isCreativeMode )
				player.experienceLevel -= requiredExperience;

			outputIsReal = !outputEmpty();
			progress = 0;
			onCraftMatrixChanged();
			checkHasRequirements = true;
		}

		return pulled;
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

		if( containerItem != null && !simulate )
			// Try to add the container item to the internal storage, or spawn the item in the world.
			if( !InventoryUtils.tryAddItemToInventory( containerItem, new InventoryStacks( crafting ), true ) )
				WorldUtils.spawnItem( worldObj, getPos().getX() + 0.5,getPos().getY() + 0.5, getPos().getZ() + 0.5,
						containerItem );

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

	private boolean outputEmpty()
	{
		for( int i = 0; i < output.getSlots(); i++ )
			if( output.getStackInSlot( i ) != null )
				return false;
		return true;
	}

	private static class CustomSlotCrafting extends SlotCrafting
	{
		public CustomSlotCrafting( EntityPlayer player, ItemStack stack )
		{
			super( player, null, null, 0, 0, 0 );
			onCrafting( stack );
		}
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public int getSizeInventory()
	{
		return 9;
	}

	@Override
	public ItemStack getStackInSlot( int index )
	{
		if( index < 0 || index >= crafting.length )
			return null;
		return crafting[index];
	}

	@Override
	public ItemStack decrStackSize( int index, int count )
	{
		if( index >= 0 && index < crafting.length )
			crafting[index] = null;
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot( int index )
	{
		if( index >= 0 && index < crafting.length )
			crafting[index] = null;
		return null;
	}

	@Override
	public void setInventorySlotContents( int index, ItemStack stack )
	{
		if( index >= 0 && index < crafting.length )
			crafting[index] = stack;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer player )
	{
		return true;
	}

	@Override
	public void openInventory( EntityPlayer player )
	{}

	@Override
	public void closeInventory( EntityPlayer player )
	{}

	@Override
	public boolean isItemValidForSlot( int index, ItemStack stack )
	{
		return true;
	}

	@Override
	public int getField( int id )
	{
		return 0;
	}

	@Override
	public void setField( int id, int value )
	{}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		crafting = new ItemStack[getSizeInventory()];
	}
}
