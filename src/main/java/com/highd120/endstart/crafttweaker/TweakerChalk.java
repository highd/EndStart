package com.highd120.endstart.crafttweaker;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.highd120.endstart.block.BlockChar;
import com.highd120.endstart.block.charmagic.CharRecipe;
import com.highd120.endstart.block.charmagic.CharRecipeData;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.endstart.Chalk")
public class TweakerChalk {
    @ZenMethod
    public static void add(IItemStack outputTweaker, int colorTweaker, IItemStack[] inputsTweaker) {
    	List<ItemStack> inputList = Arrays.stream(inputsTweaker)
    			.map(input -> CraftTweakerMC.getItemStack(input))
    			.collect(Collectors.toList());
    	ItemStack output = CraftTweakerMC.getItemStack(outputTweaker);
    	BlockChar.Color color = BlockChar.Color.values()[colorTweaker];
    	CharRecipeData data = CharRecipeData.builder()
    			.color(color)
    			.inputList(inputList)
    			.output(output)
    			.build();
    	CharRecipe.recipes.add(data);
    }
}
