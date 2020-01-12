package com.highd120.endstart.gui;

import com.highd120.endstart.block.TileAutoDireCraftingTable;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryAutoDireCrafting extends InventoryCrafting {

	private TileAutoDireCraftingTable craft;
	private Container container;

	public InventoryAutoDireCrafting(Container cont, TileAutoDireCraftingTable table) {
		super(cont, 9, 9);
		craft = table;
		container = cont;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot >= this.getSizeInventory() ? null : craft.getItemStack(slot + 1).orElse(null);
	}

	@Override
	public ItemStack getStackInRowAndColumn(int row, int column) {
		if (row >= 0 && row < 9) {
			int x = row + column * 9;
			return this.getStackInSlot(x);
		} else {
			return null;
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int decrement) {
		ItemStack stack = craft.getItemStack(slot + 1).orElse(null);
		if (stack != null) {
			ItemStack itemstack;
			if (stack.stackSize <= decrement) {
				itemstack = stack.copy();
				stack = null;
				craft.setInventory(slot + 1, null);
				this.container.onCraftMatrixChanged(this);
				return itemstack;
			} else {
				itemstack = stack.splitStack(decrement);
				if (stack.stackSize == 0) {
					stack = null;
				}
				this.container.onCraftMatrixChanged(this);
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		craft.setInventory(slot + 1, itemstack);
		this.container.onCraftMatrixChanged(this);
	}

}
