package com.ohyea777.plunder.module;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.ohyea777.plunder.PlunderTools;

public class CommandLoader {

	private Map<String, Module> commands;
	
	public CommandLoader() {
		commands = new HashMap<String, Module>();
	}
	
	public void registerCommand(String name, Module module) {
		commands.put(name, module);
	}
	
	public boolean unRegisterCommand(String name, Module module) {
		if (commands.get(name).equals(module)) {
			commands.remove(name);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Module module = (Module) commands.get(command.getName());

		if (module != null) {
			return module.onCommand(sender, command, label, args);
		}
		
		sender.sendMessage("Unknown command. Type \"/help\" for help.");
		PlunderTools.instance.getLogger().severe("Command Loader did not find corrosponding module for command " + command.getName());
		
		return false;
	}
	
}
