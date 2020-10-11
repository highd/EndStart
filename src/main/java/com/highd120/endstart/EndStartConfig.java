package com.highd120.endstart;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = EndStartMain.MOD_ID, type = Type.INSTANCE, name = EndStartMain.MOD_ID)
public class EndStartConfig {
	public static int wall_range = 7;
	public static int wall_height = 5;
	public static int AutoExtremeCraftingTickCost = 100000;
	public static String removeEnrichmentChamber[] = {};
	public static boolean isGenerateLibrary = true;
	public static int generateLibraryRate = 50;
	public static String addDamyNbtTarget[] = {};
}
