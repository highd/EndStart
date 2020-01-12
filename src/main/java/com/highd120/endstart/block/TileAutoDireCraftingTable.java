package com.highd120.endstart.block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.highd120.endstart.EndStartConfig;
import com.highd120.endstart.gui.InventoryAutoDireCrafting;
import com.highd120.endstart.network.TileEntityHasPacket;
import com.highd120.endstart.util.ItemUtil;

import morph.avaritia.recipe.extreme.ExtremeCraftingManager;
import morph.avaritia.recipe.extreme.ExtremeShapedOreRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapedRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapelessOreRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapelessRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileAutoDireCraftingTable extends TileHasInventory implements TileEntityHasPacket<Integer> {

	public int recipeId = 0;

	private EnergyStorage storage = new EnergyStorage(EndStartConfig.AutoExtremeCraftingTickCost * 100);

	public int getMaxEnergyStoraged() {
		return storage.getMaxEnergyStored();
	}

	public int getEnergyStoraged() {
		return storage.getEnergyStored();
	}

	@Override
	public void update() {
		if (storage.getEnergyStored() < EndStartConfig.AutoExtremeCraftingTickCost) {
			return;
		}
		InventoryCrafting craft = new InventoryAutoDireCrafting(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer entityPlayer) {
				return false;
			}
		}, this);

		List<IRecipe> recipeList = ExtremeCraftingManager.getInstance().getRecipeList();
		IRecipe recipe = recipeList.get(recipeId);
		ItemStack[] remaining = recipe.getRemainingItems(craft);
		if (!recipe.matches(craft, worldObj)) {
			return;
		}
		ItemStack result = recipe.getCraftingResult(craft).copy();
		int stackSize = getItemStack(0).map(item -> item.stackSize).orElse(0);
		if (stackSize + result.stackSize > result.getMaxStackSize()) {
			return;
		}
		storage.extractEnergy(EndStartConfig.AutoExtremeCraftingTickCost, false);
		result.stackSize += stackSize;
		setInventory(0, result);
		TileEntity upTileEntity = worldObj.getTileEntity(getPos().up());
		IInventory upInventory = (IInventory) upTileEntity;
		for (int i = 0; i < 81; i++) {
			ItemStack itemStack = getItemStack(i + 1).orElse(null);
			if (itemStack == null) {
				continue;
			}
			if (!ItemStack.areItemsEqualIgnoreDurability(itemStack, remaining[i])) {
				itemStack.stackSize--;
			}
			if (remaining[i] != null) {
				ItemStack remainingItem = inesetInventory(new InvWrapper(upInventory), remaining[i]);
				if (remainingItem != null) {
					itemStack = remainingItem;
					setInventory(i + 1, remainingItem);
				}
			}
			if (itemStack.stackSize <= 0) {
				setInventory(i + 1, null);
			}
		}
	}

	private static ItemStack inesetInventory(InvWrapper inventory, ItemStack itemstack) {
		if (inventory == null || inventory.getInv() == null || itemstack == null) {
			return itemstack;
		}
		for (int i = 0; i < inventory.getSlots(); i++) {
			itemstack = inventory.insertItem(i, itemstack, false);
			if (itemstack == null || itemstack.stackSize == 0) {
				return null;
			}
		}
		return itemstack;
	}

	@Override
	public void breakEvent() {
		IntStream.range(0, itemHandler.getSlots()).forEach(i -> {
			if (i != 82) {
				ItemUtil.dropItem(worldObj, pos, itemHandler.getStackInSlot(i));
			}
		});
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	@Override
	public void subReadNbt(NBTTagCompound compound) {
		recipeId = compound.getInteger("recipeId");
		if (compound.hasKey("energy")) {
			storage.receiveEnergy(compound.getInteger("energy"), false);
		}
		super.subReadNbt(compound);
	}

	@Override
	public void subWriteNbt(NBTTagCompound compound) {
		compound.setInteger("recipeId", recipeId);
		compound.setInteger("energy", storage.getEnergyStored());
		super.subWriteNbt(compound);
	}

	@Override
	public SimpleItemStackHandler createItemStackHandler() {
		return new ItemHadler(this, 83);
	}

	@Override
	public Class<Integer> getDataClass() {
		return Integer.class;
	}

	@Override
	public void execute(EntityPlayerMP playerEntity, Integer data) {
		recipeId = data;
	}

	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(storage);
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}

	public static class ItemHadler extends SimpleItemStackHandler {
		private TileAutoDireCraftingTable table;

		public ItemHadler(TileAutoDireCraftingTable inv, int limit) {
			super(inv, limit);
			table = inv;
		}

		public boolean isValidRecipe(ItemStack stack, int slot) {
			List<IRecipe> recipeList = ExtremeCraftingManager.getInstance().getRecipeList();
			IRecipe recipe = recipeList.get(table.recipeId);
			Object recipeItem = null;
			if (recipe instanceof ExtremeShapedRecipe) {
				ExtremeShapedRecipe extremeRecipe = (ExtremeShapedRecipe) recipe;
				if (extremeRecipe.recipeItems.length > slot - 1) {
					recipeItem = extremeRecipe.recipeItems[slot - 1];
				}
			}

			if (recipe instanceof ExtremeShapelessRecipe) {
				ExtremeShapelessRecipe extremeRecipe = (ExtremeShapelessRecipe) recipe;
				if (extremeRecipe.recipeItems.size() > slot - 1) {
					recipeItem = extremeRecipe.recipeItems.get(slot - 1);
				}
			}
			if (recipe instanceof ExtremeShapedOreRecipe) {
				ExtremeShapedOreRecipe extremeRecipe = (ExtremeShapedOreRecipe) recipe;
				if (extremeRecipe.getInput().length > slot - 1) {
					recipeItem = extremeRecipe.getInput()[slot - 1];
				}
			}

			if (recipe instanceof ExtremeShapelessOreRecipe) {
				ExtremeShapelessOreRecipe extremeRecipe = (ExtremeShapelessOreRecipe) recipe;
				if (extremeRecipe.getInput().size() > slot - 1) {
					recipeItem = extremeRecipe.getInput().get(slot - 1);
				}
			}
			if (recipeItem == null) {
				return false;
			}
			if (recipeItem instanceof ItemStack) {
				if (!ItemUtil.equalItemStackForRecipe((ItemStack) recipeItem, stack)) {
					return false;
				}
			}
			if (recipeItem instanceof List) {
				List<ItemStack> itemList = (List<ItemStack>) recipeItem;
				boolean flag = false;
				for (ItemStack item : itemList) {
					if (ItemUtil.equalItemStackForRecipe(item, stack)) {
						flag = true;
					}
				}
				if (!flag) {
					return false;
				}
			}
			return true;
		}

		@Override
		protected int getStackLimit(int slot, ItemStack stack) {
			List<Integer> stackSizeList = new ArrayList<>();
			for (int i = 0; i < 81; i++) {
				if (stack != null && isValidRecipe(stack, i + 1)) {
					stackSizeList.add(table.getItemStack(i + 1).map(item -> item.stackSize).orElse(0));
				}
			}
			int maxStackSize = stackSizeList.stream().max(Integer::compare).orElse(0);
			int minStackSize = stackSizeList.stream().min(Integer::compare).orElse(0);
			int stackSize = maxStackSize == minStackSize ? maxStackSize + 1 : maxStackSize;
			return Math.min(super.getStackLimit(slot, stack), stackSize);
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (slot == 0) {
				return stack;
			}
			if (!isValidRecipe(stack, slot)) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if (slot != 0) {
				return null;
			}
			return super.extractItem(slot, amount, simulate);
		}
	}
}
