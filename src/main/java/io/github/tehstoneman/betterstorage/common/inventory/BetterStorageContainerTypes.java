package io.github.tehstoneman.betterstorage.common.inventory;

import io.github.tehstoneman.betterstorage.BetterStorage;
import io.github.tehstoneman.betterstorage.ModInfo;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class BetterStorageContainerTypes
{
	public static final DeferredRegister< ContainerType< ? > >					REGISTERY			= new DeferredRegister<>(
			ForgeRegistries.CONTAINERS, ModInfo.MOD_ID );

	public static RegistryObject< ContainerType< ContainerLocker > >			LOCKER				= REGISTERY.register( "locker",
			() -> IForgeContainerType.create( ( windowID, inv, data ) ->
										{
											final BlockPos pos = data.readBlockPos();
											return new ContainerLocker( windowID, inv, BetterStorage.PROXY.getClientWorld(), pos );
										} ) );
}
