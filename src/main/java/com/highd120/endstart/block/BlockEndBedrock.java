package com.highd120.endstart.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockEndBedrock extends Block {
	public BlockEndBedrock() {
        super(Material.ROCK);
        setBlockUnbreakable();
        setResistance(6000000.0F);
        setSoundType(SoundType.STONE);
	}
	
	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		if (entity instanceof EntityDragon) {
			return false;
		}
		return super.canEntityDestroy(state, world, pos, entity);
	}
}
