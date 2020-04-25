package ridjack.lockers.client.renderer.tileentity.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelLargeLocker extends ModelLocker
{
	public ModelLargeLocker()
	{
		super( RenderType::entityCutout );

		lockerBody = new ModelRenderer( 64, 128, 0, 34 );
		lockerBody.addBox( 0.0F, 0.0F, 0.0F, 16.0F, 32.0F, 14.0F, 0.0F );
		
		lockerDoorLeft = new ModelRenderer( 64, 128, 12, 0 );
		lockerDoorLeft.addBox( 0.0F, 0.0F, 0.0F, 16.0F, 32.0F, 2.0F, 0.0F );
		lockerDoorLeft.rotationPointZ = 13.0F;

		lockerKnobLeft = new ModelRenderer( 64, 128, 0, 0 );
		lockerKnobLeft.addBox( 12.0F, 14.0F, 2.0F, 2.0F, 4.0F, 1.0F, 0.0F );
		lockerKnobLeft.rotationPointZ = 13.0F;

		lockerDoorRight = new ModelRenderer( 64, 128, 12, 0 );
		lockerDoorRight.addBox( -16.0F, 0.0F, 0.0F, 16.0F, 32.0F, 2.0F, 0.0F );
		lockerDoorRight.rotationPointX = 16.0F;
		lockerDoorRight.rotationPointZ = 13.0F;

		lockerKnobRight = new ModelRenderer( 64, 128, 0, 0 );
		lockerKnobRight.addBox( -14.0F, 14.0F, 2.0F, 2.0F, 4.0F, 1.0F, 0.0F );
		lockerKnobRight.rotationPointX = 16.0F;
		lockerKnobRight.rotationPointZ = 13.0F;
	}
}
