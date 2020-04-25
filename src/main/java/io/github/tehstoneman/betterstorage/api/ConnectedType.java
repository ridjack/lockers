package io.github.tehstoneman.betterstorage.api;

import net.minecraft.util.IStringSerializable;

/**
 * The type of connection used to connect multi-block containers
 * 
 * @author TehStoneMan
 *
 */
public enum ConnectedType implements IStringSerializable
{
	SINGLE( "single", 0 ), MASTER( "master", 2 ), SLAVE( "slave", 1 ), PILE( "pile", 3 );

	public static final ConnectedType[]	VALUES	= values();
	private final String				name;
	private final int					opposite;

	private ConnectedType(String name, int oppositeIn )
	{
		this.name = name;
		opposite = oppositeIn;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public ConnectedType opposite()
	{
		return VALUES[opposite];
	}
}
