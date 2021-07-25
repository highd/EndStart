package com.highd120.endstart.block.charmagic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.block.BlockChar;
import com.highd120.endstart.block.ModBlocks;
import com.highd120.endstart.block.BlockChar.Color;
import com.highd120.endstart.block.base.ListAndMainRecipeData;
import com.highd120.endstart.block.crafter.CrafterRecipeData;
import com.highd120.endstart.block.crafter.CrafterRecipe.JeiData;
import com.highd120.endstart.item.ModItems;
import com.highd120.endstart.jei.IListAndMainRecipeJeiData;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CharRecipe {
    public static final List<CharRecipeData> recipes = new ArrayList<>();
    public static final String UID = EndStartMain.MOD_ID + ".char";

    public static class JeiData implements IListAndMainRecipeJeiData {

		@Override
		public String getTitle() {
			return I18n.format(EndStartMain.MOD_ID + ".char");
		}

		@Override
		public String getUid() {
			return UID;
		}

		@Override
		public ListAndMainRecipeData parseIngredient(IIngredients ingredients) {
	    	List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM); 
	        ItemStack main = inputs.get(0).get(0);
	        List<ItemStack> injectionList = inputs.get(1);
	        ItemStack output = ingredients.getOutputs(VanillaTypes.ITEM).get(0).get(0);
	        return new CrafterRecipeData(main, injectionList, output, 0);
		}
    }
    
    public static IListAndMainRecipeJeiData createJeiData() {
		return new JeiData();
    }

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
                new ItemStack(Items.STICK), new ItemStack(Items.STICK), 
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

        recipes.add(createRecipe(new ItemStack(ModBlocks.stove),
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

        recipes.add(createRecipe(new ItemStack(Items.BONE, 3),
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

        recipes.add(createRecipe(new ItemStack(Blocks.GRASS),
                Color.GREEN,
                new ItemStack(Blocks.DIRT), new ItemStack(Blocks.SAPLING)));

        recipes.add(createRecipe(new ItemStack(ModItems.endHammer),
                Color.RED_BLACK,
                new ItemStack(Blocks.END_STONE), new ItemStack(Blocks.END_STONE),
                new ItemStack(Items.STICK), new ItemStack(Items.STICK)));

        recipes.add(createRecipe(new ItemStack(ModItems.fireStater),
                Color.RED_BLACK,
                new ItemStack(Items.STICK), new ItemStack(Items.STRING),
                new ItemStack(Items.STICK), new ItemStack(Items.STICK)));
    }
    
    public static Optional<Integer> craft(List<ItemStack> inputs, Color color) {
    	for (int i = 0; i < recipes.size(); i++) {
    		CharRecipeData data = recipes.get(i);
    		if (data.checkRecipe(inputs, color)) {
    			return Optional.of(i);
    		}
    	}
		return Optional.empty();
    }
}
