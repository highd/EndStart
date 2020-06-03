package com.highd120.endstart.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;

public class TileCrafterCoreRenderer extends TileEntitySpecialRenderer<TileCrafterCore>  {

    @Override
    public void renderTileEntityAt(TileCrafterCore te, double x, double y, double z,
            float partialTicks, int destroyStage) {
        if (te.getItem() != null) {
            EntityItem item = new EntityItem(te.getWorld(), 0.0D, 0.0D, 0.0D, te.getItem());
            item.getEntityItem().stackSize = 1;
            item.hoverStart = 0.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.1, (float) z + 0.5F);
            float f3 = ((System.currentTimeMillis() % 86400000) / 2000F) * (180F / (float) Math.PI);
            GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(1.25d, 1.25d, 1.25d);
            Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0.0D, 0.0D, 0.0D, 0.0F,
                    0.0F, false);
            GlStateManager.popMatrix();
        }
    }
}