package com.highd120.endstart.jei;

import com.google.common.collect.ImmutableList;
import com.highd120.endstart.block.InjectionRecipeData;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;

public class InjectionRecipeWrapper implements IRecipeWrapper {
    private final InjectionRecipeData recipe;

    public InjectionRecipeWrapper(InjectionRecipeData recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.createIngredient());
        ingredients.setOutputs(VanillaTypes.ITEM, ImmutableList.of(recipe.getOutput()));
    }
}
