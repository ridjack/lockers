package ridjack.lockers.common.block;

import javax.annotation.Nullable;

import ridjack.lockers.api.ConnectedType;
import ridjack.lockers.common.tileentity.TileEntityContainer;
import ridjack.lockers.common.tileentity.TileEntityLocker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockLocker extends BlockConnectableContainer implements IWaterLoggable
{
	public static final EnumProperty< DoorHingeSide >	HINGE		= BlockStateProperties.DOOR_HINGE;
	public static final BooleanProperty					WATERLOGGED	= BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape					SHAPE_NORTH	= makeCuboidShape( 0.0D, 0.0D, 1.0D, 16.0D, 16.0D, 16.0D );
	protected static final VoxelShape					SHAPE_SOUTH	= makeCuboidShape( 0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 15.0D );
	protected static final VoxelShape					SHAPE_WEST	= makeCuboidShape( 1.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D );
	protected static final VoxelShape					SHAPE_EAST	= makeCuboidShape( 0.0D, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D );
	public static final DirectionProperty				FACING		= BlockStateProperties.HORIZONTAL_FACING;

	public BlockLocker()
	{
		this( Block.Properties.create( Material.WOOD ).hardnessAndResistance( 2.5F ).sound( SoundType.WOOD ) );
	}

	public BlockLocker( Properties properties )
	{
		super( properties );

		//@formatter:off
		setDefaultState( stateContainer.getBaseState().with( FACING, Direction.NORTH )
													  .with( HINGE, DoorHingeSide.LEFT )
		  											  .with( WATERLOGGED, Boolean.valueOf( false ) ) );
		//@formatter:on
	}

	@Override
	protected void fillStateContainer( StateContainer.Builder< Block, BlockState > builder )
	{
		super.fillStateContainer( builder );
		builder.add( FACING, HINGE, WATERLOGGED );
	}

	/*
	 * ======================
	 * TileEntity / Rendering
	 * ======================
	 */

	@Override
	public BlockRenderType getRenderType( BlockState state )
	{
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public TileEntity createTileEntity( BlockState state, IBlockReader world )
	{
		return new TileEntityLocker();
	}

	/*@Override
	public boolean hasCustomBreakingProgress( BlockState state )
	{
		return true;
	}*/

	@Override
	public VoxelShape getShape( BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context )
	{
		switch( state.get( FACING ) )
		{
		case NORTH:
		default:
			return SHAPE_NORTH;
		case SOUTH:
			return SHAPE_SOUTH;
		case WEST:
			return SHAPE_WEST;
		case EAST:
			return SHAPE_EAST;
		}
	}

	/*
	 * =========
	 * Placement
	 * =========
	 */

	@Override
	public BlockState getStateForPlacement( BlockItemUseContext context )
	{
		ConnectedType connectedType = ConnectedType.SINGLE;
		final Direction direction = context.getPlacementHorizontalFacing().getOpposite();
		final IFluidState fluidState = context.getWorld().getFluidState( context.getPos() );
		final boolean sneaking = context.func_225518_g_();
		DoorHingeSide hingeSide = getHingeSide( context );
		if( connectedType == ConnectedType.SINGLE && !sneaking )
			if( direction == getDirectionToAttach( context, Direction.DOWN ) )
			{
				connectedType = ConnectedType.SLAVE;
				hingeSide = context.getWorld().getBlockState( context.getPos().offset( Direction.DOWN ) ).get( HINGE );
			}
			else if( direction == getDirectionToAttach( context, Direction.UP ) )
			{
				connectedType = ConnectedType.MASTER;
				hingeSide = context.getWorld().getBlockState( context.getPos().offset( Direction.UP ) ).get( HINGE );
			}

		return getDefaultState().with( FACING, direction ).with( HINGE, hingeSide ).with( TYPE, connectedType ).with( WATERLOGGED,
				Boolean.valueOf( fluidState.getFluid() == Fluids.WATER ) );
	}

	@Override
	public BlockState updatePostPlacement( BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos,
			BlockPos facingPos )
	{
		if( stateIn.get( WATERLOGGED ) )
			worldIn.getPendingFluidTicks().scheduleTick( currentPos, Fluids.WATER, Fluids.WATER.getTickRate( worldIn ) );

		if( facingState.getBlock() == this && facing.getAxis().isVertical() )
		{
			final ConnectedType lockerType = facingState.get( TYPE );

			if( stateIn.get( TYPE ) == ConnectedType.SINGLE && lockerType != ConnectedType.SINGLE
					&& stateIn.get( FACING ) == facingState.get( FACING ) && getDirectionToAttached( facingState ) == facing.getOpposite() )
				return stateIn.with( TYPE, lockerType.opposite() );
		}
		else if( getDirectionToAttached( stateIn ) == facing )
			return stateIn.with( TYPE, ConnectedType.SINGLE );

		return super.updatePostPlacement( stateIn, facing, facingState, worldIn, currentPos, facingPos );
	}

	private DoorHingeSide getHingeSide( BlockItemUseContext context )
	{
		final BlockPos blockPos = context.getPos();
		final Direction direction = context.getPlacementHorizontalFacing();
		final int offX = direction.getXOffset();
		final int offY = direction.getZOffset();
		final Vec3d v = context.getHitVec();
		final double hitX = v.x - blockPos.getX();
		final double hitY = v.z - blockPos.getZ();
		return ( offX >= 0 || !( hitY < 0.5D ) ) && ( offX <= 0 || !( hitY > 0.5D ) ) && ( offY >= 0 || !( hitX > 0.5D ) )
				&& ( offY <= 0 || !( hitX < 0.5D ) ) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
	}

	@Nullable
	private Direction getDirectionToAttach( BlockItemUseContext context, Direction facing )
	{
		final BlockState blockState = context.getWorld().getBlockState( context.getPos() );
		final BlockState faceState = context.getWorld().getBlockState( context.getPos().offset( facing ) );
		return faceState.getBlock() == this && faceState.get( TYPE ) == ConnectedType.SINGLE ? faceState.get( FACING ) : null;
	}

	public static Direction getDirectionToAttached( BlockState state )
	{
		return state.get( TYPE ) == ConnectedType.SLAVE ? Direction.DOWN : Direction.UP;
	}

	@Override
	public void onBlockPlacedBy( World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack )
	{
		if( stack.hasDisplayName() )
		{
			final TileEntity tileentity = worldIn.getTileEntity( pos );
			if( tileentity instanceof TileEntityContainer)
				( (TileEntityContainer)tileentity ).setCustomName( stack.getDisplayName() );
		}
	}

	@Override
	public void onReplaced( BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving )
	{
		if( state.getBlock() != newState.getBlock() )
		{
			final TileEntity tileentity = worldIn.getTileEntity( pos );
			if( tileentity instanceof TileEntityContainer )
			{
				( (TileEntityContainer)tileentity ).dropInventoryItems();
				worldIn.updateComparatorOutputLevel( pos, this );
			}

			super.onReplaced( state, worldIn, pos, newState, isMoving );
		}
	}

	/*
	 * ===========
	 * Adjustments
	 * ===========
	 */

	@Override
	public BlockState rotate( BlockState state, Rotation rot )
	{
		return state.with( FACING, rot.rotate( state.get( FACING ) ) );
	}

	@Override
	public BlockState mirror( BlockState state, Mirror mirrorIn )
	{
		return state.rotate( mirrorIn.toRotation( state.get( FACING ) ) );
	}

	@Override
	public boolean hasComparatorInputOverride( BlockState state )
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride( BlockState blockState, World worldIn, BlockPos pos )
	{
		return calcRedstoneFromInventory(
				( (TileEntityLocker)worldIn.getTileEntity( pos ) ).getCapability( CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ) );
	}

	public static int calcRedstoneFromInventory( @Nullable LazyOptional< IItemHandler > lazyOptional )
	{
		if( !lazyOptional.isPresent() )
			return 0;
		else
		{
			final IItemHandler inventory = lazyOptional.orElseGet( null );
			int i = 0;
			float f = 0.0F;

			for( int j = 0; j < inventory.getSlots(); ++j )
			{
				final ItemStack itemstack = inventory.getStackInSlot( j );
				if( !itemstack.isEmpty() )
				{
					f += (float)itemstack.getCount() / itemstack.getMaxStackSize();
					++i;
				}
			}

			f = f / inventory.getSlots();
			return MathHelper.floor( f * 14.0F ) + ( i > 0 ? 1 : 0 );
		}
	}

	/*
	 * ===========
	 * Interaction
	 * ===========
	 */

	@Override
	public ActionResultType onBlockActivated( BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit )
	{
		if( hit.getFace() == state.get( FACING ) )
			if( worldIn.isRemote )
				return ActionResultType.SUCCESS;
			else
			{
				final INamedContainerProvider locker = getContainer( state, worldIn, pos );
				if( locker != null )
				{
					NetworkHooks.openGui( (ServerPlayerEntity)player, locker, pos );
					player.addStat( getOpenStat() );
				}
				return ActionResultType.SUCCESS;
			}
		return ActionResultType.PASS;
	}

	protected Stat< ResourceLocation > getOpenStat()
	{
		return Stats.CUSTOM.get( Stats.OPEN_CHEST );
	}

	@Override
	@Nullable
	public INamedContainerProvider getContainer( BlockState state, World worldIn, BlockPos pos )
	{
		return this.getContainer( state, worldIn, pos, false );
	}

	public INamedContainerProvider getContainer( BlockState state, World worldIn, BlockPos pos, boolean allowBlockedChest )
	{
		final TileEntity tileentity = worldIn.getTileEntity( pos );
		if( !( tileentity instanceof TileEntityLocker ) )
			return null;
		else if( !allowBlockedChest && isBlocked( worldIn, pos ) )
			return null;
		else
		{
			final TileEntityLocker locker = (TileEntityLocker)tileentity;
			final ConnectedType chesttype = state.get( TYPE );
			if( chesttype == ConnectedType.SINGLE )
				return locker;
			else
			{
				final BlockPos blockpos = pos.offset( getDirectionToAttached( state ) );
				final BlockState iblockstate = worldIn.getBlockState( blockpos );
				if( iblockstate.getBlock() == this )
				{
					final ConnectedType chesttype1 = iblockstate.get( TYPE );
					if( chesttype1 != ConnectedType.SINGLE && chesttype != chesttype1 && iblockstate.get( FACING ) == state.get( FACING ) )
						if( !allowBlockedChest && isBlocked( worldIn, blockpos ) )
							return null;
				}

				return locker;
			}
		}
	}

	private static boolean isBlocked( IWorld world, BlockPos pos )
	{
		return isBehindSolidBlock( world, pos );
	}

	private static boolean isBehindSolidBlock( IBlockReader reader, BlockPos worldIn )
	{
		final Direction facing = reader.getBlockState( worldIn ).get( FACING );
		final BlockPos blockpos = worldIn.offset( facing );
		return reader.getBlockState( blockpos ).isNormalCube( reader, blockpos );
	}

	/*
	 * =====
	 * Fluid
	 * =====
	 */

	@Override
	public IFluidState getFluidState( BlockState state )
	{
		return state.get( WATERLOGGED ) ? Fluids.WATER.getStillFluidState( false ) : super.getFluidState( state );
	}
}
