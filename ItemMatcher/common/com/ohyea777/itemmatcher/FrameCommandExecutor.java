package com.ohyea777.itemmatcher;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FrameCommandExecutor implements CommandExecutor {

	private ItemMatcher plugin;
	private List<String> players;
	
	public FrameCommandExecutor(ItemMatcher plugin) {
		this.plugin = plugin;
		players = new ArrayList<String>();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			
		} else if (args.length == 2)
		
		return false;
	}

}
