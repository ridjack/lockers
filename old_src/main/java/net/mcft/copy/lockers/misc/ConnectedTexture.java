package io.github.tehstoneman.lockers.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class ConnectedTexture
{
	//@formatter:off
	private static final String[]	iconNames		= { "all", "none",
														"t", "b", "l", "r",
														"tl", "tr", "bl", "br",
														"tlr", "tbl", "tbr", "blr",
														"tb", "lr" };

	private static final int[][]	connectedLookup	= { { 2, 3, 4, 5 },
														{ 2, 3, 4, 5 },
														{ 1, 0, 5, 4 },
														{ 1, 0, 4, 5 },
														{ 1, 0, 2, 3 },
														{ 1, 0, 3, 2 }, };
	//@formatter:on

	// private Map<String, IIcon> icons = new HashMap<String, IIcon>();

	/*
	 * public void registerIcons(IIconRegister iconRegister, String format) {
	 * for (String name : iconNames)
	 * icons.put(name, iconRegister.registerIcon(String.format(format, name)));
	 * }
	 */

	/*
	 * public IIcon getIcon(String name) {
	 * return icons.get(name);
	 * }
	 */

	/*public IBlockState getConnectedState( IBlockAccess world, int x, int y, int z, EnumFacing side )
	{
		final boolean top = canConnect( world, x, y, z, side, ForgeDirection.getOrientation( connectedLookup[side.ordinal()][0] ) );
		final boolean bottom = canConnect( world, x, y, z, side, ForgeDirection.getOrientation( connectedLookup[side.ordinal()][1] ) );
		final boolean left = canConnect( world, x, y, z, side, ForgeDirection.getOrientation( connectedLookup[side.ordinal()][2] ) );
		final boolean right = canConnect( world, x, y, z, side, ForgeDirection.getOrientation( connectedLookup[side.ordinal()][3] ) );

		final StringBuilder iconName = new StringBuilder();
		if( !top )
			iconName.append( 't' );
		if( !bottom )
			iconName.append( 'b' );
		if( !left )
			iconName.append( 'l' );
		if( !right )
			iconName.append( 'r' );

		return getIcon( iconName.length() <= 0 ? "none" : iconName.length() >= 4 ? "all" : iconName.toString() );
	}*/

	public abstract boolean canConnect( IBlockAccess world, BlockPos pos, EnumFacing side, EnumFacing connected );

	public static enum EnumConnected implements IStringSerializable
	{
		//@formatter:off
		ALL(  0xF, "all" ),
		T(    0x1, "t" ),
		B(    0x2, "b" ),
		L(    0x4, "l" ),
		R(    0x8, "r" ),
		TL(   0x5, "tl" ),
		TR(   0x9, "tr" ),
		BL(   0x6, "bl" ),
		BR(   0xA, "br" ),
		TLR(  0xD, "tlr" ),
		TBL(  0x7, "tbl" ),
		TBR(  0xB, "tbr" ),
		BLR(  0xE, "blr" ),
		TB(   0x3, "tb" ),
		LR(   0xC, "lr" ),
		NONE( 0x0, "none" );
		//@formatter:on

		private final int						meta;
		private final String					name;

		private static final EnumConnected[]	META_LOOKUP	= new EnumConnected[values().length];

		private EnumConnected( int meta, String name )
		{
			this.meta = meta;
			this.name = name;
		}

		@Override
		public String getName()
		{
			return name;
		}

		public int getMetadata()
		{
			return meta;
		}

		public static EnumConnected byMetadata( int meta )
		{
			if( meta < 0 || meta >= META_LOOKUP.length )
				meta = 0;
			return META_LOOKUP[meta];
		}

		static
		{
			for( final EnumConnected connected : values() )
				META_LOOKUP[connected.getMetadata()] = connected;
		}

		@Override
		public String toString()
		{
			return name;
		}
	}
}
