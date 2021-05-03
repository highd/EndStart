package com.highd120.endstart.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.darkhax.gamestages.GameStageHelper;

public class ItemAncientKnowledge extends ItemBase {
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		GameStageHelper.addStage(player, "curio");
		return EnumActionResult.SUCCESS;
	}
}
