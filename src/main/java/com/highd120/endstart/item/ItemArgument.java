package com.highd120.endstart.item;

import java.util.List;

import com.highd120.endstart.util.NbtTagUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArgument extends ItemBase {
    public static final String TAG = "args";

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NbtTagUtil.getString(TAG, stack).ifPresent(text ->
            tooltip.add(text)
        );
    }
}