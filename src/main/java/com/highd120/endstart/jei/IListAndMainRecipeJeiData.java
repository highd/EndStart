package com.highd120.endstart.jei;

import com.highd120.endstart.block.base.ListAndMainRecipeData;

import mezz.jei.api.ingredients.IIngredients;

public interface IListAndMainRecipeJeiData {
	String getTitle();
	String getUid();
	ListAndMainRecipeData parseIngredient(IIngredients ingredients);
}
