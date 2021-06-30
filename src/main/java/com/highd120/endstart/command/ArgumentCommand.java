package com.highd120.endstart.command;

import com.highd120.endstart.item.ItemArgument;
import com.highd120.endstart.item.ModItems;
import com.highd120.endstart.util.NbtTagUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class ArgumentCommand extends CommandBase {
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if  (!(sender instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) sender;
        ItemStack stack = player.getHeldItemMainhand();
        if (args.length != 1 || stack == null || (stack.getItem() != ModItems.argument && stack.getItem() != ModItems.fluid && stack.getItem() != ModItems.aspect)) {
            sender.sendMessage(new TextComponentTranslation("endstart.valid")
                .setStyle(new Style().setColor(TextFormatting.RED)));
        }
        NbtTagUtil.setString(ItemArgument.TAG, stack, args[0]);
    }

	@Override
	public String getName() {
        return "endstart-argument";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}
}