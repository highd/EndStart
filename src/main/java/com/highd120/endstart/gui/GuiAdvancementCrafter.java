package com.highd120.endstart.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.lwjgl.opengl.GL11;

import com.highd120.endstart.block.advancementcafter.AdvancementCrafterRecipeData;
import com.highd120.endstart.block.advancementcafter.TileAdvancementCrafter;

import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiAdvancementCrafter extends GuiContainer {
	private static final ResourceLocation background = new ResourceLocation(
			"endstart:textures/gui/advancement_crafter.png");

	private static final ResourceLocation iconGuiElements = new ResourceLocation(
			"endstart:textures/gui/guielements.png");

	private ContainerAdvancementCrafter container;
	private TileAdvancementCrafter table;

	public GuiAdvancementCrafter(InventoryPlayer par1InventoryPlayer, World par2World, BlockPos pos,
			TileAdvancementCrafter table) {
		super(new ContainerAdvancementCrafter(par1InventoryPlayer, par2World, pos, table));
		container = (ContainerAdvancementCrafter) inventorySlots;
		this.ySize = 256;
		this.xSize = 256;
		this.table = table;
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(background);
		int startX = (this.width - this.xSize) / 2;
		int startY = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(startX, startY, 0, 0, xSize, ySize);
	}
	
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int startX = (this.width - this.xSize) / 2 + 10;
		int startY = (this.height - this.ySize) / 2 + 10;
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        int i = 0;
        List<Advancement> advancementsList = table.getAdvancementsList(mc.player.connection.getAdvancementManager());
        for (Advancement advancement: advancementsList) {
    		drawItemStack2(advancement.getDisplay().getIcon(), startX + 4, startY + 20 + i * 16, null);
    		i++;
        }
        if (startX + 4 < mouseX && mouseX < startX + 20) {
        	int id = (mouseY - startY - 20) / 16;
        	if (0 <= id && id < advancementsList.size()) {
                List<String> textLines = new ArrayList();
                textLines.add(advancementsList.get(id).getDisplay().getTitle().getFormattedText());
                textLines.add(advancementsList.get(id).getDisplay().getDescription().getFormattedText());
                this.drawHoveringText(textLines, mouseX, mouseY, fontRenderer);
        	}
        }
        
        Optional<AdvancementCrafterRecipeData> result = table.find();
        
        result.ifPresent(data -> {
			Slot slot = inventorySlots.inventorySlots.get(0);
			if (!slot.getStack().isEmpty() ) {
				return;
			}
			ItemStack stack = data.getOutput();
			int x = slot.xPos + guiLeft;
			int y = slot.yPos + guiTop;
			itemRender.renderItemAndEffectIntoGUI(stack, x, y);
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.disableDepth();
			this.mc.getTextureManager().bindTexture(iconGuiElements);
			drawTexturedModalRect(x, y, 0, 0, 16, 16);
			GlStateManager.enableDepth();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		});

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.colorMask(true, true, true, false);
		int endEnergyX = (this.xSize - 10 - startX) * table.getEnergy() / TileAdvancementCrafter.MAX_ENERGY + startX;
        this.drawGradientRect(startX, startY, this.xSize - 10, startY + 14, Color.BLACK.getRGB(), Color.BLACK.getRGB());
        result.ifPresent(data -> {
    		int endMinEnergyX = (this.xSize - 10 - startX) * data.getMinEnergy() / TileAdvancementCrafter.MAX_ENERGY + startX;
            this.drawGradientRect(startX, startY, endMinEnergyX, startY + 14, Color.WHITE.getRGB(), Color.WHITE.getRGB());
        });
        this.drawGradientRect(startX, startY, endEnergyX, startY + 14, Color.RED.getRGB(), Color.RED.getRGB());
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        fontRenderer.drawString(table.getEnergy() + "/", xSize, startY, 0);
        fontRenderer.drawString(TileAdvancementCrafter.MAX_ENERGY + "", xSize, startY + 10, 0);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
    private void drawItemStack2(ItemStack stack, int x, int y, String altText) {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        this.itemRender.zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
    }
}