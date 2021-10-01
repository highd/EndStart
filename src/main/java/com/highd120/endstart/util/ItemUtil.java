package com.highd120.endstart.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * アイテムのユーティリティ。
 * @author hdgam
 */
public class ItemUtil {
	/**
	 * アイテムをドロップさせる。
	 * @param world ワールド。
	 * @param postion ドロップさせる座標。
	 * @param stack ドロップさせるアイテム。
	 * @return アイテムのエンティティ。
	 */
	public static EntityItem dropItem(World world, BlockPos postion, ItemStack stack) {
		if (world.isRemote || stack == null) {
			return null;
		}
		EntityItem result = new EntityItem(world, postion.getX(), postion.getY(), postion.getZ(),
				stack);
		world.spawnEntity(result);
		return result;
	}

	/**
	 * アイテムをドロップさせる。
	 * @param world ワールド。
	 * @param postion ドロップさせる座標。
	 * @param stack ドロップさせるアイテム。
	 * @return アイテムのエンティティ。
	 */
	public static EntityItem dropItem(World world, Vec3d postion, ItemStack stack) {
		if (world.isRemote || stack == null) {
			return null;
		}
		EntityItem result = new EntityItem(world, postion.x, postion.y, postion.z, stack);
		world.spawnEntity(result);
		return result;
	}

	public static boolean equalItemStackForRecipe(ItemStack item, ItemStack recipe) {
		if (item == null && recipe == null) {
			return true;
		}
		if (item == null || recipe == null) {
			return false;
		}
		if (item.getItem() != recipe.getItem()) {
			return false;
		}
		if (item.getHasSubtypes() && item.getItemDamage() != recipe.getItemDamage()) {
			return false;
		}
		if (!NbtTagUtil.checkRecipe(recipe.getTagCompound(), item.getTagCompound())) {
			return false;

		}
		return true;
	}
	
	public static boolean canMarge(ItemStack itemStack1, ItemStack itemStack2, int maxStackSize) {
		if (itemStack1.isEmpty() || itemStack2.isEmpty()) {
			return true;
		}
		if (itemStack1.getItem() != itemStack2.getItem()) {
			return false;
		}
		if (itemStack1.getHasSubtypes() && itemStack1.getMetadata() != itemStack2.getMetadata()) {
			return false;
		}
		if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2)) {
			maxStackSize = Math.min(maxStackSize, itemStack1.getMaxStackSize());
			return itemStack1.getCount() + itemStack2.getCount() <= maxStackSize;
		}
		return false;
	}
	
	public static Stream<ItemStack> getPlayerStream(EntityPlayer player) {
		List<ItemStack> list = new ArrayList<>();
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack item = player.inventory.getStackInSlot(i);
			list.add(item);
		}
		return list.stream();
	}
	
	public static void drawItem(ItemStack item, double x, double y, double z, double size, boolean isUpDown) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        double boop = Minecraft.getSystemTime() / 800D;
        if (isUpDown) {
        	GlStateManager.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
        }
        GlStateManager.rotate((float) (boop * 40D % 360), 0, 1, 0);

        float scale = item.getItem() instanceof ItemBlock ? 0.85F : 0.65F;
        scale *= size;
        GlStateManager.scale(scale, scale, scale);

        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.pushAttrib();
        Minecraft.getMinecraft().getRenderItem().renderItem(item, TransformType.FIXED);
        GlStateManager.popAttrib();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();

        GlStateManager.popMatrix();
		
	}
}
