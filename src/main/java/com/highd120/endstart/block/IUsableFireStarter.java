package com.highd120.endstart.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IUsableFireStarter {
	boolean isUsable(World world, BlockPos pos, IBlockState blockstate);
	void fire(World world, BlockPos pos, IBlockState blockstate);
}
