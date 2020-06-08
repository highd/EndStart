package com.highd120.endstart;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EndStartCreativeTab extends CreativeTabs {
    public static final EndStartCreativeTab INSTANCE = new EndStartCreativeTab();

    public EndStartCreativeTab() {
        super(EndStartMain.MOD_ID);
    }

    @Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Blocks.END_STONE);
	}

    @Override
    public Item getTabIconItem() {
        return getIconItemStack().getItem();
    }
}