package com.highd120.endstart;

import com.highd120.endstart.block.CharRecipe;
import com.highd120.endstart.block.InjectionRecipe;
import com.highd120.endstart.block.TileBlood;
import com.highd120.endstart.block.TileChar;
import com.highd120.endstart.block.TileCrafterCore;
import com.highd120.endstart.block.TileStand;
import com.highd120.endstart.command.ArgumentCommand;
import com.highd120.endstart.command.DebugCommand;
import com.highd120.endstart.command.DeleteRecipeNewTmpCommand;
import com.highd120.endstart.command.ReloadCommand;
import com.highd120.endstart.item.ItemNewRecipeCreater;
import com.highd120.endstart.proxy.CommonProxy;
import com.highd120.endstart.world.WorldGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;;

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
        EndStartMessages.registerNetworkMessages();
        proxy.registerRenderers();
	}

	/**
	 * 初期化。
	 * @param event イベント。
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(PlayerDataEvents.class);
        GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
        InjectionRecipe.init();
        CharRecipe.init();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new ReloadCommand());
		event.registerServerCommand(new DeleteRecipeNewTmpCommand());
		event.registerServerCommand(new DebugCommand());
		event.registerServerCommand(new ArgumentCommand());
	}
}
