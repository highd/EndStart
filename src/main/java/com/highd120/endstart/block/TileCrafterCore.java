package com.highd120.endstart.block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.highd120.endstart.EndStartMessages;
import com.highd120.endstart.SoundList;
import com.highd120.endstart.network.NetworkInjectionEffect;
import com.highd120.endstart.network.NetworkInjectionEffectEnd;
import com.highd120.endstart.util.ItemUtil;
import com.highd120.endstart.util.MathUtil;
import com.highd120.endstart.util.NbtTagUtil;
import com.highd120.endstart.util.WorldUtil;

import cofh.api.energy.IEnergyReceiver;
import lombok.AllArgsConstructor;
import lombok.Value;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileCrafterCore extends TileHasSingleItem implements IEnergyReceiver {
    public enum InjectionState {
        NOT_WORKING, CHARGE_MANA, EFFECT, ITEM_FLOW;
    }

    private ItemStack resultItem;
    private InjectionState state = InjectionState.NOT_WORKING;
    private int soundCount = 0;
    private CrafterEffectManager effect;
    private int energy = 0;
    private int completeEnergy = 0;

    private static InjectionState[] stateList = InjectionState.values();

    public CrafterEffectManager getEffect() {
        return effect;
    }

    /**
     * 現在の状態の取得。
     * 
     * @return 状態。
     */
    public InjectionState getState() {
        return state;
    }

    @Override
    public void subReadNbt(NBTTagCompound compound) {
        super.subReadNbt(compound);
        resultItem = NbtTagUtil.readItem(compound, "resultItem");
        state = stateList[compound.getInteger("state")];
        NbtTagUtil.readListFunction(compound, "standList", NbtTagUtil::readBlockPos);
        if (compound.hasKey("effect")) {
            effect = new CrafterEffectManager();
            effect.readNbt(compound);
        }
		if (compound.hasKey("energy")) {
			energy = compound.getInteger("energy");
		}
    }

    @Override
    public void subWriteNbt(NBTTagCompound compound) {
        super.subWriteNbt(compound);
        compound.setInteger("state", state.ordinal());
        NbtTagUtil.writeItem(compound, "resultItem", resultItem);
        if (effect != null) {
            effect.writeNbt(compound);
        }
		compound.setInteger("energy", energy);
    }

    @Override
    public void update() {
        if (effect != null) {
            effect.upDate();
            EndStartMessages.sendToNearby(getWorld(), getPos(), new NetworkInjectionEffect(getPos()));
        }
        if (state == InjectionState.CHARGE_MANA) {
            soundCount++;
            if (soundCount % 9 == 0) {
                // SoundList.playSoundBlock(getWorld(), BotaniaSoundEvents.ding, getPos());
            }
            if (energy >= completeEnergy) {
                SoundList.playSoundBlock(getWorld(), SoundList.injectionEffect, getPos());
                state = InjectionState.EFFECT;
                effect.start();
                soundCount = 0;
                blockUpdate();
            }
        }
        if (state == InjectionState.EFFECT) {
            if (effect.isEnd()) {
                SoundList.playSoundBlock(getWorld(), SoundList.injectionComplete, getPos());
                effect = null;
                complete();
                blockUpdate();
            }
        }
    }

    private void complete() {
        EndStartMessages.sendToNearby(getWorld(), getPos(), new NetworkInjectionEffectEnd(getPos()));
        if (!getWorld().isRemote) {
            EntityItem result = ItemUtil.dropItem(getWorld(), getPos().add(0, 4, 0), resultItem);
            result.setNoGravity(true);
            result.setVelocity(0, -0.2f, 0);
        }
        state = InjectionState.NOT_WORKING;
    }

    @AllArgsConstructor
    private static class LaunchableResult {
        List<TileStand> standList;
        boolean isLaunchable;
    }

    /**
     * モードをアクティブにする。
     */
    public void active() {
        LaunchableResult result = isLaunchable();
        if (state == InjectionState.NOT_WORKING && result.isLaunchable) {
            List<BlockPos> standList = result.standList.stream().filter(stand -> stand.getItem() != null)
                    .map(stand -> stand.getPos()).collect(Collectors.toList());
            effect = new CrafterEffectManager(standList, getPos());
            state = InjectionState.CHARGE_MANA;
            result.standList.forEach(stand -> stand.removeItem());
            itemHandler.setItemStock(0, null);
        }
    }

    @Value
    private static class InjectionItemData implements Comparable<InjectionItemData> {
        String name;

        @Override
        public int compareTo(InjectionItemData o) {
            return name.compareTo(o.name);
        }
    }

    /**
     * 稼働できるかの判定。
     * 
     * @return 稼働できるか。
     */
    public LaunchableResult isLaunchable() {
        if (getItem() == null) {
            return new LaunchableResult(new ArrayList<>(), false);
        }
        List<TileStand> standList = WorldUtil.scanTileEnity(getWorld(), MathUtil.getAxisAlignedPlane(getPos(), 3),
                tile -> tile instanceof TileStand);
        List<ItemStack> itemList = standList.stream().map(stand -> stand.getItem()).filter(item -> item != null)
                .collect(Collectors.toList());
        for (InjectionRecipeData data : InjectionRecipe.recipes) {
            if (data.checkRecipe(itemList, getItem())) {
                resultItem = data.craft(itemList, getItem());
                completeEnergy = data.useEnergy;
                return new LaunchableResult(standList, true);
            }
        }
        return new LaunchableResult(new ArrayList<>(), false);
    }

	@Override
	public int getEnergyStored(EnumFacing facing) {
		return energy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing facing) {
		return 1000000;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing facing) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing facing, int maxReceive, boolean simulate) {
		int energyReceived = Math.min(getMaxEnergyStored(facing) - energy, maxReceive);

		if (!simulate) {
			energy += energyReceived;
		}
		markDirty();
		blockUpdate();
		return energyReceived;
	}

}
