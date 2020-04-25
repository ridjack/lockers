package ridjack.lockers.common.item;

import ridjack.lockers.common.block.lockersBlocks;
//import io.github.tehstoneman.lockers.common.enchantment.EnchantmentKey;
//import io.github.tehstoneman.lockers.common.enchantment.EnchantmentLock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

public class lockersItemGroup extends ItemGroup
{
	public lockersItemGroup()
	{
		super( "better_storage_too" );
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack((IItemProvider) lockersBlocks.LOCKER.get());
	}
}
