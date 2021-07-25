package com.highd120.endstart.block.base;

import com.highd120.endstart.util.ItemUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class TileSingleItemRenderer extends TileEntitySpecialRenderer<TileHasSingleItem> {
    private float yOffset;

    public TileSingleItemRenderer(float yOffset) {
        this.yOffset = yOffset;
    }

    @Override
    public void render(TileHasSingleItem te, double x, double y, double z, float par5, int par6, float f) {
        if (te.getItem() != null) {
            ItemStack stack = te.getItem();
            if (stack != null) {
            	ItemUtil.drawItem(stack, x + 0.5, y + yOffset, z + 0.5, 0.7, true);
            }
        }
    }
}
