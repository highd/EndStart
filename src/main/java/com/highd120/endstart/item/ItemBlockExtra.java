package com.highd120.endstart.item;

import com.highd120.endstart.block.BlockExtra;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockExtra extends ItemBlock {
	public ItemBlockExtra(Block block) {
		super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int damage) {
        return damage;
    }

    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + BlockExtra.Type.values()[stack.getMetadata()].getName();
    }

}
