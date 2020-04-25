package net.mcft.copy.lockers.addon.nei;

import net.mcft.copy.lockers.addon.Addon;
import net.mcft.copy.lockers.client.gui.GuiCraftingStation;
import net.mcft.copy.lockers.content.lockersItems;
import net.mcft.copy.lockers.content.lockersTiles;
import net.mcft.copy.lockers.misc.Constants;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import codechicken.nei.api.API;
import codechicken.nei.recipe.DefaultOverlayHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class NEIAddon extends Addon {
	
	public NEIAddon() {
		super("NotEnoughItems");
	}
	
	@Override
	public void postInitialize() {	
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) return;
		
		NEIRecipeHandler handler = new NEIRecipeHandler();
		API.registerRecipeHandler(handler);
		API.registerUsageHandler(handler);
		
		API.registerGuiOverlay(GuiCraftingStation.class, Constants.modId + ".craftingStation");
		API.registerGuiOverlay(GuiCraftingStation.class, "crafting", -8, 11);
		
		API.registerGuiOverlayHandler(GuiCraftingStation.class,
				new StationOverlayHandler(), Constants.modId + ".craftingStation");
		API.registerGuiOverlayHandler(GuiCraftingStation.class,
				new StationOverlayHandler(-8, 11), "crafting");
		
		API.hideItem(new ItemStack(lockersTiles.lockableDoor));
		API.hideItem(new ItemStack(lockersItems.presentBook));
		
		// Fake key recipes
		if (lockersItems.key != null) {
			GameRegistry.addRecipe(new FakeShapedRecipe(new ItemStack(lockersItems.key),
					".o",
					".o",
					" o", 'o', Items.gold_ingot,
					      '.', Items.gold_nugget));
			GameRegistry.addRecipe(new FakeShapedRecipe(new ItemStack(lockersItems.key),
					".o ",
					".o ",
					" ok", 'o', Items.gold_ingot,
					       '.', Items.gold_nugget,
					       'k', lockersItems.key));
		}
	}
	
	private static class StationOverlayHandler extends DefaultOverlayHandler {
		public StationOverlayHandler() {  }
		public StationOverlayHandler(int x, int y) { super(x, y); }
		
		@Override
		public boolean canMoveFrom(Slot slot, GuiContainer gui) {
			return (slot.slotNumber >= 18);
		}
	}
	
	private static class FakeShapedRecipe extends ShapedOreRecipe {
		public FakeShapedRecipe(ItemStack result, Object... recipe) { super(result, recipe); }
		
		@Override
		public boolean matches(InventoryCrafting crafting, World world) { return false; }
	}
}
