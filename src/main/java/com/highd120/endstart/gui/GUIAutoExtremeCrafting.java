package com.highd120.endstart.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.highd120.endstart.block.TileAutoDireCraftingTable;

import morph.avaritia.recipe.extreme.ExtremeCraftingManager;
import morph.avaritia.recipe.extreme.ExtremeShapedOreRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapedRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapelessOreRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapelessRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GUIAutoExtremeCrafting extends GuiContainer {
	private static final ResourceLocation background = new ResourceLocation(
			"endstart:textures/gui/auto_dire_crafting_gui.png");
	private static final ResourceLocation backgroundButton = new ResourceLocation(
			"endstart:textures/gui/arrow.png");

	private static final ResourceLocation iconGuiElements = new ResourceLocation(
			"endstart:textures/gui/guielements.png");

	private ContainerAutoExtremeCrafting container;
	private TileAutoDireCraftingTable table;

	public GUIAutoExtremeCrafting(InventoryPlayer par1InventoryPlayer, World par2World, BlockPos pos,
			TileAutoDireCraftingTable table) {
		super(new ContainerAutoExtremeCrafting(par1InventoryPlayer, par2World, pos, table));
		container = (ContainerAutoExtremeCrafting) inventorySlots;
		this.ySize = 256;
		this.xSize = 238;
		this.table = table;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new NextButton(0, guiLeft + 175, guiTop + 8));
		buttonList.add(new PrevButton(1, guiLeft + 213, guiTop + 8));
	}

	private void drawGhostSlot(IRecipe recipe, int slotId) {
		Slot slot = inventorySlots.inventorySlots.get(slotId + 1);
		if (slot.getHasStack()) {
			return;
		}
		ItemStack stack = getRecipeItem(recipe, slotId);
		if (stack == null) {
			return;
		}
		int x = slot.xDisplayPosition + guiLeft;
		int y = slot.yDisplayPosition + guiTop;

		itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		this.mc.getTextureManager().bindTexture(iconGuiElements);
		drawTexturedModalRect(x, y, 0, 0, 16, 16);
		GlStateManager.enableDepth();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
	}

	public ItemStack getRecipeItem(IRecipe recipe, int slot) {
		Object recipeItem = null;
		if (recipe instanceof ExtremeShapedRecipe) {
			ExtremeShapedRecipe extremeRecipe = (ExtremeShapedRecipe) recipe;
			if (extremeRecipe.recipeItems.length > slot) {
				recipeItem = extremeRecipe.recipeItems[slot];
			}
		}

		if (recipe instanceof ExtremeShapelessRecipe) {
			ExtremeShapelessRecipe extremeRecipe = (ExtremeShapelessRecipe) recipe;
			if (extremeRecipe.recipeItems.size() > slot) {
				recipeItem = extremeRecipe.recipeItems.get(slot);
			}
		}
		if (recipe instanceof ExtremeShapedOreRecipe) {
			ExtremeShapedOreRecipe extremeRecipe = (ExtremeShapedOreRecipe) recipe;
			if (extremeRecipe.getInput().length > slot) {
				recipeItem = extremeRecipe.getInput()[slot];
			}
		}

		if (recipe instanceof ExtremeShapelessOreRecipe) {
			ExtremeShapelessOreRecipe extremeRecipe = (ExtremeShapelessOreRecipe) recipe;
			if (extremeRecipe.getInput().size() > slot) {
				recipeItem = extremeRecipe.getInput().get(slot);
			}
		}
		if (recipeItem == null) {
			return null;
		}
		if (recipeItem instanceof ItemStack) {
			return (ItemStack) recipeItem;
		}
		if (recipeItem instanceof List) {
			return ((List<ItemStack>) recipeItem).get(0);
		}
		return null;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(background);
		int foo = (this.width - this.xSize) / 2;
		int bar = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(foo, bar, 0, 0, this.ySize, this.ySize);
		fontRendererObj.drawString(Integer.toString(table.getEnergyStored(EnumFacing.UP)), guiLeft + 175, guiTop + 25,
				0x000000);
		fontRendererObj.drawString(Integer.toString(table.getMaxEnergyStored(EnumFacing.UP)), guiLeft + 175,
				guiTop + 35, 0x000000);

		List<IRecipe> recipeList = ExtremeCraftingManager.getInstance().getRecipeList();
		IRecipe recipe = recipeList.get(table.getRecipeId());

		for (int i = 0; i < 81; i++) {
			drawGhostSlot(recipe, i);
		}
	}

	public class CustomButton extends GuiButton {
		private final int graph;

		public CustomButton(int buttonId, int x, int y, int graph) {
			super(buttonId, x, y, 16, 16, "");
			this.graph = graph;
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			if (this.visible) {
				mc.getTextureManager().bindTexture(backgroundButton);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition
						&& mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
				int i = this.getHoverState(this.hovered);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
						GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
						GlStateManager.DestFactor.ZERO);
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
						GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				this.drawTexturedModalRect(this.xPosition, this.yPosition, 0 + graph, i * 16 - 16, 16, 16);
				this.mouseDragged(mc, mouseX, mouseY);
			}

		}
	}

	public class NextButton extends CustomButton {

		public NextButton(int buttonId, int x, int y) {
			super(buttonId, x, y, 16);
		}

		@Override
		public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
			if (!super.mousePressed(mc, mouseX, mouseY)) {
				return false;
			}
			container.nextRecipe();
			return true;
		}

	}

	public class PrevButton extends CustomButton {

		public PrevButton(int buttonId, int x, int y) {
			super(buttonId, x, y, 0);
		}

		@Override
		public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
			if (!super.mousePressed(mc, mouseX, mouseY)) {
				return false;
			}
			container.prevRecipe();
			return true;
		}

	}
}
