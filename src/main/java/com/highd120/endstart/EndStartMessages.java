package com.highd120.endstart;

import com.highd120.endstart.network.NetworkInjectionEffect;
import com.highd120.endstart.network.NetworkInjectionEffectEnd;
import com.highd120.endstart.network.PacketNormal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class EndStartMessages {
	public static SimpleNetworkWrapper INSTANCE;

	public static void registerNetworkMessages() {
		SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(EndStartMain.MOD_ID);
		INSTANCE = network;
        network.registerMessage(NetworkInjectionEffect.Handler.class, NetworkInjectionEffect.class,
                1, Side.CLIENT);
        network.registerMessage(NetworkInjectionEffectEnd.Handler.class,
                NetworkInjectionEffectEnd.class,
                2, Side.CLIENT);
		network.registerMessage(PacketNormal.Handler.class, PacketNormal.class, 0, Side.CLIENT);
    }

    /**
     * メッセージの送信。
     * @param world ワールド。
     * @param pos 座標。
     * @param toSend メッセージ。
     */
    public static void sendToNearby(World world, BlockPos pos, IMessage toSend) {
        if (world instanceof WorldServer) {
            WorldServer ws = (WorldServer) world;

            for (EntityPlayer player : ws.playerEntities) {
                EntityPlayerMP playerMp = (EntityPlayerMP) player;

                if (playerMp.getDistanceSq(pos) < 64 * 64 && ws.getPlayerChunkMap()
                        .isPlayerWatchingChunk(playerMp, pos.getX() >> 4, pos.getZ() >> 4)) {
                    INSTANCE.sendTo(toSend, playerMp);
                }
            }

        }
    }
}
