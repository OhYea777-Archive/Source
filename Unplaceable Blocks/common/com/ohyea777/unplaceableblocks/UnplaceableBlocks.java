package com.ohyea777.unplaceableblocks;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class UnplaceableBlocks extends JavaPlugin {
	
	public static UnplaceableBlocks instance;
	private PlaceListener listener;
	
	@Override
	public void onEnable() {
		instance = this;
		
		getLogger().info(String.format("Version: %s - By OhYea777 Enabled!", getDescription().getVersion()));
		
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		
		listener = new PlaceListener();
		getServer().getPluginManager().registerEvents(listener, instance);
	}
	
	@Override
	public void onDisable() {
		instance = null;
		
		getLogger().info(String.format("Version: %s - By OhYea777 Disabled!", getDescription().getVersion()));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1 && args[0].equalsIgnoreCase("about")) {
			sender.sendMessage(Messages.PREFIX + ChatColor.translateAlternateColorCodes('&', "Plugin by &6OhYea777&f!"));
			sender.sendMessage(Messages.PREFIX + ChatColor.translateAlternateColorCodes('&', "Version: &6" + getDescription().getVersion() + "&f!"));
			return true;
		} else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (sender.hasPermission("ub.reload")) {
				reloadConfig();
				listener.reload();
				sender.sendMessage(Messages.PREFIX + "" + Messages.RELOADED);
				return true;
			} else {
				sender.sendMessage(Messages.PREFIX + "" + Messages.NO_PERM_RELOAD);
				return true;
			}
		}
		
		sender.sendMessage(Messages.PREFIX + ChatColor.translateAlternateColorCodes('&', "For Info About the Plugin: &6/ub about"));
		sender.sendMessage(Messages.PREFIX + ChatColor.translateAlternateColorCodes('&', "To Reload the Plugin's Configs: &6/ub reload"));
		
		return false;
	}
	
}
