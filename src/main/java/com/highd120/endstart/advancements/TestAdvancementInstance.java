package com.highd120.endstart.advancements;

import com.highd120.endstart.block.charmagic.CharRecipeData;

import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TestAdvancementInstance extends AbstractCriterionInstance {
	private final ItemPredicate output;
	public TestAdvancementInstance(ResourceLocation criterionIn, ItemPredicate output) {
		super(criterionIn);
		this.output = output;
	}

	public boolean test(ItemStack data) {
		return output.test(data);
	}

}
