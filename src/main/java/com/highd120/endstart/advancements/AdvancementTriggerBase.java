package com.highd120.endstart.advancements;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.highd120.endstart.EndStartMain;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.ICriterionTrigger.Listener;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class AdvancementTriggerBase<T extends ICriterionInstance> implements ICriterionTrigger<T> {
	private Map<PlayerAdvancements, Set<Listener<T>>> listenerMap = Maps.newHashMap();
	
	public void triggerBase(EntityPlayerMP player, Predicate<T> checker) {
		Set<Listener<T>> listenerList = listenerMap.get(player.getAdvancements());
        if (listenerList != null) {
        	listenerList.forEach(listener -> {
        		if (checker.test(listener.getCriterionInstance())) {
        			listener.grantCriterion(player.getAdvancements());
        		};
        	});
        }
	}

	@Override
	public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<T> listener) {
		Set<Listener<T>> listenerList = listenerMap.get(playerAdvancementsIn);
        if (listenerList == null) {
        	listenerList = Sets.newHashSet();
        	listenerMap.put(playerAdvancementsIn, listenerList);
        }
    	listenerList.add(listener);
	}

	@Override
	public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<T> listener) {
		Set<Listener<T>> listenerList = listenerMap.get(playerAdvancementsIn);
        if (listenerList != null) {
        	listenerList.remove(listener);
        }
	}

	@Override
	public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
		listenerMap.remove(playerAdvancementsIn);
	}
}

