package com.highd120.endstart.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.crafting.IRecipe;

public class SlotAutoCrafting extends Slot {

	public SlotAutoCrafting(IInventory inventoryIn, int index, int xPosition, int yPosition, IRecipe recipe,
			int recipeId) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public int getSlotStackLimit() {
		return super.getSlotStackLimit();
	}

}
