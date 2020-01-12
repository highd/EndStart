package com.highd120.endstart.gui;

import java.util.List;
import java.util.stream.Collectors;

import com.highd120.endstart.EndStartMessages;
import com.highd120.endstart.block.TileAutoDireCraftingTable;
import com.highd120.endstart.network.PacketNormal;

import morph.avaritia.recipe.extreme.ExtremeCraftingManager;
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

public class ContainerAutoExtremeCrafting extends Container {

	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	public InventoryAutoDireCraftSelect slecter;
	protected World worldObj;
	protected BlockPos pos;
	private TileAutoDireCraftingTable table;

	private List<ItemStack> recipeList;

	public ContainerAutoExtremeCrafting(InventoryPlayer player, World world, BlockPos pos,
			TileAutoDireCraftingTable table) {

		this.worldObj = world;
		this.pos = pos;
		this.table = table;
		craftMatrix = new InventoryAutoDireCrafting(this, table);
		craftResult = new InventoryAutoDireCraftResult(table);
		slecter = new InventoryAutoDireCraftSelect(table);
		this.addSlotToContainer(new Slot(this.craftResult, 0, 210, 80));
		int wy;
		int ex;

		for (wy = 0; wy < 9; ++wy) {
			for (ex = 0; ex < 9; ++ex) {
				this.addSlotToContainer(new Slot(this.craftMatrix, ex + wy * 9, 12 + ex * 18, 8 + wy * 18));
			}
		}
		this.addSlotToContainer(new SlotSelecter(slecter, 100, 14 + 10 * 18, 8));

		for (wy = 0; wy < 3; ++wy) {
			for (ex = 0; ex < 9; ++ex) {
				this.addSlotToContainer(new Slot(player, ex + wy * 9 + 9, 39 + ex * 18, 174 + wy * 18));
			}
		}

		for (ex = 0; ex < 9; ++ex) {
			this.addSlotToContainer(new Slot(player, ex, 39 + ex * 18, 232));
		}

		int recipeId = table.getRecipeId();

		recipeList = ExtremeCraftingManager.getInstance().getRecipeList().stream()
				.map(recipe -> recipe.getRecipeOutput()).collect(Collectors.toList());
		slecter.setInventorySlotContents(0, recipeList.get(recipeId));
		this.onCraftMatrixChanged(this.craftMatrix);
	}

	public void nextRecipe() {
		int recipeId = table.getRecipeId();
		recipeId++;
		if (recipeId == recipeList.size()) {
			recipeId = 0;
		}
		table.setRecipeId(recipeId);
		table.markDirty();
		slecter.setInventorySlotContents(0, recipeList.get(recipeId));
		PacketNormal packet = new PacketNormal(table.getPos(), null);
		packet.setValue(recipeId);
		EndStartMessages.INSTANCE.sendToServer(packet);
	}

	public void prevRecipe() {
		int recipeId = table.getRecipeId();
		recipeId--;
		if (recipeId == -1) {
			recipeId = recipeList.size() - 1;
		}
		table.setRecipeId(recipeId);
		table.markDirty();
		slecter.setInventorySlotContents(0, recipeList.get(recipeId));
		PacketNormal packet = new PacketNormal(table.getPos(), null);
		packet.setValue(recipeId);
		EndStartMessages.INSTANCE.sendToServer(packet);
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
			return null;
		}
		ItemStack oldItemStack = slot.getStack();
		ItemStack newItemStack = oldItemStack.copy();
		if (slotNumber < 82 && !mergeItemStack(newItemStack, 83, 119, false)) {
			return null;
		}
		if (slotNumber == 82) {
			return null;
		}
		if (slotNumber > 82 && slotNumber < 119 && !mergeItemStack(newItemStack, 1, 82, false)) {
			return null;
		}
		if (oldItemStack.stackSize == newItemStack.stackSize) {
			return null;
		}
		if (newItemStack.stackSize == 0) {
			slot.putStack(null);
		} else {
			slot.putStack(newItemStack);
		}
		slot.onPickupFromSlot(player, newItemStack);
		return newItemStack;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		System.out.println("slotId = " + slotId + ", dragType = " + dragType + ", clickTypeIn = " + clickTypeIn);
		if (slotId == 82) {
			return null;
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

}