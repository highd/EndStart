package com.highd120.endstart.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRack extends Block {
	public BlockRack() {
		super(Material.ROCK);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	private static int targetMap[][] = {
			{0, 2, 4, 6}, 
			{1, 3, 5, 7}, 
			{0, 2, 1, 3}, 
			{4, 6, 5, 7}, 
			{0, 1, 4, 5}, 
			{2, 3, 6, 7}
	};
	private static int indexMap[][] = {
			{1, 0, 2},
			{1, 0, 2},
			{1, 2, 0},
			{1, 2, 0},
			{0, 1, 2},
			{0, 1, 2},
	};
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState blockState, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		int index = hitX > 0.5 ? indexMap[facing.ordinal()][0] : 0;
		index += hitY > 0.5 ? indexMap[facing.ordinal()][1] : 0;
		index += hitZ > 0.5 ? indexMap[facing.ordinal()][2] : 0;
		int slot = targetMap[facing.ordinal()][index];
    	TileRack tile = (TileRack)worldIn.getTileEntity(pos);
		ItemStack stack = playerIn.getHeldItem(hand);
    	tile.accessSlot(slot, stack);
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileRack();
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	TileRack tile = (TileRack)worldIn.getTileEntity(pos);
    	tile.breakEvent();
    	super.breakBlock(worldIn, pos, state);
    }
}
