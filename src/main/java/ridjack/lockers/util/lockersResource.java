package ridjack.lockers.util;

import ridjack.lockers.ModInfo;
import net.minecraft.util.ResourceLocation;

public class lockersResource extends ResourceLocation
{
	public lockersResource( String resourceName )
	{
		super( ModInfo.MOD_ID, resourceName );
	}
}
