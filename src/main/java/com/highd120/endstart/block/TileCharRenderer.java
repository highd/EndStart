package com.highd120.endstart.block;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class TileCharRenderer extends TileEntitySpecialRenderer<TileChar> {
    public TileCharRenderer() {
    }
    
    public void draw(ItemStack stack, double x, double y, double z) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.2f, (float) z + 0.5F);

        double boop = Minecraft.getSystemTime() / 800D;
        GlStateManager.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
        GlStateManager.rotate((float) (boop * 40D % 360), 0, 1, 0);

        float scale = stack.getItem() instanceof ItemBlock ? 0.85F : 0.65F;
        scale *= 0.2;
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
    public void render(TileChar te, double x, double y, double z, float par5, int par6, float f) {
    	List<ItemStack> inputs = te.getInputItems();
    	for (int i = 0; i < inputs.size(); i++) {
    		double xDiff = Math.sin(Math.PI * 2 / inputs.size() * i) * 0.3;
    		double zDiff = Math.cos(Math.PI * 2 / inputs.size() * i) * 0.3;
    		draw(inputs.get(i), xDiff + x, y, zDiff + z);
    	}
		draw(te.getResult(), x, y + 1.0f, z);
    }
}
