package com.highd120.endstart.item;

import java.util.List;

import com.highd120.endstart.util.NbtTagUtil;
import com.highd120.endstart.util.item.ItemRegister;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@ItemRegister(name = "argument")
public class ItemArgument extends ItemBase {
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        NbtTagUtil.getString("args", stack).ifPresent(text ->
            tooltip.add(text)
        );
    }
}