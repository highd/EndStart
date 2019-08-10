package com.highd120.endstart.item;

import java.util.List;

import com.google.common.base.Predicate;
import com.highd120.endstart.EndStartConfig;
import com.highd120.endstart.util.item.ItemRegister;

import net.minecraft.creativetab.CreativeTabs;
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

@ItemRegister(name = "create_wall")
public class ItemCreateWall extends ItemBase {
	public ItemCreateWall() {
		setCreativeTab(CreativeTabs.TOOLS);
	}

	private void setBlock(World world, BlockPos point) {
		if (!world.isAirBlock(point)) {
			return;
		}
		world.setBlockState(point, Blocks.END_STONE.getDefaultState());
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		for (int y = -EndStartConfig.wall_height; y <= EndStartConfig.wall_height; y++) {
			for (int x = -EndStartConfig.wall_range; x <= EndStartConfig.wall_range; x++) {
				setBlock(worldIn, pos.add(x, y, -EndStartConfig.wall_range));
				setBlock(worldIn, pos.add(x, y, EndStartConfig.wall_range));
			}
			for (int z = -EndStartConfig.wall_range; z <= EndStartConfig.wall_range; z++) {
				setBlock(worldIn, pos.add(-EndStartConfig.wall_range, y, z));
				setBlock(worldIn, pos.add(EndStartConfig.wall_range, y, z));
			}
		}
		Predicate<Entity> selector = entity -> {
			if (entity instanceof EntityPlayer) {
				return false;
			}
			return entity instanceof EntityLivingBase;
		};
		List<Entity> entities = worldIn.getEntitiesInAABBexcluding(playerIn,
				new AxisAlignedBB(
						pos.add(-EndStartConfig.wall_range, -EndStartConfig.wall_range, -EndStartConfig.wall_range),
						pos.add(EndStartConfig.wall_range, EndStartConfig.wall_height, EndStartConfig.wall_range)),
				selector);
		entities.forEach(entity -> {
			worldIn.removeEntity(entity);
		});
		stack.stackSize--;
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
