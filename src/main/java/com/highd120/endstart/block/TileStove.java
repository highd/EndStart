package com.highd120.endstart.block;

import com.highd120.endstart.block.BlockStove.State;
import com.highd120.endstart.block.base.TileHasInventory;
import com.highd120.endstart.util.ItemUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

public class TileStove extends TileHasInventory {
	private static final int FUEL_SLOT = 0;
	private static final int ITEM_SLOT = 1;
	private static final int CACHE_ITEM_SLOT = 2;
	private static final int CACHE_RESULT_SLOT = 3;
	
	private int fuelCount = 0;
	private int itemCount = 0;
	private boolean isCheckRecipe = false;
	private boolean isCanSmeletResult = false;
	
	public ItemStack getItem() {
		return itemHandler.getStackInSlot(ITEM_SLOT);
	}
	
	@Override
	public void subReadNbt(NBTTagCompound compound) {
		super.subReadNbt(compound);
		fuelCount = compound.getInteger("fuelCount");
		itemCount = compound.getInteger("itemCount");
		isCheckRecipe = compound.getBoolean("isCheckRecipe");
		isCanSmeletResult = compound.getBoolean("isCanSmeletResult");
	}
	
	@Override
	public void subWriteNbt(NBTTagCompound compound) {
		super.subWriteNbt(compound);
		compound.setInteger("fuelCount", fuelCount);
		compound.setInteger("itemCount", itemCount);
		compound.setBoolean("isCheckRecipe", isCheckRecipe);
		compound.setBoolean("isCanSmeletResult", isCanSmeletResult);
	}
	
	void changeItem(ItemStack stack, boolean isCreative) {
		ItemStack input = stack.copy();
		input.setCount(1);
		ItemStack item = itemHandler.getStackInSlot(ITEM_SLOT);
		if (!item.isEmpty() && !StoveFuelList.isFuel(input)) {
			removeItem();
		}
		if (!stack.isEmpty()) {
			addItem(stack, isCreative);
		}
	}

	void addItem(ItemStack stack, boolean isCreative) {
		ItemStack input = stack.copy();
		ItemStack fuel = itemHandler.getStackInSlot(FUEL_SLOT);
		input.setCount(1);
		if (fuel.isEmpty() && StoveFuelList.isFuel(input)) {
			itemHandler.setItemStock(FUEL_SLOT, input);
			IBlockState blockState = world.getBlockState(pos);
			State state = State.HAS_COAL;
			blockState = blockState.withProperty(BlockStove.STATE, state);
			world.setBlockState(pos, blockState, 3);
			if (!isCreative) {
				stack.shrink(1);
			}
			return;
		}
		if (itemHandler.getStackInSlot(ITEM_SLOT).isEmpty()) {
			itemHandler.setItemStock(ITEM_SLOT, input);
			isCheckRecipe = true;
			if (!isCreative) {
				stack.shrink(1);
			}
		}
	}

	void removeItem() {
		ItemStack fuel = itemHandler.getStackInSlot(FUEL_SLOT);
		ItemStack item = itemHandler.getStackInSlot(ITEM_SLOT);
		if (!item.isEmpty()) {
			itemHandler.setItemStock(ITEM_SLOT, ItemStack.EMPTY);
			ItemUtil.dropItem(getWorld(), getPos(), item);
			return;
		}

		if (!fuel.isEmpty()) {
			itemHandler.setItemStock(FUEL_SLOT, ItemStack.EMPTY);
			ItemUtil.dropItem(getWorld(), getPos(), fuel);
			IBlockState blockState = world.getBlockState(pos);
			State state = State.NORMAL;
			blockState = blockState.withProperty(BlockStove.STATE, state);
			world.setBlockState(pos, blockState, 3);
		}
	}
	
	void addFuel() {
		IBlockState blockState = world.getBlockState(pos);
		State state = State.FIRE;
		blockState = blockState.withProperty(BlockStove.STATE, state);
		world.setBlockState(pos, blockState, 3);
		fuelCount = FUEL_TIME;
	}
	
	void fire() {
		addFuel();
		itemHandler.setItemStock(FUEL_SLOT, ItemStack.EMPTY);
	}
	
	private static int itemSmeltingTime = 220;
	
	public static final int FUEL_TIME = 1400;
	
	public ItemStack getResultItem() {
		ItemStack item = itemHandler.getStackInSlot(ITEM_SLOT);
		ItemStack cacheItem = itemHandler.getStackInSlot(CACHE_ITEM_SLOT);
		ItemStack cacheResult = itemHandler.getStackInSlot(CACHE_RESULT_SLOT);
		if (ItemStack.areItemStacksEqualUsingNBTShareTag(item, cacheItem)) {
			return cacheResult;
		}
		if (isCheckRecipe) {
			if (ItemStack.areItemStacksEqualUsingNBTShareTag(item, cacheResult) 
					&& !isCanSmeletResult) {
				isCheckRecipe = false;
				return ItemStack.EMPTY;
			}
			ItemStack result = FurnaceRecipes.instance().getSmeltingResult(item);
			if (result.isEmpty()) {
				isCheckRecipe = false;
				return ItemStack.EMPTY;
			}
			itemHandler.setStackInSlot(CACHE_ITEM_SLOT, item.copy());
			itemHandler.setStackInSlot(CACHE_RESULT_SLOT, result.copy());
			ItemStack nextResult = FurnaceRecipes.instance().getSmeltingResult(result);
			isCanSmeletResult = !nextResult.isEmpty();
			isCheckRecipe = false;
			itemCount = itemSmeltingTime;
			return result;
		}
		isCheckRecipe = false;
		return ItemStack.EMPTY;
	}
	
	@Override
	public void update() {
		IBlockState blockState = world.getBlockState(pos);
		State state = blockState.getValue(BlockStove.STATE);
		if (fuelCount != 0 && fuelCount < FUEL_TIME / 10) {
			blockState = blockState.withProperty(BlockStove.STATE, State.WEAK_FIRE);
			world.setBlockState(pos, blockState, 3);
		}
		if (fuelCount > 0) {
			fuelCount--;
			ItemStack result = getResultItem();
			if (!result.isEmpty()) {
				itemCount--;
				if (itemCount == 0) {
					isCheckRecipe = true;
					itemCount = itemSmeltingTime;
					itemHandler.setStackInSlot(ITEM_SLOT, result.copy());
				}
			}
			if (fuelCount == 0 && (state == State.FIRE || state == State.WEAK_FIRE)) {
				blockState = blockState.withProperty(BlockStove.STATE, State.NORMAL);
				world.setBlockState(pos, blockState, 3);
			}
		}
	}
	
	@Override
	public void breakEvent() {
		ItemUtil.dropItem(world, pos, itemHandler.getStackInSlot(0));
		ItemUtil.dropItem(world, pos, itemHandler.getStackInSlot(1));
	}

	public static class ItemStackHandler extends SimpleItemStackHandler {
		public ItemStackHandler(TileHasInventory inv) {
			super(inv, 5);
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return ItemStack.EMPTY;
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			return stack;
		}
		
		@Override
		public int getSlotLimit(int slot) {
			if (slot == CACHE_RESULT_SLOT) {
				return 64;
			}
			return 1;
		}
	}

	@Override
	public SimpleItemStackHandler createItemStackHandler() {
		return new ItemStackHandler(this);
	}

}
