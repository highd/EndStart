package com.highd120.endstart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.highd120.endstart.item.ModItems;
import com.highd120.endstart.util.ItemUtil;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class PlayerDataEvents {
	protected static final Random random = new Random();

	public static final String TAG_PLAYER_SPAWN = "endstart.spawn";

	public static final String TAG_NO_DRAGON = "endstart.no_dragon";

	public static final String TAG_TOUCH_ENDPORTAL = "endstart.touch_portal";

	public static final PropertyDirection FACING = PropertyDirection.create("facing",
			EnumFacing.Plane.HORIZONTAL);

	
    private static Chunk getChunk(World worldIn, Vec3d vec3) {
        return worldIn.getChunkFromChunkCoords(MathHelper.floor(vec3.x / 16.0D), MathHelper.floor(vec3.z / 16.0D));
    }
    
    private static BlockPos findSpawnpointInChunk(Chunk chunkIn) {
        BlockPos blockpos = new BlockPos(chunkIn.x * 16, 30, chunkIn.z * 16);
        int i = chunkIn.getTopFilledSegment() + 16 - 1;
        BlockPos blockpos1 = new BlockPos(chunkIn.x * 16 + 16 - 1, i, chunkIn.z * 16 + 16 - 1);
        BlockPos blockpos2 = null;
        double d0 = 0.0D;

        for (BlockPos blockpos3 : BlockPos.getAllInBox(blockpos, blockpos1))
        {
            IBlockState iblockstate = chunkIn.getBlockState(blockpos3);

            if (iblockstate.getBlock() == Blocks.END_STONE && !chunkIn.getBlockState(blockpos3.up(1)).isBlockNormalCube() && !chunkIn.getBlockState(blockpos3.up(2)).isBlockNormalCube())
            {
                double d1 = blockpos3.distanceSqToCenter(0.0D, 0.0D, 0.0D);

                if (blockpos2 == null || d1 < d0)
                {
                    blockpos2 = blockpos3;
                    d0 = d1;
                }
            }
        }

        return blockpos2;
    }
    
    private static BlockPos findHighestBlock(World p_184308_0_, BlockPos p_184308_1_, int p_184308_2_, boolean p_184308_3_) {
        BlockPos blockpos = null;

        for (int i = -p_184308_2_; i <= p_184308_2_; ++i) {
            for (int j = -p_184308_2_; j <= p_184308_2_; ++j) {
                if (i != 0 || j != 0 || p_184308_3_) {
                    for (int k = 255; k > (blockpos == null ? 0 : blockpos.getY()); --k) {
                        BlockPos blockpos1 = new BlockPos(p_184308_1_.getX() + i, k, p_184308_1_.getZ() + j);
                        IBlockState iblockstate = p_184308_0_.getBlockState(blockpos1);

                        if (iblockstate.isBlockNormalCube() && (p_184308_3_ || iblockstate.getBlock() != Blocks.BEDROCK)) {
                            blockpos = blockpos1;
                            break;
                        }
                    }
                }
            }
        }

        return blockpos == null ? p_184308_1_ : blockpos;
    }
    
    private static BlockPos calculateSpawnPoint(World worldIn) {
    	BlockPos exitPortal;
        Vec3d vec3d = (new Vec3d(1, 0.0D, 0)).normalize();
        Vec3d vec3d1 = vec3d.scale(1024.0D);

        for (int i = 16; getChunk(worldIn, vec3d1).getTopFilledSegment() > 0 && i-- > 0; vec3d1 = vec3d1.add(vec3d.scale(-16.0D))) {
        }

        for (int j = 16; getChunk(worldIn, vec3d1).getTopFilledSegment() == 0 && j-- > 0; vec3d1 = vec3d1.add(vec3d.scale(16.0D))) {
        }

        Chunk chunk = getChunk(worldIn, vec3d1);
        exitPortal = findSpawnpointInChunk(chunk);

        if (exitPortal == null) {
            exitPortal = new BlockPos(vec3d1.x + 0.5D, 75.0D, vec3d1.z + 0.5D);
            (new WorldGenEndIsland()).generate(worldIn, new Random(exitPortal.toLong()), exitPortal);
        }
        exitPortal = findHighestBlock(worldIn, exitPortal, 16, true);
        return exitPortal;
    }
    
    public static void createPlace(World world, BlockPos point) {
    	
    }
	/**
	 * プレイヤーの更新処理。
	 * @param event イベントデータ
	 */
	@SubscribeEvent
	public static void onPlayerUpdate(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer
				&& !event.getEntityLiving().getEntityWorld().isRemote) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			NBTTagCompound data = player.getEntityData();
			if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
				data.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
			}
			World world = player.getEntityWorld();
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
					BlockPos playerPostion = calculateSpawnPoint(world);
					persist.setInteger("endstart.x", playerPostion.getX());
					persist.setInteger("endstart.y", playerPostion.getY());
					persist.setInteger("endstart.z", playerPostion.getZ());
					player.setPositionAndUpdate(playerPostion.getX(), playerPostion.getY(),
							playerPostion.getZ());
				}
			}
			if (player.ticksExisted > 3 && !persist.getBoolean(TAG_PLAYER_SPAWN)) {
				persist.setBoolean(TAG_PLAYER_SPAWN, true);
				if (player.isCreative()) {
					persist.setBoolean(TAG_NO_DRAGON, true);
					persist.setBoolean(TAG_TOUCH_ENDPORTAL, true);
				} else {
					player.changeDimension(1);
					persist.setBoolean(TAG_NO_DRAGON, false);
				}
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

	/**
	 * ブロック破壊時のイベント。
	 * @param event イベント。
	 */
	@SubscribeEvent
	public static void onBreakBlock(BreakEvent event) {
		if (event.getState().getBlock() == Blocks.END_STONE && 
			event.getPlayer().getHeldItem(EnumHand.MAIN_HAND) == null &&
			!event.getPlayer().isCreative()) {
			ItemUtil.dropItem(event.getWorld(), event.getPos(), new ItemStack(ModItems.endStoneShard));
		}
	}
	
	private static void addDropItem(LivingDropsEvent event, ItemStack item) {
		BlockPos postion = event.getEntity().getPosition();
		EntityItem result = new EntityItem(event.getEntity().getEntityWorld(), postion.getX(), postion.getY(),
				postion.getZ(), item);
		event.getDrops().add(result);
	}

	@SubscribeEvent
	public static void onLivingDrops(LivingDropsEvent event) {
		if (event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if (player.getHeldItemMainhand().getItem() == ModItems.endStoneSword) {
				addDropItem(event, new ItemStack(ModItems.itemBlood));
			}
		}
		if ("ieCrushed".equals(event.getSource().getDamageType()) && event.getEntity() instanceof EntityZombie
				&& random.nextInt(100) < 5) {
			addDropItem(event, new ItemStack(ModItems.extra, 1, 1));
		}
	}
}
