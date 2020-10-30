package com.highd120.endstart.jei;

import com.highd120.endstart.block.CharRecipe;
import com.highd120.endstart.block.CharRecipeData;
import com.highd120.endstart.block.InjectionRecipe;
import com.highd120.endstart.block.InjectionRecipeData;
import com.highd120.endstart.block.ModBlocks;
import com.highd120.endstart.item.ModItems;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

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
		registry.addRecipeCategories(new CharCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void register(IModRegistry registry) {
		IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.blockChar));
		
		registry.handleRecipes(InjectionRecipeData.class, InjectionRecipeWrapper::new, InjectionCategory.UID);
		registry.addRecipes(InjectionRecipe.recipes, InjectionCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.crafterCore), InjectionCategory.UID);
        
		registry.handleRecipes(CharRecipeData.class, CharRecipeWrapper::new, CharCategory.UID);
		registry.addRecipes(CharRecipe.recipes, CharCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModItems.chalk), CharCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.stove), VanillaRecipeCategoryUid.SMELTING);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

}
