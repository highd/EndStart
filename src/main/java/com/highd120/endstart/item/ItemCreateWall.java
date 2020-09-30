package com.highd120.endstart.item;

import java.util.List;

import com.google.common.base.Predicate;
import com.highd120.endstart.EndStartConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCreateWall extends ItemBase {
	public ItemCreateWall() {
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		int range = EndStartConfig.wall_range;
		int height = EndStartConfig.wall_height;
		IBlockState endStone = Blocks.END_STONE.getDefaultState();
		IBlockState air = Blocks.AIR.getDefaultState();
		for (int y = 1; y <= height - 1; y++) {
			for (int x = -range; x <= range; x++) {
				worldIn.setBlockState(pos.add(x, y, -range), endStone);
				worldIn.setBlockState(pos.add(x, y, range), endStone);
			}
			for (int z = -range; z <= range; z++) {
				worldIn.setBlockState(pos.add(-range, y, z), endStone);
				worldIn.setBlockState(pos.add(range, y, z), endStone);
			}
		}
		for (int y = 1; y <= height - 1; y++) {
			for (int x = -range + 1; x <= range - 1; x++) {
				for (int z = -range + 1; z <= range - 1; z++) {
					worldIn.setBlockState(pos.add(x, y, z), air);
				}
			}
		}
		for (int x = -range; x <= range; x++) {
			for (int z = -range; z <= range; z++) {
				worldIn.setBlockState(pos.add(x, 0, z), endStone);
				worldIn.setBlockState(pos.add(x, height, z), endStone);
			}
		}
		Predicate<Entity> selector = entity -> {
			if (entity instanceof EntityPlayer) {
				return false;
			}
			return entity instanceof EntityLivingBase;
		};
		List<Entity> entities = worldIn.getEntitiesInAABBexcluding(player,
				new AxisAlignedBB(
						pos.add(-EndStartConfig.wall_range, -EndStartConfig.wall_range, -EndStartConfig.wall_range),
						pos.add(EndStartConfig.wall_range, EndStartConfig.wall_height, EndStartConfig.wall_range)),
				selector);
		entities.forEach(entity -> {
			worldIn.removeEntity(entity);
		});
		ItemStack stack = player.getHeldItem(hand);
		stack.shrink(1);
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
