package com.highd120.endstart.item;

import java.util.List;

import com.highd120.endstart.util.NbtTagUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class ItemFluid extends ItemBase {
    public static final String NAME = "name";
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		ItemStack bucket = player.inventory.getStackInSlot(0);
		if (bucket.getItem() == ForgeModContainer.getInstance().universalBucket) {
            NbtTagUtil.getString("FluidName", bucket).ifPresent(name -> {
            	NbtTagUtil.setString(NAME, stack, name);
            });
		}
		if (bucket.getItem() == Items.WATER_BUCKET) {
			NbtTagUtil.setString(NAME, stack, "water");
		}
		if (bucket.getItem() == Items.LAVA_BUCKET) {
			NbtTagUtil.setString(NAME, stack, "lava");
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NbtTagUtil.getString(ItemArgument.TAG, stack)
        .flatMap(count -> NbtTagUtil.getString(NAME, stack).map(name -> name + "*" + count))
        .ifPresent(text ->
            tooltip.add(text)
        );
    }
}
