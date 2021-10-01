package com.highd120.endstart.jei;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.highd120.endstart.block.advancementcafter.AdvancementCrafterRecipeData;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

public class AdvancementCrafterWrapper implements IRecipeWrapper {
	private final AdvancementCrafterRecipeData data;
	public AdvancementCrafterWrapper(AdvancementCrafterRecipeData data){
		this.data = data;
	}
	
	public AdvancementCrafterRecipeData getData() {
		return data;
	}
	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputs = data.getInputList().stream()
				.map(recipe -> recipe.getList())
				.collect(Collectors.toList());
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		ingredients.setOutput(VanillaTypes.ITEM, data.getOutput());
	}
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		ClientAdvancementManager manager = minecraft.player.connection.getAdvancementManager();
		List<Advancement> list = data.getAdvancementsList().stream()
			.map(name -> manager.getAdvancementList().getAdvancement(new ResourceLocation(name)))
			.collect(Collectors.toList());
		int i = 0;
		for (Advancement advancement: list) {
			int x = 8 + i * 16;
			int y = 100;
			ItemStack stack = advancement.getDisplay().getIcon();
			RenderItem itemRender = minecraft.getRenderItem();
	        GlStateManager.translate(0.0F, 0.0F, 32.0F);
	        itemRender.zLevel = 200.0F;
	        FontRenderer font = stack.getItem().getFontRenderer(stack);
	        if (font == null) font = minecraft.fontRenderer;
	        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
	        itemRender.renderItemOverlayIntoGUI(font, stack, x, y, null);
	        itemRender.zLevel = 0.0F;
    		i++;
        }
		if (100 < mouseY && mouseY < 116) {
			int id = (mouseX - 8) / 16;
			if (0 <= id && id < list.size()) {
				DisplayInfo info = list.get(id).getDisplay();
				List<String> textLines = new ArrayList<>();
				textLines.add(info.getTitle().getFormattedText());
				textLines.add(info.getDescription().getFormattedText());
				GuiUtils.drawHoveringText(textLines, mouseX, mouseY, recipeWidth, recipeHeight, -1, minecraft.fontRenderer);
			}
		}
	}
	
    protected void drawHoveringText(List<String> textLines, int x, int y, FontRenderer fontRenderer) {
    }
    protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)300).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)300).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)300).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)300).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
