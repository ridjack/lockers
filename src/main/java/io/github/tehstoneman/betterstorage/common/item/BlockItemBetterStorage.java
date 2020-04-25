package io.github.tehstoneman.betterstorage.common.item;

import io.github.tehstoneman.betterstorage.BetterStorage;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBetterStorage extends BlockItem
{
	public BlockItemBetterStorage( Block blockIn )
	{
		this( blockIn, new Properties() );
	}

	public BlockItemBetterStorage( Block blockIn, Properties properties )
	{
		super( blockIn, properties.group( BetterStorage.ITEM_GROUP ) );
	}
}
