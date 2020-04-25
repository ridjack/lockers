package io.github.tehstoneman.lockers.common.item;

import java.util.List;

import io.github.tehstoneman.lockers.common.tileentity.TileEntityPresent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockPresent extends ItemBlockCardboardBox
{
	public ItemBlockPresent( Block block )
	{
		super( block );
	}

	@Override
	public EnumRarity getRarity( ItemStack stack )
	{
		return EnumRarity.UNCOMMON;
	}

	@Override
	public boolean showDurabilityBar( ItemStack stack )
	{
		return false;
	}

	/*
	 * @Override
	 *
	 * @SideOnly(Side.CLIENT)
	 * public int getColorFromItemStack(ItemStack stack, int renderPass) { return 0xFFFFFF; }
	 */

	@Override
	@SideOnly( Side.CLIENT )
	public void addInformation( ItemStack stack, EntityPlayer player, List list, boolean advancedTooltips )
	{
		if( stack.hasTagCompound() )
		{
			final NBTTagCompound compound = stack.getTagCompound();
			if( compound.hasKey( TileEntityPresent.TAG_NAMETAG ) )
				list.add( "for " + compound.getString( TileEntityPresent.TAG_NAMETAG ) );
		}
	}

	@Override
	public boolean canBeStoredInContainerItem( ItemStack item )
	{
		return true;
	}
}
