package com.highd120.endstart.recipe;

import java.util.List;

import com.highd120.endstart.util.ItemUtil;

import mezz.jei.api.gui.IGuiItemStackGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeOreDictionary implements IRecipeItem {
	private final String oreDictionary;
	public RecipeOreDictionary(String oreDictionary) {
		this.oreDictionary = oreDictionary;
	}
	@Override
	public boolean checkRecipe(ItemStack item) {
		if (item.isEmpty()) return false;
		return OreDictionary.getOres(oreDictionary).stream()
			.filter(recipe -> ItemUtil.equalItemStackForRecipe(item, recipe))
			.findFirst()
			.isPresent();
	}
	@Override
	public void setJeiRecipe(int slotIndex, IGuiItemStackGroup itemStacks) {
		itemStacks.set(slotIndex, OreDictionary.getOres(oreDictionary));
	}
	@Override
	public List<ItemStack> getList() {
		return OreDictionary.getOres(oreDictionary);
	}

}
