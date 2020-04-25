package io.github.tehstoneman.lockers.misc;

import io.github.tehstoneman.lockers.common.item.lockersItems;
import io.github.tehstoneman.lockers.item.ItemDrinkingHelmet;
import io.github.tehstoneman.lockers.utils.NbtUtils;
import io.github.tehstoneman.lockers.utils.StackUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public final class DungeonLoot
{
	private DungeonLoot()
	{}

	public static void add()
	{

		/*if( lockersItems.drinkingHelmet != null )
		{
			final ItemStack drinkingHelmet = new ItemStack( lockersItems.drinkingHelmet );
			// addMultiple(drinkingHelmet, ChestGenHooks.DUNGEON_CHEST, 1.0,
			// ChestGenHooks.PYRAMID_DESERT_CHEST, 2.0,
			// ChestGenHooks.PYRAMID_JUNGLE_CHEST, 2.0,
			// ChestGenHooks.STRONGHOLD_CORRIDOR, 1.0,
			// ChestGenHooks.STRONGHOLD_CROSSING, 1.0);

			final ItemStack drinkingHelmetSpecial = new ItemStack( lockersItems.drinkingHelmet );
			drinkingHelmetSpecial.setStackDisplayName( "Nishmet" );
			final ItemStack fireResPotion = new ItemStack( Items.POTIONITEM, 1, 8259 );
			final ItemStack healPotion = new ItemStack( Items.POTIONITEM, 1, 8229 );
			ItemDrinkingHelmet.setPotions( drinkingHelmetSpecial, new ItemStack[] { fireResPotion, healPotion } );
			// drinkingHelmetSpecial.addEnchantment(Enchantment.fireProtection, 5);
			// drinkingHelmetSpecial.addEnchantment(Enchantment.fireAspect, 3);
			// drinkingHelmetSpecial.addEnchantment(Enchantment.unbreaking, 2);
			final NBTTagList drinkingHelmetLore = NbtUtils.createList( "\"i fail at names for items\" -Nishtown" );
			StackUtils.set( drinkingHelmetSpecial, drinkingHelmetLore, "display", "Lore" );
			// addMultiple(drinkingHelmetSpecial, ChestGenHooks.PYRAMID_DESERT_CHEST, 0.2);
		}*/

		/*
		 * if (lockersTiles.backpack != null) {
		 * ItemStack backpackSpecial1 = new ItemStack(lockersItems.itemBackpack);
		 * backpackSpecial1.setStackDisplayName("Everlasting Backpack");
		 * backpackSpecial1.addEnchantment(Enchantment.unbreaking, 4);
		 * StackUtils.set(backpackSpecial1, 0x006622, "display", "color");
		 * addMultiple(backpackSpecial1, ChestGenHooks.PYRAMID_JUNGLE_CHEST, 0.5);
		 *
		 * ItemStack backpackSpecial2 = new ItemStack(lockersItems.itemBackpack);
		 * backpackSpecial2.setStackDisplayName("Shielding Backpack");
		 * backpackSpecial2.addEnchantment(Enchantment.protection, 5);
		 * StackUtils.set(backpackSpecial2, 0x0000BB, "display", "color");
		 * addMultiple(backpackSpecial2, ChestGenHooks.PYRAMID_DESERT_CHEST, 0.4);
		 * }
		 */

	}

	private static void addMultiple( ItemStack stack, Object... args )
	{
		// for( int i = 0; i < args.length; i += 2 )
		// ChestGenHooks.addItem( (String)args[i], makeItem( stack, (Double)args[i + 1] ) );
	}

	/*
	 * private static WeightedRandomChestContent makeItem(ItemStack stack, double chance) {
	 * if (chance < 1.0) return new RareChestContent(stack, chance);
	 * return new WeightedRandomChestContent(stack, 1, 1, (int)chance);
	 * }
	 */

	/*
	 * private static class RareChestContent extends WeightedRandomChestContent {
	 * private final double chance;
	 * public RareChestContent(ItemStack stack, double chance) {
	 * super(stack, 1, 1, 1);
	 * this.chance = chance;
	 * }
	 * 
	 * @Override
	 * protected ItemStack[] generateChestContent(Random random, IInventory newInventory) {
	 * if (random.nextDouble() >= chance) return new ItemStack[0];
	 * else return super.generateChestContent(random, newInventory);
	 * }
	 * }
	 */
}
