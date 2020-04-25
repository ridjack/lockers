package io.github.tehstoneman.lockers.common.item.crafting;

import io.github.tehstoneman.lockers.common.block.lockersBlocks;
import io.github.tehstoneman.lockers.common.tileentity.TileEntityPresent;
import io.github.tehstoneman.lockers.utils.StackUtils;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PresentRecipe extends ShapedRecipes
{

	public PresentRecipe()
	{
		super( 3, 3, createRecipeInput(), new ItemStack( lockersBlocks.PRESENT ) );
	}

	private static ItemStack[] createRecipeInput()
	{
		final ItemStack box = new ItemStack( lockersBlocks.CARDBOARD_BOX );
		final ItemStack wool1 = new ItemStack( Blocks.WOOL, 1, 14 );
		final ItemStack wool2 = new ItemStack( Blocks.WOOL, 1, 0 );
		return new ItemStack[] { wool1, wool2, wool1, wool1, box, wool1, wool1, wool2, wool1 };
	}

	@Override
	public boolean matches( InventoryCrafting crafting, World world )
	{
		final ItemStack box = crafting.getStackInSlot( 4 );
		if( box == null || box.getItem() != Item.getItemFromBlock( lockersBlocks.CARDBOARD_BOX ) || !StackUtils.has( box, "Items" ) )
			return false;

		final int corners = checkWoolColor( crafting, 0, 2, 6, 8 );
		if( corners >= 16 )
			return false;
		final int topBottom = checkWoolColor( crafting, 1, 7 );
		final int leftRight = checkWoolColor( crafting, 3, 5 );

		if( corners < 0 || topBottom < 0 || leftRight < 0 || corners == topBottom || leftRight != corners && leftRight != topBottom )
			return false;
		return true;
	}

	@Override
	public ItemStack getCraftingResult( InventoryCrafting crafting )
	{
		final int colorInner = checkWoolColor( crafting, 0 );
		final int colorOuter = checkWoolColor( crafting, 1 );
		final int leftRight = checkWoolColor( crafting, 3 );
		final boolean skojanzaMode = leftRight == colorOuter;
		final ItemStack box = crafting.getStackInSlot( 4 );

		final ItemStack present = new ItemStack( lockersBlocks.PRESENT );
		final NBTTagCompound compound = box.getTagCompound().copy();
		compound.setByte( TileEntityPresent.TAG_COLOR_INNER, (byte)colorInner );
		compound.setByte( TileEntityPresent.TAG_COLOR_OUTER, (byte)colorOuter );
		compound.setBoolean( TileEntityPresent.TAG_SKOJANZA_MODE, skojanzaMode );
		final int color = StackUtils.get( box, -1, "display", "color" );
		if( color >= 0 )
			compound.setInteger( "color", color );
		present.setTagCompound( compound );
		StackUtils.remove( present, "display", "color" );
		return present;
	}

	private static int checkWoolColor( InventoryCrafting crafting, int... slots )
	{
		int color = -1;
		for( int i = 0; i < slots.length; i++ )
		{
			final int slot = slots[i];
			final ItemStack stack = crafting.getStackInSlot( slot );
			int woolColor;
			if( stack == null )
				return -1;
			else
				if( stack.getItem() == Item.getItemFromBlock( Blocks.WOOL ) )
					woolColor = stack.getItemDamage();
				else
					if( stack.getItem() == Item.getItemFromBlock( Blocks.GOLD_BLOCK ) )
						woolColor = 16;
					else
						return -1;
			if( i <= 0 )
				color = woolColor;
			else
				if( woolColor != color )
					return -1;
		}
		return color;
	}

}
