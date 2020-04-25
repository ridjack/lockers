package net.mcft.copy.lockers.addon.thaumcraft;

import java.util.ArrayList;
import java.util.List;

import net.mcft.copy.lockers.lockers;
import net.mcft.copy.lockers.addon.Addon;
import net.mcft.copy.lockers.client.renderer.ItemRendererBackpack;
import net.mcft.copy.lockers.client.renderer.TileEntityReinforcedChestRenderer;
import net.mcft.copy.lockers.config.setting.BooleanSetting;
import net.mcft.copy.lockers.content.lockersItems;
import net.mcft.copy.lockers.content.lockersTiles;
import net.mcft.copy.lockers.misc.Constants;
import net.mcft.copy.lockers.proxy.ClientProxy;
import net.mcft.copy.lockers.tile.TileBackpack;
import net.mcft.copy.lockers.tile.entity.TileEntityBackpack;
import net.mcft.copy.lockers.utils.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ThaumcraftAddon extends Addon {
	
	public static final String thaumcraftBackpackEnabled = "tile.thaumcraftBackpack";
	public static final String thaumiumChestEnabled = "tile.thaumiumChest";
	
	public static TileBackpack thaumcraftBackpack;
	public static TileThaumiumChest thaumiumChest;
	
	public static ItemThaumcraftBackpack itemThaumcraftBackpack;
	
	public static int thaumiumChestRenderId;
	
	public static ItemStack thaumium;
	public static ItemStack thaumiumBlock;
	public static ItemStack fabric;
	
	public static InfusionRecipe thaumcraftBackpackRecipe;
	public static InfusionRecipe thaumiumChestRecipe;
	
	public ThaumcraftAddon() {
		super("Thaumcraft");
	}
	
	@Override
	public void setupConfig() {
		
		new BooleanSetting(lockers.globalConfig, thaumcraftBackpackEnabled, true);
		new BooleanSetting(lockers.globalConfig, thaumiumChestEnabled, true);
		
	}
	
	@Override
	public void initializeBlocks() {
		thaumcraftBackpack = MiscUtils.conditionalNew(TileThaumcraftBackpack.class, thaumcraftBackpackEnabled);
		thaumiumChest = MiscUtils.conditionalNew(TileThaumiumChest.class, thaumiumChestEnabled);
	}
	
	@Override
	public void initializeItems() {
		itemThaumcraftBackpack = MiscUtils.conditionalNew(ItemThaumcraftBackpack.class, thaumcraftBackpackEnabled);
	}

	@Override
	public void addRecipes() {
		
		thaumium      = ItemApi.getItem("itemResource", 2);
		thaumiumBlock = ItemApi.getBlock("blockCosmeticSolid", 4);
		fabric        = ItemApi.getItem("itemResource", 7);
		
		ItemStack log = new ItemStack(Blocks.log);
		
		// Thaumaturge's backpack recipe
		if ((thaumcraftBackpack != null) && (lockersTiles.backpack != null)) {
			thaumcraftBackpackRecipe = ThaumcraftApi.addInfusionCraftingRecipe("lockers.magicstorage",
					new ItemStack(itemThaumcraftBackpack), 1,
					createAspectList(Aspect.VOID, 16, Aspect.EXCHANGE, 12, Aspect.MAGIC, 10),
					new ItemStack(lockersItems.itemBackpack),
					new ItemStack[]{ thaumium, fabric, fabric, fabric });
		}
		
		// Thaumium chest recipe
		if ((thaumiumChest != null) && (lockersTiles.reinforcedChest != null)) {
			thaumiumChestRecipe = ThaumcraftApi.addInfusionCraftingRecipe("lockers.magicstorage",
					new ItemStack(thaumiumChest), 4,
					createAspectList(Aspect.METAL, 16, Aspect.VOID, 20, Aspect.MAGIC, 16),
					new ItemStack(lockersTiles.reinforcedChest),
					new ItemStack[]{ thaumiumBlock, thaumium, log, thaumium, log, thaumium });
		}
		
	}
	
	private void addItemAspects() {
		
		addAspectsFor(lockersTiles.crate, -1, true, Aspect.VOID, 3);
		addAspectsFor(lockersTiles.locker, -1, true, Aspect.VOID, 4);
		addAspectsFor(lockersTiles.reinforcedChest, -1, true, Aspect.VOID, 5, Aspect.METAL, 10, Aspect.ARMOR, 6);
		addAspectsFor(lockersTiles.craftingStation, -1, true, Aspect.CRAFT, 6, Aspect.MECHANISM, 4);
		
		addAspectsFor(lockersItems.itemBackpack, -1, true, Aspect.VOID, 4, Aspect.EXCHANGE, 6);
		addAspectsFor(lockersItems.itemEnderBackpack, -1, true, Aspect.DARKNESS, 8, Aspect.VOID, 4, Aspect.EXCHANGE, 8,
		                                             Aspect.TRAVEL, 4, Aspect.ELDRITCH, 4, Aspect.MAGIC, 4);
		
		addAspectsFor(lockersItems.cardboardSheet, -1, false, Aspect.CRAFT, 1);
		addAspectsFor(lockersTiles.cardboardBox, -1, true, Aspect.VOID, 2, Aspect.TRAVEL, 2);
		
		addAspectsFor(lockersItems.key, -1, false, Aspect.GREED, 5, Aspect.METAL, 4, Aspect.TOOL, 2);
		addAspectsFor(lockersItems.lock, -1, false, Aspect.GREED, 4, Aspect.METAL, 6, Aspect.MECHANISM, 6, Aspect.ARMOR, 8);
		addAspectsFor(lockersItems.keyring, -1, false, Aspect.GREED, 1, Aspect.METAL, 2, Aspect.TOOL, 1);
		
		addAspectsFor(lockersItems.drinkingHelmet, -1, true, Aspect.ARMOR, 2, Aspect.MECHANISM, 5, Aspect.ENERGY, 6);
		addAspectsFor(lockersItems.slimeBucket, -1, false, Aspect.METAL, 8, Aspect.VOID, 1, Aspect.SLIME, 4);
		
	}
	
	private void addEntityAspects() {
		
		addAspectsFor("lockers.Frienderman", Aspect.ELDRITCH, 4, Aspect.TRAVEL, 4, Aspect.EXCHANGE, 2);
		addAspectsFor("lockers.Cluckington", Aspect.BEAST, 2, Aspect.FLIGHT, 2, Aspect.WEAPON, 1);
		
	}
	
	private static void addAspectsFor(Block block, int meta, boolean add, Object... aspects) {
		if (block != null) {
			AspectList list = createAspectList(aspects);
			if (add) ThaumcraftApi.registerComplexObjectTag(new ItemStack(block, meta), list);
			else ThaumcraftApi.registerObjectTag(new ItemStack(block, meta), list);
		}
	}
	private static void addAspectsFor(Item item, int meta, boolean add, Object... aspects) {
		if (item != null) {
			AspectList list = createAspectList(aspects);
			if (add) ThaumcraftApi.registerComplexObjectTag(new ItemStack(item, meta), list);
			else ThaumcraftApi.registerObjectTag(new ItemStack(item, meta), list);
		}
	}
	private static void addAspectsFor(String entityName, Object... aspects) {
		ThaumcraftApi.registerEntityTag(entityName, createAspectList(aspects));
	}
	
	public static AspectList createAspectList(Object... aspects) {
		AspectList list = new AspectList();
		for (int i = 0; i < aspects.length; i += 2)
			list.add((Aspect)aspects[i], (Integer)aspects[i + 1]);
		return list;
	}
	
	@Override
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityThaumcraftBackpack.class, Constants.containerThaumcraftBackpack);
		GameRegistry.registerTileEntity(TileEntityThaumiumChest.class, Constants.containerThaumiumChest);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderers() {
		if (thaumcraftBackpack != null)
			MinecraftForgeClient.registerItemRenderer(
					itemThaumcraftBackpack, new ItemRendererBackpack(TileEntityBackpack.class));
		if (thaumiumChest != null)
			thaumiumChestRenderId = ClientProxy.registerTileEntityRenderer(
					TileEntityThaumiumChest.class, new TileEntityReinforcedChestRenderer());
	}
	
	@Override
	public void postInitialize() {
		
		addItemAspects();
		addEntityAspects();
		
		if ((thaumcraftBackpack != null) || (thaumiumChest != null)) {
			
			List<ResearchPage> pages = new ArrayList<ResearchPage>();
			pages.add(new ResearchPage("tc.research_page.lockers.magicstorage.1"));
			if (thaumcraftBackpack != null)
				pages.add(new ResearchPage(thaumcraftBackpackRecipe));
			if (thaumiumChest != null)
				pages.add(new ResearchPage(thaumiumChestRecipe));
			
			ResearchItem research = new ResearchItem(
					"lockers.magicstorage", "ARTIFICE",
					createAspectList(Aspect.VOID, 8, Aspect.MAGIC, 5, Aspect.EXCHANGE, 5),
					2, 2, 2, new ItemStack(((thaumcraftBackpack != null) ? itemThaumcraftBackpack
					                                                     : Item.getItemFromBlock(thaumiumChest))))
				.setPages(pages.toArray(new ResearchPage[0]))
				.setParents("ENCHFABRIC")
				.setParentsHidden("INFUSION")
				.setConcealed()
				.registerResearchItem();
			
		}
		
	}
	
}
