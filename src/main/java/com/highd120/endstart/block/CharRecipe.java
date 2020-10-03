package com.highd120.endstart.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.highd120.endstart.block.BlockChar.Color;
import com.highd120.endstart.item.ModItems;

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
        recipes.add(createRecipe(new ItemStack(Blocks.DIRT, 8),
                Color.RED,
                new ItemStack(ModItems.itemBlood, 1, 1),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand)));
        
        recipes.add(createRecipe(new ItemStack(ModItems.water),
                Color.RED,
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand)));

        recipes.add(createRecipe(new ItemStack(Blocks.LOG, 3),
                Color.RED,
                new ItemStack(Blocks.DIRT), new ItemStack(Blocks.DIRT),
                new ItemStack(Blocks.DIRT), new ItemStack(Blocks.DIRT),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand)));
        
        recipes.add(createRecipe(new ItemStack(Items.POTATO, 2),
                Color.RED,
                new ItemStack(Blocks.DIRT), new ItemStack(Blocks.DIRT),
                new ItemStack(Blocks.DIRT), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand)));
        
        recipes.add(createRecipe(new ItemStack(Items.WHEAT_SEEDS, 2),
                Color.RED,
                new ItemStack(Blocks.DIRT), new ItemStack(Blocks.DIRT),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand)));
        
        recipes.add(createRecipe(new ItemStack(Blocks.COBBLESTONE, 8),
                Color.RED,
                new ItemStack(Items.STICK), 
                new ItemStack(Blocks.DIRT), new ItemStack(Blocks.DIRT),
                new ItemStack(Blocks.DIRT), new ItemStack(Blocks.DIRT),
                new ItemStack(Blocks.DIRT), new ItemStack(Blocks.DIRT),
                new ItemStack(Blocks.DIRT), new ItemStack(Blocks.DIRT)));

        recipes.add(createRecipe(new ItemStack(Blocks.GRAVEL, 8),
                Color.RED,
                new ItemStack(Items.STICK), 
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE)));

        recipes.add(createRecipe(new ItemStack(ModItems.endPickaxe),
                Color.RED,
                new ItemStack(Items.STICK), new ItemStack(Items.STICK), 
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand)));


        recipes.add(createRecipe(new ItemStack(Blocks.SAND, 8),
                Color.RED,
                new ItemStack(Items.STICK), 
                new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.GRAVEL),
                new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.GRAVEL),
                new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.GRAVEL),
                new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.GRAVEL)));

        recipes.add(createRecipe(new ItemStack(Blocks.FURNACE, 8),
                Color.RED_BLACK,
                new ItemStack(Items.FLINT), new ItemStack(Items.FLINT),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE)));

        recipes.add(createRecipe(new ItemStack(Items.STONE_HOE),
                Color.RED_BLACK,
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Items.STICK), new ItemStack(Items.STICK)));

        recipes.add(createRecipe(new ItemStack(Items.STONE_PICKAXE),
                Color.RED_BLACK,
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Items.STICK), new ItemStack(Items.STICK)));

        recipes.add(createRecipe(new ItemStack(Items.BONE, 2),
                Color.RED_BLACK,
                new ItemStack(Blocks.SAND), new ItemStack(Blocks.SAND),
                new ItemStack(Blocks.SAND), new ItemStack(Blocks.SAND),
                new ItemStack(Blocks.SAND), new ItemStack(Blocks.SAND),
                new ItemStack(Blocks.SAND), new ItemStack(Blocks.SAND),
                new ItemStack(Blocks.SAND), new ItemStack(Blocks.SAND),
                new ItemStack(Blocks.SAND), new ItemStack(Blocks.SAND),
                new ItemStack(Blocks.SAND), new ItemStack(Blocks.SAND),
                new ItemStack(Blocks.SAND), new ItemStack(Blocks.SAND)));

        recipes.add(createRecipe(new ItemStack(Items.STRING),
                Color.RED_BLACK,
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand)));

        recipes.add(createRecipe(new ItemStack(Items.STRING),
                Color.RED_BLACK,
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand),
                new ItemStack(ModBlocks.endSand), new ItemStack(ModBlocks.endSand)));        

        recipes.add(createRecipe(new ItemStack(Blocks.DIRT, 1, 2),
                Color.GREEN,
                new ItemStack(Blocks.DIRT), new ItemStack(Items.WHEAT_SEEDS),
                new ItemStack(Items.MELON_SEEDS), new ItemStack(Items.SUGAR)));

        recipes.add(createRecipe(new ItemStack(Items.STONE_AXE),
                Color.GREEN,
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Items.STICK), new ItemStack(Items.STICK)));

        recipes.add(createRecipe(new ItemStack(Items.BLAZE_ROD),
                Color.GREEN,
                new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER),
                new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER),
                new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER),
                new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER)));

        recipes.add(createRecipe(new ItemStack(Blocks.CRAFTING_TABLE),
                Color.GREEN,
                new ItemStack(Blocks.PLANKS), new ItemStack(Blocks.PLANKS),
                new ItemStack(Blocks.PLANKS), new ItemStack(Blocks.PLANKS),
                new ItemStack(Items.IRON_INGOT)));

        recipes.add(createRecipe(new ItemStack(Items.LAVA_BUCKET),
                Color.GREEN,
                new ItemStack(Blocks.TORCH), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Items.BUCKET), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE)));

        recipes.add(createRecipe(new ItemStack(Items.LAVA_BUCKET, 2),
                Color.GREEN,
                new ItemStack(Items.LAVA_BUCKET), new ItemStack(Items.BUCKET), 
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE),
                new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.COBBLESTONE)));

        recipes.add(createRecipe(new ItemStack(Blocks.SAPLING),
                Color.GREEN,
                new ItemStack(ModItems.itemBlood, 1, 0), new ItemStack(ModItems.itemBlood, 1, 0), 
                new ItemStack(ModItems.itemBlood, 1, 1), new ItemStack(ModItems.itemBlood, 1, 1), 
                new ItemStack(ModItems.itemBlood, 1, 2), new ItemStack(ModItems.itemBlood, 1, 2), 
                new ItemStack(Items.BEETROOT), new ItemStack(Blocks.PUMPKIN),
                new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.MELON),
                new ItemStack(Items.POTATO), new ItemStack(Items.CARROT)));

        recipes.add(createRecipe(new ItemStack(Blocks.GRASS),
                Color.GREEN,
                new ItemStack(Blocks.DIRT), new ItemStack(Blocks.SAPLING)));
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
