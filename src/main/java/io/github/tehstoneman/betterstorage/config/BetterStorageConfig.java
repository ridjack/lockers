/**    This file is currently irrelevant, as there are no configuration options for the lockers.
        Everything here is leftover from the original BetterStorageToo;
        I'm leaving this in here in case I want to set up configs later and need something to refer back to. - R
 **/

//package io.github.tehstoneman.betterstorage.config;
//
//import net.minecraftforge.common.ForgeConfigSpec;
//import net.minecraftforge.fml.ModLoadingContext;
//import net.minecraftforge.fml.config.ModConfig;
//
//public class BetterStorageConfig
//{
//	private static final ForgeConfigSpec.Builder	COMMON_BUILDER	= new ForgeConfigSpec.Builder();
//
//	public static Common							COMMON			= new Common( COMMON_BUILDER );
//	// public static Client CLIENT = new Common( CLIENT_BUILDER );
//	// public static Server SERVER = new Common( SERVER_BUILDER );
//
//	private static ForgeConfigSpec					COMMON_SPEC		= COMMON_BUILDER.build();
//
//	public static class Common
//	{
//		public ForgeConfigSpec.IntValue		reinforcedColumns;
//		public ForgeConfigSpec.BooleanValue	crateAllowAutomation;
//
//		public ForgeConfigSpec.IntValue		cardboardBoxRows;
//		public ForgeConfigSpec.IntValue		cardboardBoxUses;
//		public ForgeConfigSpec.BooleanValue	cardboardBoxShowContents;
//		public ForgeConfigSpec.BooleanValue	cardboardBoxPistonBreakable;
//		public ForgeConfigSpec.BooleanValue	cardboardBoxDispenserPlaceable;
//
//		public ForgeConfigSpec.BooleanValue	lockBreakable;
//		public ForgeConfigSpec.IntValue tankBuckets;
//		public ForgeConfigSpec.BooleanValue	useFluidMilk;
//
//		public Common( ForgeConfigSpec.Builder builder )
//		{
//			builder.comment( "General settings." ).push( "General" );
//			//@formatter:off
//			// Reinforced chest settings
//			reinforcedColumns = builder
//					.comment( "Number of columns in reinforced chests and lockers." )
//					.translation( "config.betterstorage.general.reinforcedColumns" )
//					.defineInRange( "reinforcedColumns", 13, 9, 13 );
//
//			// Crate settings
//			crateAllowAutomation = builder
//					.comment( "Allow atomation to access the contents of crates." )
//					.translation( "config.betterstorage.general.crateAllowAutomation" )
//					.define( "crateAllowAutomation", true );
//
//			// Cardboard box settings
//			cardboardBoxRows = builder
//					.comment( "Number of rows in cardboard boxes." )
//					.translation( "config.betterstorage.general.cardboardBoxRows" )
//					.defineInRange( "cardboardBoxRows", 3, 1, 3 );
//			cardboardBoxUses = builder
//					.comment( "Number of times cardboard boxes can be picked up with items before they break. Use 0 for infinite uses." )
//					.translation( "config.betterstorage.general.cardboardBoxUses" )
//					.defineInRange( "cardboardBoxUses", 25, 0, Integer.MAX_VALUE );
//			cardboardBoxShowContents = builder
//					.comment( "If disabled, doesn't show cardboard box contents in their tooltips." )
//					.translation( "config.betterstorage.general.cardboardBoxShowContents" )
//					.define( "cardboardBoxShowContents", true );
//			cardboardBoxPistonBreakable = builder
//					.comment( "Allow pistons to break cardboard boxes." )
//					.translation( "config.betterstorage.general.cardboardBoxPistonBreakable" )
//					.define( "cardboardBoxPistonBreakable", true );
//			cardboardBoxDispenserPlaceable = builder
//					.comment( "Allow dispensers to place cardboard boxes." )
//					.translation( "config.betterstorage.general.cardboardBoxDispenserPlaceable" )
//					.define( "cardboardBoxDispenserPlaceable", true );
//
//			// Miscellaneous settings
//			lockBreakable = builder
//					.comment( "Allow locked blocks to be breakable." )
//					.translation( "config.betterstorage.general.lockBreakable" )
//					.define( "lockBreakable", true );
//			tankBuckets = builder
//					.comment( "Number of buckets that one tank can hold." )
//					.translation( "config.betterstorage.general.tankBuckets" )
//					.defineInRange( "tankBuckets", 16, 1, Integer.MAX_VALUE );
//			useFluidMilk = builder
//					.comment( "Allow the use of fluid milk" )
//					.translation( "config.betterstorage.general.useFluidMilk" )
//					.define( "useFluidMilk", true );
//			//@formatter:on
//			builder.pop();
//		}
//	}
//
//	public static void register( ModLoadingContext context )
//	{
//		context.registerConfig( ModConfig.Type.COMMON, COMMON_SPEC );
//	}
//}
