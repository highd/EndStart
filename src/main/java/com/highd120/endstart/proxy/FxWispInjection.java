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
        super(world, initPoint.xCoord, initPoint.yCoord, initPoint.zCoord, 6, 0, 1, 0);
    }

    /**
     * コンストラクター。
     * @param world ワールド。
     * @param initPoint 初期地点。
     */
    public FxWispInjection(World world, Vec3d initPoint, int size, int lifeTime) {
        super(world, initPoint.xCoord, initPoint.yCoord, initPoint.zCoord, size, 0, 1, 0);
    }
}
