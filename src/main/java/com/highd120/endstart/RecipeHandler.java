package com.highd120.endstart;

import com.highd120.endstart.item.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;

@EventBusSubscriber(modid = EndStartMain.MOD_ID)
public class RecipeHandler {
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        ThaumcraftApi.addCrucibleRecipe(
        		new ResourceLocation(EndStartMain.MOD_ID, "ancient_knowledge_recipe"), 
        		new CrucibleRecipe(
        				"GET_ANCIENT_KNOWLEDGE@2", 
        				new ItemStack(ModItems.ancientKnowledge), 
        				Items.PAPER, 
        				new AspectList().add(Aspect.SOUL, 10).add(Aspect.SENSES, 10)
        		)
        );

    }
}
