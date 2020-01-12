package com.highd120.endstart.gui;

import javax.annotation.Nullable;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSelecter extends Slot {

	public SlotSelecter(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack stack) {
		return false;
	}

}
