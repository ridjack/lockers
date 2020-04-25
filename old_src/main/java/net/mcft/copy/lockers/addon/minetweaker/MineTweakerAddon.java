package net.mcft.copy.lockers.addon.minetweaker;

import minetweaker.MineTweakerAPI;
import net.mcft.copy.lockers.addon.Addon;

public class MineTweakerAddon extends Addon {
	
	public MineTweakerAddon() {
		super("MineTweaker3");
	}
	
	@Override
	public void setupConfig() {
		MineTweakerAPI.registerClass(MTCraftingStation.class);
	}
	
}
