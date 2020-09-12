package com.highd120.endstart.command;

import com.highd120.endstart.item.ItemNewRecipeCreater;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
public class ReloadCommand extends CommandBase {

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ItemNewRecipeCreater.load();
	}

	@Override
	public String getName() {
		return "endstart-reload";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}
}
