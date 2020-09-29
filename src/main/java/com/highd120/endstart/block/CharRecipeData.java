package com.highd120.endstart.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.highd120.endstart.item.ItemChalk;
import com.highd120.endstart.util.ItemUtil;
import com.highd120.endstart.util.item.ItemManager;

import lombok.Builder;
import lombok.Value;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;


@Builder
@Value
public class CharRecipeData {
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
    public boolean checkRecipe(List<ItemStack> inputList, BlockChar.Color color) {
    	if (color != this.color) return false;
    	if (inputList.size() != this.inputList.size()) return false;
    	List<ItemStack> cloned = new ArrayList<>();
    	for (ItemStack item: inputList) {
    		cloned.add(item);
    	}
    	for (ItemStack recipe: this.inputList) {
    		boolean isFind = false;
    		for (int i = 0; i < cloned.size(); i++) {
    			ItemStack item = cloned.get(i);
    			if (ItemUtil.equalItemStackForRecipe(item, recipe)) {
    				isFind = true;
    				cloned.remove(i);
    				break;
    			}
    		}
    		if (!isFind) return false;
    	}
		return true;
    }
    
    public ItemStack getMain() {
        return ItemManager.getItemStack(ItemChalk.class, color.ordinal());
    }
    
    public List<List<ItemStack>> createIngredient() {
        List<List<ItemStack>> ingredient = new ArrayList<>();
        ItemStack chalk = getMain();
        ingredient.add(Collections.singletonList(chalk));
        ingredient.add(inputList);
        return ingredient;
    }
}
