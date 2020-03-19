package com.highd120.endstart.util.recipe;

import net.minecraft.item.ItemStack;

public interface IRecpeSlot {
	ItemStack getDisplayItem();

	boolean isValid(ItemStack itemStack);
}
