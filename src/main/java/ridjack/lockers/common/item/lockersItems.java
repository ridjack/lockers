package ridjack.lockers.common.item;

import ridjack.lockers.ModInfo;
//import io.github.tehstoneman.lockers.common.fluid.lockersFluids;
//import io.github.tehstoneman.lockers.common.item.cardboard.ItemBlockCardboardBox;
//import io.github.tehstoneman.lockers.common.item.cardboard.ItemCardboardArmor;
//import io.github.tehstoneman.lockers.common.item.cardboard.ItemCardboardAxe;
//import io.github.tehstoneman.lockers.common.item.cardboard.ItemCardboardHoe;
//import io.github.tehstoneman.lockers.common.item.cardboard.ItemCardboardPickaxe;
//import io.github.tehstoneman.lockers.common.item.cardboard.ItemCardboardSheet;
//import io.github.tehstoneman.lockers.common.item.cardboard.ItemCardboardShovel;
//import io.github.tehstoneman.lockers.common.item.cardboard.ItemCardboardSword;
//import io.github.tehstoneman.lockers.common.item.locking.ItemKey;
//import io.github.tehstoneman.lockers.common.item.locking.ItemKeyring;
//import io.github.tehstoneman.lockers.common.item.locking.ItemLock;
//import io.github.tehstoneman.lockers.common.item.locking.ItemMasterKey;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class lockersItems
{
	public static final DeferredRegister< Item >				REGISTERY				= new DeferredRegister<>( ForgeRegistries.ITEMS,
			ModInfo.MOD_ID );

	public static RegistryObject< ItemBlockLocker >				LOCKER					= REGISTERY.register( "locker", () -> new ItemBlockLocker() );

}
