package com.highd120.endstart;

import com.highd120.endstart.network.PacketNormal;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class EndStartMessages {

	public static SimpleNetworkWrapper INSTANCE;

	public static void registerNetworkMessages() {
		SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(EndStartMain.MOD_ID);
		network.registerMessage(PacketNormal.Handler.class, PacketNormal.class, 0, Side.SERVER);
		INSTANCE = network;
	}
}
