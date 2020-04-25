package io.github.tehstoneman.lockers.misc.handlers;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly( Side.CLIENT )
public class KeyBindingHandler
{

	/*
	 * public static final KeyBinding backpackOpen =
	 * new KeyBinding("key.lockers.backpackOpen", Keyboard.KEY_B, "key.categories.gameplay");
	 */
	public static final KeyBinding		drinkingHelmet	= new KeyBinding( "key.lockers.drinkingHelmet", Keyboard.KEY_F,
			"key.categories.gameplay" );

	private static final KeyBinding[]	bindings		= new KeyBinding[] { drinkingHelmet };

	public KeyBindingHandler()
	{
		MinecraftForge.EVENT_BUS.register( this );
		FMLCommonHandler.instance().bus().register( this );

		// ClientRegistry.registerKeyBinding(backpackOpen);
		ClientRegistry.registerKeyBinding( drinkingHelmet );
	}

	@SubscribeEvent
	public void onKey( KeyInputEvent event )
	{
		final Minecraft mc = Minecraft.getMinecraft();
		final EntityPlayer player = mc.thePlayer;
		if( !mc.inGameHasFocus || player == null )
			return;
		/*
		 * if (backpackOpen.isPressed() && (ItemBackpack.getBackpack(player) != null) &&
		 * lockers.globalConfig.getBoolean(GlobalConfig.enableBackpackOpen))
		 * lockers.networkChannel.sendToServer(new PacketBackpackOpen());
		 * else
		 */
		/*
		 * if (drinkingHelmet.isPressed() && (player.getEquipmentInSlot(EquipmentSlot.HEAD) != null))
		 * lockers.networkChannel.sendToServer(new PacketDrinkingHelmetUse());
		 */
	}

}
