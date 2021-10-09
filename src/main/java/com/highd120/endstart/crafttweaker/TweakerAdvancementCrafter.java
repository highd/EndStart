package com.highd120.endstart.crafttweaker;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.highd120.endstart.block.advancementcafter.AdvancementCrafterRecipe;
import com.highd120.endstart.block.advancementcafter.AdvancementCrafterRecipeData;
import com.highd120.endstart.recipe.IRecipeItem;
import com.highd120.endstart.recipe.RecipeItemStack;
import com.highd120.endstart.recipe.RecipeOreDictionary;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.endstart.AdvancementCrafter")
public class TweakerAdvancementCrafter {
	private static IRecipeItem convert(IIngredient data) {
		if (data == null) {
			return new RecipeItemStack(ItemStack.EMPTY);		}
		if (data instanceof IItemStack) {
			return new RecipeItemStack(CraftTweakerMC.getItemStack(data));
		} else if (data instanceof IOreDictEntry) {
			return new RecipeOreDictionary(((IOreDictEntry)data).getName());
		}
		return new RecipeItemStack(ItemStack.EMPTY);
	}
    @ZenMethod
    public static void add(IItemStack outputTweaker, IIngredient[] inputsTweaker, String[] advancementList, int energy) {
    	ItemStack output = CraftTweakerMC.getItemStack(outputTweaker);
    	List<IRecipeItem> inputList = Arrays.stream(inputsTweaker)
    			.map(input -> convert(input))
    			.collect(Collectors.toList());
    	AdvancementCrafterRecipeData recipe = AdvancementCrafterRecipeData.builder()
    			.inputList(inputList)
				.advancementsList(Arrays.asList(advancementList))
				.output(output)
				.minEnergy(energy)
				.build();
    	AdvancementCrafterRecipe.recipeList.add(recipe);
    	
    }
}
