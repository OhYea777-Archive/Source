package com.ohyea777.plunder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.plunder.module.CommandLoader;
import com.ohyea777.plunder.module.ModuleLoader;

public class PlunderTools extends JavaPlugin {

	public static PlunderTools instance;
	private ModuleLoader loader;
	
	@Override
	public void onEnable() {
		instance = this;
		
		getLogger().info(String.format("Version: [%s], Authors: %s - Is Now Enabled", getDescription().getVersion(), getDescription().getAuthors()));
		saveDefaultConfig();
		
		loader = new ModuleLoader();
		loader.load();
	}
	
	@Override
	public void onDisable() {
		getLogger().info(String.format("Version: [%s], Authors: %s - Has Been Disabled", getDescription().getVersion(), getDescription().getAuthors()));
		loader.onDisable();
		
		loader = null;
		instance = null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return loader.onCommand(sender, command, label, args);
	}
	
	public CommandLoader getCommandLoader() {
		return loader.getCommandLoader();
	}
	
}
