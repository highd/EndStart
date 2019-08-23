package com.highd120.endstart.command;

import com.highd120.endstart.item.ItemRecipeCreater;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class DeleteRecipeTmpCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "endstart-delete";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			ItemStack stack = player.getHeldItemMainhand();
			ItemRecipeCreater.deleteRecipe(stack);
		}

	}

}
