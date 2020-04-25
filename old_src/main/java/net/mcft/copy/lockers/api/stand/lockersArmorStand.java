package io.github.tehstoneman.lockers.api.stand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class lockersArmorStand
{
	private static final Map< EnumArmorStandRegion, List< ArmorStandEquipHandler > >		handlersByPriority	= new HashMap< >();
	private static final Map< EnumArmorStandRegion, Map< String, ArmorStandEquipHandler > >	handlersById		= new HashMap< >();

	private static final List< IArmorStandRenderHandler >									renderHandlers		= new ArrayList< >();

	static
	{
		for( final EnumArmorStandRegion region : EnumArmorStandRegion.values() )
		{
			handlersByPriority.put( region, new ArrayList< ArmorStandEquipHandler >() );
			handlersById.put( region, new HashMap< String, ArmorStandEquipHandler >() );
		}
	}

	// Vanilla equipment handlers
	// To get/set armor from the armor stand, for example.
	public static ArmorStandEquipHandler	helmet;
	public static ArmorStandEquipHandler	chestplate;
	public static ArmorStandEquipHandler	leggins;
	public static ArmorStandEquipHandler	boots;

	private lockersArmorStand()
	{}

	/**
	 * Registers an armor stand equipment handler, which handles
	 * interaction with a specific equipment region of armor stands.
	 */
	public static void registerEquipHandler( ArmorStandEquipHandler handler )
	{
		final List< ArmorStandEquipHandler > list = handlersByPriority.get( handler.region );
		list.add( handler );
		Collections.sort( list );
		handlersById.get( handler.region ).put( handler.id, handler );
	}

	/** Returns all equipment handlers for this region. */
	public static Iterable< ArmorStandEquipHandler > getEquipHandlers( EnumArmorStandRegion region )
	{
		return handlersByPriority.get( region );
	}

	/** Returns for this region and the specified ID. */
	public static ArmorStandEquipHandler getEquipHandler( EnumArmorStandRegion region, String id )
	{
		return handlersById.get( region ).get( id );
	}

	/** Registers and armor stand render handler. */
	public static void registerRenderHandler( IArmorStandRenderHandler renderHandler )
	{
		renderHandlers.add( renderHandler );
	}

	/** Returns all armor stand render handlers. */
	public static Iterable< IArmorStandRenderHandler > getRenderHandlers()
	{
		return renderHandlers;
	}
}
