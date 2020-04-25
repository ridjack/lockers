package ridjack.lockers.common.inventory;

import ridjack.lockers.lockers;
import ridjack.lockers.ModInfo;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class lockersContainerTypes
{
	public static final DeferredRegister< ContainerType< ? > >					REGISTERY			= new DeferredRegister<>(
			ForgeRegistries.CONTAINERS, ModInfo.MOD_ID );

	public static RegistryObject< ContainerType< ContainerLocker > >			LOCKER				= REGISTERY.register( "locker",
			() -> IForgeContainerType.create( ( windowID, inv, data ) ->
										{
											final BlockPos pos = data.readBlockPos();
											return new ContainerLocker( windowID, inv, lockers.PROXY.getClientWorld(), pos );
										} ) );
}
