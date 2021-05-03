package com.highd120.endstart;

import com.highd120.endstart.block.CharRecipe;
import com.highd120.endstart.block.InjectionRecipe;
import com.highd120.endstart.block.StoveFuelList;
import com.highd120.endstart.block.TileBlood;
import com.highd120.endstart.block.TileChar;
import com.highd120.endstart.block.TileCrafterCore;
import com.highd120.endstart.block.TileRack;
import com.highd120.endstart.block.TileStand;
import com.highd120.endstart.block.TileStove;
import com.highd120.endstart.command.ArgumentCommand;
import com.highd120.endstart.command.DebugCommand;
import com.highd120.endstart.command.DeleteRecipeNewTmpCommand;
import com.highd120.endstart.command.ReloadCommand;
import com.highd120.endstart.item.ItemNewRecipeCreater;
import com.highd120.endstart.proxy.CommonProxy;
import com.highd120.endstart.world.WorldGenerator;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.ThaumcraftApi;

/**
 * メインとなるクラス。
 * @author hdgam
 */
@Mod(modid = EndStartMain.MOD_ID, version = EndStartMain.VERSION)
public class EndStartMain {
	public static final String MOD_ID = "endstart";
	public static final String MOD_NAME = "End Start";
	public static final String VERSION = "1.12.2";

	@Instance
	public static EndStartMain instance = new EndStartMain();


    @SidedProxy(clientSide = "com.highd120.endstart.proxy.ClientProxy",
            serverSide = "com.highd120.endstart.proxy.CommonProxy")
    public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
        ItemNewRecipeCreater.load();
		GameRegistry.registerTileEntity(TileCrafterCore.class, new ResourceLocation(MOD_ID, "crafter_core"));
		GameRegistry.registerTileEntity(TileStand.class, new ResourceLocation(MOD_ID, "stand"));
		GameRegistry.registerTileEntity(TileBlood.class, new ResourceLocation(MOD_ID, "blood"));
		GameRegistry.registerTileEntity(TileChar.class, new ResourceLocation(MOD_ID, "char"));
		GameRegistry.registerTileEntity(TileStove.class, new ResourceLocation(MOD_ID, "stove"));
		GameRegistry.registerTileEntity(TileRack.class, new ResourceLocation(MOD_ID, "rack"));
        EndStartMessages.registerNetworkMessages();
		EntityRegistry.registerModEntity(new ResourceLocation(EndStartMain.MOD_ID, "end_zombie"), EntityEndZombie.class,
				"end_zombie", 23764, this, 64, 3, true);
        proxy.registerRenderers();
	}

	/**
	 * 初期化。
	 * @param event イベント。
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ThaumcraftApi.registerResearchLocation(new ResourceLocation(MOD_ID, "research.json"));
		EntityRegistry.addSpawn(EntityEndZombie.class, 10, 1, 3, EnumCreatureType.CREATURE, Biomes.SKY);
		MinecraftForge.EVENT_BUS.register(PlayerDataEvents.class);
		MinecraftForge.EVENT_BUS.register(CheckerForDamyNbt.class);
		MinecraftForge.EVENT_BUS.register(WoodBreakEvent.class);
        GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
        InjectionRecipe.init();
        CharRecipe.init();
        StoveFuelList.init();
        proxy.init();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new ReloadCommand());
		event.registerServerCommand(new DeleteRecipeNewTmpCommand());
		event.registerServerCommand(new DebugCommand());
		event.registerServerCommand(new ArgumentCommand());
	}
}
