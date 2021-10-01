package com.highd120.endstart.recipe;

import java.util.List;

import mezz.jei.api.gui.IGuiItemStackGroup;
import net.minecraft.item.ItemStack;

public interface IRecipeItem {
	boolean checkRecipe(ItemStack item);
	void setJeiRecipe(int slotIndex, IGuiItemStackGroup itemStacks);
	List<ItemStack> getList();
}
