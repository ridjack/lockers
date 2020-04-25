package io.github.tehstoneman.lockers.client.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import io.github.tehstoneman.lockers.api.stand.lockersArmorStand;
import io.github.tehstoneman.lockers.api.stand.ClientArmorStandPlayer;
import io.github.tehstoneman.lockers.api.stand.IArmorStandRenderHandler;
import io.github.tehstoneman.lockers.client.model.ModelArmorStand;
import io.github.tehstoneman.lockers.misc.Resources;
import io.github.tehstoneman.lockers.tile.stand.TileEntityArmorStand;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly( Side.CLIENT )
public class TileEntityArmorStandRenderer extends TileEntitySpecialRenderer
{
	private ClientArmorStandPlayer	playerDummy		= null;
	//private final RenderArmor		renderArmor		= new RenderArmor( RenderManager.instance );

	private final ModelArmorStand	armorStandModel	= new ModelArmorStand();

	public void renderTileEntityAt( TileEntityArmorStand armorStand, double x, double y, double z, float par8 )
	{

		final int rotation = armorStand.rotation * 360 / 16;

		final ModelArmorStand model = armorStandModel;
		bindTexture( Resources.textureArmorStand );

		GL11.glPushMatrix();
		GL11.glEnable( GL12.GL_RESCALE_NORMAL );
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
		GL11.glTranslated( x, y + 2.0, z + 1.0 );
		GL11.glScalef( 1.0F, -1.0F, -1.0F );
		GL11.glTranslated( 0.5F, 0.5F, 0.5F );

		model.setRotation( rotation );
		model.renderAll();

		GL11.glDisable( GL12.GL_RESCALE_NORMAL );
		GL11.glPopMatrix();
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );

		if( armorStand.getWorld() == null )
			return;

		if( playerDummy == null )
			playerDummy = new ClientArmorStandPlayer( armorStand.getWorld() );

		playerDummy.ticksExisted = armorStand.ticksExisted;
		playerDummy.worldObj = armorStand.getWorld();
		playerDummy.renderYawOffset = playerDummy.prevRenderYawOffset = rotation;
		playerDummy.rotationYawHead = playerDummy.prevRotationYawHead = rotation;

		for( final IArmorStandRenderHandler handler : lockersArmorStand.getRenderHandlers() )
			handler.onPreRender( armorStand, playerDummy );

		//renderArmor.doRender( playerDummy, x + 0.5, y + 27 / 16.0, z + 0.5, rotation, par8 );

		GL11.glPushMatrix();
		GL11.glTranslated( x + 0.5, y + 27 / 16.0, z + 0.5 );
		GL11.glRotatef( 180, 1, 0, 0 );
		GL11.glRotatef( rotation, 0, 1, 0 );
		for( final IArmorStandRenderHandler handler : lockersArmorStand.getRenderHandlers() )
			handler.onPostRender( armorStand, playerDummy );
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt( TileEntity entity, double x, double y, double z, float par8, int damageStage )
	{
		renderTileEntityAt( (TileEntityArmorStand)entity, x, y, z, par8 );
	}
}
