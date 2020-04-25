package io.github.tehstoneman.betterstorage.client;

import io.github.tehstoneman.betterstorage.ModInfo;
import io.github.tehstoneman.betterstorage.client.gui.GuiLocker;
import io.github.tehstoneman.betterstorage.client.renderer.Resources;
import io.github.tehstoneman.betterstorage.client.renderer.tileentity.TileEntityLockerRenderer;
import io.github.tehstoneman.betterstorage.common.inventory.BetterStorageContainerTypes;
import io.github.tehstoneman.betterstorage.common.tileentity.BetterStorageTileEntityTypes;
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
		ScreenManager.registerFactory( BetterStorageContainerTypes.LOCKER.get(), GuiLocker::new );

		// Register Tile Entity Renderers
		ClientRegistry.bindTileEntityRenderer( BetterStorageTileEntityTypes.LOCKER.get(), TileEntityLockerRenderer::new );
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