package com.highd120.endstart.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.highd120.endstart.block.InjectionRecipeData.Input;
import com.highd120.endstart.item.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
/**
 * 注入レシピのマネージャー。
 * @author hdgam
 */
public class InjectionRecipe {

    public static final List<InjectionRecipeData> recipes = new ArrayList<>();

    /**
     * 注入レシピの作成。
     * @param main 注入するアイテム。
     * @param output 注入後のアイテム。
     * @param injections 注入の材料。
     * @return レシピ。
     */
    public static InjectionRecipeData createRecipe(ItemStack main, ItemStack output, int useMana,
            ItemStack... injections) {
        List<ItemStack> injectionList = Arrays.asList(injections);
        Input input = new Input(main, injectionList);
        return new InjectionRecipeData(input, output, useMana);
    }

    /**
     * レシピの初期化。
     */
    public static void init() {
        recipes.add(createRecipe(new ItemStack(Items.BUCKET),
                new ItemStack(Items.WATER_BUCKET),
                1000,
                new ItemStack(Blocks.LEAVES), new ItemStack(Blocks.LEAVES),
                new ItemStack(Blocks.LEAVES), new ItemStack(Blocks.LEAVES)));
        recipes.add(createRecipe(
                new ItemStack(ModBlocks.nolifeWitherSkeleton),
                new ItemStack(Items.SKULL,1,1),
                1000,
                new ItemStack(ModItems.extra, 1, 2),
                new ItemStack(ModItems.extra, 1, 3),
                new ItemStack(ModItems.extra, 1, 2),
                new ItemStack(ModItems.extra, 1, 3)));
    }
}

