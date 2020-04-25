package io.github.tehstoneman.lockers.common.tileentity;

import java.util.logging.Logger;

import io.github.tehstoneman.lockers.ModInfo;
import io.github.tehstoneman.lockers.common.block.lockersBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;

public class TileEntityPresent extends TileEntityCardboardBox implements ITickable
{

	public static final String	TAG_COLOR_INNER		= "presentColorInner";
	public static final String	TAG_COLOR_OUTER		= "presentColorOuter";
	public static final String	TAG_SKOJANZA_MODE	= "skojanzaMode";
	public static final String	TAG_NAMETAG			= "nameTag";

	public int					colorInner			= 14;
	public int					colorOuter			= 0;
	public boolean				skojanzaMode		= false;
	public String				nameTag				= null;

	public int					breakProgress		= 0;
	public int					breakPause			= 0;

	@Override
	protected boolean canPickUp()
	{
		return true;
	}

	@Override
	protected ItemStack getItemDropped()
	{
		return !destroyed ? new ItemStack( lockersBlocks.PRESENT ) : null;
	}

	@Override
	protected void onItemDropped( ItemStack stack )
	{
		super.onItemDropped( stack );
		final NBTTagCompound compound = stack.getTagCompound();
		compound.setByte( TAG_COLOR_INNER, (byte)colorInner );
		compound.setByte( TAG_COLOR_OUTER, (byte)colorOuter );
		compound.setBoolean( TAG_SKOJANZA_MODE, skojanzaMode );
		if( nameTag != null )
			compound.setString( TAG_NAMETAG, nameTag );
		// StackUtils.remove( stack, "display", "color" );
		// compound.setInteger( "color", color );
	}

	@Override
	public void update()
	{
		breakPause = Math.max( 0, breakPause - 1 );
		if( breakPause <= 0 )
			breakProgress = Math.max( 0, breakProgress - 1 );
	}

	// TileEntityContainer stuff

	@Override
	public void onBlockPlaced( EntityLivingBase player, ItemStack stack )
	{
		super.onBlockPlaced( player, stack );
		if( stack.hasTagCompound() )
		{
			final NBTTagCompound compound = stack.getTagCompound();
			if( compound.hasKey( TAG_COLOR_INNER ) )
				colorInner = compound.getByte( TAG_COLOR_INNER );
			if( compound.hasKey( TAG_COLOR_OUTER ) )
				colorOuter = compound.getByte( TAG_COLOR_OUTER );
			if( compound.hasKey( TAG_SKOJANZA_MODE ) )
				skojanzaMode = compound.getByte( TAG_SKOJANZA_MODE ) > 0;
			if( compound.hasKey( TAG_NAMETAG ) )
				nameTag = compound.getString( TAG_NAMETAG );
			// color = StackUtils.get( stack, -1, "color" );
		}
		// worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, colorInner, SetBlockFlag.SEND_TO_CLIENT);
	}

	@Override
	public boolean onBlockActivated( EntityPlayer player, int side, float hitX, float hitY, float hitZ )
	{
		final ItemStack holding = player.getHeldItemMainhand();
		if( holding == null )
		{
			Logger.getLogger( ModInfo.modId ).info( "Present hit : " + breakPause );

			if( breakPause > 0 )
				return false;
			breakPause = 10 - breakProgress / 10;
			/*
			 * if ((nameTag != null) && !player.getCommandSenderName().equalsIgnoreCase(nameTag)) {
			 * breakPause = 40;
			 * if (!worldObj.isRemote)
			 * ((EntityPlayerMP)player).addChatMessage(new ChatComponentText(
			 * EnumChatFormatting.YELLOW + "This present is for " + nameTag + ", not you!"));
			 * return false;
			 * }
			 */
			if( ( breakProgress += 20 ) > 100 )
				destroyed = true;
			if( worldObj.isRemote )
				return true;

			final double x = pos.getX() + 0.5;
			final double y = pos.getY() + 0.5;
			final double z = pos.getZ() + 0.5;

			/*
			 * String sound = Block.soundTypeCloth.getBreakSound();
			 * worldObj.playSoundEffect(x, y, z, sound, 0.75F, 0.8F + breakProgress / 80.0F);
			 * worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, sound, 1.0F, 0.4F + breakProgress / 160.0F);
			 */

			/*
			 * lockers.networkChannel.sendToAllAround(
			 * new PacketPresentOpen(xCoord, yCoord, zCoord, destroyed),
			 * worldObj, x, y, z, 64);
			 */

			if( !destroyed )
				return true;

			//if( lockersBlocks.CARDBOARD_BOX != null )
				//if( worldObj.setBlockState( pos, lockersBlocks.CARDBOARD_BOX.getDefaultState() ) )
				//{
					// TileEntityCardboardBox box = WorldUtils.get(worldObj, xCoord, yCoord, zCoord, TileEntityCardboardBox.class);
					// box.uses = ItemBlockCardboardBox.getUses();
					// box.color = color;
					// System.arraycopy(contents, 0, box.contents, 0, contents.length);
				//}
				//else
					for( int i = 0; i < inventory.getSlots(); i++ )
					{
						final ItemStack stack = inventory.getStackInSlot( i );
						if( stack != null && stack.stackSize > 0 )
						{
							final EntityItem entityItem = new EntityItem( worldObj, pos.getX(), pos.getY(), pos.getZ(), stack );
							worldObj.spawnEntityInWorld( entityItem );
						}
					}

			return true;

		}
		/*
		 * else if ((holding.getItem() == Items.name_tag) &&
		 * (nameTag == null) && holding.hasDisplayName()) {
		 * if (holding.getDisplayName().matches("^[a-zA-Z0-9_]{2,16}$")) {
		 * if (!worldObj.isRemote) {
		 * nameTag = holding.getDisplayName();
		 * holding.stackSize--;
		 * markForUpdate();
		 * }
		 * return true;
		 * } else {
		 * if (!worldObj.isRemote)
		 * ((EntityPlayerMP)player).addChatMessage(new ChatComponentText(
		 * EnumChatFormatting.YELLOW + "The nametag doesn't seem to contain a valid username."));
		 * return false;
		 * }
		 * } else
		 */
		return false;
	}

