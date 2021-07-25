package com.highd120.endstart.network;

import com.highd120.endstart.block.crafter.CrafterEffectManager;
import com.highd120.endstart.block.crafter.TileCrafterCore;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class NetworkInjectionEffect implements IMessage {
    private BlockPos point;

    public NetworkInjectionEffect(BlockPos point) {
        this.point = point;
    }

    public NetworkInjectionEffect() {
        this.point = new BlockPos(0, 0, 0);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        point = new BlockPos(x, y, z);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(point.getX());
        buf.writeInt(point.getY());
        buf.writeInt(point.getZ());
    }

    public static class Handler implements IMessageHandler<NetworkInjectionEffect, IMessage> {

        @Override
        public IMessage onMessage(NetworkInjectionEffect message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().world;
                TileCrafterCore tile = (TileCrafterCore) world
                        .getTileEntity(new BlockPos(message.point));
                CrafterEffectManager effect = tile.getEffect();
                if (effect != null) {
                    effect.spawnWisp();
                }
            });
            return null;
        }

    }
}
