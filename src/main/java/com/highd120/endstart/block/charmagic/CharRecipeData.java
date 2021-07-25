package com.highd120.endstart.block.charmagic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.highd120.endstart.block.BlockChar;
import com.highd120.endstart.block.BlockChar.Color;
import com.highd120.endstart.block.base.CrafterUtil;
import com.highd120.endstart.block.base.ListAndMainRecipeData;
import com.highd120.endstart.item.ModItems;
import com.highd120.endstart.util.ItemUtil;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;


@Builder
@Value
public class CharRecipeData implements ListAndMainRecipeData {
    private List<ItemStack> inputList;
    private ItemStack output;
    private BlockChar.Color color;

    public static CharRecipeData parseIngredient(IIngredients ingredients) {
    	List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM); 
        ItemStack main = inputs.get(0).get(0);
        List<ItemStack> inputList = inputs.get(1);
        ItemStack output = ingredients.getOutputs(VanillaTypes.ITEM).get(0).get(0);
        BlockChar.Color color = BlockChar.Color.values()[main.getMetadata()];
        return new CharRecipeData(inputList, output, color);
    }
    public boolean checkRecipe(List<ItemStack> itemList, BlockChar.Color color) {
    	if (color != this.color) return false;
		return CrafterUtil.checkListRecipe(inputList, itemList);
    }
    
    public ItemStack getMain() {
    	return new ItemStack(ModItems.chalk, 1, color.ordinal());
    }
}
