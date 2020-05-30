package com.highd120.endstart.block;

import javax.annotation.Nonnull;

import com.highd120.endstart.util.block.BlockRegister;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@BlockRegister(name = "crafter_energy")
public class BlockCrafterEnergy extends Block {
	public BlockCrafterEnergy() {
		super(Material.IRON);
		setHarvestLevel("pickaxe", 3);
		setSoundType(SoundType.GLASS);
		setHardness(100.0F);
		setResistance(2000.0F);
		IBlockState state = blockState.getBaseState()
                .withProperty(BlockDirectional.FACING, EnumFacing.NORTH);
        setDefaultState(state);
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
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockDirectional.FACING);
    }
    
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state,
            EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        EnumFacing orientation = BlockPistonBase.getFacingFromEntity(pos, par5EntityLivingBase);
        world.setBlockState(pos, state.withProperty(BlockDirectional.FACING, orientation), 1 | 2);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(BlockDirectional.FACING).getIndex();
        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing face = EnumFacing.getFront(meta);
        return getDefaultState()
                .withProperty(BlockDirectional.FACING, face);
    }    
}
