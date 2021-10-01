package com.highd120.endstart.jei;

import javax.annotation.Nonnull;

import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.block.advancementcafter.AdvancementCrafterRecipeData;
import com.highd120.endstart.block.base.ListAndMainRecipeData;
import com.highd120.endstart.recipe.IRecipeItem;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AdvancementCrafterCategory implements IRecipeCategory<AdvancementCrafterWrapper>{
    private final IDrawable background;
    public static final String UID = EndStartMain.MOD_ID + ".advancementcrafter";
    private final IDrawable overlay;
    
    public AdvancementCrafterCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(175, 150);
        overlay = guiHelper.createDrawable(
                new ResourceLocation("endstart", "textures/gui/advancement_crafter_jei.png"), 0, 0, 175, 150);
    }

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format(EndStartMain.MOD_ID + ".advancementcrafter");
	}

	@Override
	public String getModName() {
		return EndStartMain.MOD_ID;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}
	
    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        overlay.draw(minecraft);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AdvancementCrafterWrapper recipeWrapper,
			IIngredients ingredients) {
        recipeWrapper.getIngredients(ingredients);
        final AdvancementCrafterRecipeData recipe = recipeWrapper.getData();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, false, 135, 46);
        itemStacks.set(0, recipe.getOutput());
        for (int x = 0; x < 5; x++) {
        	for (int y = 0; y < 5; y++) {
                IRecipeItem input = recipe.getInputList().get(x + y * 5);
                itemStacks.init(x + y * 5 + 1, true, x * 18 + 8, y * 18 + 10);
                input.setJeiRecipe(x + y * 5 + 1, itemStacks);
        	}
        }
	}
}
