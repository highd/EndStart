package com.highd120.endstart.jei;

import com.highd120.endstart.block.BlockCrafterCore;
import com.highd120.endstart.block.InjectionRecipe;
import com.highd120.endstart.block.InjectionRecipeData;
import com.highd120.endstart.util.item.ItemManager;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class EndStartPlugin implements IModPlugin {

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new InjectionCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(InjectionRecipeData.class, InjectionRecipeWrapper::new, InjectionCategory.UID);
		registry.addRecipes(InjectionRecipe.recipes, InjectionCategory.UID);
        registry.addRecipeCatalyst(ItemManager.getItemStack(BlockCrafterCore.class), InjectionCategory.UID);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

}
