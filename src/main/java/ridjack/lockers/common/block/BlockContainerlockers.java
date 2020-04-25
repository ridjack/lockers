package ridjack.lockers.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockContainerlockers extends Block
{
	protected BlockContainerlockers( Properties properties )
	{
		super( properties );
	}

	/*
	 * ======================
	 * TileEntity / Rendering
	 * ======================
	 */

	@Override
	public boolean hasTileEntity( BlockState state )
	{
		return true;
	}

	/*
	 * ===========
	 * Interaction
	 * ===========
	 */

	@Override
	public boolean eventReceived( BlockState state, World worldIn, BlockPos pos, int id, int param )
	{
		final TileEntity te = worldIn.getTileEntity( pos );
		return te != null ? te.receiveClientEvent( id, param ) : false;
	}
}
