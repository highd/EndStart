package com.highd120.endstart.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.highd120.endstart.block.BlockChar.Color;
import com.highd120.endstart.item.ItemExtra;
import com.highd120.endstart.util.item.ItemManager;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CharRecipe {
    public static final List<CharRecipeData> recipes = new ArrayList<>();

    public static CharRecipeData createRecipe(ItemStack output, Color color,
            ItemStack... inputs) {
        List<ItemStack> inputList = Arrays.asList(inputs);
        return new CharRecipeData(inputList, output, color);
    }
    
    public static void init() {
        recipes.add(createRecipe(new ItemStack(Items.BUCKET),
                Color.RED,
                new ItemStack(Blocks.LEAVES), new ItemStack(Blocks.LEAVES),
                new ItemStack(Blocks.LEAVES), new ItemStack(Blocks.LEAVES)));
        recipes.add(createRecipe(
                ItemManager.getItemStack(BlockNoLifeSkull.class),
                Color.GREEN,
                ItemManager.getItemStack(ItemExtra.class, 2),
                ItemManager.getItemStack(ItemExtra.class, 3),
                ItemManager.getItemStack(ItemExtra.class, 2),
                ItemManager.getItemStack(ItemExtra.class, 3)));
        recipes.add(createRecipe(new ItemStack(Items.APPLE),
                Color.RED,
                new ItemStack(Blocks.BEDROCK), new ItemStack(Blocks.LEAVES),
                new ItemStack(Blocks.LEAVES), new ItemStack(Blocks.BEDROCK)));
    }
    
    public static Optional<ItemStack> craft(List<ItemStack> inputs, Color color) {
    	for (CharRecipeData data: recipes) {
    		if (data.checkRecipe(inputs, color)) {
    			return Optional.of(data.getOutput());
    		}
    	}
		return Optional.empty();
    }
}
