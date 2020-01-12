package com.highd120.endstart.network;

import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketNormal implements IMessage {
	private Data data;

	public PacketNormal() {

	}

	public PacketNormal(BlockPos position, Integer dimensionId) {
		data = new Data();
		data.x = position.getX();
		data.y = position.getY();
		data.z = position.getZ();
		data.dimensionId = dimensionId;
	}

	public <T> void setValue(T value) {
		Gson gson = new Gson();
		data.json = gson.toJson(value);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		Gson gson = new Gson();
		String json = new String(buf.array());
		json = json.substring(1).trim();
		data = gson.fromJson(json, Data.class);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		Gson gson = new Gson();
		String json = gson.toJson(data);
		buf.writeBytes(json.getBytes());
	}

	public static class Data {
		public Integer dimensionId;
		public double x;
		public double y;
		public double z;
		public String json;
	}

	public static class Handler implements IMessageHandler<PacketNormal, IMessage> {
		@Override
		public IMessage onMessage(PacketNormal message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketNormal message, MessageContext ctx) {
			Gson gson = new Gson();
			EntityPlayerMP playerEntity = ctx.getServerHandler().playerEntity;
			World world;
			if (message.data.dimensionId == null) {
				world = playerEntity.worldObj;
			} else {
				world = DimensionManager.getWorld(message.data.dimensionId);
			}
			if (world == null) {
				return;
			}
			double x = message.data.x;
			double y = message.data.y;
			double z = message.data.z;
			BlockPos postion = new BlockPos(x, y, z);
			TileEntity tile = world.getTileEntity(postion);
			TileEntityHasPacket hasPacket = (TileEntityHasPacket) tile;
			Object data = gson.fromJson(message.data.json, hasPacket.getDataClass());
			hasPacket.execute(playerEntity, data);
		}
	}

}
