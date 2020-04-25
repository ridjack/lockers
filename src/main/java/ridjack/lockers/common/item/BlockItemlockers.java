package ridjack.lockers.common.item;

import ridjack.lockers.lockers;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class BlockItemlockers extends BlockItem
{
	public BlockItemlockers( Block blockIn )
	{
		this( blockIn, new Properties() );
	}

	public BlockItemlockers( Block blockIn, Properties properties )
	{
		super( blockIn, properties.group( lockers.ITEM_GROUP ) );
	}
}
