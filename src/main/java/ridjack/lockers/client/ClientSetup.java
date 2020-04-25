package ridjack.lockers.client;

import ridjack.lockers.ModInfo;
import ridjack.lockers.client.gui.GuiLocker;
import ridjack.lockers.client.renderer.Resources;
import ridjack.lockers.client.renderer.tileentity.TileEntityLockerRenderer;
import ridjack.lockers.common.inventory.lockersContainerTypes;
import ridjack.lockers.common.tileentity.lockersTileEntityTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber( modid = ModInfo.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD )
public class ClientSetup
{
	@SubscribeEvent
	public static void init( final FMLClientSetupEvent event )
	{
		// Register GUIs
		ScreenManager.registerFactory( lockersContainerTypes.LOCKER.get(), GuiLocker::new );

		// Register Tile Entity Renderers
		ClientRegistry.bindTileEntityRenderer( lockersTileEntityTypes.LOCKER.get(), TileEntityLockerRenderer::new );
	}

	@SubscribeEvent
	public static void onTextureStitch( TextureStitchEvent.Pre event )
	{
		if( !event.getMap().getBasePath().equals( PlayerContainer.LOCATION_BLOCKS_TEXTURE ) )
			return;

		event.addSprite( Resources.TEXTURE_LOCKER_NORMAL );
		event.addSprite( Resources.TEXTURE_LOCKER_NORMAL_DOUBLE );
	}
}