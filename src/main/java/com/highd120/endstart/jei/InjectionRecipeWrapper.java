package com.highd120.endstart.jei;

import com.highd120.endstart.block.InjectionRecipeData;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public class InjectionRecipeWrapper extends BlankRecipeWrapper {
    private final InjectionRecipeData recipe;

    public InjectionRecipeWrapper(InjectionRecipeData recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, recipe.createIngredient());
        ingredients.setOutput(ItemStack.class, recipe.getOutput());
    }
}
