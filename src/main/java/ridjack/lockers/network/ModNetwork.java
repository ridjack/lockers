package ridjack.lockers.network;

import ridjack.lockers.ModInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ModNetwork
{
	public static ResourceLocation	CHANEL_NAME		= new ResourceLocation( ModInfo.MOD_ID, "network" );
	public static String			NETWORK_VERSION	= new ResourceLocation( ModInfo.MOD_ID, "1" ).toString();

	public static SimpleChannel getNetworkChannel()
	{
		final SimpleChannel channel = NetworkRegistry.ChannelBuilder.named( CHANEL_NAME ).clientAcceptedVersions( version -> true )
				.serverAcceptedVersions( version -> true ).networkProtocolVersion( () -> NETWORK_VERSION ).simpleChannel();

		int index = 1;

//		channel.messageBuilder( UpdateCrateMessage.class, index++ ).decoder( UpdateCrateMessage::decode ).encoder( UpdateCrateMessage::encode )
//				.consumer( UpdateCrateMessage::handle ).add();

		return channel;
	}
}
