package com.highd120.endstart.jei;

import javax.annotation.Nonnull;

import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.block.InjectionRecipeData;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class InjectionCategory extends BlankRecipeCategory<InjectionRecipeWrapper> {
    private final IDrawable background;
    public static final String UID = EndStartMain.MOD_ID + ".Injection";
    private final IDrawable overlay;


    /**
     * コンストラクター。
     * @param guiHelper GUIのヘルパー。
     */
    public InjectionCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(150, 110);
        overlay = guiHelper.createDrawable(
                new ResourceLocation("endstart", "textures/gui/injection.png"), 0, 0, 150, 110);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("endstart.injection");
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
    public void setRecipe(IRecipeLayout recipeLayout, InjectionRecipeWrapper recipeWrapper,
            IIngredients ingredients) {
        recipeWrapper.getIngredients(ingredients);
        final InjectionRecipeData recipe = InjectionRecipeData.parseIngredient(ingredients);
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        final int centerX = 64;
        final int centerY = 62;
        final int radius = 32;
        itemStacks.init(0, true, centerX, centerY);
        itemStacks.set(0, recipe.getMain());
        final int inputSize = recipe.getInjectionList().size();
        int index = 1;
        for (ItemStack item : recipe.getInjectionList()) {
            double angle = Math.PI * 2 * (index + 1) / inputSize;
            int x = (int) (Math.sin(angle) * radius) + centerX;
            int y = (int) (Math.cos(angle) * radius) + centerY;
            itemStacks.init(index, true, x, y);
            itemStacks.set(index, item);
            index++;
        }
        itemStacks.init(inputSize + 1, false, 105, 17);
        itemStacks.set(inputSize + 1, recipe.getOutput());
    }
}
