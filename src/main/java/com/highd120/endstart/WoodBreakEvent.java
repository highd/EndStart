package com.highd120.endstart;

import com.highd120.endstart.block.ModBlocks;
import com.highd120.endstart.item.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent ;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class WoodBreakEvent {
	@SubscribeEvent
	public static void onBreakBlock(HarvestDropsEvent  event) {
		if (event.getHarvester() == null) {
			return;
		}
		ItemStack stack = event.getHarvester().getHeldItemMainhand();
		if (stack.getItem() != ModItems.endHammer) {
			return;
		}
		World worldIn = event.getWorld();
		IBlockState state = event.getState();
		BlockPos pos = event.getPos();
		@SuppressWarnings("deprecation")
		ItemStack block = state.getBlock().getItem(worldIn, pos, state);
		for (ItemStack item : OreDictionary.getOres("logWood")) {
			if (ItemStack.areItemStackTagsEqual(item, block)) {
				event.getDrops().clear();
				event.getDrops().add(new ItemStack(ModBlocks.woodDust));
				break;
			}
		}
	}
}
