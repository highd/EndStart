package com.highd120.endstart;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.highd120.endstart.block.TileAutoDireCraftingTable;
import com.highd120.endstart.block.TileCrafterCore;
import com.highd120.endstart.command.DebugCommand;
import com.highd120.endstart.command.DeleteRecipeTmpCommand;
import com.highd120.endstart.item.ItemRecipeCreater;
import com.highd120.endstart.util.block.BlockManager;
import com.highd120.endstart.util.item.ItemManager;
import com.highd120.endstart.world.WorldGenerator;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconInfusionRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;

import mekanism.api.infuse.InfuseRegistry;
import mekanism.api.infuse.InfuseType;
import mekanism.common.MekanismItems;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.RecipeHandler.Recipe;
import mekanism.common.recipe.inputs.InfusionInput;
import mekanism.common.recipe.inputs.ItemStackInput;
import mekanism.common.recipe.machines.EnrichmentRecipe;
import mekanism.common.recipe.machines.MetallurgicInfuserRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;;

/**
 * メインとなるクラス。
 * @author hdgam
 */
@Mod(modid = EndStartMain.MOD_ID, version = EndStartMain.VERSION, dependencies = "required-after:Mekanism;required-after:abyssalcraft;")
public class EndStartMain {
	public static final String MOD_ID = "endstart";
	public static final String MOD_NAME = "End Start";
	public static final String VERSION = "1.10.2";

	@Instance
	public static EndStartMain instance = new EndStartMain();

    
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ItemRecipeCreater.load();
		BlockManager.init();
		ItemManager.init(event.getSide().isClient());
		GameRegistry.registerTileEntity(TileAutoDireCraftingTable.class, MOD_ID + ".auto_dire_crafting");
		GameRegistry.registerTileEntity(TileCrafterCore.class, MOD_ID + ".crafter_core");
		EndStartMessages.registerNetworkMessages();
	}

	/**
	 * 初期化。
	 * @param event イベント。
	 */
	@EventHandler
	public void init(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(PlayerDataEvents.class);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiManager());
        GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
	}

	private static String convertItemDataString(EnrichmentRecipe recipe) {
		ItemStack input = recipe.getInput().ingredient;
		String registryName = input.getItem().getRegistryName().toString();
		String metaData = Integer.toString(input.getItemDamage());
		return registryName + "," + metaData;
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		InfuseType redstoneType = InfuseRegistry.get("REDSTONE");
		ItemStack osmium = new ItemStack(MekanismItems.Ingot, 1, 1);
		InfusionInput circuitInput = new InfusionInput(redstoneType, 10, osmium);
		ItemStack circuit = new ItemStack(MekanismItems.ControlCircuit, 1, 0);
		MetallurgicInfuserRecipe circuitRecipe = new MetallurgicInfuserRecipe(circuitInput, circuit);
		RecipeHandler.removeRecipe(Recipe.METALLURGIC_INFUSER, circuitRecipe);
		HashMap<ItemStackInput, EnrichmentRecipe> recipeMap = Recipe.ENRICHMENT_CHAMBER.get();
		List<String> removes = Arrays.asList(EndStartConfig.removeEnrichmentChamber);
		List<EnrichmentRecipe> recipeList = recipeMap.entrySet().stream()
				.map(entry -> entry.getValue())
				.collect(Collectors.toList());
		recipeList.stream()
				.filter(recipe -> removes.contains(convertItemDataString(recipe)))
				.forEach(recipe -> {
					RecipeHandler.removeRecipe(Recipe.ENRICHMENT_CHAMBER, recipe);
				});
		try {
			new File(Minecraft.getMinecraft().mcDataDir, "logs\\endstart").mkdir();
			Path path = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\logs\\endstart\\removes.txt");
			Files.write(path, recipeList.stream().map(EndStartMain::convertItemDataString)
					.collect(Collectors.toList()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> blackList = Arrays.asList(
			"abyssalcraft:psdl",
			"abyssalcraft:ethaxiumingot",
			"abyssalcraft:gatewaykeydl",
			"abyssalcraft:drainstaff",
			"abyssalcraft:dreadaltartop",
			"abyssalcraft:dreadaltarbottom",
			"abyssalcraft:tieredsacrificialaltar",
			"abyssalcraft:tieredenergypedestal",
			"abyssalcraft:transmutationgem",
			"abyssalcraft:oc",
			"abyssalcraft:staff"
		);
		List<NecronomiconRitual> removeList = RitualRegistry.instance().getRituals().stream().filter(ritual -> {
			if (ritual instanceof NecronomiconInfusionRitual) {
				NecronomiconInfusionRitual infusion = (NecronomiconInfusionRitual) ritual;
				String registryName = infusion.getItem().getItem().getRegistryName().toString();
				return blackList.contains(registryName);
			}
			return false;
		}).collect(Collectors.toList());
		RitualRegistry.instance().getRituals().removeAll(removeList);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new DeleteRecipeTmpCommand());
		event.registerServerCommand(new DebugCommand());
	}
}
