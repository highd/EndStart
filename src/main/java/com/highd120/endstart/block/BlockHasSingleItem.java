package com.highd120.endstart.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockHasSingleItem extends Block {

    public BlockHasSingleItem(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public abstract TileEntity createTileEntity(World world, IBlockState state);

    @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileHasSingleItem tile = (TileHasSingleItem) worldIn.getTileEntity(pos);
            tile.setItemFromEvent(playerIn.getHeldItem(hand), playerIn.isCreative());
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileHasSingleItem tile = (TileHasSingleItem) world.getTileEntity(pos);

        if (tile != null) {
            tile.breakEvent();
        }

        super.breakBlock(world, pos, state);
    }

}
