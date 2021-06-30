package com.highd120.endstart.item;

import java.util.List;

import com.highd120.endstart.util.NbtTagUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import thaumcraft.api.items.ItemsTC;

public class ItemAspect extends ItemBase {
    public static final String NAME = "name";
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		ItemStack crystal = player.inventory.getStackInSlot(0);
		if (crystal.getItem() == ItemsTC.crystalEssence) {
			String key = ((NBTTagCompound)(crystal.getTagCompound().getTagList("Aspects", 10).get(0))).getString("key");
        	NbtTagUtil.setString(NAME, stack, key);
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
