package com.highd120.endstart.util.recipe;

import java.util.List;

import com.highd120.endstart.util.ItemUtil;

import net.minecraft.item.ItemStack;

public class RecipeSlotOre implements IRecpeSlot {
	List<ItemStack> recipeItemList;

	public RecipeSlotOre(List<ItemStack> itemStackList) {
		this.recipeItemList = itemStackList;
	}

	@Override
	public ItemStack getDisplayItem() {
		return recipeItemList.get(0);
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		for (ItemStack recipeItem : recipeItemList) {
			if (!ItemUtil.equalItemStackForRecipe(itemStack, recipeItem)) {
				return false;
			}
		}
		return true;
	}

}
