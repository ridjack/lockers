package net.mcft.copy.lockers.addon.thaumcraft;

import net.mcft.copy.lockers.client.gui.Guilockers;
import net.mcft.copy.lockers.container.Containerlockers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiThaumiumChest extends Guilockers {
	
	public GuiThaumiumChest(EntityPlayer player, int columns, int rows, String name, boolean localized) {
		super(new Containerlockers(player, new InventoryBasic(name, localized, columns * rows), columns, rows, 20));
	}
	
	@Override
	protected int getHeight() { return 229; }
	
	@Override
	protected int getTextureWidth() { return 512; }
	
	@Override
	protected ResourceLocation getResource() { return ThaumcraftResources.thaumiumChestContainer; }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString(title, 8, 6, 0x303030);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8 + (xSize - 176) / 2, ySize - 94, 0x404040);
	}
	
}
