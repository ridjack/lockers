package io.github.tehstoneman.lockers.api.stand;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly( Side.CLIENT )
public class ClientArmorStandPlayer extends AbstractClientPlayer
{

	public ClientArmorStandPlayer( World world )
	{
		super( world, new GameProfile( null, "[ARMOR STAND]" ) );
		setInvisible( true );
	}

	@Override
	public void addChatMessage( ITextComponent message )
	{}

	/*
	 * @Override
	 * public ChunkCoordinates getPlayerCoordinates()
	 * {
	 * return null;
	 * }
	 */

	@Override
	public boolean canCommandSenderUseCommand( int permissionLevel, String commandName )
	{
		return false;
	}

}
