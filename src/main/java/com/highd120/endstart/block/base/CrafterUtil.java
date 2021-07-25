package com.highd120.endstart.block.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.highd120.endstart.block.BlockChar.Color;
import com.highd120.endstart.block.charmagic.CharRecipeData;
import com.highd120.endstart.util.ItemUtil;

import lombok.Builder;
import lombok.Value;
import net.minecraft.item.ItemStack;

public class CrafterUtil {
	public static boolean checkListRecipe(List<ItemStack> recipeList, List<ItemStack> inputList) {
		List<ItemStack> cloned = new ArrayList<>();
		for (ItemStack item: inputList) {
			cloned.add(item);
		}
		for (ItemStack recipe: recipeList) {
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
}
