package com.highd120.endstart.world;

import java.util.Random;

import com.highd120.endstart.EndStartConfig;
import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.util.WorldUtil;

import net.minecraft.init.Biomes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator {
	private static ResourceLocation LIBRARY_STRUCTURE = new ResourceLocation(EndStartMain.MOD_ID, "end_library");
	
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
            IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(!(world instanceof WorldServer))
			return;
		WorldServer sWorld = (WorldServer) world;
		int x = chunkX * 16;
		int z = chunkZ * 16;
        BlockPos pos = world.getHeight(new BlockPos(x + 8, 0, z + 8)).add(0, 130, 0);
		int y = pos.getY();
        if (isGenerateLand(pos, random, world)) {
        	generateLand(sWorld, random, pos);
        }
        
    }

    private boolean isGenerateLand(BlockPos pos, Random random, World world) {
        if (!(EndStartConfig.isGenerateLibrary)) {
            return false;
        }
        if (pos.getY() > 200) {
            return false;
        }
        boolean isValid = WorldUtil
                .getPostionList(new AxisAlignedBB(pos.add(-7, 0, -7), pos.add(7, 11, 7)))
                .stream()
                .allMatch(postion -> world.isAirBlock(postion));

		Biome biome = world.getBiomeForCoordsBody(pos);
        return isValid && 
        		random.nextInt(EndStartConfig.generateLibraryRate) == 0 &&
        		biome == Biomes.SKY;
    }
	public static void generateLand(WorldServer world, Random random, BlockPos pos) {
		MinecraftServer server = world.getMinecraftServer();
		Template template = world.getStructureTemplateManager().get(server, LIBRARY_STRUCTURE);
		PlacementSettings settings = new PlacementSettings();
		template.addBlocksToWorld(world, pos, settings);
	}

}
