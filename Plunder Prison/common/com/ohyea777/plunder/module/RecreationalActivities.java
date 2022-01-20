package com.ohyea777.plunder.module;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ohyea777.plunder.PlunderTools;
import com.ohyea777.plunder.util.ConfigManager;
import com.ohyea777.plunder.util.PlunderSerialization;

public class RecreationalActivities implements Module {

	protected PlunderTools plugin;
	private Logger log;
	
	private boolean hasStarted = false;
	private int wait;
	private int warning;
	
	@Override
	public void onInit(PlunderTools plugin) {
		this.plugin = plugin;
		this.log = plugin.getLogger();
		
		log.info("Module: [RecreationalActivities] - Has Been Enabled");
		
		plugin.getCommandLoader().registerCommand("recreation", this);
		
		if (ConfigManager.isRecreationLocationSet())
			start();
	}

	@Override
	public void onDeinit() {
		log.info("Module: [RecreationalActivities] - Has Been Disabled");
		
		plugin.getServer().getScheduler().cancelTask(wait);
		plugin.getServer().getScheduler().cancelTask(warning);
		plugin.getCommandLoader().unRegisterCommand("recreation", this);
		
		log = null;
		plugin = null;
	}
	
	private void start() {
		hasStarted = true;
		wait = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				warn();
			}
		}, ConfigManager.getRecreationWait() * 20 * 60);
	}
	
	private void warn() {
		plugin.getServer().broadcastMessage(ConfigManager.getRecreationWarningMessage().replace("{warning}", "" + ConfigManager.getRecreationWarning()));
		warning = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				teleport();
			}
		}, ConfigManager.getRecreationWarning() * 20 * 60);
	}
	
	private void teleport() {
		for (Player p : plugin.getServer().getOnlinePlayers()) {
			if (!p.hasPermission("plundertools.recreation.exempt") || !p.hasPermission("plundertools.recreation.*") || !p.hasPermission("plundertools.*")) {
				p.teleport(ConfigManager.getRecreationLocation());
				p.sendMessage(ConfigManager.getRecreationTeleportMessage());
			}
		}
		start();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.isOp()) {
			if (args.length == 1 && args[0].equalsIgnoreCase("start")) { 
				if (ConfigManager.isRecreationLocationSet()) {
					if (!hasStarted) {
						start();
						sender.sendMessage(ConfigManager.getPrefix() + _("Recreation Timer Started!"));
					} else {
						sender.sendMessage(ConfigManager.getPrefix() + _("&4Recreation Timer Has Already Been Set!"));
					}
				} else {
					sender.sendMessage(ConfigManager.getPrefix() + _("&4Recreation Location Is Not Set!"));
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("set")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					Location loc = p.getLocation();
					String l = PlunderSerialization.Location.serialize(loc);
					ConfigManager.setRecreationLocation(l);
					sender.sendMessage(ConfigManager.getPrefix() + _("Recreation Location Updated!"));
				} else {
					sender.sendMessage(ConfigManager.getPrefix() + _("&4In-Game Players Only!"));
				}
			} else {
				sender.sendMessage(ConfigManager.getPrefix() + _("&4Invalid Args!"));
			}
		} else {
			sender.sendMessage("Unknown command. Type \"/help\" for help.");
		}
		
		return false;
	}
	
	private String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

}
