package com.highd120.endstart.block.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.highd120.endstart.util.ItemUtil;

import net.minecraft.item.ItemStack;

public interface ListAndMainRecipeData {
	ItemStack getMain();
	List<ItemStack> getInputList();
	ItemStack getOutput();
    default List<List<ItemStack>> createIngredient() {
        List<List<ItemStack>> ingredient = new ArrayList<>();
        ItemStack chalk = getMain();
        ingredient.add(Collections.singletonList(chalk));
        ingredient.add(getInputList());
        return ingredient;
    }
}
