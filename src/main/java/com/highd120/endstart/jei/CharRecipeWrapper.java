package com.highd120.endstart.jei;

import com.google.common.collect.ImmutableList;
import com.highd120.endstart.block.CharRecipeData;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CharRecipeWrapper implements IRecipeWrapper {
    private final CharRecipeData recipe;

    public CharRecipeWrapper(CharRecipeData recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.createIngredient());
        ingredients.setOutputs(VanillaTypes.ITEM, ImmutableList.of(recipe.getOutput()));
    }
}
