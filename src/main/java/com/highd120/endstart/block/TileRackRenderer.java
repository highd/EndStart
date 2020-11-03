package com.highd120.endstart.block;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class TileRackRenderer extends TileEntitySpecialRenderer<TileRack> {
    public void draw(ItemStack stack, double x, double y, double z, double size) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.2f, (float) z + 0.5F);

        double boop = Minecraft.getSystemTime() / 800D;
        GlStateManager.rotate((float) (boop * 40D % 360), 0, 1, 0);

        float scale = stack.getItem() instanceof ItemBlock ? 0.85F : 0.65F;
        scale *= size;
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
    
    @Override
    public void render(TileRack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	List<ItemStack> inputs = te.getItems();
    	draw(inputs.get(0), x - 0.25, y      , z - 0.25, 0.5);
    	draw(inputs.get(1), x - 0.25, y + 0.5, z - 0.25, 0.5);
    	draw(inputs.get(2), x + 0.25, y      , z - 0.25, 0.5);
    	draw(inputs.get(3), x + 0.25, y + 0.5, z - 0.25, 0.5);
    	draw(inputs.get(4), x - 0.25, y      , z + 0.25, 0.5);
    	draw(inputs.get(5), x - 0.25, y + 0.5, z + 0.25, 0.5);
    	draw(inputs.get(6), x + 0.25, y      , z + 0.25, 0.5);
    	draw(inputs.get(7), x + 0.25, y + 0.5, z + 0.25, 0.5);
    }

}
