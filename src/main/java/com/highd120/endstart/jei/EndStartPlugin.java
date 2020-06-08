package com.highd120.endstart.jei;

import com.highd120.endstart.block.BlockCrafterCore;
import com.highd120.endstart.block.BlockEndBed;
import com.highd120.endstart.block.InjectionRecipe;
import com.highd120.endstart.util.item.ItemManager;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;

@JEIPlugin
public class EndStartPlugin implements IModPlugin {

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
	}

	@Override
	public void register(IModRegistry registry) {
		IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
		blacklist.addIngredientToBlacklist(ItemManager.getItemStack(BlockEndBed.class));
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new InjectionCategory(jeiHelpers.getGuiHelper()));
        registry.addRecipeHandlers(new InjectionHandler());
        registry.addRecipes(InjectionRecipe.recipes);
        registry.addRecipeCategoryCraftingItem(ItemManager.getItemStack(BlockCrafterCore.class),
                InjectionCategory.UID);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

}
