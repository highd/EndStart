package com.highd120.endstart;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityEndZombie extends EntityZombie {
	public EntityEndZombie(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected ResourceLocation getLootTable() {
		return new ResourceLocation(EndStartMain.MOD_ID, "entities/end_zombie");
	}
}
