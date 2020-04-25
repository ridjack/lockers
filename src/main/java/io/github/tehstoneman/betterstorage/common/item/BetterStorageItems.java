package io.github.tehstoneman.betterstorage.common.item;

import io.github.tehstoneman.betterstorage.BetterStorage;
import io.github.tehstoneman.betterstorage.ModInfo;
import io.github.tehstoneman.betterstorage.common.block.BetterStorageBlocks;
//import io.github.tehstoneman.betterstorage.common.fluid.BetterStorageFluids;
//import io.github.tehstoneman.betterstorage.common.item.cardboard.ItemBlockCardboardBox;
//import io.github.tehstoneman.betterstorage.common.item.cardboard.ItemCardboardArmor;
//import io.github.tehstoneman.betterstorage.common.item.cardboard.ItemCardboardAxe;
//import io.github.tehstoneman.betterstorage.common.item.cardboard.ItemCardboardHoe;
//import io.github.tehstoneman.betterstorage.common.item.cardboard.ItemCardboardPickaxe;
//import io.github.tehstoneman.betterstorage.common.item.cardboard.ItemCardboardSheet;
//import io.github.tehstoneman.betterstorage.common.item.cardboard.ItemCardboardShovel;
//import io.github.tehstoneman.betterstorage.common.item.cardboard.ItemCardboardSword;
//import io.github.tehstoneman.betterstorage.common.item.locking.ItemKey;
//import io.github.tehstoneman.betterstorage.common.item.locking.ItemKeyring;
//import io.github.tehstoneman.betterstorage.common.item.locking.ItemLock;
//import io.github.tehstoneman.betterstorage.common.item.locking.ItemMasterKey;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class BetterStorageItems
{
	public static final DeferredRegister< Item >				REGISTERY				= new DeferredRegister<>( ForgeRegistries.ITEMS,
			ModInfo.MOD_ID );

	public static RegistryObject< ItemBlockLocker >				LOCKER					= REGISTERY.register( "locker", () -> new ItemBlockLocker() );

}
