package com.highd120.endstart.block;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityBase extends TileEntity implements ITickable {
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState,
			@Nonnull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1nbtTagCompound) {
		NBTTagCompound ret = super.writeToNBT(par1nbtTagCompound);
		subWriteNbt(ret);
		return ret;
	}

	@Nonnull
	@Override
	public final NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		subReadNbt(par1nbtTagCompound);
	}

	public void subWriteNbt(NBTTagCompound compound) {
	}

	public void subReadNbt(NBTTagCompound compound) {
	}

	public void markDirtyClient() {
		markDirty();
		if (worldObj != null) {
			IBlockState state = worldObj.getBlockState(getPos());
			worldObj.notifyBlockUpdate(getPos(), state, state, 3);
		}
	}

	/**
	 * ブロックの更新。
	 */
	public void blockUpdate() {
		getWorld().scheduleUpdate(getPos(), getBlockType(),
				getBlockType().tickRate(getWorld()));
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		subWriteNbt(tag);
		return new SPacketUpdateTileEntity(pos, -999, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		subReadNbt(packet.getNbtCompound());
	}
}