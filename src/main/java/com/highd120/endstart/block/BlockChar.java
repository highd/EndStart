package com.highd120.endstart.block;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.highd120.endstart.util.block.BlockRegister;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@BlockRegister(name = "char")
public class BlockChar extends net.minecraft.block.Block {
	public enum Color implements IStringSerializable {
		RED, RED_BLACK, GREEN;
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
    public static final PropertyEnum<Color> COLOR = PropertyEnum.create("color", Color.class);

    private static final AxisAlignedBB AABB = new AxisAlignedBB(
    		1.0 / 16.0, 0, 1.0 / 16.0, 
    		15.0 / 16.0, 2.0 / 16.0, 15.0 / 16.0);
	public BlockChar() {
		super(Material.ROCK);;
        IBlockState state = blockState.getBaseState()
                .withProperty(COLOR, Color.RED);
        setDefaultState(state);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState downBlock = worldIn.getBlockState(pos.down());
		return downBlock.isFullBlock();
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		IBlockState downBlock = worldIn.getBlockState(pos.down());
		if (downBlock.getBlock() == Blocks.AIR) {
            worldIn.setBlockToAir(pos);
		}
	}

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return AABB;
    }
    
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).ordinal();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	Color color = Color.values()[meta];
        return getDefaultState()
                .withProperty(COLOR, color);
    }
}
