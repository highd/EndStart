package com.highd120.endstart.item;

import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = EndStartMain.MOD_ID)
public class ModItems {
	private static <T extends ItemBase> T regist(IForgeRegistry<Item> register, T item, String name) {
        item.setRegistryName(new ResourceLocation(EndStartMain.MOD_ID, name));
        item.setUnlocalizedName(EndStartMain.MOD_ID + "." + name);
        register.register(item);
        item.registerModel();
		return item;
	}
	
	private static <T extends Item> T registNotItemBase(IForgeRegistry<Item> register, T item, String name) {
        item.setRegistryName(new ResourceLocation(EndStartMain.MOD_ID, name));
        item.setUnlocalizedName(EndStartMain.MOD_ID + "." + name);
        register.register(item);
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
		return item;
	}
	
	private static ItemBlock registItemBlock(IForgeRegistry<Item> register, Block block) {
        ItemBlock item = new ItemBlock(block);
        item.setUnlocalizedName(block.getUnlocalizedName());
        item.setRegistryName(block.getRegistryName());
        register.register(item);
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
		return item;
	}
	
	public static ItemArgument argument;
	public static ItemBlood itemBlood;
	public static ItemChalk chalk;
	public static ItemCreateWall createWall;
	public static ItemEndPickaxe endPickaxe;
	public static ItemEndStoneShard endStoneShard;
	public static ItemEndStoneSword endStoneSword;
	public static ItemExtra extra;
	public static ItemNewRecipeCreater newRecipeCreater;
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> register = evt.getRegistry();
		argument = regist(register, new ItemArgument(), "argument");
		itemBlood = regist(register, new ItemBlood(), "item_blood");
		chalk = regist(register, new ItemChalk(), "chalk");
		createWall = regist(register, new ItemCreateWall(), "create_wall");
		endPickaxe = regist(register, new ItemEndPickaxe(), "end_pickaxe");
		endStoneShard = regist(register, new ItemEndStoneShard(), "end_stone_shard");
		endStoneSword = registNotItemBase(register, new ItemEndStoneSword(), "end_stone_sword");
		extra = regist(register, new ItemExtra(), "extra");
		newRecipeCreater = regist(register, new ItemNewRecipeCreater(), "new_recipe");
		
		registItemBlock(register, ModBlocks.blackhole);
		registItemBlock(register, ModBlocks.blood);
		registItemBlock(register, ModBlocks.blockChar);
		registItemBlock(register, ModBlocks.crafterCore);
		registItemBlock(register, ModBlocks.endBed);
		registItemBlock(register, ModBlocks.endSand);
		registItemBlock(register, ModBlocks.nolifeWitherSkeleton);
		registItemBlock(register, ModBlocks.stand);
	}
}