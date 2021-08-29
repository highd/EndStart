package com.highd120.endstart.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.common.blocks.BlockTC;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.IPlayerWarp.EnumWarpType;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;

public class ItemMedicine extends ItemBase {
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(player.getPosition()).getBlock();
		if (BlocksTC.purifyingFluid.equals(block)) {
			IPlayerWarp warp = ThaumcraftCapabilities.getWarp(player);
			warp.add(EnumWarpType.PERMANENT, -10);
			warp.add(EnumWarpType.TEMPORARY, -10);
			warp.add(EnumWarpType.NORMAL, -10);
			ItemStack stack = player.getHeldItem(hand);
			stack.shrink(1);
		}
		return EnumActionResult.SUCCESS;
	}
}
