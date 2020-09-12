package com.highd120.endstart.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.highd120.endstart.EndStartMessages;
import com.highd120.endstart.SoundList;
import com.highd120.endstart.network.NetworkInjectionEffect;
import com.highd120.endstart.network.NetworkInjectionEffectEnd;
import com.highd120.endstart.util.ItemUtil;
import com.highd120.endstart.util.MathUtil;
import com.highd120.endstart.util.NbtTagUtil;
import com.highd120.endstart.util.WorldUtil;


import net.minecraftforge.energy.IEnergyStorage;
import lombok.AllArgsConstructor;
import lombok.Value;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class TileCrafterCore extends TileHasSingleItem implements IEnergyStorage {
    public enum InjectionState {
        NOT_WORKING, CHARGE_MANA, EFFECT, ITEM_FLOW;
    }

    private ItemStack resultItem;
    private InjectionState state = InjectionState.NOT_WORKING;
    private CrafterEffectManager effect;
    private int energy = 0;
    private int completeEnergy = 0;
    private static final String ENERGY_TAG = "energy";

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
        if (compound.hasKey("effect")) {
            effect = new CrafterEffectManager();
            effect.readNbt(compound);
        }
        if (compound.hasKey(ENERGY_TAG)) {
            energy = compound.getInteger(ENERGY_TAG);
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
        compound.setInteger(ENERGY_TAG, energy);
    }

    @Override
    public void update() {
        if (effect == null) {
            return;
        }
        effect.upDate();
        EndStartMessages.sendToNearby(getWorld(), getPos(), new NetworkInjectionEffect(getPos()));
        if (state == InjectionState.CHARGE_MANA && energy >= completeEnergy) {
            SoundList.playSoundBlock(getWorld(), SoundList.injectionEffect, getPos());
            state = InjectionState.EFFECT;
            effect.start();
            blockUpdate();
        }
        if (state == InjectionState.EFFECT && effect.isEnd()) {
            SoundList.playSoundBlock(getWorld(), SoundList.injectionComplete, getPos());
            effect = null;
            complete();
            blockUpdate();
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
            List<BlockPos> standList = result.standList.stream()
                .filter(stand -> !stand.getItem().isEmpty())
                .map(TileStand::getPos)
                .collect(Collectors.toList());
            effect = new CrafterEffectManager(standList, getPos());
            state = InjectionState.CHARGE_MANA;
            result.standList.forEach(TileStand::removeItem);
            itemHandler.setItemStock(0,  ItemStack.EMPTY);
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
        List<TileStand> standList = WorldUtil.scanTileEnity(
            getWorld(),
            MathUtil.getAxisAlignedPlane(getPos(), 3),
            tile -> tile instanceof TileStand);
        List<ItemStack> itemList = standList.stream()
            .map(TileStand::getItem)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        for (InjectionRecipeData data : InjectionRecipe.recipes) {
            if (data.checkRecipe(itemList, getItem())) {
                resultItem = data.craft();
                completeEnergy = data.useEnergy;
                return new LaunchableResult(standList, true);
            }
        }
        return new LaunchableResult(new ArrayList<>(), false);
    }

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(getMaxEnergyStored() - energy, maxReceive);

        if (!simulate) {
            energy += energyReceived;
        }
        markDirty();
        blockUpdate();
        return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored() {
        return energy;
	}

	@Override
	public int getMaxEnergyStored() {
        return 1000000;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
        return true;
	}

}
