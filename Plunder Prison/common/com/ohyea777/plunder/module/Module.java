package com.ohyea777.plunder.module;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.ohyea777.plunder.PlunderTools;

public interface Module {

	public void onInit(PlunderTools plugin);
	
	public void onDeinit();
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args);
	
}
