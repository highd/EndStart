package com.highd120.endstart.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.farmingforblockheads.Trade")
public class TweakerFarmingForBlockheads {
    @ZenMethod
    public static void add(IItemStack costItem, IItemStack outputItem) {
    	NBTTagCompound data = new NBTTagCompound();
    	data.setTag("CostItem", new NBTTagCompound());
    	data.setTag("OutputItem", new NBTTagCompound());
    	CraftTweakerMC.getItemStack(costItem).writeToNBT(data.getCompoundTag("CostItem"));
    	CraftTweakerMC.getItemStack(outputItem).writeToNBT(data.getCompoundTag("OutputItem"));
    	data.setString("Category", "farmingforblockheads:other");
    	FMLInterModComms.sendMessage("farmingforblockheads", "RegisterMarketEntry", data);
    }
}
