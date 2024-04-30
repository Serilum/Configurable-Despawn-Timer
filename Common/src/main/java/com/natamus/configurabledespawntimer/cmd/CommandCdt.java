package com.natamus.configurabledespawntimer.cmd;

import com.mojang.brigadier.CommandDispatcher;
import com.natamus.collective.functions.MessageFunctions;
import com.natamus.configurabledespawntimer.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandCdt {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("cdt").requires((iCommandSender) -> iCommandSender.hasPermission(2))
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
					MessageFunctions.sendMessage(source, "Something went wrong while reloading the despawn timer config file.", ChatFormatting.RED);
					ex.printStackTrace();
					return 0;
				}
				
				MessageFunctions.sendMessage(source, "Successfully loaded the despawn timer config file.", ChatFormatting.DARK_GREEN);
				return 1;
			}))
		);
	}
	
	private static void showUsage(CommandSourceStack source) {
		MessageFunctions.sendMessage(source, "Configurable Despawn Timer Usage:", ChatFormatting.DARK_GREEN, true);
		MessageFunctions.sendMessage(source, " /cemd usage", ChatFormatting.DARK_GREEN);
		MessageFunctions.sendMessage(source, "  Show this message.", ChatFormatting.DARK_GRAY);
		MessageFunctions.sendMessage(source, " /cemd reload", ChatFormatting.DARK_GREEN);
		MessageFunctions.sendMessage(source, "  Reloads the config file.", ChatFormatting.DARK_GRAY);
	}
}
