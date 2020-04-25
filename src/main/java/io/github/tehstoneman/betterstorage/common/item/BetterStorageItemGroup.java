package io.github.tehstoneman.betterstorage.common.item;

import io.github.tehstoneman.betterstorage.common.block.BetterStorageBlocks;
//import io.github.tehstoneman.betterstorage.common.enchantment.EnchantmentKey;
//import io.github.tehstoneman.betterstorage.common.enchantment.EnchantmentLock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

public class BetterStorageItemGroup extends ItemGroup
{
	public BetterStorageItemGroup()
	{
		super( "better_storage_too" );
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack((IItemProvider) BetterStorageBlocks.LOCKER.get());
	}
}
