package com.highd120.endstart;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEndZombie extends RenderLiving<EntityEndZombie> {

	public RenderEndZombie(RenderManager rendermanagerIn, ModelBase modelbaseIn) {
		super(rendermanagerIn, modelbaseIn, 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityEndZombie entity) {
		return new ResourceLocation(EndStartMain.MOD_ID, "textures/entity/end_zombie.png");
	}

}
