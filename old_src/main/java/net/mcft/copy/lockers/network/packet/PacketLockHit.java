package io.github.tehstoneman.lockers.network.packet;

import java.io.IOException;

import io.github.tehstoneman.lockers.lockers;
import io.github.tehstoneman.lockers.common.tileentity.TileEntityLockable;
import io.github.tehstoneman.lockers.config.GlobalConfig;
import io.github.tehstoneman.lockers.network.AbstractPacket;
import io.github.tehstoneman.lockers.utils.WorldUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

/** Lets other clients know about a lock being hit. */
public class PacketLockHit extends AbstractPacket< PacketLockHit >
{
	public int		x, y, z;
	public boolean	damage;

	public PacketLockHit()
	{}

	public PacketLockHit( int x, int y, int z, boolean damage )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.damage = damage;
	}

	@Override
	public void encode( PacketBuffer buffer ) throws IOException
	{
		buffer.writeInt( x );
		buffer.writeInt( y );
		buffer.writeInt( z );
		buffer.writeBoolean( damage );
	}

	@Override
	public void decode( PacketBuffer buffer ) throws IOException
	{
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		damage = buffer.readBoolean();
	}

	@Override
	public void handle( EntityPlayer player )
	{
		final TileEntityLockable lockable = WorldUtils.get( player.worldObj, x, y, z, TileEntityLockable.class );
		damage &= lockers.globalConfig.getBoolean( GlobalConfig.lockBreakable );
		if( lockable != null )
			lockable.lockAttachment.hit( damage );
	}
}
