package com.highd120.endstart.network;

import net.minecraft.entity.player.EntityPlayerMP;

public interface TileEntityHasPacket<T> {
	Class<T> getDataClass();

	void execute(EntityPlayerMP playerEntity, T data);
}
