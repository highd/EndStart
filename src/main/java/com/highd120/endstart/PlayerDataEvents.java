package com.highd120.endstart;

import java.util.ArrayList;
import java.util.List;

import com.highd120.endstart.util.ItemUtil;
import com.highd120.endstart.util.NbtTagUtil;
import com.highd120.endstart.util.WorldUtil;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class PlayerDataEvents {

    public static final String TAG_PLAYER_SPAWN = "endstart.spawn";

    public static final String TAG_NO_DRAGON = "endstart.no_dragon";

    public static final String TAG_TOUCH_ENDPORTAL = "endstart.touch_portal";

    public static final PropertyDirection FACING = PropertyDirection.create("facing",
            EnumFacing.Plane.HORIZONTAL);

    /**
     * プレイヤーの更新処理。
     * @param event イベントデータ
     */
    @SubscribeEvent
    public static void onPlayerUpdate(LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer
                && !event.getEntityLiving().worldObj.isRemote) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            NBTTagCompound data = player.getEntityData();
            if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
                data.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
            }
            World world = player.worldObj;
            NBTTagCompound persist = data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
            if (player.ticksExisted > 3 && !persist.getBoolean(TAG_NO_DRAGON)) {
                List<EntityDragon> list = world.getEntities(EntityDragon.class,
                        EntitySelectors.IS_ALIVE);
                if (!list.isEmpty() && world.provider instanceof WorldProviderEnd) {
                    world.removeEntity(list.get(0));
                    final WorldProviderEnd end = (WorldProviderEnd) world.provider;
                    end.getDragonFightManager().processDragonDeath(list.get(0));
                    world.setBlockToAir(world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION));
                    deletePortal(world);
                    createPortalBlock(world, player);
                    persist.setBoolean(TAG_NO_DRAGON, true);
                    BlockPos playerPostion = world
                            .getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION)
                            .add(1, -3, 1);
                    persist.setInteger("endstart.x", playerPostion.getX());
                    persist.setInteger("endstart.y", playerPostion.getY());
                    persist.setInteger("endstart.z", playerPostion.getZ());
                    player.setPositionAndUpdate(playerPostion.getX(), playerPostion.getY(),
                            playerPostion.getZ());
                }
            }
            if (player.ticksExisted > 3 && !persist.getBoolean(TAG_PLAYER_SPAWN)) {
                player.addChatComponentMessage(new TextComponentString("Spawn!"));
                player.changeDimension(1);
                persist.setBoolean(TAG_PLAYER_SPAWN, true);
                persist.setBoolean(TAG_NO_DRAGON, false);
            }
            int playerY = player.getPosition().getY();
            BlockPos playerBottom = player.getPosition().add(0, -1, 0);
            if (playerY > 2 && world.getBlockState(playerBottom).getBlock() == Blocks.END_PORTAL) {
                persist.setBoolean(TAG_TOUCH_ENDPORTAL, true);
            }
        }
    }

    private static void deletePortal(World world) {
        List<BlockPos> portalList = new ArrayList<>();
        for (int i = -8; i <= 8; ++i) {
            for (int j = -8; j <= 8; ++j) {
                Chunk chunk = world.getChunkFromChunkCoords(i, j);
                for (TileEntity tileentity : chunk.getTileEntityMap().values()) {
                    if (tileentity instanceof TileEntityEndPortal) {
                        portalList.add(tileentity.getPos());
                    }
                }
            }
        }
        for (BlockPos postion : portalList) {
            world.setBlockToAir(postion);
        }
    }

    private static void createPortalBlock(World world, EntityPlayer player) {
        world.setBlockState(player.getPosition().add(0, -2, 0),
                Blocks.BEDROCK.getDefaultState(), 2);
        world.setBlockState(player.getPosition().add(0, -3, 0),
                Blocks.END_PORTAL.getDefaultState(), 2);
        world.setBlockState(player.getPosition().add(0, -4, 0),
                Blocks.BEDROCK.getDefaultState(), 2);
        world.setBlockState(player.getPosition().add(1, -3, 0),
                Blocks.BEDROCK.getDefaultState(), 2);
        world.setBlockState(player.getPosition().add(-1, -3, 0),
                Blocks.BEDROCK.getDefaultState(), 2);
        world.setBlockState(player.getPosition().add(0, -3, 1),
                Blocks.BEDROCK.getDefaultState(), 2);
        world.setBlockState(player.getPosition().add(0, -3, -1),
                Blocks.BEDROCK.getDefaultState(), 2);
    }

    /**
     * プレイヤーがリスポーンした時の処理。
     * @param event イベントデータ
     */
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        NBTTagCompound data = player.getEntityData();
        if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            data.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
        }
        NBTTagCompound persist = data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

        int x = persist.getInteger("endstart.x");
        int y = persist.getInteger("endstart.y");
        int z = persist.getInteger("endstart.z");
        if (!persist.getBoolean(TAG_TOUCH_ENDPORTAL)) {
            player.changeDimension(1);
            player.setPositionAndUpdate(x, y, z);
        }
    }

    private static void onBreakDrawer(BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player == null) {
            return;
        }
        RayTraceResult rayResult = ForgeHooks.rayTraceEyes(player,
                ((EntityPlayerMP) player).interactionManager.getBlockReachDistance() + 1);
        if (rayResult == null) {
            return;
        }
        EnumFacing side = rayResult.sideHit;
        if (side == event.getWorld().getBlockState(event.getPos()).getValue(FACING)) {
            return;
        }
        WorldUtil.getNbtTag(event.getWorld(), event.getPos()).ifPresent(tag -> {
            NbtTagUtil.nbtTagListToList(tag.getTagList("Upgrades", 10)).stream()
                    .filter(child -> child.getString("id").equals("storagedrawers:upgradeCreative"))
                    .forEach(child -> {
                        ItemStack stack = ItemStack.loadItemStackFromNBT(child);
                        ItemUtil.dropItem(event.getWorld(), event.getPos(), stack);
                    });
        });
    }

    /**
     * ブロック破壊時のイベント。
     * @param event イベント。
     */
    @SubscribeEvent
    public static void onBreakBlock(BreakEvent event) {
        String blockName = event.getState().getBlock().getRegistryName().toString();
        if (blockName.equals("storagedrawers:basicDrawers")) {
            onBreakDrawer(event);
        }
    }
}
