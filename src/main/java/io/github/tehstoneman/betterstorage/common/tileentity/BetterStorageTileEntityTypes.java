package io.github.tehstoneman.betterstorage.common.tileentity;

import io.github.tehstoneman.betterstorage.ModInfo;
import io.github.tehstoneman.betterstorage.common.block.BetterStorageBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class BetterStorageTileEntityTypes
{
	public static final DeferredRegister< TileEntityType< ? > >						REGISTERY			= new DeferredRegister<>(
			ForgeRegistries.TILE_ENTITIES, ModInfo.MOD_ID );

	public static RegistryObject< TileEntityType< TileEntityLocker > >				LOCKER				= REGISTERY.register( "locker",
			() -> TileEntityType.Builder.create( TileEntityLocker::new, BetterStorageBlocks.LOCKER.get() ).build( null ) );

}
