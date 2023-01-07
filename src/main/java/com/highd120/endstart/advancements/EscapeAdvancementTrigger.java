package com.highd120.endstart.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.highd120.endstart.EndStartMain;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EscapeAdvancementTrigger extends AdvancementTriggerBase<EscapeAdvancementInstance> {
	private static final ResourceLocation ID = new ResourceLocation(EndStartMain.MOD_ID, "escape");

	@Override
	public EscapeAdvancementInstance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
		return new EscapeAdvancementInstance(getId());
	}
	
	public void trigger(EntityPlayerMP player) {
		triggerBase(player, instance -> instance.test());
	}

	@Override
	public ResourceLocation getId() {
		return ID;
	}
}