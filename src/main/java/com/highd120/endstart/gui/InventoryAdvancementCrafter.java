package com.highd120.endstart.gui;

import javax.annotation.Nullable;

import com.highd120.endstart.block.advancementcafter.TileAdvancementCrafter;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryAdvancementCrafter extends InventoryCrafting {

	private TileAdvancementCrafter craft;
	private Container container;

	public InventoryAdvancementCrafter(Container cont, TileAdvancementCrafter table) {
		super(cont, 5, 5);
		craft = table;
		container = cont;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot >= this.getSizeInventory() ? ItemStack.EMPTY : craft.getItemStack(slot + 1).orElse(ItemStack.EMPTY);
	}

	@Override
	public ItemStack getStackInRowAndColumn(int row, int column) {
		if (row >= 0 && row < 5) {
			int x = row + column * 5;
			return this.getStackInSlot(x);
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int decrement) {
		ItemStack stack = craft.getItemStack(slot + 1).orElse(null);
		if (stack != null) {
			ItemStack itemstack;
			if (stack.getCount() <= decrement) {
				itemstack = stack.copy();
				stack = null;
				craft.setInventory(slot + 1, ItemStack.EMPTY);
				this.container.onCraftMatrixChanged(this);
				return itemstack;
			} else {
				itemstack = stack.splitStack(decrement);
				if (stack.getCount() == 0) {
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
