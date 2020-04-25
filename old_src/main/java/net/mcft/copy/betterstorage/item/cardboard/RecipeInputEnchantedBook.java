package io.github.tehstoneman.betterstorage.item.cardboard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.tehstoneman.betterstorage.api.crafting.ContainerInfo;
import io.github.tehstoneman.betterstorage.api.crafting.IRecipeInput;
import io.github.tehstoneman.betterstorage.common.item.BetterStorageItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RecipeInputEnchantedBook implements IRecipeInput
{
	private static Set< Enchantment >				validEnchantments	= null;

	public static final RecipeInputEnchantedBook	instance			= new RecipeInputEnchantedBook();

	private RecipeInputEnchantedBook()
	{}

	@Override
	public int getAmount()
	{
		return 1;
	}

	@Override
	public boolean matches( ItemStack stack )
	{
		if( validEnchantments == null )
		{
			validEnchantments = new HashSet<>();
			final ItemStack[] items = { new ItemStack( BetterStorageItems.cardboardHelmet ), new ItemStack( BetterStorageItems.CARDBOARD_CHESTPLATE ),
					new ItemStack( BetterStorageItems.CARDBOARD_LEGGINGS ), new ItemStack( BetterStorageItems.CARDBOARD_BOOTS ),
					new ItemStack( BetterStorageItems.CARDBOARD_SWORD ), new ItemStack( BetterStorageItems.CARDBOARD_PICKAXE ),
					new ItemStack( BetterStorageItems.CARDBOARD_SHOVEL ), new ItemStack( BetterStorageItems.CARDBOARD_AXE ),
					new ItemStack( BetterStorageItems.CARDBOARD_HOE ) };
			/*
			 * for (Enchantment ench : Enchantment.enchantmentsList) {
			 * if ((ench == null) || !ench.isAllowedOnBooks()) continue;
			 * for (ItemStack item : items)
			 * if (item.getItem() != null && ench.canApply(item)) {
			 * validEnchantments.add(ench);
			 * break;
			 * }
			 * }
			 */
		}
		/*
		 * if (stack.getItem() instanceof ItemEnchantedBook)
		 * Map< Enchantment, Integer > enchants = EnchantmentHelper.getEnchantments(stack);
		 * for (Enchantment ench : validEnchantments)
		 * if (enchants.containsKey(ench.effectId))
		 * return true;
		 */
		return false;
	}

	@Override
	public void craft( ItemStack input, ContainerInfo containerInfo )
	{}

	@Override
	@SideOnly( Side.CLIENT )
	public List< ItemStack > getPossibleMatches()
	{
		return null;
	}
}