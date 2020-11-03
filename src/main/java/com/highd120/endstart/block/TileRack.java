package com.highd120.endstart.block;

import java.util.ArrayList;
import java.util.List;

import com.highd120.endstart.util.ItemUtil;

import net.minecraft.item.ItemStack;

public class TileRack extends TileHasInventory {

	@Override
	public void update() {		
	}
    
	public List<ItemStack> getItems() {
		List<ItemStack> inputs = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			ItemStack stack = itemHandler.getStackInSlot(i);
			inputs.add(stack);
		}
		return inputs;
	}
	
	public void accessSlot(int slot, ItemStack item) {
		ItemStack slotItem = itemHandler.getStackInSlot(slot);
		if (!slotItem.isEmpty()) {
			ItemUtil.dropItem(getWorld(), getPos(), slotItem);
		}
		itemHandler.setItemStock(slot, item.copy());
		item.setCount(0);
	}

	@Override
	public SimpleItemStackHandler createItemStackHandler() {
		return new SimpleItemStackHandler(this, 8);
	}
}
