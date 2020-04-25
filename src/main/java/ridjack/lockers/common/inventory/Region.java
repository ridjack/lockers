package ridjack.lockers.common.inventory;

import com.google.common.base.MoreObjects;

import ridjack.lockers.util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Region
{
	public BlockPos				posMin;
	public BlockPos				posMax;
	public final static Region	EMPTY	= new Region( BlockPos.ZERO );

	public Region( BlockPos posMin, BlockPos posMax )
	{
		this.posMin = posMin;
		this.posMax = posMax;
		// set( posMin, posMax );
	}

	public Region( BlockPos pos )
	{
		this( pos, pos );
	}

	public Region( TileEntity entity )
	{
		this( entity.getPos() );
	}

	/**
	 * Get the width (x-direction) of this region
	 *
	 * @return The width
	 */
	public int width()
	{
		return isEmpty() ? 0 : posMax.getX() - posMin.getX() + 1;
	}

	/**
	 * Get the depth (z-direction) of this region
	 *
	 * @return The depth
	 */
	public int depth()
	{
		return isEmpty() ? 0 : posMax.getZ() - posMin.getZ() + 1;
	}

	/**
	 * Get the height (y-direction) of this region
	 *
	 * @return The height
	 */
	public int height()
	{
		return isEmpty() ? 0 : posMax.getY() - posMin.getY() + 1;
	}

	/**
	 * Get the volume of this region
	 *
	 * @return The volume
	 */
	public int volume()
	{
		return width() * depth() * height();
	}

	/**
	 * Sets the area of this region<p>
	 * Automatically adjusts min and max values
	 *
	 * @param posMin
	 *            A {@link BlockPos} that represents minimum corner
	 * @param posMax
	 *            A {@link BlockPos} that represents maximum corner
	 */
	public void set( BlockPos posMin, BlockPos posMax )
	{
		//@formatter:off
		this.posMin = new BlockPos( Math.min( posMin.getX(), posMax.getX() ),
									Math.min( posMin.getY(), posMax.getY() ),
									Math.min( posMin.getZ(), posMax.getZ() ) );
		this.posMax = new BlockPos( Math.max( posMin.getX(), posMax.getX() ),
									Math.max( posMin.getY(), posMax.getY() ),
									Math.max( posMin.getZ(), posMax.getZ() ) );
		//@formatter:on
	}

	/**
	 * Expand the region by the given size
	 *
	 * @param posMin
	 *            A {@link BlockPos} that represents minimum size
	 * @param posMax
	 *            A {@link BlockPos} that represents maximum size
	 */
	public void expand( BlockPos posMin, BlockPos posMax )
	{
		if( equals( EMPTY ) )
			set( posMin, posMax );
		//@formatter:off
		this.posMin.subtract( new BlockPos( Math.min( posMin.getX(), posMax.getX() ),
											Math.min( posMin.getY(), posMax.getY() ),
											Math.min( posMin.getZ(), posMax.getZ() ) ) );
		this.posMax.add( new BlockPos(	Math.max( posMin.getX(), posMax.getX() ),
										Math.max( posMin.getY(), posMax.getY() ),
										Math.max( posMin.getZ(), posMax.getZ() ) ) );
		//@formatter:on
	}

	/**
	 * Expand the region by the given size
	 *
	 * @param size
	 *            The size to expand by
	 */
	public void expand( int size )
	{
		expand( new BlockPos( size, size, size ), new BlockPos( size, size, size ) );
	}

	/**
	 * Expand the region to contain the given position
	 *
	 * @param pos
	 *            The {@link BlockPos} to contain
	 */
	public void expandToContain( BlockPos pos )
	{
		if( equals( EMPTY ) )
			set( pos, pos );
		posMin = new BlockPos( Math.min( posMin.getX(), pos.getX() ), Math.min( posMin.getY(), pos.getY() ), Math.min( posMin.getZ(), pos.getZ() ) );
		posMax = new BlockPos( Math.max( posMax.getX(), pos.getX() ), Math.max( posMax.getY(), pos.getY() ), Math.max( posMax.getZ(), pos.getZ() ) );
	}

	/**
	 * Expand the region to contain the given {@link TileEntity}
	 *
	 * @param entity
	 *            The {@link TileEntity} to contain
	 */
	public void expandToContain( TileEntity entity )
	{
		expandToContain( entity.getPos() );
	}

	/**
	 * Expand the region to contain the given {@link Region}
	 *
	 * @param region
	 *            The {@link Region} to contain
	 */
	public void expandToContain( Region region )
	{
		if( equals( EMPTY ) )
			set( region.posMin, region.posMax );
		else
		{
			posMin = new BlockPos( Math.min( posMin.getX(), region.posMin.getX() ), Math.min( posMin.getY(), region.posMin.getY() ),
					Math.min( posMin.getZ(), region.posMin.getZ() ) );
			posMax = new BlockPos( Math.max( posMax.getX(), region.posMax.getX() ), Math.max( posMax.getY(), region.posMax.getY() ),
					Math.max( posMax.getZ(), region.posMax.getZ() ) );
		}
	}

	/**
	 * Test if this region is equal to empty
	 *
	 * @return True if empty
	 */
	public boolean isEmpty()
	{
		return equals( EMPTY );
	}

	/**
	 * Test if this region only contains the given {@link Block}
	 *
	 * @param world
	 *            The {@link World} to test in
	 * @param block
	 *            The {@link Block} to test for
	 * @return True if the region only contains the given {@link Block}
	 */
	public boolean isFull( World world, Block block )
	{
		for( final BlockPos blockPos : BlockUtils.getAllInBox( posMin, posMax ) )
			if( !world.getBlockState( blockPos ).getBlock().equals( block ) )
				return false;
		return true;
	}

	/**
	 * Test if this region only contains the given {@link TileEntity}
	 *
	 * @param world
	 *            The {@link World} to test in
	 * @param tileEntity
	 *            The {@link TileEntity} to test for
	 * @return True if the region only contains the given {@link TileEntity}
	 */
	public boolean isFull( World world, TileEntity tileEntity )
	{
		for( final BlockPos blockPos : BlockUtils.getAllInBox( posMin, posMax ) )
			if( !world.getTileEntity( blockPos ).equals( tileEntity ) )
				return false;
		return true;
	}

	/**
	 * Contract the region by the given size
	 *
	 * @param posMin
	 *            A {@link BlockPos} that represents minimum size
	 * @param posMax
	 *            A {@link BlockPos} that represents maximum size
	 */
	public void contract( BlockPos posMin, BlockPos posMax )
	{
		//@formatter:off
		this.posMin.add( new BlockPos( Math.min( posMin.getX(), posMax.getX() ),
									   Math.min( posMin.getY(), posMax.getY() ),
									   Math.min( posMin.getZ(), posMax.getZ() ) ) );
		this.posMax.subtract( new BlockPos(	Math.max( posMin.getX(), posMax.getX() ),
											Math.max( posMin.getY(), posMax.getY() ),
											Math.max( posMin.getZ(), posMax.getZ() ) ) );
		//@formatter:on
	}

	/**
	 * Checks if the region contains the given position
	 *
	 * @param pos
	 *            The {@link BlockPos} the test for
	 * @return True if the {@link BlockPos} is within the region
	 */
	public boolean contains( BlockPos pos )
	{
		return pos.getX() >= posMin.getX() && pos.getY() >= posMin.getY() && pos.getZ() >= posMin.getZ() && pos.getX() <= posMax.getX()
				&& pos.getY() <= posMax.getY() && pos.getZ() <= posMax.getZ();
	}

	/**
	 * Checks if the region contains the given {@link TileEntity}
	 *
	 * @param entity
	 *            The {@link TileEntity} to test for
	 * @return True if the {@link TileEntity} is within the region
	 */
	public boolean contains( TileEntity entity )
	{
		return contains( entity.getPos() );
	}

	/**
	 * Convert region to {@link CompoundNBT} data for serialization
	 *
	 * @return The converted region
	 */
	public CompoundNBT toCompound()
	{
		final CompoundNBT nbt = new CompoundNBT();
		nbt.put( "posMin", NBTUtil.writeBlockPos( posMin ) );
		nbt.put( "posMax", NBTUtil.writeBlockPos( posMax ) );
		return nbt;
	}

	/**
	 * Convert {@link CompoundNBT} data to region for deserialization
	 *
	 * @param nbt
	 *            The {@link CompoundNBT} to read from
	 * @return The converted region
	 */
	public static Region fromCompound( CompoundNBT nbt )
	{
		final BlockPos posMin = NBTUtil.readBlockPos( nbt.getCompound( "posMin" ) );
		final BlockPos posMax = NBTUtil.readBlockPos( nbt.getCompound( "posMax" ) );
		return new Region( posMin, posMax );
	}

	@Override
	public Region clone()
	{
		return new Region( posMin, posMax );
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper( this ).add( "min", posMin.toString() ).add( "max", posMax.toString() ).toString();
	}

	@Override
	public boolean equals( Object obj )
	{
		if( obj instanceof Region )
		{
			final Region test = (Region)obj;
			return test.posMin.equals( posMin ) && test.posMax.equals( posMax );
		}
		return false;
	}
}
