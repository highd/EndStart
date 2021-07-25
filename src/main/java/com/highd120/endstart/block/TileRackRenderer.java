package com.highd120.endstart.block;

import java.util.List;

import com.highd120.endstart.util.ItemUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class TileRackRenderer extends TileEntitySpecialRenderer<TileRack> {    
    @Override
    public void render(TileRack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	List<ItemStack> inputs = te.getItems();
    	ItemUtil.drawItem(inputs.get(0), x - 0.25, y      , z - 0.25, 0.5, false);
    	ItemUtil.drawItem(inputs.get(1), x - 0.25, y + 0.5, z - 0.25, 0.5, false);
    	ItemUtil.drawItem(inputs.get(2), x + 0.25, y      , z - 0.25, 0.5, false);
    	ItemUtil.drawItem(inputs.get(3), x + 0.25, y + 0.5, z - 0.25, 0.5, false);
    	ItemUtil.drawItem(inputs.get(4), x - 0.25, y      , z + 0.25, 0.5, false);
    	ItemUtil.drawItem(inputs.get(5), x - 0.25, y + 0.5, z + 0.25, 0.5, false);
    	ItemUtil.drawItem(inputs.get(6), x + 0.25, y      , z + 0.25, 0.5, false);
    	ItemUtil.drawItem(inputs.get(7), x + 0.25, y + 0.5, z + 0.25, 0.5, false);
    }

}
