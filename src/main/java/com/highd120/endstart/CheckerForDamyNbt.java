package com.highd120.endstart;

import java.util.Arrays;

import com.highd120.endstart.util.NbtTagUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CheckerForDamyNbt {
	@SubscribeEvent
	public static void onPlayerUpdate(LivingUpdateEvent event) {
		if (!(event.getEntityLiving() instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer)event.getEntity();
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if (Arrays.asList(EndStartConfig.addDamyNbtTarget).contains(stack.getItem().getRegistryName().toString())) {
				NbtTagUtil.setInterger("a", stack, 0);
			}
		}
	}
}
