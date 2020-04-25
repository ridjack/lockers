package ridjack.lockers;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ridjack.lockers.common.block.lockersBlocks;
import ridjack.lockers.common.inventory.lockersContainerTypes;
import ridjack.lockers.common.item.lockersItemGroup;
import ridjack.lockers.common.item.lockersItems;
import ridjack.lockers.common.tileentity.lockersTileEntityTypes;
import ridjack.lockers.network.ModNetwork;
import ridjack.lockers.proxy.ClientProxy;
import ridjack.lockers.proxy.IProxy;
import ridjack.lockers.proxy.ServerProxy;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod( ModInfo.MOD_ID )
public class lockers {
	public static final Logger LOGGER = LogManager.getLogger(ModInfo.MOD_ID);
	public static final ItemGroup ITEM_GROUP = new lockersItemGroup();
	public static final SimpleChannel NETWORK = ModNetwork.getNetworkChannel();
	public static final IProxy PROXY = DistExecutor.<IProxy>runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

	public static Random RANDOM;

	public lockers() {
		// Initialize random numbers
		RANDOM = new Random();
/**
 * Below commented line refers to the currently-irrelevant config options. Leaving it for now. -R
 */
//		lockersConfig.register(ModLoadingContext.get());

		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		lockersBlocks.REGISTERY.register(modEventBus);
		lockersItems.REGISTERY.register(modEventBus);
		lockersTileEntityTypes.REGISTERY.register(modEventBus);
		lockersContainerTypes.REGISTERY.register(modEventBus);

		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}

	public void setup(FMLCommonSetupEvent event){}
}
