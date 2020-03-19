package com.highd120.endstart.util.recipe;

import com.highd120.endstart.util.ItemUtil;

import net.minecraft.item.ItemStack;

public class RecipeSlotItemStack implements IRecpeSlot {
	ItemStack recipeitem;

	public RecipeSlotItemStack(ItemStack itemStack) {
		this.recipeitem = itemStack;
	}

	@Override
	public ItemStack getDisplayItem() {
		return recipeitem;
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		return ItemUtil.equalItemStackForRecipe(itemStack, recipeitem);
	}

}
