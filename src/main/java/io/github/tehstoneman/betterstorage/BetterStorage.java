package io.github.tehstoneman.betterstorage;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.tehstoneman.betterstorage.common.block.BetterStorageBlocks;
import io.github.tehstoneman.betterstorage.common.inventory.BetterStorageContainerTypes;
import io.github.tehstoneman.betterstorage.common.item.BetterStorageItemGroup;
import io.github.tehstoneman.betterstorage.common.item.BetterStorageItems;
import io.github.tehstoneman.betterstorage.common.tileentity.BetterStorageTileEntityTypes;
import io.github.tehstoneman.betterstorage.network.ModNetwork;
import io.github.tehstoneman.betterstorage.proxy.ClientProxy;
import io.github.tehstoneman.betterstorage.proxy.IProxy;
import io.github.tehstoneman.betterstorage.proxy.ServerProxy;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod( ModInfo.MOD_ID )
public class BetterStorage {
	public static final Logger LOGGER = LogManager.getLogger(ModInfo.MOD_ID);
	public static final ItemGroup ITEM_GROUP = new BetterStorageItemGroup();
	public static final SimpleChannel NETWORK = ModNetwork.getNetworkChannel();
	public static final IProxy PROXY = DistExecutor.<IProxy>runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

	public static Random RANDOM;

	public BetterStorage() {
		// Initialize random numbers
		RANDOM = new Random();
/**
 * Below commented line refers to the currently-irrelevant config options. Leaving it for now. -R
 */
//		BetterStorageConfig.register(ModLoadingContext.get());

		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		BetterStorageBlocks.REGISTERY.register(modEventBus);
		BetterStorageItems.REGISTERY.register(modEventBus);
		BetterStorageTileEntityTypes.REGISTERY.register(modEventBus);
		BetterStorageContainerTypes.REGISTERY.register(modEventBus);

		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}

	public void setup(FMLCommonSetupEvent event){}
}
