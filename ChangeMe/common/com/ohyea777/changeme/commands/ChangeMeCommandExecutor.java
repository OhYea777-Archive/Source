package com.ohyea777.changeme.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChangeMeCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Bukkit.broadcastMessage("Label='" + label + "', Args='" + args + "', Usage='" + cmd.getUsage() + "', Description='" + cmd.getDescription() + "'");
		return true;
	}

}
