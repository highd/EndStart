package com.highd120.endstart.advancements;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.block.charmagic.CharRecipeData;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TestAdvancementTrigger extends AdvancementTriggerBase<TestAdvancementInstance> {
	private static final ResourceLocation ID = new ResourceLocation(EndStartMain.MOD_ID, "test");

	@Override
	public TestAdvancementInstance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
		ItemPredicate item = ItemPredicate.deserialize(json.get("item"));
		return new TestAdvancementInstance(getId(), item);
	}
	
	public void trigger(EntityPlayerMP player, ItemStack output) {
		triggerBase(player, instance -> instance.test(output));
	}

	@Override
	public ResourceLocation getId() {
		return ID;
	}
}
