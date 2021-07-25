package com.highd120.endstart.proxy;

import com.highd120.endstart.EntityEndZombie;
import com.highd120.endstart.RenderEndZombie;
import com.highd120.endstart.block.TileRack;
import com.highd120.endstart.block.TileRackRenderer;
import com.highd120.endstart.block.TileStand;
import com.highd120.endstart.block.TileStove;
import com.highd120.endstart.block.TileStoveRenderer;
import com.highd120.endstart.block.base.TileSingleItemRenderer;
import com.highd120.endstart.block.charmagic.TileChar;
import com.highd120.endstart.block.charmagic.TileCharRenderer;
import com.highd120.endstart.block.crafter.TileCrafterCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrafterCore.class,
                new TileSingleItemRenderer(1.7f));
        ClientRegistry.bindTileEntitySpecialRenderer(TileStand.class,
                new TileSingleItemRenderer(1.5f));
        ClientRegistry.bindTileEntitySpecialRenderer(TileChar.class,
                new TileCharRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileStove.class,
                new TileStoveRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRack.class,
                new TileRackRenderer());
    }

    @Override
    public void init() {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        renderManager.entityRenderMap.put(EntityEndZombie.class, new RenderEndZombie(renderManager, new ModelZombie()));
    }

    @Override
	public void wispFX(double x, double y, double z, float r, float g, float b, float size, float motionx, float motiony, float motionz, float maxAgeMul) {
        FXWisp wisp = new FXWisp(Minecraft.getMinecraft().world, x, y, z, size, r, g, b);
        wisp.setSpeed(motionx, motiony, motionz);
		Minecraft.getMinecraft().effectRenderer.addEffect(wisp);
	}

    @Override
    public void spawnWispInjection(Vec3d initPoint) {
        FxWispInjection wisp = new FxWispInjection(Minecraft.getMinecraft().world, initPoint);
        Minecraft.getMinecraft().effectRenderer.addEffect(wisp);
    }

    @Override
    public void spawnWispInjection(Vec3d initPoint, int size, int lifeTime) {
        World world = Minecraft.getMinecraft().world;
        FxWispInjection wisp = new FxWispInjection(world, initPoint, size, lifeTime);
        Minecraft.getMinecraft().effectRenderer.addEffect(wisp);
    }

}