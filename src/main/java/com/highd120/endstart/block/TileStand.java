package com.highd120.endstart.block;

import com.highd120.endstart.block.base.TileHasSingleItem;

import net.minecraft.item.ItemStack;

public class TileStand extends TileHasSingleItem {
    /**
     * アイテムの削除。
     */
    public void removeItem() {
        if (getItem() != null) {
            itemHandler.setStackInSlot(0, ItemStack.EMPTY);
        }
    }

    @Override
    public void update() {
    }
}
