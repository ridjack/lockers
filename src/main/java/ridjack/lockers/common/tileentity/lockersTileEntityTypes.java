package ridjack.lockers.common.tileentity;

import ridjack.lockers.ModInfo;
import ridjack.lockers.common.block.lockersBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class lockersTileEntityTypes
{
	public static final DeferredRegister< TileEntityType< ? > >						REGISTERY			= new DeferredRegister<>(
			ForgeRegistries.TILE_ENTITIES, ModInfo.MOD_ID );

	public static RegistryObject< TileEntityType< TileEntityLocker > >				LOCKER				= REGISTERY.register( "locker",
			() -> TileEntityType.Builder.create( TileEntityLocker::new, lockersBlocks.LOCKER.get() ).build( null ) );

}
