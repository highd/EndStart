package com.highd120.endstart.util.recipe;

import net.minecraft.item.ItemStack;

public class RecipeSlotNoItem implements IRecpeSlot {

	@Override
	public ItemStack getDisplayItem() {
		return null;
	}

	@Override
	public boolean isValid(ItemStack itemStack) {
		return itemStack == null;
	}

}
