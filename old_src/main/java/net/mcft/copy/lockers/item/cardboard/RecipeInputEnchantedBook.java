package io.github.tehstoneman.lockers.item.cardboard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.tehstoneman.lockers.api.crafting.ContainerInfo;
import io.github.tehstoneman.lockers.api.crafting.IRecipeInput;
import io.github.tehstoneman.lockers.common.item.lockersItems;
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
			final ItemStack[] items = { new ItemStack( lockersItems.cardboardHelmet ), new ItemStack( lockersItems.CARDBOARD_CHESTPLATE ),
					new ItemStack( lockersItems.CARDBOARD_LEGGINGS ), new ItemStack( lockersItems.CARDBOARD_BOOTS ),
					new ItemStack( lockersItems.CARDBOARD_SWORD ), new ItemStack( lockersItems.CARDBOARD_PICKAXE ),
					new ItemStack( lockersItems.CARDBOARD_SHOVEL ), new ItemStack( lockersItems.CARDBOARD_AXE ),
					new ItemStack( lockersItems.CARDBOARD_HOE ) };
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