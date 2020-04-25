package io.github.tehstoneman.lockers.content;

import io.github.tehstoneman.lockers.lockers;
import io.github.tehstoneman.lockers.addon.Addon;
import io.github.tehstoneman.lockers.entity.EntityCluckington;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public final class lockersEntities
{
	private lockersEntities()
	{}

	public static void register()
	{
		// EntityRegistry.registerModEntity(EntityFrienderman.class, "Frienderman", 1, lockers.instance, 64, 4, true);
		EntityRegistry.registerModEntity( EntityCluckington.class, "Cluckington", 2, lockers.instance, 64, 4, true );

		Addon.registerEntitesAll();
	}
}
