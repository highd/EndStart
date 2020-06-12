package com.highd120.endstart.command;

import com.highd120.endstart.item.ItemNewRecipeCreater;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
public class ReloadCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "endstart-reload";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ItemNewRecipeCreater.load();
	}
}
