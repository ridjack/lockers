package io.github.tehstoneman.betterstorage.content;

import io.github.tehstoneman.betterstorage.BetterStorage;
import io.github.tehstoneman.betterstorage.addon.Addon;
import io.github.tehstoneman.betterstorage.entity.EntityCluckington;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public final class BetterStorageEntities
{
	private BetterStorageEntities()
	{}

	public static void register()
	{
		// EntityRegistry.registerModEntity(EntityFrienderman.class, "Frienderman", 1, BetterStorage.instance, 64, 4, true);
		EntityRegistry.registerModEntity( EntityCluckington.class, "Cluckington", 2, BetterStorage.instance, 64, 4, true );

		Addon.registerEntitesAll();
	}
}
