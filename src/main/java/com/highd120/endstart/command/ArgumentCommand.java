package com.highd120.endstart.command;

import com.highd120.endstart.item.ItemArgument;
import com.highd120.endstart.util.NbtTagUtil;
import com.highd120.endstart.util.item.ItemManager;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import scala.actors.threadpool.Arrays;

public class ArgumentCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "endstart-argument";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if  (!(sender instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) sender;
        ItemStack stack = player.getHeldItemMainhand();
        if (args.length != 1 || stack == null || stack.getItem() != ItemManager.getItem(ItemArgument.class)) {
            sender.addChatMessage(new TextComponentTranslation("endstart.valid")
                .setStyle(new Style().setColor(TextFormatting.RED)));
        }
        NbtTagUtil.setString(ItemArgument.TAG, stack, args[0]);
    }
}