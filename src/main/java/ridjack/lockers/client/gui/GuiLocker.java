package ridjack.lockers.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import ridjack.lockers.client.renderer.Resources;
import ridjack.lockers.common.inventory.ContainerLocker;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiLocker extends ContainerScreen<ContainerLocker>
{
	private final int	columns;
	private final int	rows;
	private final int	offsetX;
	private final int	offsetY;

	public GuiLocker( ContainerLocker container, PlayerInventory playerInventory, ITextComponent title )
	{
		super( container, playerInventory, title );

		columns = container.getColumns();
		rows = container.getRows();

		xSize = Math.max( 14 + columns * 18, 176 );
		ySize = 114 + rows * 18;

		offsetX = Math.max( ( 176 - xSize ) / 2, 0 );
		offsetY = 17 + rows * 18;
	}

	protected ResourceLocation getResource()
	{
		if( columns <= 9 )
			return Resources.CONTAINER_GENERIC;
		else
			return Resources.CONTAINER_EXPANDABLE;
	}

	@Override
	public void render( int mouseX, int mouseY, float partialTicks )
	{
		renderBackground();
		super.render( mouseX, mouseY, partialTicks );
		renderHoveredToolTip( mouseX, mouseY );
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int par1, int par2 )
	{
		font.drawString( title.getFormattedText(), 8, 6, 0x404040 );
		font.drawString( playerInventory.getDisplayName().getFormattedText(), 8 + offsetX, offsetY + 2, 0x404040 );
	}

	@Override
	protected void drawGuiContainerBackgroundLayer( float partialTicks, int x, int y )
	{
		minecraft.getTextureManager().bindTexture( getResource() );
		GlStateManager.color4f( 1.0F, 1.0F, 1.0F, 1.0F );

		blit( guiLeft, guiTop, 0, 0, xSize, offsetY );
		blit( guiLeft, guiTop + offsetY, 0, 126, xSize, 96 );
	}
}
