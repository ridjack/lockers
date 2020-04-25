package ridjack.lockers.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import ridjack.lockers.api.ConnectedType;
import ridjack.lockers.client.renderer.Resources;
import ridjack.lockers.client.renderer.tileentity.model.ModelLargeLocker;
import ridjack.lockers.client.renderer.tileentity.model.ModelLocker;
import ridjack.lockers.common.block.lockersBlocks;
import ridjack.lockers.common.block.BlockConnectableContainer;
import ridjack.lockers.common.block.BlockLocker;
import ridjack.lockers.common.tileentity.TileEntityLocker;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

public class TileEntityLockerRenderer extends TileEntityRenderer<TileEntityLocker>
{
	private final ModelLocker	simpleLocker	= new ModelLocker();
	private final ModelLocker	largeLocker		= new ModelLargeLocker();
	private ItemRenderer		itemRenderer;

	public TileEntityLockerRenderer( TileEntityRendererDispatcher rendererDispatcher )
	{
		super( rendererDispatcher );
	}

	@Override
	public void render( TileEntityLocker tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight,
			int combinedOverlay )
	{
		final BlockState blockState = tileEntity.hasWorld() ? tileEntity.getBlockState()
				: lockersBlocks.LOCKER.get().getDefaultState().with( BlockLocker.FACING, Direction.SOUTH );
		final ConnectedType lockerType = blockState.has( BlockConnectableContainer.TYPE ) ? blockState.get( BlockConnectableContainer.TYPE )
				: ConnectedType.SINGLE;
		final DoorHingeSide hingeSide = blockState.has( BlockStateProperties.DOOR_HINGE ) ? blockState.get( BlockStateProperties.DOOR_HINGE )
				: DoorHingeSide.LEFT;

		if( lockerType != ConnectedType.SLAVE )
		{
			final boolean flag = lockerType != ConnectedType.SINGLE;
			final ModelLocker modelLocker = getLockerModel( tileEntity, flag );

			matrixStack.push();

			final float f = blockState.get( BlockLocker.FACING ).getHorizontalAngle();
			matrixStack.translate( 0.5, 0.5, 0.5 );
			matrixStack.rotate( Vector3f.YP.rotationDegrees( -f ) );
			matrixStack.translate( -0.5, -0.5, -0.5 );

			final Material material = getMaterial( tileEntity, flag );
			final IVertexBuilder vertexBuilder = material.getBuffer( buffer, RenderType::entityCutout );

			rotateDoor( tileEntity, partialTicks, modelLocker, hingeSide );
			modelLocker.render( matrixStack, vertexBuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F, hingeSide == DoorHingeSide.LEFT );
			matrixStack.pop();
		}
	}

	private ModelLocker getLockerModel( TileEntityLocker tileEntityLocker, boolean flag )
	{
		return flag ? largeLocker : simpleLocker;
	}

	protected Material getMaterial( TileEntityLocker tileEntity, boolean flag )
	{
		final ResourceLocation resourcelocation;
			resourcelocation = flag ? Resources.TEXTURE_LOCKER_NORMAL_DOUBLE : Resources.TEXTURE_LOCKER_NORMAL;
		return new Material( PlayerContainer.LOCATION_BLOCKS_TEXTURE, resourcelocation );
	}

	private void rotateDoor( TileEntityLocker tileEntityLocker, float partialTicks, ModelLocker modelLocker, DoorHingeSide hingeSide )
	{
		float angle = ( (IChestLid)tileEntityLocker ).getLidAngle( partialTicks );
		angle = 1.0F - angle;
		angle = 1.0F - angle * angle * angle;
		modelLocker.rotateDoor( angle, hingeSide == DoorHingeSide.LEFT );
	}


}
