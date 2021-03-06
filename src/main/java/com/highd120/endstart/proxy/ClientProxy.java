package com.highd120.endstart.proxy;

import com.highd120.endstart.block.TileCrafterCore;
import com.highd120.endstart.block.TileSingleItemRenderer;
import com.highd120.endstart.block.TileStand;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrafterCore.class,
                new TileSingleItemRenderer(0.1f));
        ClientRegistry.bindTileEntitySpecialRenderer(TileStand.class,
                new TileSingleItemRenderer(0.5f));
    }

    @Override
	public void wispFX(double x, double y, double z, float r, float g, float b, float size, float motionx, float motiony, float motionz, float maxAgeMul) {
        FXWisp wisp = new FXWisp(Minecraft.getMinecraft().theWorld, x, y, z, size, r, g, b);
        wisp.setSpeed(motionx, motiony, motionz);
		Minecraft.getMinecraft().effectRenderer.addEffect(wisp);
	}

    @Override
    public void spawnWispInjection(Vec3d initPoint) {
        FxWispInjection wisp = new FxWispInjection(Minecraft.getMinecraft().theWorld, initPoint);
        Minecraft.getMinecraft().effectRenderer.addEffect(wisp);
    }

    @Override
    public void spawnWispInjection(Vec3d initPoint, int size, int lifeTime) {
        World world = Minecraft.getMinecraft().theWorld;
        FxWispInjection wisp = new FxWispInjection(world, initPoint, size, lifeTime);
        Minecraft.getMinecraft().effectRenderer.addEffect(wisp);
    }

}