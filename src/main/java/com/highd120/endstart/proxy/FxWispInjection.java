package com.highd120.endstart.proxy;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FxWispInjection extends FXWisp {
    /**
     * コンストラクター。
     * @param world ワールド。
     * @param initPoint 初期地点。
     */
    public FxWispInjection(World world, Vec3d initPoint) {
        super(world, initPoint.x, initPoint.y, initPoint.z, 6, 0, 1, 0);
    }

    /**
     * コンストラクター。
     * @param world ワールド。
     * @param initPoint 初期地点。
     */
    public FxWispInjection(World world, Vec3d initPoint, int size, int lifeTime) {
        super(world, initPoint.x, initPoint.y, initPoint.z, size, 0, 1, 0);
    }
}
