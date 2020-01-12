package com.highd120.endstart.gui;

import com.highd120.endstart.block.TileAutoDireCraftingTable;

import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;

public class InventoryAutoDireCraftSelect extends InventoryCraftResult {

	private TileAutoDireCraftingTable craft;

	public InventoryAutoDireCraftSelect(TileAutoDireCraftingTable table) {
		craft = table;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return craft.getItemStack(82).orElse(null);
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		ItemStack stack = craft.getItemStack(82).orElse(null);
		if (stack != null) {
			ItemStack itemstack = stack;
			craft.setInventory(82, null);
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		craft.setInventory(82, par2ItemStack);
	}

}
