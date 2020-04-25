package ridjack.lockers.client.gui;

import ridjack.lockers.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

public class lockersGuiFactory // implements IModGuiFactory
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
		return new lockersGuiConfig( parentScreen );
	}

	/*
	 * @Override
	 * public Set< RuntimeOptionCategoryElement > runtimeGuiCategories()
	 * {
	 * return null;
	 * }
	 */

	public static class lockersGuiConfig extends Screen
	{
		protected lockersGuiConfig( Screen parentScreen )
		{
			super( new TranslationTextComponent( ModInfo.MOD_ID ) );
		}

		/*
		 * public lockersGuiConfig( Screen parentScreen )
		 * {
		 * // super( parentScreen, getConfigElements(), ModInfo.modId, false, false, ModInfo.modName );
		 * }
		 */

		// private static List< IConfigElement > getConfigElements()
		// {
		// final List< IConfigElement > configElements = new ArrayList<>();

		// final Configuration config = lockers.config.getConfig();
		// final ConfigElement generalCategory = new ConfigElement( config.getCategory( Configuration.CATEGORY_GENERAL ) );
		// final ConfigElement blockCategory = new ConfigElement( config.getCategory( lockersConfig.CATEGORY_BLOCKS ) );
		// final ConfigElement itemCategory = new ConfigElement( config.getCategory( lockersConfig.CATEGORY_ITEMS ) );
		// final ConfigElement enchantCategory = new ConfigElement( config.getCategory( lockersConfig.CATEGORY_ENCHANTMENTS ) );

		// configElements.addAll( generalCategory.getChildElements() );
		// configElements.add( new DummyCategoryElement( "block", "config.lockers.category.block", blockCategory.getChildElements() ) );
		// configElements.add( new DummyCategoryElement( "item", "config.lockers.category.item", itemCategory.getChildElements() ) );
		// configElements.add( new DummyCategoryElement( "enchant", "config.lockers.category.enchantment", enchantCategory.getChildElements() ) );

		// return configElements;
		// }
	}
}
