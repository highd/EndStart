package com.highd120.endstart.block;

import com.highd120.endstart.EndStartMain;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = EndStartMain.MOD_ID)
public class ModBlocks {
	private static <T extends Block> T regist(IForgeRegistry<Block> register, T block, String name) {
        block.setRegistryName(new ResourceLocation(EndStartMain.MOD_ID, name));
        block.setUnlocalizedName(EndStartMain.MOD_ID + "." + name);
        register.register(block);
		return block;
	}
	
	public static BlockExtra blackhole;
	public static BlockBlood blood;
	public static BlockChar blockChar;
	public static BlockCrafterCore crafterCore;
	public static BlockEndBed endBed;
	public static BlockEndSand endSand;
	public static BlockNoLifeSkull nolifeWitherSkeleton;
	public static BlockStand stand;
	public static BlockLavaStone lavaStone;
	public static BlockWoodDust woodDust;
	public static BlockStove stove;
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> register = evt.getRegistry();
		blackhole = regist(register, new BlockExtra(), "block_extra");
		blood = regist(register, new BlockBlood(), "blood");
		blockChar = regist(register, new BlockChar(), "char");
		crafterCore = regist(register, new BlockCrafterCore(), "crafter_core");
		endBed = regist(register, new BlockEndBed(), "end_bed");
		endSand = regist(register, new BlockEndSand(), "end_sand");
		nolifeWitherSkeleton = regist(register, new BlockNoLifeSkull(), "nolife_wither_skeleton");
		stand = regist(register, new BlockStand(), "stand");
		lavaStone = regist(register, new BlockLavaStone(), "lava_stone");
		woodDust = regist(register, new BlockWoodDust(), "wood_dust");
		stove = regist(register, new BlockStove(), "stove");
	}
}
