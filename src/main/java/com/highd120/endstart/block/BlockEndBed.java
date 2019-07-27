package com.highd120.endstart.block;

import java.util.Random;

import com.highd120.endstart.item.ItemEndBed;
import com.highd120.endstart.util.ChatUtil;
import com.highd120.endstart.util.block.BlockRegister;
import com.highd120.endstart.util.item.ItemManager;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@BlockRegister(name = "end_bed_block")
public class BlockEndBed extends BlockBed {
	protected static final AxisAlignedBB BED_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D);

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}
		if (playerIn.dimension != 1) {
			ChatUtil.addChatMsg(playerIn, "is_not_end");
		} else {
			ChatUtil.addChatMsg(playerIn, "chane_spawn");
			NBTTagCompound data = playerIn.getEntityData();
			NBTTagCompound persist = data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			persist.setInteger("endstart.x", pos.getX());
			persist.setInteger("endstart.y", pos.getY());
			persist.setInteger("endstart.z", pos.getZ());
		}
		return true;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BED_AABB;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(PART) == BlockBed.EnumPartType.HEAD ? null : ItemManager.getItem(ItemEndBed.class);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return ItemManager.getItemStack(ItemEndBed.class);
	}
}
