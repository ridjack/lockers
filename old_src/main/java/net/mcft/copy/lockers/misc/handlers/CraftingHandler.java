package io.github.tehstoneman.lockers.misc.handlers;

import io.github.tehstoneman.lockers.common.item.lockersItems;
import io.github.tehstoneman.lockers.common.item.locking.ItemKey;
import io.github.tehstoneman.lockers.utils.InventoryUtils;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

/** Handles key and lock crafting. */
public class CraftingHandler
{
	public CraftingHandler()
	{
		MinecraftForge.EVENT_BUS.register( this );
		FMLCommonHandler.instance().bus().register( this );
	}

	@SubscribeEvent
	public void onItemCrafted( ItemCraftedEvent event )
	{
		// If item crafted is a key ...
		if( event.crafting.getItem() instanceof ItemKey )
		{

			// See if a key was modified by checking if no gold was used in the recipe.
			final boolean modifyKey = !InventoryUtils.hasItem( event.craftMatrix, Items.GOLD_INGOT );

			// If it is, remove it from the crafting matrix.
			if( modifyKey )
			{
				final int keyIndex = InventoryUtils.findItemSlot( event.craftMatrix, lockersItems.KEY );
				event.craftMatrix.setInventorySlotContents( keyIndex, null );
			}

		}
	}
}
