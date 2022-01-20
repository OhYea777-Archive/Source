package com.ohyea777.unplaceableblocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceListener implements Listener {

	private List<String> name;
	private List<String> lore;
	
	public PlaceListener() {
		reload();
	}
	
	@EventHandler
	public void onPlayerPlace(BlockPlaceEvent event) {
		if (!event.isCancelled() && event.getItemInHand().hasItemMeta() && (event.getItemInHand().getItemMeta().hasDisplayName() || event.getItemInHand().getItemMeta().hasLore())) {
			if (event.getItemInHand().getItemMeta().hasDisplayName()) {
				String n = ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName());
				if (name.contains(n) && !event.getPlayer().hasPermission("ub.place")) {
					event.getPlayer().sendMessage(Messages.PREFIX + "" + Messages.NO_PERM_PLACE);
					event.setCancelled(true);
				}
			}
			if (event.getItemInHand().getItemMeta().hasLore()) {
				for (String s : event.getItemInHand().getItemMeta().getLore()) {
					String l = ChatColor.stripColor(s);
					if (lore.contains(l) && !event.getPlayer().hasPermission("ub.place")) {
						event.getPlayer().sendMessage(Messages.PREFIX + "" + Messages.NO_PERM_PLACE);
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	public void reload() {
		name = UnplaceableBlocks.instance.getConfig().getStringList("Name") != null ? UnplaceableBlocks.instance.getConfig().getStringList("Name") : new ArrayList<String>();
		lore = UnplaceableBlocks.instance.getConfig().getStringList("Name") != null ? UnplaceableBlocks.instance.getConfig().getStringList("Lore") : new ArrayList<String>();
	}
	
}
