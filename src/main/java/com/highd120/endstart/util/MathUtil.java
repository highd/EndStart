package com.highd120.endstart.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class MathUtil {
    /**
     * AxisAlignedBBDCubeの作成。
     * @param postion 中心座標。
     * @param range 半径。
     * @return
     */
    public static AxisAlignedBB getAxisAlignedCube(BlockPos postion, int range) {
        return new AxisAlignedBB(postion.add(-range, -range, -range),
                postion.add(range + 1, range + 1, range + 1));
    }

    /**
     * AxisAlignedBBDPlaneの作成。
     * @param postion 中心座標。
     * @param range 半径。
     * @return
     */
    public static AxisAlignedBB getAxisAlignedPlane(BlockPos postion, int range) {
        return new AxisAlignedBB(postion.add(-range, 0, -range),
                postion.add(range + 1, 0, range + 1));
    }

    /**
     * 二つのベクトルの角度の計算。
     * @param vec1 ベクトル1
     * @param vec2 ベクトル2
     * @return
     */
    public static double angleCalculation(Vec3d vec1, Vec3d vec2) {
        double a = vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z;
        double b = vec1.x * vec1.x + vec1.y * vec1.y + vec1.z * vec1.z;
        double c = vec2.x * vec2.x + vec2.y * vec2.y + vec2.z * vec2.z;
        return Math.toDegrees(Math.acos(a / (Math.sqrt(b) * Math.sqrt(c))));
    }

    public static Vec3d blockPosToVec3dCenter(BlockPos pos) {
        return new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    }

    public static Vec3d vec3iToVec3d(Vec3i pos) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }
}
