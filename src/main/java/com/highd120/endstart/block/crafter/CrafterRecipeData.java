package com.highd120.endstart.block.crafter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.highd120.endstart.block.base.CrafterUtil;
import com.highd120.endstart.block.base.ListAndMainRecipeData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;

@AllArgsConstructor
@Value
public class CrafterRecipeData implements ListAndMainRecipeData {
    private ItemStack main;
    private List<ItemStack> inputList;
    protected ItemStack output;
    protected int useEnergy;

    /**
     * Ingredientからレシピの作成。
     * @return レシピ。
     */
    public static CrafterRecipeData parseIngredient(IIngredients ingredients) {
    	List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM); 
        ItemStack main = inputs.get(0).get(0);
        List<ItemStack> injectionList = inputs.get(1);
        ItemStack output = ingredients.getOutputs(VanillaTypes.ITEM).get(0).get(0);
        return new CrafterRecipeData(main, injectionList, output, 0);

    }
    /**
     * レシピに合致しているか。
     * @param itemList アイテムのリスト。
     * @param injection 注入するアイテム。
     * @return
     */
    public boolean checkRecipe(List<ItemStack> itemList, ItemStack item) {
    	if (main.equals(item)) return false;
        return CrafterUtil.checkListRecipe(inputList, itemList);
    }
}
