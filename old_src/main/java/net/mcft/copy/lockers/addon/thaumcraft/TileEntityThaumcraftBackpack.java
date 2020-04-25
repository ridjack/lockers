package net.mcft.copy.lockers.addon.thaumcraft;

import net.mcft.copy.lockers.misc.Constants;
import net.mcft.copy.lockers.tile.entity.TileEntityBackpack;

public class TileEntityThaumcraftBackpack extends TileEntityBackpack {
	
	@Override
	public String getName() { return Constants.containerThaumcraftBackpack; }
	
	@Override
	public int getColumns() { return 13; }
	
}
