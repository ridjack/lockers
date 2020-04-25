package ridjack.lockers.common.block;

import ridjack.lockers.ModInfo;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class lockersBlocks
{
	public static final DeferredRegister< Block >			REGISTERY			= new DeferredRegister<>( ForgeRegistries.BLOCKS, ModInfo.MOD_ID );


	public static RegistryObject< BlockLocker >				LOCKER				= REGISTERY.register( "locker", () -> new BlockLocker() );
}
