package com.highd120.endstart.command;

import com.highd120.endstart.PlayerDataEvents;
import com.highd120.endstart.block.ModBlocks;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CreateGatewayCommand extends CommandBase {

	@Override
	public String getName() {
		return "endstart-gateway";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!(sender.getCommandSenderEntity() instanceof EntityPlayer)) {
			return;
		}
		World world = sender.getEntityWorld();
		EntityPlayer player = (EntityPlayer)sender.getCommandSenderEntity();
		NBTTagCompound data = player.getEntityData();
		if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
			data.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
		}
		NBTTagCompound persist = data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

		int x = persist.getInteger("endstart.x");
		int y = persist.getInteger("endstart.y");
		int z = persist.getInteger("endstart.z");
		BlockPos point = new BlockPos(x, y, z).up(PlayerDataEvents.SUB_LAND_GATEWAY_HEIGHT);
		BlockPos point2 = new BlockPos(100, 80, 0);

		createGateWay(world, point);
		setGatewayTarget(world, point, point2.up(4));

		createGateWay(world, point2);
		setGatewayTarget(world, point2, point.up(4));
	}
	
	public static void createGateWay(World world, BlockPos point) {
		world.setBlockState(point.add(0, -2, 0), ModBlocks.endBedRock.getDefaultState(), 3);

		world.setBlockState(point.add(1,  -1,  0), ModBlocks.endBedRock.getDefaultState(), 3);
		world.setBlockState(point.add(-1, -1,  0), ModBlocks.endBedRock.getDefaultState(), 3);
		world.setBlockState(point.add(0,  -1,  0), ModBlocks.endBedRock.getDefaultState(), 3);
		world.setBlockState(point.add(0,  -1,  1), ModBlocks.endBedRock.getDefaultState(), 3);
		world.setBlockState(point.add(0,  -1, -1), ModBlocks.endBedRock.getDefaultState(), 3);

		world.setBlockState(point, Blocks.END_GATEWAY.getDefaultState(), 3);

		world.setBlockState(point.add(1,  1,  0), ModBlocks.endBedRock.getDefaultState(), 3);
		world.setBlockState(point.add(-1, 1,  0), ModBlocks.endBedRock.getDefaultState(), 3);
		world.setBlockState(point.add(0,  1,  0), ModBlocks.endBedRock.getDefaultState(), 3);
		world.setBlockState(point.add(0,  1,  1), ModBlocks.endBedRock.getDefaultState(), 3);
		world.setBlockState(point.add(0,  1, -1), ModBlocks.endBedRock.getDefaultState(), 3);
		
		world.setBlockState(point.add(0, 2, 0), ModBlocks.endBedRock.getDefaultState(), 3);
	}
	
	public static void setGatewayTarget(World world, BlockPos gatewayPos, BlockPos target) {
		TileEntity tile = world.getTileEntity(gatewayPos);
		((TileEntityEndGateway)tile).setExactPosition(target);
	}

}
