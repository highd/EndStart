package com.highd120.endstart.block;

import javax.annotation.Nonnull;

import com.highd120.endstart.EndStartCreativeTab;
import com.highd120.endstart.block.BlockHasSingleItem;
import com.highd120.endstart.util.block.BlockRegister;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@BlockRegister(name = "stand")
public class BlockStand extends BlockHasSingleItem {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 11.0 / 16.0, 1);

    /**
     * コンストラクター。
     */
    public BlockStand() {
        super(Material.ROCK);
        setHardness(3.5F);
        setSoundType(SoundType.STONE);
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
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileStand();
    }
}
