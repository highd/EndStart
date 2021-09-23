package com.highd120.endstart.gui;

import java.util.List;
import java.util.stream.Collectors;

import com.highd120.endstart.EndStartMessages;
import com.highd120.endstart.block.advancementcafter.TileAdvancementCrafter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerAdvancementCrafter extends Container {

	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	protected World worldObj;
	protected BlockPos pos;
	private TileAdvancementCrafter table;

	private List<ItemStack> recipeList;

	public ContainerAdvancementCrafter(InventoryPlayer player, World world, BlockPos pos,
			TileAdvancementCrafter table) {

		this.worldObj = world;
		this.pos = pos;
		this.table = table;
		craftMatrix = new InventoryAdvancementCrafter(this, table);
		craftResult = new InventoryAdvancementCrafterResult(table);
		this.addSlotToContainer(new Slot(this.craftResult, 0, 184, 72));
		int wy;
		int ex;

		for (wy = 0; wy < 5; ++wy) {
			for (ex = 0; ex < 5; ++ex) {
				this.addSlotToContainer(new Slot(this.craftMatrix, ex + wy * 5, 57 + ex * 18, 35 + wy * 18));
			}
		}

		for (wy = 0; wy < 3; ++wy) {
			for (ex = 0; ex < 9; ++ex) {
				this.addSlotToContainer(new Slot(player, ex + wy * 9 + 9, 39 + ex * 18, 174 + wy * 18));
			}
		}

		for (ex = 0; ex < 9; ++ex) {
			this.addSlotToContainer(new Slot(player, ex, 39 + ex * 18, 232));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory matrix) {
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(pos) <= 64.0D;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		Slot slot = inventorySlots.get(slotNumber);
		if (slot == null || !slot.getHasStack()) {
			return ItemStack.EMPTY;
		}
		ItemStack oldItemStack = slot.getStack();
		ItemStack newItemStack = oldItemStack.copy();
		if (slotNumber < 26 && !mergeItemStack(newItemStack, 26, 61, false)) {
			return ItemStack.EMPTY;
		}
		if (slotNumber > 25 && slotNumber < 61 && !mergeItemStack(newItemStack, 1, 26, false)) {
			return ItemStack.EMPTY;
		}
		if (oldItemStack.getCount() == newItemStack.getCount()) {
			return ItemStack.EMPTY;
		}
		if (newItemStack.getCount() == 0) {
			slot.putStack(ItemStack.EMPTY);
		} else {
			slot.putStack(newItemStack);
		}
		return newItemStack;
	}

}