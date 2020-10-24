package com.highd120.endstart.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IUsableFireStarter {
	void fire(World world, BlockPos pos, IBlockState blockstate);
}
