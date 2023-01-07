package com.highd120.endstart.advancements;

import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EscapeAdvancementInstance extends AbstractCriterionInstance {
	public EscapeAdvancementInstance(ResourceLocation criterionIn) {
		super(criterionIn);
	}

	public boolean test() {
		return true;
	}
}