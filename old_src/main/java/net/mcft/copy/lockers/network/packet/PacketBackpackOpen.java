package net.mcft.copy.lockers.network.packet;

import java.io.IOException;

import net.mcft.copy.lockers.lockers;
import net.mcft.copy.lockers.config.GlobalConfig;
import net.mcft.copy.lockers.item.ItemBackpack;
import net.mcft.copy.lockers.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

/** Sent when the player presses the button to open eir backpack. */
public class PacketBackpackOpen extends AbstractPacket<PacketBackpackOpen> {
	
	public PacketBackpackOpen() {  }
	
	@Override
	public void encode(PacketBuffer buffer) throws IOException {
		// No additional data.
	}
	
	@Override
	public void decode(PacketBuffer buffer) throws IOException {
		// No additional data.
	}
	
	@Override
	public void handle(EntityPlayer player) {
		if (lockers.globalConfig.getBoolean(GlobalConfig.enableBackpackOpen))
			ItemBackpack.openBackpack(player, player);
	}
	
}
