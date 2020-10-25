package com.highd120.endstart.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class StoveFuelList {
	public static List<ItemStack> list;
	
	public static void init() {
		list = new ArrayList<>();
		list.add(new ItemStack(Items.COAL));
		list.add(new ItemStack(Items.COAL, 1, 1));
	}
	
	public static boolean isFuel(ItemStack stack) {
		for (ItemStack item : list) {
			if (ItemStack.areItemStacksEqualUsingNBTShareTag(stack, item)) {
				return true;
			}
		}
		return false;
	}
}
