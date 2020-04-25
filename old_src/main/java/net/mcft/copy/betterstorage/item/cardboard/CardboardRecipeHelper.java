package io.github.tehstoneman.betterstorage.item.cardboard;

import io.github.tehstoneman.betterstorage.utils.StackUtils.StackEnchantment;

public final class CardboardRecipeHelper
{
	private CardboardRecipeHelper()
	{}

	/**
	 * Returns additional costs for an enchantment to be put
	 * onto the cardboard item, like silk touch and fortune.
	 */
	public static int getAdditionalEnchantmentCost( StackEnchantment ench )
	{
		/*
		 * if (ench.ench == Enchantment.silkTouch) return 10;
		 * else if (ench.ench == Enchantment.fortune) return 1 + ench.getLevel();
		 * else if (ench.ench == Enchantment.fireAspect) return 2;
		 * else if (ench.ench == Enchantment.thorns) return 1;
		 * else
		 */ return 0;
	}
}
