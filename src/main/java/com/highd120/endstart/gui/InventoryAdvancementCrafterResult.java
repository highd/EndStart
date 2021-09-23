package com.highd120.endstart.gui;

import com.highd120.endstart.block.advancementcafter.TileAdvancementCrafter;

import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;

public class InventoryAdvancementCrafterResult extends InventoryCraftResult {

	private TileAdvancementCrafter craft;

	public InventoryAdvancementCrafterResult(TileAdvancementCrafter table) {
		craft = table;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return craft.getItemStack(0).orElse(ItemStack.EMPTY);
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		ItemStack stack = craft.getItemStack(0).orElse(ItemStack.EMPTY);
		if (stack != null) {
			ItemStack itemstack = stack;
			craft.setInventory(0, ItemStack.EMPTY);
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		craft.setInventory(0, par2ItemStack);
	}

}