package com.highd120.endstart.proxy;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FXWisp extends Particle {
    private final float maxSize;

	public FXWisp(World world, double x, double y, double z,  float size, float red, float green, float blue) {
        super(world, x, y, z, 0, 0, 0);
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.setParticleTextureIndex(32);
        this.setSize(0.08F, 0.08F);
        this.particleScale = size;
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        this.maxSize = size;
        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
    }

    public void setSpeed(float x, float y, float z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }

    @Override
    public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float f = (float)this.particleTextureIndexX / 16.0F;
        float f1 = f + 0.0624375F;
        float f2 = (float)this.particleTextureIndexY / 16.0F;
        float f3 = f2 + 0.0624375F;
        float f4 = 0.1F * this.particleScale;
        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        Vec3d[] avec3d = new Vec3d[] {
            new Vec3d(
                (double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4),
                (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4),
                (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)
            ),
            new Vec3d(
                (double)(rotationX * f4 + rotationXY * f4),
                (double)(rotationZ * f4),
                (double)(rotationYZ * f4 + rotationXZ * f4)
            ),
            new Vec3d(
                (double)(rotationX * f4 - rotationXY * f4),
                (double)(-rotationZ * f4),
                (double)(rotationYZ * f4 - rotationXZ * f4)
            )
        };
        worldRendererIn.pos((double)f5 + avec3d[0].x, (double)f6 + avec3d[0].y, (double)f7 + avec3d[0].z).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        worldRendererIn.pos((double)f5 + avec3d[1].x, (double)f6 + avec3d[1].y, (double)f7 + avec3d[1].z).tex((double)f1, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        worldRendererIn.pos((double)f5 + avec3d[2].x, (double)f6 + avec3d[2].y, (double)f7 + avec3d[2].z).tex((double)f, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        worldRendererIn.pos((double)f5 + avec3d[3].x, (double)f6 + avec3d[3].y, (double)f7 + avec3d[3].z).tex((double)f, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.particleAge++;
        this.particleScale = ((float) (this.particleMaxAge - this.particleAge)) / this.particleMaxAge * maxSize;
        if (this.particleAge >= this.particleMaxAge) {
            this.setExpired();
        }
    }
}