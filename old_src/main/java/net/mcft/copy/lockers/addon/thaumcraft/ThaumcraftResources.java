package net.mcft.copy.lockers.addon.thaumcraft;

import net.mcft.copy.lockers.misc.lockersResource;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ThaumcraftResources {
	
	public static final ResourceLocation thaumiumChestContainer = new lockersResource("textures/gui/thaumiumChest.png");
	
	public static final ResourceLocation thaumcraftBackpackTexture = new lockersResource("textures/models/thaumcraftBackpack.png");
	public static final ResourceLocation thaumcraftBackpackTextureOverlay = new lockersResource("textures/models/thaumcraftBackpack_overlay.png");
	
	private ThaumcraftResources() {  }
	
}
