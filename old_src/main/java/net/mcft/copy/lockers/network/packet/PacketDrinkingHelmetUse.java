package io.github.tehstoneman.lockers.network.packet;

import java.io.IOException;

import io.github.tehstoneman.lockers.item.ItemDrinkingHelmet;
import io.github.tehstoneman.lockers.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

/** Sent when the player presses the button to use eir drinking helmet. */
public class PacketDrinkingHelmetUse extends AbstractPacket< PacketDrinkingHelmetUse >
{

	public PacketDrinkingHelmetUse()
	{}

	@Override
	public void encode( PacketBuffer buffer ) throws IOException
	{
		// No additional data.
	}

	@Override
	public void decode( PacketBuffer buffer ) throws IOException
	{
		// No additional data.
	}

	@Override
	public void handle( EntityPlayer player )
	{
		ItemDrinkingHelmet.use( player );
	}

}
