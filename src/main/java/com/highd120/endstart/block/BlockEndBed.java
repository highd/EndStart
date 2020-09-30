package com.highd120.endstart.block;

import com.highd120.endstart.util.ChatUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEndBed extends Block {
	public BlockEndBed() {
        super(Material.ROCK);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
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
	
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
