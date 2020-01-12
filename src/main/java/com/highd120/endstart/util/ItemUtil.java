package com.highd120.endstart.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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
		world.spawnEntityInWorld(result);
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
		EntityItem result = new EntityItem(world, postion.xCoord, postion.yCoord, postion.zCoord,
				stack);
		world.spawnEntityInWorld(result);
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
		if (!equalNbtTagForRecipe(item.getTagCompound(), recipe.getTagCompound())) {
			return false;

		}
		return true;

	}

	public static boolean equalNbtTagForRecipe(NBTTagCompound item, NBTTagCompound recipe) {
		if (item == null && recipe == null) {
			return true;
		}
		if (item == null || recipe == null) {
			return false;
		}
		for (String key : recipe.getKeySet()) {
			NBTBase itemChild = item.getTag(key);
			if (itemChild == null) {
				return false;
			}
			if (itemChild instanceof NBTTagCompound
					&& !equalNbtTagForRecipe(item.getCompoundTag(key), recipe.getCompoundTag(key))) {
				return false;
			}
			if (!itemChild.equals(recipe.getTag(key))) {
				return false;
			}
		}
		return true;
	}
}
