package com.highd120.endstart.block;

import com.highd120.endstart.block.TileHasSingleItem;

public class TileStand extends TileHasSingleItem {
    /**
     * アイテムの削除。
     */
    public void removeItem() {
        if (getItem() != null) {
            itemHandler.setStackInSlot(0, null);
        }
    }

    @Override
    public void update() {
    }
}
