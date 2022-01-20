package com.mcjailed.jailedrp;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.mcjailed.jailedrp.util.Messages.*;

public class RPLockPick implements IItemUse {

	private JailedRP plugin;
	private Map<String, Map<Location, Integer>> clicks;
	
	@Override
	public void addItem(JailedRP plugin) {
		this.plugin = plugin;
		clicks = new HashMap<String, Map<Location, Integer>>();
	}

	@Override
	public void onPlayerRightClickBlock(PlayerInteractEvent event) {
		if (event.getClickedBlock().getType().equals(Material.WOODEN_DOOR)) {
			Location location = event.getClickedBlock().getLocation();
			if (event.getClickedBlock().getRelative(BlockFace.UP).getType().equals(Material.WOODEN_DOOR)) {
				location = event.getClickedBlock().getRelative(BlockFace.UP).getLocation();
			}
			
			if (!plugin.getLoader().getDoor(location).isLocked() || plugin.getLoader().getDoor(location).isUser(event.getPlayer()))
				return;
			
			if (clicks.containsKey(event.getPlayer().getName())) {
				Map<Location, Integer> locations = clicks.get(event.getPlayer().getName());
				if (locations.containsKey(location)) {
					int click = locations.remove(location) + 1;
					event.getPlayer().sendMessage(PREFIX + "" + PROGRESS_RPLOCKPICK + getString(click, 30));
					if (click == 30) {
						plugin.getLoader().getDoor(location).setLocked(false);
						event.getPlayer().sendMessage(PREFIX + "" + DOOR_PICKED_RPLOCKPICK);
						event.getPlayer().getWorld().playSound(location, Sound.CLICK, 0.5F, 1F);
					} else {
						locations.put(location, click);
						clicks.remove(event.getPlayer().getName());
						clicks.put(event.getPlayer().getName(), locations);
					}
				} else {
					locations.put(location, 1);
					clicks.remove(event.getPlayer().getName());
					clicks.put(event.getPlayer().getName(), locations);
					event.getPlayer().sendMessage(PREFIX + "" + PROGRESS_RPLOCKPICK + getString(1, 30));
					schedule(event.getPlayer(), location);
				}
			} else {
				Map<Location, Integer> locations = new HashMap<Location, Integer>();
				locations.put(location, 1);
				clicks.put(event.getPlayer().getName(), locations);
				event.getPlayer().sendMessage(PREFIX + "" + PROGRESS_RPLOCKPICK + getString(1, 30));
				schedule(event.getPlayer(), location);
			}
		}
	}
	
	public String getString(int current, int max) {
		if (current <= max) {
			StringBuilder string = new StringBuilder(ChatColor.DARK_GRAY + "[");
			for (int i = 0; i < max; i ++) {
				if (i < current)
					string.append(ChatColor.AQUA).append("|");
				else
					string.append(ChatColor.GRAY).append("|");
			}
			return string.append(ChatColor.DARK_GRAY).append("]").toString();
		} else {
			return "";
		}
	}
	
	private void schedule(final Player player, final Location location) {
		final String name = player.getName();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				Map<Location, Integer> locations = clicks.remove(name);
				if (plugin.getLoader().getDoor(location).isLocked() && player.isOnline())
					player.sendMessage(PREFIX + "" + FAILED_TO_PICK_RPLOCKPICK);
				locations.remove(location);
				clicks.put(name, locations);
			}
		}, 13 * 20);
	}

	@Override
	public void onPlayerLeftClickBlock(PlayerInteractEvent event) { }

	@Override
	public void onPlayerRightClickAir(PlayerInteractEvent event) { }

	@Override
	public void onPlayerLeftClickAir(PlayerInteractEvent event) { }

	@Override
	public void onPlayerRightClickEntity(PlayerInteractEntityEvent event) { }

	@Override
	public void onPlayerLeftClickEntity(EntityDamageByEntityEvent event) { }

	@Override
	public boolean cancelAnvil() {
		return true;
	}

}
