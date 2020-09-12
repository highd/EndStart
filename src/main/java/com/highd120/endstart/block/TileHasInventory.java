package com.highd120.endstart.block;

import java.util.Optional;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import com.highd120.endstart.util.ItemUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileHasInventory extends TileEntityBase {
	protected SimpleItemStackHandler itemHandler = createItemStackHandler();

	public abstract SimpleItemStackHandler createItemStackHandler();

	public Optional<ItemStack> getItemStack(int slot) {
		return itemHandler.getItemStock(slot);
	}

	public void setInventory(int slot, ItemStack item) {
		itemHandler.setItemStock(slot, item);
	}

	@Override
	public void subReadNbt(NBTTagCompound compound) {
		super.subReadNbt(compound);
		itemHandler = createItemStackHandler();
		itemHandler.deserializeNBT(compound);
	}

	@Override
	public void subWriteNbt(NBTTagCompound compound) {
		super.subWriteNbt(compound);
		compound.merge(itemHandler.serializeNBT());
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, @Nonnull EnumFacing side) {
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| super.hasCapability(cap, side);
	}

	@Nonnull
	@Override
	public <T> T getCapability(@Nonnull Capability<T> cap, @Nonnull EnumFacing side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
		}
		return super.getCapability(cap, side);
	}

	/**
	 * ブロックの破壊時のイベント。
	 */
	public void breakEvent() {
		IntStream.range(0, itemHandler.getSlots()).forEach(i -> 
			ItemUtil.dropItem(world, pos, itemHandler.getStackInSlot(i))
		);
	}

	public static class SimpleItemStackHandler extends ItemStackHandler {
		private final TileHasInventory tile;

		public SimpleItemStackHandler(TileHasInventory inv, int limit) {
			super(limit);
			tile = inv;
		}

		@Override
		public void onContentsChanged(int slot) {
			if (!tile.world.isRemote) {
				IBlockState state = tile.getWorld().getBlockState(tile.getPos());
				tile.getWorld().notifyBlockUpdate(tile.getPos(), state, state, 8);
				tile.world.scheduleUpdate(tile.pos, tile.getBlockType(),
						tile.getBlockType().tickRate(tile.world));
			}
			tile.markDirty();
		}

		public Optional<ItemStack> getItemStock(int slot) {
			return Optional.ofNullable(getStackInSlot(slot));
		}

		public void setItemStock(int slot, ItemStack stack) {
			setStackInSlot(slot, stack);
		}
	}
}
