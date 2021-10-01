package com.highd120.endstart.block;

import javax.annotation.Nonnull;

import com.highd120.endstart.EndStartCreativeTab;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockEndLight extends Block {
    private static final AxisAlignedBB AABB = new AxisAlignedBB(
    		5.0 / 16.0, 0, 5.0 / 16.0, 
    		11.0 / 16.0, 7.0 / 16.0, 11.0 / 16.0);
	public BlockEndLight() {
		super(Material.ROCK);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
		setHardness(1.0F);
		setLightLevel(1);
	}
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return AABB;
    }
}
