package com.highd120.endstart;

import com.highd120.endstart.item.ItemRecipeCreater;
import com.highd120.endstart.util.block.BlockManager;
import com.highd120.endstart.util.item.ItemManager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * メインとなるクラス。
 * @author hdgam
 */
@Mod(modid = EndStartMain.MOD_ID, version = EndStartMain.VERSION)
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
    }

    /**
     * 初期化。
     * @param event イベント。
     */
    @EventHandler
    public void init(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(PlayerDataEvents.class);
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        //event.registerServerCommand(new DebugCommand());
    }
}
