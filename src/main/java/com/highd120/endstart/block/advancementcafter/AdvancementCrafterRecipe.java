package com.highd120.endstart.block.advancementcafter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.highd120.endstart.recipe.IRecipeItem;
import com.highd120.endstart.recipe.RecipeItemStack;
import com.highd120.endstart.recipe.RecipeOreDictionary;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AdvancementCrafterRecipe {
	public static List<AdvancementCrafterRecipeData> recipeList = new ArrayList<>();
	
	private static List<IRecipeItem> convert(Object... recipes) {
		return Arrays.stream(recipes).map(recipe -> {
			if (recipe instanceof ItemStack) {
				return new RecipeItemStack((ItemStack)recipe);
			}
			return new RecipeOreDictionary((String)recipe);
		})
		.collect(Collectors.toList());
	}
	
	public static void init() {
		AdvancementCrafterRecipeData recipe = AdvancementCrafterRecipeData.builder()
			.inputList(convert(
				ItemStack.EMPTY					,	new ItemStack(Items.APPLE),		new ItemStack(Items.GOLD_INGOT),	new ItemStack(Items.APPLE),		ItemStack.EMPTY, 
				new ItemStack(Items.IRON_INGOT) ,	new ItemStack(Items.DIAMOND),	new ItemStack(Items.BOOK),			new ItemStack(Items.DIAMOND),	new ItemStack(Items.IRON_INGOT), 
				ItemStack.EMPTY					,	new ItemStack(Items.APPLE),		new ItemStack(Items.GOLD_INGOT),	new ItemStack(Items.APPLE),		ItemStack.EMPTY, 
				new ItemStack(Items.IRON_INGOT) ,	new ItemStack(Items.DIAMOND),	new ItemStack(Items.BOOK),			new ItemStack(Items.DIAMOND),	new ItemStack(Items.IRON_INGOT), 
				ItemStack.EMPTY					,	new ItemStack(Items.APPLE),		new ItemStack(Items.GOLD_INGOT),	new ItemStack(Items.APPLE),		ItemStack.EMPTY
			))
			.advancementsList(Arrays.asList(new String[]{
				"minecraft:story/mine_diamond", "minecraft:story/iron_tools"
			}))
			.output(new ItemStack(Items.ELYTRA))
			.minEnergy(1000000)
			.build();
		recipeList.add(recipe);
		AdvancementCrafterRecipeData recipe2 = AdvancementCrafterRecipeData.builder()
				.inputList(convert(
					ItemStack.EMPTY ,ItemStack.EMPTY ,ItemStack.EMPTY 					,ItemStack.EMPTY ,ItemStack.EMPTY, 
					ItemStack.EMPTY ,ItemStack.EMPTY ,ItemStack.EMPTY 					,ItemStack.EMPTY ,ItemStack.EMPTY, 
					ItemStack.EMPTY ,ItemStack.EMPTY ,new ItemStack(Items.GOLD_INGOT) 	,ItemStack.EMPTY ,ItemStack.EMPTY, 
					ItemStack.EMPTY ,ItemStack.EMPTY ,ItemStack.EMPTY 					,ItemStack.EMPTY ,ItemStack.EMPTY, 
					ItemStack.EMPTY ,ItemStack.EMPTY ,ItemStack.EMPTY 					,ItemStack.EMPTY ,ItemStack.EMPTY
				))
				.advancementsList(Arrays.asList(new String[]{
					"minecraft:story/mine_diamond", "minecraft:story/iron_tools"
				}))
				.output(new ItemStack(Items.DRAGON_BREATH))
				.minEnergy(4000000)
				.build();
		recipeList.add(recipe2);
		//logWood
		AdvancementCrafterRecipeData recipe3 = AdvancementCrafterRecipeData.builder()
				.inputList(convert(
					ItemStack.EMPTY ,ItemStack.EMPTY ,"fenceWood"		 					,ItemStack.EMPTY ,ItemStack.EMPTY, 
					ItemStack.EMPTY ,ItemStack.EMPTY ,ItemStack.EMPTY 					,ItemStack.EMPTY ,ItemStack.EMPTY, 
					ItemStack.EMPTY ,ItemStack.EMPTY ,new ItemStack(Items.GOLD_INGOT) 	,ItemStack.EMPTY ,ItemStack.EMPTY, 
					ItemStack.EMPTY ,ItemStack.EMPTY ,ItemStack.EMPTY 					,ItemStack.EMPTY ,ItemStack.EMPTY, 
					ItemStack.EMPTY ,ItemStack.EMPTY ,"fenceWood" 						,ItemStack.EMPTY ,ItemStack.EMPTY
				))
				.advancementsList(Arrays.asList(new String[]{
					"minecraft:story/mine_diamond"
				}))
				.output(new ItemStack(Items.BEETROOT))
				.minEnergy(4000000)
				.build();
		recipeList.add(recipe3);
	}
}
