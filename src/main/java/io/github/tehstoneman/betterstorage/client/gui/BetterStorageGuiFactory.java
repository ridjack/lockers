package io.github.tehstoneman.betterstorage.client.gui;

import io.github.tehstoneman.betterstorage.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class BetterStorageGuiFactory // implements IModGuiFactory
{
	// @Override
	public void initialize( Minecraft minecraftInstance )
	{}

	// @Override
	public boolean hasConfigGui()
	{
		return true;
	}

	// @Override
	public Screen createConfigGui( Screen parentScreen )
	{
		return new BetterStorageGuiConfig( parentScreen );
	}

	/*
	 * @Override
	 * public Set< RuntimeOptionCategoryElement > runtimeGuiCategories()
	 * {
	 * return null;
	 * }
	 */

	public static class BetterStorageGuiConfig extends Screen
	{
		protected BetterStorageGuiConfig( Screen parentScreen )
		{
			super( new TranslationTextComponent( ModInfo.MOD_ID ) );
		}

		/*
		 * public BetterStorageGuiConfig( Screen parentScreen )
		 * {
		 * // super( parentScreen, getConfigElements(), ModInfo.modId, false, false, ModInfo.modName );
		 * }
		 */

		// private static List< IConfigElement > getConfigElements()
		// {
		// final List< IConfigElement > configElements = new ArrayList<>();

		// final Configuration config = BetterStorage.config.getConfig();
		// final ConfigElement generalCategory = new ConfigElement( config.getCategory( Configuration.CATEGORY_GENERAL ) );
		// final ConfigElement blockCategory = new ConfigElement( config.getCategory( BetterStorageConfig.CATEGORY_BLOCKS ) );
		// final ConfigElement itemCategory = new ConfigElement( config.getCategory( BetterStorageConfig.CATEGORY_ITEMS ) );
		// final ConfigElement enchantCategory = new ConfigElement( config.getCategory( BetterStorageConfig.CATEGORY_ENCHANTMENTS ) );

		// configElements.addAll( generalCategory.getChildElements() );
		// configElements.add( new DummyCategoryElement( "block", "config.betterstorage.category.block", blockCategory.getChildElements() ) );
		// configElements.add( new DummyCategoryElement( "item", "config.betterstorage.category.item", itemCategory.getChildElements() ) );
		// configElements.add( new DummyCategoryElement( "enchant", "config.betterstorage.category.enchantment", enchantCategory.getChildElements() ) );

		// return configElements;
		// }
	}
}
