package io.github.tehstoneman.betterstorage.item;

import java.util.List;

import io.github.tehstoneman.betterstorage.ModInfo;
import io.github.tehstoneman.betterstorage.misc.ChristmasEventHandler;
import io.github.tehstoneman.betterstorage.utils.MiscUtils;
import io.github.tehstoneman.betterstorage.utils.StackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPresentBook extends ItemWritableBook
{
	public ItemPresentBook()
	{
		setMaxStackSize( 1 );
		setCreativeTab( null );

		final String name = MiscUtils.getName( this );
		setUnlocalizedName( ModInfo.modId + "." + name );
		// setTextureName(Constants.modId + ":" + name);
	}

	@Override
	@SideOnly( Side.CLIENT )
	public boolean hasEffect( ItemStack stack )
	{
		return true;
	}

	@Override
	public EnumRarity getRarity( ItemStack stack )
	{
		return EnumRarity.RARE;
	}

	@Override
	public String getItemStackDisplayName( ItemStack stack )
	{
		return "BetterChristmas " + StackUtils.get( stack, 9001, "year" );
	}

	@Override
	@SideOnly( Side.CLIENT )
	public void addInformation( ItemStack stack, EntityPlayer player, List list, boolean advancedTooltips )
	{
		// list.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "An instructionary tale..?");
		list.add( "for " + StackUtils.get( stack, "[undefined]", "name" ) );
	}

	@Override
	public ActionResult< ItemStack > onItemRightClick( ItemStack stack, World world, EntityPlayer player, EnumHand hand )
	{
		if( hand != EnumHand.MAIN_HAND )
			return super.onItemRightClick( stack, world, player, hand );
		if( !world.isRemote || !ChristmasEventHandler.isBeforeChristmas()
				|| StackUtils.get( stack, 9001, "year" ) != ChristmasEventHandler.getYear() )
			return new ActionResult( EnumActionResult.SUCCESS, stack );

		final int days = 24 - ChristmasEventHandler.getDay();

		// String r = EnumChatFormatting.RESET.toString();
		// String b = EnumChatFormatting.BOLD.toString();
		// String i = EnumChatFormatting.ITALIC.toString();
		// String u = EnumChatFormatting.UNDERLINE.toString();

		// String red = EnumChatFormatting.RED.toString();
		// String blue = EnumChatFormatting.BLUE.toString();
		// String black = EnumChatFormatting.BLACK.toString();
		// String darkRed = EnumChatFormatting.DARK_RED.toString();
		// String darkAqua = EnumChatFormatting.DARK_AQUA.toString();
		// String darkGray = EnumChatFormatting.DARK_GRAY.toString();

		/*
		 * NBTTagList pages = NbtUtils.createList(
		 * 
		 * "  " + darkAqua + b + u + "BetterChristmas" +
		 * r + "\n             by copygirl" + black + "\n\n" +
		 * "Dear " + darkRed + StackUtils.get(stack, "[undefined]", "name") + black + ",\n" +
		 * "it's only " + days + " more day" + ((days != 1) ? "s" : "") + " until Christmas. " +
		 * "I bet you're all excited to get some nifty presents IRL.\n\n" +
		 * "But you know what's even better than real presents?",
		 * 
		 * 
		 * darkRed + b + " Virtual presents!!\n" +
		 * " ~~~~~~~~~~~~~\n" + black +
		 * "That's right, you can now make presents in BetterStorage! " +
		 * "If you didn't know already, that is.\n\n" +
		 * "Additionally, if you hold on to this book, you can " +
		 * darkRed + i + "get a present" + black +
		 * " this Christmas! Read on to learn more.",
		 * 
		 * 
		 * darkRed + " [ " + b + "Spirit of Giving" + darkRed + " ]" + black + "\n\n" +
		 * "How to make present:\n" +
		 * "- Fill cardboard box\n" +
		 * "- Surround with wool\n" +
		 * "You may choose any two types of wool.\n\n" +
		 * " " + u + "Crafting Recipe:\n\n" +
		 * red+b + " #" + blue+b + "#" + red+b + "# " + "#" + blue+b + "#" + red+b + "#\n" +
		 * " #" + darkRed+b + "O" + red+b + "# " + blue+b + "#" + darkRed+b + "O" + blue+b + "#  " + darkGray+b + "#" + black + " = Wool\n" +
		 * red+b + " #" + blue+b + "#" + red+b + "# " + "#" + blue+b + "#" + red+b + "#  " + darkRed+b + "O" + black + " = Box",
		 * 
		 * 
		 * darkRed + " [ " + b + "Gift Protection" + darkRed + " ]" + black + "\n\n" +
		 * "When it's time to open the presents, it can get quite chaotic.\n" +
		 * darkRed + i + "Name tags" + black + " with the recipient's name will ensure only they can open their presents.\n\n" +
		 * "You can remove name tags in a " + darkRed + i + "crafting station" + black + " using shears.",
		 * 
		 * 
		 * darkRed + "[ " + b + "Free Free Free!" + darkRed + " ]" + black + "\n\n" +
		 * "Don't lose this book, don't let it fall into the enemies' (nor your friends') hands.\n" +
		 * "Hold it and log in on Christmas (24-26 Dec), and you'll get your very own present!\n\n" +
		 * "This only works with your own book."
		 * 
		 * );
		 */

		// showBookScreen(player, pages);
		return new ActionResult( EnumActionResult.SUCCESS, stack );
	}

	@SideOnly( Side.CLIENT )
	public static void showBookScreen( EntityPlayer player, NBTTagList pages )
	{
		final ItemStack stack = new ItemStack( Items.WRITTEN_BOOK );
		StackUtils.set( stack, pages, "pages" );
		Minecraft.getMinecraft().displayGuiScreen( new GuiScreenBook( player, stack, false ) );
	}
}
