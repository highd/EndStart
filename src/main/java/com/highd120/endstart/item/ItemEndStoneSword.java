package com.highd120.endstart.item;

import java.util.Random;

import com.highd120.endstart.EndStartCreativeTab;
import com.highd120.endstart.util.ItemUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemEndStoneSword extends ItemSword {
	protected static final Random random = new Random();

	public ItemEndStoneSword() {
		super(ToolMaterial.WOOD);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
        this.setMaxDamage(30);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (random.nextInt(100) < 60) {
			ItemUtil.dropItem(target.getEntityWorld(), target.getPosition(), new ItemStack(ModItems.itemBlood));
		}
		return super.hitEntity(stack, target, attacker);
	}
}
