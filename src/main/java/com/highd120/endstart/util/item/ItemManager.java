package com.highd120.endstart.util.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.item.ItemBase;
import com.highd120.endstart.util.ClassUtil;
import com.highd120.endstart.util.block.BlockManager;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemManager {
    private static List<Class<?>> classList = new ArrayList<>();
    private static Map<Class<?>, Item> itemMap = new HashMap<>();

    static {
        classList = ClassUtil.getClassList("com.highd120.endstart.item", ItemRegister.class);
    }

    /**
     * 初期化。
     */
    public static void init(boolean isClient) {
        classList.forEach(clazz -> {
            Object obj = ClassUtil.newInstance(clazz);
            ItemRegister itemRegister = clazz.getAnnotation(ItemRegister.class);
            String name = itemRegister.name();
            if (obj instanceof Item) {
                Item item = (Item) obj;
                item.setRegistryName(new ResourceLocation(EndStartMain.MOD_ID, name));
                item.setUnlocalizedName(EndStartMain.MOD_ID + "." + name);
                GameRegistry.register(item);
                itemMap.put(clazz, item);
                if (isClient) {
                    if (item instanceof ItemBase) {
                        ((ItemBase) item).registerModel();
                    } else {
                        ModelLoader.setCustomModelResourceLocation(item, 0,
                                new ModelResourceLocation(item.getRegistryName(), "inventory"));
                    }

                }
            } else {
                throw new ClassCastException(clazz.getName());
            }
        });
        BlockManager.itemBlockInit(itemMap, isClient);

    }

    @SuppressWarnings("unchecked")
    public static <T> T getItem(Class<T> clazz) {
        return (T) itemMap.get(clazz);
    }

    public static <T> ItemStack getItemStack(Class<T> clazz) {
        return new ItemStack((Item) getItem(clazz));
    }

    public static <T> ItemStack getItemStack(Class<T> clazz, int meta) {
        return new ItemStack((Item) getItem(clazz), 1, meta);
    }
}
