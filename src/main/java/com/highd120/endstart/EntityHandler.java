package com.highd120.endstart;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@EventBusSubscriber(modid = EndStartMain.MOD_ID)
public class EntityHandler {
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		EntityEntry endZombie = EntityEntryBuilder.<EntityEndZombie>create()
	    	.id(new ResourceLocation(EndStartMain.MOD_ID, "end_zombie"), 0)
	    	.entity(EntityEndZombie.class)
	    	.factory(EntityEndZombie::new)
	    	.name(EndStartMain.MOD_ID + ".end_zombie")
	    	.tracker(80, 3, true)
	    	.egg(0xe4e47c, 0xc2a666)
	    	.build();
		event.getRegistry().register(endZombie);
	}
}
