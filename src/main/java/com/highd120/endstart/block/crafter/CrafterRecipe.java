package com.highd120.endstart.block.crafter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.block.ModBlocks;
import com.highd120.endstart.block.base.ListAndMainRecipeData;
import com.highd120.endstart.item.ModItems;
import com.highd120.endstart.jei.IListAndMainRecipeJeiData;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
/**
 * 注入レシピのマネージャー。
 * @author hdgam
 */
public class CrafterRecipe {
    public static final String UID = EndStartMain.MOD_ID + ".Injection";

    public static final List<CrafterRecipeData> recipes = new ArrayList<>();
    
    public static class JeiData implements IListAndMainRecipeJeiData {

		@Override
		public String getTitle() {
			return I18n.format(EndStartMain.MOD_ID + ".injection");
		}

		@Override
		public String getUid() {
			return UID;
		}

		@Override
		public ListAndMainRecipeData parseIngredient(IIngredients ingredients) {
	    	List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM); 
	        ItemStack main = inputs.get(0).get(0);
	        List<ItemStack> injectionList = inputs.get(1);
	        ItemStack output = ingredients.getOutputs(VanillaTypes.ITEM).get(0).get(0);
	        return new CrafterRecipeData(main, injectionList, output, 0);
		}
    }
    
    public static IListAndMainRecipeJeiData createJeiData() {
		return new JeiData();
    }

    /**
     * 注入レシピの作成。
     * @param main 注入するアイテム。
     * @param output 注入後のアイテム。
     * @param injections 注入の材料。
     * @return レシピ。
     */
    public static CrafterRecipeData createRecipe(ItemStack main, ItemStack output, int useMana,
            ItemStack... injections) {
        List<ItemStack> injectionList = Arrays.asList(injections);
        return new CrafterRecipeData(main, injectionList, output, useMana);
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

