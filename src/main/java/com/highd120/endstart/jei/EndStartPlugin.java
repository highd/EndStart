package com.highd120.endstart.jei;

import com.highd120.endstart.block.ModBlocks;
import com.highd120.endstart.block.charmagic.CharRecipe;
import com.highd120.endstart.block.charmagic.CharRecipeData;
import com.highd120.endstart.block.crafter.CrafterRecipe;
import com.highd120.endstart.block.crafter.CrafterRecipeData;
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
		registry.addRecipeCategories(new ListAndMainRecipeCategory(registry.getJeiHelpers().getGuiHelper(), 
				CrafterRecipe.createJeiData()));
		registry.addRecipeCategories(new ListAndMainRecipeCategory(registry.getJeiHelpers().getGuiHelper(), 
				CharRecipe.createJeiData()));
	}

	@Override
	public void register(IModRegistry registry) {
		IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
		blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.blockChar));

		registry.handleRecipes(CrafterRecipeData.class, ListAndMainRecipeWrapper::new, CrafterRecipe.UID);
		registry.addRecipes(CrafterRecipe.recipes, CrafterRecipe.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.crafterCore), CrafterRecipe.UID);
        
		registry.handleRecipes(CharRecipeData.class, ListAndMainRecipeWrapper::new, CharRecipe.UID);
		registry.addRecipes(CharRecipe.recipes, CharRecipe.UID);
        registry.addRecipeCatalyst(new ItemStack(ModItems.chalk), CharRecipe.UID);

		registry.addRecipeCatalyst(new ItemStack(ModBlocks.stove), VanillaRecipeCategoryUid.SMELTING);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

}
