package com.highd120.endstart.recipe;

import java.util.Collections;
import java.util.List;

import com.highd120.endstart.util.ItemUtil;

import mezz.jei.api.gui.IGuiItemStackGroup;
import net.minecraft.item.ItemStack;

public class RecipeItemStack implements IRecipeItem {
	private final ItemStack recipeItem;
	public RecipeItemStack(ItemStack recipeItem) {
		this.recipeItem = recipeItem;
	}
	@Override
	public boolean checkRecipe(ItemStack item) {
		return ItemUtil.equalItemStackForRecipe(item, recipeItem);
	}
	@Override
	public void setJeiRecipe(int slotIndex, IGuiItemStackGroup itemStacks) {
		if (!recipeItem.isEmpty()) {
			itemStacks.set(slotIndex, recipeItem);
		}
	}
	@Override
	public List<ItemStack> getList() {
		return Collections.singletonList(recipeItem);
	}

}
