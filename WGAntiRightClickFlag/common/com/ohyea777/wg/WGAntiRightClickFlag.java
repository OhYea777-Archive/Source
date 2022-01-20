package com.ohyea777.wg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;

public class WGAntiRightClickFlag extends JavaPlugin implements Listener {

	public static WGAntiRightClickFlag instance;
	private StateFlag flag;
	private ConfigManager manager;

	@Override
	public void onEnable() {
		instance = this;
		flag = new StateFlag("rightclick", true);
		getWGCustomFlags().addCustomFlag(flag);
		manager = new ConfigManager(this);

		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (sender.hasPermission("wgantirightclickflag.reload") || sender.hasPermission("wgantirightclickflag.*")) {
				reloadConfig();
				sender.sendMessage(Messages.PREFIX + "" + Messages.RELOADED);
				return true;
			} else {
				sender.sendMessage(Messages.PREFIX + "" + Messages.NO_PERM);
				return false;
			}
		} else if (args.length == 1 && args[0].equalsIgnoreCase("about")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.PREFIX + "Plugin by &bOhYea777&7!"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.PREFIX + "Version: &b" + getDescription().getVersion() + "&7!"));
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.PREFIX + "For Info About the Plugin: &b/rc about"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.PREFIX + "To Reload the Plugin's Configs: &b/rc reload"));
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		if (args.length == 1) {
			String s = args[0].toLowerCase();
			if ((s.length() <= "about".length() || s.length() <= "reload".length()) && s.length() > 0) {
				if ("about".substring(0, s.length()).equals(s)) {
					list.add("about");
				} else if ("reload".substring(0, s.length()).equals(s)) {
					list.add("reload");
				} else {
					list.add("about");
					list.add("reload");
				}
			}  else {
				list.add("about");
				list.add("reload");
			}
		}
		return list;
	}

	private WGCustomFlagsPlugin getWGCustomFlags() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WGCustomFlags");
		if (plugin == null || !(plugin instanceof WGCustomFlagsPlugin)) {
			return null;
		}
		return (WGCustomFlagsPlugin) plugin;
	}

	private WorldGuardPlugin getWG() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null;
		}
		return (WorldGuardPlugin) plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			Block block = event.getClickedBlock();
			if (!event.getPlayer().isOp()) {
				if (!isAllowed(block, event.getPlayer())) {
					if (event.getPlayer().getItemInHand() != null && manager.isItemEnabled(event.getPlayer().getItemInHand(), getRegions(block))) {
						event.setCancelled(true);
						event.getPlayer().sendMessage(Messages.PREFIX + "" + Messages.NO_PERM);
						return;
					}
					if (manager.isBlockEnabled(block, getRegions(block))) {
						event.setCancelled(true);
						event.getPlayer().sendMessage(Messages.PREFIX + "" + Messages.NO_PERM);
						return;
					}
				}
			}
		} else if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (!event.getPlayer().isOp()) {
				if (!isAllowed(event.getPlayer())) {
					if (event.getPlayer().getItemInHand() != null && manager.isItemEnabled(event.getPlayer().getItemInHand(), getRegions(event.getPlayer()))) {
						event.setCancelled(true);
						event.getPlayer().sendMessage(Messages.PREFIX + "" + Messages.NO_PERM);
						return;
					}
				}
			}
		}
	}

	private boolean isAllowed(Block block, Player player) {
		return getWG().getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation()).allows(flag, getWG().wrapPlayer(player));
	}
	
	private boolean isAllowed(Player player) {
		return getWG().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).allows(flag, getWG().wrapPlayer(player));
	}
	
	private ApplicableRegionSet getRegions(Block block) {
		return getWG().getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation());
	}
	
	private ApplicableRegionSet getRegions(Player player) {
		return getWG().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
	}

}
