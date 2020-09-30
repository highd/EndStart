package com.highd120.endstart.item;

import com.highd120.endstart.block.BlockChar;
import com.highd120.endstart.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemChalk extends ItemHasMeta {

	public ItemChalk() {
		super(new String[] { 
				"chalk_red", 
				"chalk_red_black", 
				"chalk_green"});
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (facing != EnumFacing.UP) {
			return EnumActionResult.PASS;
		}
		IBlockState block = worldIn.getBlockState(pos);
		if (block == null || !block.isFullBlock()) {
			return EnumActionResult.PASS;			
		}
		ItemStack stack = player.getHeldItem(hand);
		int meta = stack.getMetadata();
		BlockChar.Color color = BlockChar.Color.values()[meta];
		IBlockState charBlock = ModBlocks.blockChar.getDefaultState()
                .withProperty(BlockChar.COLOR, color);
		worldIn.setBlockState(pos.up(), charBlock);
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}
