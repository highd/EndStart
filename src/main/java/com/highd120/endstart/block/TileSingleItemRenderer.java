package com.highd120.endstart.block;

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
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) x + 0.5F, (float) y + yOffset, (float) z + 0.5F);

                double boop = Minecraft.getSystemTime() / 800D;
                GlStateManager.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
                GlStateManager.rotate((float) (boop * 40D % 360), 0, 1, 0);

                float scale = stack.getItem() instanceof ItemBlock ? 0.85F : 0.65F;
                GlStateManager.scale(scale, scale, scale);

                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();
                GlStateManager.pushAttrib();
                Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.FIXED);
                GlStateManager.popAttrib();
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();

                GlStateManager.popMatrix();
            }
        }
    }
}
