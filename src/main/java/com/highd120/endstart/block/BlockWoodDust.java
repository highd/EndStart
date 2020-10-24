package com.highd120.endstart.block;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWoodDust extends Block implements IUsableFireStarter {
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 1.0 / 16, 1);

	public BlockWoodDust() {
		super(Material.WOOD);
	}

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return AABB;
    }

	@Override
	public void fire(World world, BlockPos pos, IBlockState blockstate) {
		world.setBlockState(pos, Blocks.FIRE.getDefaultState());
	}
}
