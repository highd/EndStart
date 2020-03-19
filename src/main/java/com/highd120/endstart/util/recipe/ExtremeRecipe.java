package com.highd120.endstart.util.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import morph.avaritia.recipe.extreme.ExtremeCraftingManager;
import morph.avaritia.recipe.extreme.ExtremeShapedOreRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapedRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapelessOreRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapelessRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import scala.actors.threadpool.Arrays;

public class ExtremeRecipe {
	private static List<List<IRecpeSlot>> recipeList = new ArrayList<>();

	public static List<List<IRecpeSlot>> getRecipeList() {
		List<IRecipe> defaultRecipeList = ExtremeCraftingManager.getInstance().getRecipeList();
		if (defaultRecipeList.size() != recipeList.size()) {
			recipeList = defaultRecipeList.stream()
					.map(ExtremeRecipe::convertRecipe)
					.collect(Collectors.toList());
		}
		return recipeList;
	}

	private static List<IRecpeSlot> convertRecipeNoOre(List<ItemStack> slotList) {
		List<IRecpeSlot> list = new ArrayList<>();
		for (int i = 0; i < 81; i++) {
			if (slotList.size() > i) {
				list.add(new RecipeSlotNoItem());
			} else {
				list.add(new RecipeSlotItemStack(slotList.get(i)));

			}
		}
		return list;
	}

	private static List<IRecpeSlot> convertRecipeOre(List<Object> slotList) {
		List<IRecpeSlot> list = new ArrayList<>();
		for (int i = 0; i < 81; i++) {
			if (slotList.size() > i) {
				list.add(new RecipeSlotNoItem());
			} else {
				Object slot = slotList.get(i);
				if (slot instanceof ItemStack) {
					list.add(new RecipeSlotItemStack((ItemStack) slot));
				}
				if (slot instanceof List) {
					list.add(new RecipeSlotOre((List<ItemStack>) slot));
				}

			}
		}
		return list;
	}

	public static List<IRecpeSlot> convertRecipe(IRecipe recipe) {
		if (recipe instanceof ExtremeShapedRecipe) {
			ExtremeShapedRecipe extremeRecipe = (ExtremeShapedRecipe) recipe;
			return convertRecipeNoOre(Arrays.asList(extremeRecipe.recipeItems));
		}

		if (recipe instanceof ExtremeShapelessRecipe) {
			ExtremeShapelessRecipe extremeRecipe = (ExtremeShapelessRecipe) recipe;
			return convertRecipeNoOre(extremeRecipe.recipeItems);
		}
		if (recipe instanceof ExtremeShapedOreRecipe) {
			ExtremeShapedOreRecipe extremeRecipe = (ExtremeShapedOreRecipe) recipe;
			return convertRecipeOre(Arrays.asList(extremeRecipe.getInput()));
		}

		if (recipe instanceof ExtremeShapelessOreRecipe) {
			ExtremeShapelessOreRecipe extremeRecipe = (ExtremeShapelessOreRecipe) recipe;
			return convertRecipeOre(extremeRecipe.getInput());
		}
		return new ArrayList<>();
	}
}
