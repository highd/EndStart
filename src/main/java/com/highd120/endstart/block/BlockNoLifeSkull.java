package com.highd120.endstart.block;

import javax.annotation.Nonnull;

import com.highd120.endstart.EndStartCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockNoLifeSkull extends Block {
    private static final AxisAlignedBB AABB = new AxisAlignedBB(
        4.0 / 16.0, 0, 4.0 / 16.0,
        12.0 / 16.0, 8.0 / 16.0, 12.0 / 16.0);

    public BlockNoLifeSkull() {
        super(Material.CIRCUITS);
		setHardness(1.5F);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}