	@Override
	public void dropContents()
	{}

	/*
	 * @Override
	 *
	 * @SideOnly( Side.CLIENT )
	 * public void onBlockRenderAsItem( ItemStack stack )
	 * {
	 * final NBTTagCompound compound = stack.getTagCompound();
	 * colorInner = StackUtils.get( stack, (byte)14, TAG_COLOR_INNER );
	 * colorOuter = StackUtils.get( stack, (byte)0, TAG_COLOR_OUTER );
	 * skojanzaMode = StackUtils.get( stack, (byte)0, TAG_SKOJANZA_MODE ) > 0;
	 * nameTag = StackUtils.get( stack, (String)null, TAG_NAMETAG );
	 * }
	 */

	// Tile entity synchronization

	@Override
	public NBTTagCompound getUpdateTag()
	{
		final NBTTagCompound compound = super.getUpdateTag();

		compound.setByte( TAG_COLOR_INNER, (byte)colorInner );
		compound.setByte( TAG_COLOR_OUTER, (byte)colorOuter );
		compound.setBoolean( TAG_SKOJANZA_MODE, skojanzaMode );
		if( nameTag != null )
			compound.setString( TAG_NAMETAG, nameTag );

		return compound;
	}

	@Override
	public void onDataPacket( NetworkManager net, SPacketUpdateTileEntity packet )
	{
		super.onDataPacket( net, packet );
		final NBTTagCompound compound = packet.getNbtCompound();
		colorInner = compound.getByte( TAG_COLOR_INNER );
		colorOuter = compound.getByte( TAG_COLOR_OUTER );
		skojanzaMode = compound.getBoolean( TAG_SKOJANZA_MODE );
		nameTag = compound.hasKey( TAG_NAMETAG ) ? compound.getString( TAG_NAMETAG ) : null;
	}

	@Override
	public void handleUpdateTag( NBTTagCompound compound )
	{
		super.handleUpdateTag( compound );

		colorInner = compound.getByte( TAG_COLOR_INNER );
		colorOuter = compound.getByte( TAG_COLOR_OUTER );
		skojanzaMode = compound.getBoolean( TAG_SKOJANZA_MODE );
		nameTag = compound.hasKey( TAG_NAMETAG ) ? compound.getString( TAG_NAMETAG ) : null;
	}

	// Reading from / writing to NBT

	@Override
	public void readFromNBT( NBTTagCompound compound )
	{
		super.readFromNBT( compound );
		colorInner = compound.getByte( TAG_COLOR_INNER );
		colorOuter = compound.getByte( TAG_COLOR_OUTER );
		skojanzaMode = compound.getBoolean( TAG_SKOJANZA_MODE );
		nameTag = compound.hasKey( TAG_NAMETAG ) ? compound.getString( TAG_NAMETAG ) : null;
	}

	@Override
	public NBTTagCompound writeToNBT( NBTTagCompound compound )
	{
		super.writeToNBT( compound );
		compound.setByte( TAG_COLOR_INNER, (byte)colorInner );
		compound.setByte( TAG_COLOR_OUTER, (byte)colorOuter );
		compound.setBoolean( TAG_SKOJANZA_MODE, skojanzaMode );
		if( nameTag != null )
			compound.setString( TAG_NAMETAG, nameTag );
		return compound;
	}

}
