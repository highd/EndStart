package com.highd120.endstart.block;

import com.highd120.endstart.util.ItemUtil;
import net.minecraft.item.ItemStack;

public abstract class TileHasSingleItem extends TileHasInventory {

    /**
     * アイテムの設置のイベント。
     * @param stack プレイヤー。
     */
    public void setItemFromEvent(ItemStack stack, boolean isCreative) {
        if (stack != null && getItem() == null) {
            ItemStack insertItem = stack.copy();
            insertItem.stackSize = 1;
            itemHandler.setStackInSlot(0, insertItem);
            if (!isCreative) {
                stack.stackSize--;
            }
        } else if (getItem() != null) {
            ItemUtil.dropItem(worldObj, pos.add(0, 1, 0), getItem());
            itemHandler.setStackInSlot(0, null);
        }
    }

    @Override
    public SimpleItemStackHandler createItemStackHandler() {
        return new SingleItemHandler(this, 1);
    }

    /**
     * アイテムの取得。
     * @return アイテム。
     */
    public ItemStack getItem() {
        return itemHandler.getStackInSlot(0);
    }

    /**
     *  ひとつのアイテムのみのアイテムハンドラー。
     * @author hdgam
     *
     */
    private static class SingleItemHandler extends SimpleItemStackHandler {
        public SingleItemHandler(TileHasInventory inv, int limit) {
            super(inv, limit);
        }

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

    }
}
