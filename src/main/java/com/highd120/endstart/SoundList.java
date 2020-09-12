package com.highd120.endstart;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class SoundList {
    public static SoundEvent injectionEffect;
    public static SoundEvent injectionComplete;


	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		IForgeRegistry<SoundEvent> register = evt.getRegistry();
        injectionEffect = register("injection_effect", register);
        injectionComplete = register("injection_complete", register);
    }

    private static SoundEvent register(String name, IForgeRegistry<SoundEvent> register) {
        ResourceLocation location = new ResourceLocation(EndStartMain.MOD_ID + ":" + name);
        SoundEvent sound = new SoundEvent(location);
        sound.setRegistryName(location);
        register.register(sound);
        return sound;
    }

    /**
     * 効果音の発生。
     * @param world ワールド。
     * @param sound 効果音。
     * @param postion 座標。
     */
    public static void playSoundBlock(World world, SoundEvent sound, BlockPos postion) {
        if (!world.isRemote) {
            world.playSound(null, postion.getX(), postion.getY(), postion.getZ(), sound,
                    SoundCategory.BLOCKS, 1, 1);
        }

    }
}