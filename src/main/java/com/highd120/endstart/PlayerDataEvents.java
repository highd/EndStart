package com.highd120.endstart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.highd120.endstart.item.ItemExtra;
import com.highd120.endstart.util.ItemUtil;
import com.highd120.endstart.util.NbtTagUtil;
import com.highd120.endstart.util.WorldUtil;
import com.highd120.endstart.util.item.ItemManager;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.registry.GameData;

public class PlayerDataEvents {
	protected static final Random random = new Random();

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

	private static int getBeheadingLevel(ItemStack item) {
		if (item == null) {
			return 0;
		}
		NBTTagCompound tag = item.getTagCompound();
		if (tag == null) {
			return 0;
		}
		NBTTagList modifiers = tag.getTagList("Modifiers", 10);
		if (modifiers == null) {
			return 0;
		}
		for (int i = 0; i < modifiers.tagCount(); i++) {
			NBTTagCompound child = modifiers.getCompoundTagAt(i);
			String id = child.getString("identifier");
			if ("beheading".equals(id) || "beheading_cleaver".equals(id)) {
				return child.getInteger("level");
			}
		}
		return 0;
	}

	private static ItemStack getHead(EntityLivingBase entity) {
		System.out.println(EntityList.getEntityString(entity));
		if (entity instanceof EntityEnderman) {
			Item flower = GameData.getItemRegistry().getObject(new ResourceLocation("enderio", "blockEndermanSkull"));
			return new ItemStack(flower);
		}
		return null;
	}

	@SubscribeEvent
	public static void onLivingDrops(LivingDropsEvent event) {
		if ("ieCrushed".equals(event.getSource().getDamageType()) && event.getEntity() instanceof EntityZombie
				&& random.nextInt(100) < 5) {
			ItemStack lifeCore = ItemManager.getItemStack(ItemExtra.class, 1);
			BlockPos postion = event.getEntity().getPosition();
			EntityItem result = new EntityItem(event.getEntity().getEntityWorld(), postion.getX(), postion.getY(),
					postion.getZ(), lifeCore);
			event.getDrops().add(result);
		}
		int rank = (int) (50 + event.getLootingLevel() * 6.25f);
		if (event.getEntity() instanceof EntityShulker && rank < random.nextInt(100)) {
			EntityItem entityitem = new EntityItem(event.getEntityLiving().getEntityWorld(),
					event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, 
					ItemManager.getItemStack(ItemExtra.class, 4));
			entityitem.setDefaultPickupDelay();
			event.getDrops().add(entityitem);
		}
		if (!(event.getSource().getEntity() instanceof EntityPlayer)) {
			return;
		}
		ItemStack item = ((EntityPlayer) event.getSource().getEntity()).getHeldItem(EnumHand.MAIN_HAND);
		int level = getBeheadingLevel(item);
		if (level == 0) {
			return;
		}
		ItemStack head = getHead(event.getEntityLiving());
		if (head != null && level > random.nextInt(10)) {
			EntityItem entityitem = new EntityItem(event.getEntityLiving().getEntityWorld(),
					event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, head);
			entityitem.setDefaultPickupDelay();
			event.getDrops().add(entityitem);
		}
	}
}
