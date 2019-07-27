package com.highd120.endstart.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldUtil {
    /**
     * ワールドからタイルエンティティを検索してリストにする。
     * @param world ワールド。
     * @param box 検索範囲。
     * @param isCast キャストできるか。
     * @return リスト。
     */
    public static <T extends TileEntity> List<T> scanTileEnity(World world, AxisAlignedBB box,
            Predicate<TileEntity> isCast) {
        List<T> list = new ArrayList<>();
        for (int x = (int) box.minX; x <= box.maxX; x++) {
            for (int y = (int) box.minY; y <= box.maxY; y++) {
                for (int z = (int) box.minZ; z <= box.maxZ; z++) {
                    TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
                    if (tile == null || !isCast.test(tile)) {
                        continue;
                    }
                    @SuppressWarnings("unchecked")
                    T casted = (T) tile;
                    list.add(casted);
                }
            }
        }
        return list;
    }

    /**
     * 範囲内の座標のリストの入手。
     * @param box 検索範囲。
     * @return 座標のリスト。
     */
    public static List<BlockPos> getPostionList(AxisAlignedBB box) {
        List<BlockPos> postionList = new ArrayList<>();
        for (int x = (int) box.minX; x <= box.maxX; x++) {
            for (int y = (int) box.minY; y <= box.maxY; y++) {
                for (int z = (int) box.minZ; z <= box.maxZ; z++) {
                    postionList.add(new BlockPos(x, y, z));
                }
            }
        }
        return postionList;
    }

    /**
     * NBTタグの入手。
     * @param world ワールド。
     * @param pos 座標。
     * @return NBTタグ。
     */
    public static Optional<NBTTagCompound> getNbtTag(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile == null) {
            return Optional.empty();
        }
        NBTTagCompound tag = new NBTTagCompound();
        tile.writeToNBT(tag);
        return Optional.ofNullable(tag);
    }
}