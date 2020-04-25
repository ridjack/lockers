package io.github.tehstoneman.betterstorage.common.block;

import io.github.tehstoneman.betterstorage.api.ConnectedType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;

public abstract class BlockConnectableContainer extends BlockContainerBetterStorage
{
	public static final EnumProperty< ConnectedType > TYPE = EnumProperty.create( "type", ConnectedType.class );

	protected BlockConnectableContainer( Block.Properties builder )
	{
		super( builder );

		//@formatter:off
		setDefaultState( stateContainer.getBaseState().with( TYPE, ConnectedType.SINGLE ) );
		//@formatter:on
	}

	@Override
	protected void fillStateContainer( StateContainer.Builder< Block, BlockState > builder )
	{
		super.fillStateContainer( builder );
		builder.add( TYPE );
	}
}
