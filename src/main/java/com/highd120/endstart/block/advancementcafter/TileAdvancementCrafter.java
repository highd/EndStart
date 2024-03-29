package com.highd120.endstart.block.advancementcafter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.highd120.endstart.EndStartMessages;
import com.highd120.endstart.block.base.TileHasInventory;
import com.highd120.endstart.network.PacketNormal;
import com.highd120.endstart.network.TileEntityHasPacket;
import com.highd120.endstart.recipe.IRecipeItem;
import com.highd120.endstart.util.ItemUtil;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class TileAdvancementCrafter extends TileHasInventory implements TileEntityHasPacket<Boolean> {
	
	private List<String> advancementsList = new ArrayList<>();
	private boolean isEnd;
	public static final int MAX_ENERGY = 10000000;
    private EnergyStorage energyStorage = new EnergyStorage(MAX_ENERGY);
    private static final String ENERGY_TAG = "energy";
	
	@Override
	public void update() {
		if (isEnd) {
			find().ifPresent(data -> {
				if (energyStorage.getEnergyStored() < data.getMinEnergy()) {
					return;
				}
				if (ItemUtil.canMarge(itemHandler.getStackInSlot(0), data.getOutput(), 64)) {
					for (int i = 0; i < 25; i++) {
						if (itemHandler.getStackInSlot(i + 1).getCount() == 1) {
							itemHandler.setItemStock(i + 1, ItemStack.EMPTY);
						} else {
							itemHandler.getStackInSlot(i + 1).shrink(1);
							itemHandler.onContentsChanged(i + 1);
						}
					}
					if (itemHandler.getStackInSlot(0).isEmpty()) {
						itemHandler.setItemStock(0, data.getOutput().copy());
					} else {
						itemHandler.getStackInSlot(0).grow(1);
						itemHandler.onContentsChanged(0);
					}
					energyStorage.extractEnergy(data.getMinEnergy(), false);
				}
			});
			isEnd = false;
		}
		advancementsList = find().map(data -> {
			return data.getAdvancementsList();
		}).orElse(Collections.emptyList());
	}
	
	public Optional<AdvancementCrafterRecipeData> find() {
		for (AdvancementCrafterRecipeData recipe: AdvancementCrafterRecipe.recipeList) {
			boolean isMatch = true;
			for (int i = 0; i < 25; i++) {
				ItemStack item = itemHandler.getStackInSlot(i + 1);
				IRecipeItem recipeItem = recipe.getInputList().get(i);
				if (!recipeItem.checkRecipe(item)) {
					isMatch = false;
					break;
				}
			}
			if (isMatch) {
				return Optional.of(recipe);
			}
		}
		return Optional.empty();
	}
	
	public void check(EntityPlayerMP player, MinecraftServer server) {
    	AdvancementManager manager = world.getMinecraftServer().getAdvancementManager();
    	isEnd = advancementsList.stream().allMatch(name -> {
    		Advancement advancement = manager.getAdvancement(new ResourceLocation(name));
    		if (advancement == null) {
    			return true;
    		}
    		return player.getAdvancements().getProgress(advancement).isDone();
    	});
    	System.out.println(isEnd);
		PacketNormal packet = new PacketNormal(pos, player.dimension);
		packet.setValue(isEnd);
		EndStartMessages.INSTANCE.sendToAll(packet);
	}
	
	@Override
	public void subReadNbt(NBTTagCompound compound) {
		NBTTagCompound advancementsListTag = compound.getCompoundTag("advancementsList");
		if (advancementsListTag != null) {
			advancementsList = new ArrayList<>();
			int size = advancementsListTag.getInteger("size");
			for (int i = 0; i < size; i++) {
				String advancement = advancementsListTag.getString(Integer.toString(i));
				advancementsList.add(advancement);
			}
		}
        if (compound.hasKey(ENERGY_TAG)) {
        	energyStorage = new EnergyStorage(MAX_ENERGY, MAX_ENERGY, MAX_ENERGY, compound.getInteger(ENERGY_TAG));
        }
		super.subReadNbt(compound);
	}
	
	@Override
	public void subWriteNbt(NBTTagCompound compound) {
		NBTTagCompound advancementsListTag = new NBTTagCompound();
		int i = 0;
		for (String advancement: advancementsList) {
			advancementsListTag.setString(Integer.toString(i), advancement);
			i++;
		}
		advancementsListTag.setInteger("size", advancementsList.size());
		compound.setTag("advancementsList", advancementsListTag);
        compound.setInteger(ENERGY_TAG, energyStorage.getEnergyStored());
		super.subWriteNbt(compound);
	}
	
	public List<Advancement> getAdvancementsList(ClientAdvancementManager manager) {
		return advancementsList.stream()
				.map(name -> manager.getAdvancementList().getAdvancement(new ResourceLocation(name)))
				.filter(advancement -> advancement != null)
				.collect(Collectors.toList());
	}
	
	static class ItemHandler extends SimpleItemStackHandler {

		public ItemHandler(TileHasInventory inv) {
			super(inv, 26);
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (slot == 0 && 25 < slot) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if (slot != 0) {
				return ItemStack.EMPTY;
			}
			return super.extractItem(slot, amount, simulate);
		}
		
		@Override
		protected int getStackLimit(int slot, ItemStack stack) {
			return 64;
		}
	}
	
	@Override
	public SimpleItemStackHandler createItemStackHandler() {
		return new ItemHandler(this);
	}

	@Override
	public void execute(Boolean data) {
		System.out.print("world.isRemote = " + world.isRemote);
		isEnd = data;
	}

	@Override
	public Class<Boolean> getDataClass() {
		return Boolean.class;
	}
    
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energyStorage);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}
	
	public int getEnergy() {
		return energyStorage.getEnergyStored();
	}
}
