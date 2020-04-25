package io.github.tehstoneman.betterstorage.common.item;

import io.github.tehstoneman.betterstorage.common.block.BetterStorageBlocks;
import net.minecraft.block.Block;

public class ItemBlockLocker extends BlockItemBetterStorage
{
	public ItemBlockLocker()
	{
		super( BetterStorageBlocks.LOCKER.get() );
	}

	public ItemBlockLocker( Block block )
	{
		super( block );
	}
}
