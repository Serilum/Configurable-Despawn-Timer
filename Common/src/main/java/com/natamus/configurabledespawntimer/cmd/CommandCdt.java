package com.natamus.configurabledespawntimer.cmd;
import com.natamus.configurabledespawntimer.util.Reference;

import com.mojang.brigadier.CommandDispatcher;
import com.natamus.collective.functions.MessageFunctions;
import com.natamus.configurabledespawntimer.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.permissions.Permissions;

public class CommandCdt {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("cdt").requires((iCommandSender) -> iCommandSender.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
			.executes((command) -> {
				CommandSourceStack source = command.getSource();
				
				showUsage(source);
				return 1;
			})
			.then(Commands.literal("usage")
			.executes((command) -> {
				CommandSourceStack source = command.getSource();
				
				showUsage(source);
				return 1;
			}))
			.then(Commands.literal("reload")
			.executes((command) -> {
				CommandSourceStack source = command.getSource();
				
				try {
					Util.loadItemConfig(source.getLevel());
				} catch (Exception ex) {
					MessageFunctions.sendTranslatableMessage(source, "collective.configurabledespawntimer.message.somethingwentwrong", ChatFormatting.RED);
					ex.printStackTrace();
					return 0;
				}
				
				MessageFunctions.sendTranslatableMessage(source, "collective.configurabledespawntimer.message.successfullyloadeddespawn", ChatFormatting.DARK_GREEN);
				return 1;
			}))
		);
	}
	
	private static void showUsage(CommandSourceStack source) {
		MessageFunctions.sendTranslatableMessage(source, "collective.shared.message.usage", true, ChatFormatting.DARK_GREEN, Reference.NAME);
		MessageFunctions.sendMessage(source, " /cdt usage", ChatFormatting.DARK_GREEN);
		MessageFunctions.sendTranslatableMessage(source, "  ", "collective.shared.message.showmessage", ChatFormatting.DARK_GRAY);
		MessageFunctions.sendMessage(source, " /cdt reload", ChatFormatting.DARK_GREEN);
		MessageFunctions.sendTranslatableMessage(source, "  ", "collective.shared.message.reloadsconfigfile", ChatFormatting.DARK_GRAY);
	}
}
