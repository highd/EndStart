package com.highd120.endstart.util.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.util.ClassUtil;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = EndStartMain.MOD_ID)
public class BlockManager {
    private static List<Class<?>> classList = new ArrayList<>();
    private static Map<Class<?>, Block> blockMap = new HashMap<>();

    static {
        classList = ClassUtil.getClassList("com.highd120.endstart.block", BlockRegister.class);
    }


	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> register = evt.getRegistry();
        classList.forEach(clazz -> {
            Object obj = ClassUtil.newInstance(clazz);
            String name = clazz.getAnnotation(BlockRegister.class).name();
            if (obj instanceof Block) {
                Block block = (Block) obj;
                block.setRegistryName(new ResourceLocation(EndStartMain.MOD_ID, name));
                block.setUnlocalizedName(EndStartMain.MOD_ID + "." + name);
                register.register(block);
                blockMap.put(clazz, block);
            } else {
                throw new ClassCastException(clazz.getName());
            }
        });
    }

    /**
     * アイテムブロックの初期化。
     */
    public static void itemBlockInit(Map<Class<?>, Item> itemMap, IForgeRegistry<Item> register) {
        blockMap.forEach((key, block) -> {
            ItemBlock item = new ItemBlock(block);
            item.setUnlocalizedName(block.getUnlocalizedName());
            item.setRegistryName(block.getRegistryName());
            register.register(item);
            itemMap.put(key, item);
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory"));
        });
    }

    @SuppressWarnings("unchecked")
    public static <T extends Block> T getBlock(Class<T> clazz) {
        return (T) blockMap.get(clazz);
    }
}
