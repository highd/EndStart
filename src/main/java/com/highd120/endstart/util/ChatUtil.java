package com.highd120.endstart.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil {
	public static void addChatMsg(EntityPlayer player, String msgId) {
		String msg = I18n.format("item.endstart.msg." + msgId);
		player.addChatComponentMessage(new TextComponentString(msg));
	}
}
