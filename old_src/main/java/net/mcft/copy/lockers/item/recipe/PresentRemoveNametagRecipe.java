package io.github.tehstoneman.lockers.item.recipe;

import java.util.Arrays;
import java.util.List;

import io.github.tehstoneman.lockers.api.crafting.ContainerInfo;
import io.github.tehstoneman.lockers.api.crafting.IRecipeInput;
import io.github.tehstoneman.lockers.api.crafting.RecipeBounds;
import io.github.tehstoneman.lockers.api.crafting.RecipeInputItemStack;
import io.github.tehstoneman.lockers.api.crafting.ShapelessStationRecipe;
import io.github.tehstoneman.lockers.api.crafting.StationCrafting;
import io.github.tehstoneman.lockers.common.block.lockersBlocks;
import io.github.tehstoneman.lockers.common.tileentity.TileEntityPresent;
import io.github.tehstoneman.lockers.utils.RandomUtils;
import io.github.tehstoneman.lockers.utils.StackUtils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PresentRemoveNametagRecipe extends ShapelessStationRecipe
{
	public PresentRemoveNametagRecipe()
	{
		super( new IRecipeInput[] { new RecipeInputPresent(), new RecipeInputShears() },
				new ItemStack[] { new ItemStack( lockersBlocks.PRESENT ), new ItemStack( Items.NAME_TAG ) } );
	}

	@Override
	public StationCrafting checkMatch( ItemStack[] input, RecipeBounds bounds )
	{
		final StationCrafting match = super.checkMatch( input, bounds );
		if( match == null )
			return null;
		final ItemStack[] output = new ItemStack[input.length];
		int shearsIndex = -1;
		String nameTag = null;
		for( int i = 0; i < input.length; i++ )
		{
			final ItemStack stack = input[i];
			if( stack == null )
				continue;
			if( stack.getItem() == Item.getItemFromBlock( lockersBlocks.PRESENT ) )
			{
				nameTag = StackUtils.get( stack, null, TileEntityPresent.TAG_NAMETAG );
				StackUtils.remove( output[i] = stack.copy(), TileEntityPresent.TAG_NAMETAG );
			}
			else
				if( stack.getItem() instanceof ItemShears )
					shearsIndex = i;
		}
		( output[shearsIndex] = new ItemStack( Items.NAME_TAG ) ).setStackDisplayName( nameTag );
		return new StationCrafting( output, match.getCraftRequirements(), 8 );
	}

	private static class RecipeInputPresent extends RecipeInputItemStack
	{
		public RecipeInputPresent()
		{
			super( new ItemStack( lockersBlocks.PRESENT ) );
		}

		@Override
		public int getAmount()
		{
			return 1;
		}

		@Override
		public boolean matches( ItemStack stack )
		{
			return super.matches( stack ) && StackUtils.has( stack, TileEntityPresent.TAG_NAMETAG );
		}

		@Override
		@SideOnly( Side.CLIENT )
		public List< ItemStack > getPossibleMatches()
		{
			final ItemStack stack = this.stack;
			StackUtils.set( stack, "(somebody)", TileEntityPresent.TAG_NAMETAG );
			return Arrays.asList( stack );
		}
	}

	private static class RecipeInputShears implements IRecipeInput
	{
		public RecipeInputShears()
		{}

		@Override
		public int getAmount()
		{
			return 0;
		}

		@Override
		public boolean matches( ItemStack stack )
		{
			return stack.getItem() instanceof ItemShears;
		}

		@Override
		public void craft( ItemStack input, ContainerInfo containerInfo )
		{
			input.attemptDamageItem( 16, RandomUtils.random );
		}

		@Override
		@SideOnly( Side.CLIENT )
		public List< ItemStack > getPossibleMatches()
		{
			return Arrays.asList( new ItemStack( Items.SHEARS ) );
		}
	}
}
