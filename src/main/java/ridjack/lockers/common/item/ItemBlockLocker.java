package ridjack.lockers.common.item;

import ridjack.lockers.common.block.lockersBlocks;
import net.minecraft.block.Block;

public class ItemBlockLocker extends BlockItemlockers
{
	public ItemBlockLocker()
	{
		super( lockersBlocks.LOCKER.get() );
	}

	public ItemBlockLocker( Block block )
	{
		super( block );
	}
}
