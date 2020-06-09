package com.highd120.endstart.jei;

import com.highd120.endstart.block.InjectionRecipeData;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class InjectionHandler implements IRecipeHandler<InjectionRecipeData> {

    @Override
    public Class<InjectionRecipeData> getRecipeClass() {
        return InjectionRecipeData.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return InjectionCategory.UID;
    }

    @Override
    public String getRecipeCategoryUid(InjectionRecipeData recipe) {
        return getRecipeCategoryUid();
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(InjectionRecipeData recipe) {
        return new InjectionRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(InjectionRecipeData recipe) {
        return true;
    }

}
