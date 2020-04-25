package net.mcft.copy.lockers.network.packet;

import java.io.IOException;

import net.mcft.copy.lockers.item.ItemBackpack;
import net.mcft.copy.lockers.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

/** Updates the "hasItems" status for equipped backpacks. */
public class PacketBackpackHasItems extends AbstractPacket<PacketBackpackHasItems> {
	
	public boolean hasItems;
	
	public PacketBackpackHasItems() {  }
	public PacketBackpackHasItems(boolean hasItems) {
		this.hasItems = hasItems;
	}
	
	@Override
	public void encode(PacketBuffer buffer) throws IOException {
		buffer.writeBoolean(hasItems);
	}
	
	@Override
	public void decode(PacketBuffer buffer) throws IOException {
		hasItems = buffer.readBoolean();
	}
	
	@Override
	public void handle(EntityPlayer player) {
		ItemBackpack.getBackpackData(player).hasItems = hasItems;
	}
	
}
