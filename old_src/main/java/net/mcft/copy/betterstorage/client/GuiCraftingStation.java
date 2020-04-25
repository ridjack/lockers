package io.github.tehstoneman.betterstorage.client.gui;

import java.util.Calendar;

import org.lwjgl.opengl.GL11;

import io.github.tehstoneman.betterstorage.BetterStorage;
import io.github.tehstoneman.betterstorage.ModInfo;
import io.github.tehstoneman.betterstorage.common.inventory.ContainerCraftingStation;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiCraftingStation extends GuiContainer
{
	public final ContainerCraftingStation	containerCraftingStation;

	private GuiButton						clearButton;

	public GuiCraftingStation( ContainerCraftingStation containerCraftingStation )
	{
		super( containerCraftingStation );
		xSize = 175;
		ySize = 207;
		this.containerCraftingStation = containerCraftingStation;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		final Calendar c = Calendar.getInstance();
		if( c.get( Calendar.MONTH ) == Calendar.APRIL && c.get( Calendar.DAY_OF_MONTH ) > 1 && c.get( Calendar.DAY_OF_MONTH ) < 5 )
			buttonList.add( clearButton = new GuiButtonExt( 0, guiLeft + 72, guiTop + 16, 12, 12, "x" ) );
	}

	@Override
	protected void actionPerformed( GuiButton button )
	{
		final EntityPlayerSP p = mc.thePlayer;
		p.worldObj.createExplosion( null, p.posX, p.posY, p.posZ, 10, true );
		p.worldObj.playSound( p, p.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, 1.0F );
		p.addChatMessage( new TextComponentString( "Happy belated April Fools!" ) );
	}

	@Override
	protected void drawGuiContainerBackgroundLayer( float var1, int var2, int var3 )
	{
		mc.renderEngine.bindTexture( new ResourceLocation( ModInfo.modId, "textures/gui/crafting_station.png" ) );

		drawTexturedModalRect( guiLeft, guiTop, 0, 0, xSize, ySize );

		// final int x = ( width - xSize ) / 2;
		// final int y = ( height - ySize ) / 2;
		final int maxProgress = containerCraftingStation.getMaxProgress();
		final int progress = 24 * containerCraftingStation.getCraftingProgress() / maxProgress;
		drawTexturedModalRect( guiLeft + 76, guiTop + 34, 176, 0, progress, 18 );
		// final int requiredExperience = inv.currentCrafting != null ? inv.currentCrafting.getRequiredExperience() : 0;
		/*
		 * if( requiredExperience != 0 )
		 * {
		 * final String str = Integer.toString( requiredExperience );
		 * final int strX = x + ( xSize - fontRendererObj.getStringWidth( str ) ) / 2;
		 * final int strY = y + 58 - fontRendererObj.FONT_HEIGHT / 2;
		 * fontRendererObj.drawString( str, strX - 1, strY, 0x444444 );
		 * fontRendererObj.drawString( str, strX + 1, strY, 0x444444 );
		 * fontRendererObj.drawString( str, strX, strY - 1, 0x444444 );
		 * fontRendererObj.drawString( str, strX, strY + 1, 0x444444 );
		 * fontRendererObj.drawString( str, strX, strY, 0x80FF20 );
		 * }
		 */
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int par1, int par2 )
	{
		fontRendererObj.drawString( BetterStorage.proxy.localize( ModInfo.containerCraftingStation ), 15, 6, 0x404040 );
		fontRendererObj.drawString( BetterStorage.proxy.localize( "container.inventory" ), 8, ySize - 96 + 2, 0x404040 );

		if( containerCraftingStation.getCurrentCrafting() != null )
			for( int i = 9; i < 18; i++ )
			{
				final Slot slot = inventorySlots.getSlot( i );
				if( !slot.getHasStack() )
					continue;

				GL11.glDisable( GL11.GL_DEPTH_TEST );
				GL11.glEnable( GL11.GL_BLEND );
				GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );

				// final float a = inv.progress < inv.currentCrafting.getCraftingTime() ? 0.5F : 1.0F;
				GL11.glColor4f( 0.5F, 0.5F, 0.5F, 0.6F );
				mc.renderEngine.bindTexture( new ResourceLocation( ModInfo.modId, "textures/gui/crafting_station.png" ) );
				final int slotX = slot.xDisplayPosition;
				final int slotY = slot.yDisplayPosition;
				drawTexturedModalRect( slotX, slotY, slotX, slotY, 16, 16 );

				GL11.glDisable( GL11.GL_BLEND );
				GL11.glEnable( GL11.GL_DEPTH_TEST );
			}

	}

	/*@Override
	public void drawScreen( int mouseX, int mouseY, float partialTicks )
	{
		super.drawScreen( mouseX, mouseY, partialTicks );
	}

	private void drawSlotGhosted( Slot slotIn )
	{
		final int i = slotIn.xDisplayPosition;
		final int j = slotIn.yDisplayPosition;
		final ItemStack itemstack = slotIn.getStack();
		final ItemStack itemstack1 = mc.thePlayer.inventory.getItemStack();
		final String s = null;

		zLevel = 100.0F;
		itemRender.zLevel = 100.0F;

		GlStateManager.enableDepth();
		itemRender.renderItemAndEffectIntoGUI( mc.thePlayer, itemstack, i, j );
		itemRender.renderItemOverlayIntoGUI( fontRendererObj, itemstack, i, j, s );

		final TextureAtlasSprite textureatlassprite = slotIn.getBackgroundSprite();

		if( textureatlassprite != null )
		{
			GlStateManager.disableLighting();
			GlStateManager.alphaFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );

			mc.getTextureManager().bindTexture( slotIn.getBackgroundLocation() );
			this.drawTexturedModalRect( i, j, textureatlassprite, 16, 16 );
			GlStateManager.enableLighting();
		}

		itemRender.zLevel = 0.0F;
		zLevel = 0.0F;
	}*/
}
