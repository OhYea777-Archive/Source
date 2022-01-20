package com.mcjailed.jailedrp.listener;

import static com.mcjailed.jailedrp.util.Messages.DOOR_LOCKED_RPKEY;
import static com.mcjailed.jailedrp.util.Messages.PICKUP_DM;
import static com.mcjailed.jailedrp.util.Messages.PREFIX;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mcjailed.jailedrp.JailedRP;
import com.mcjailed.jailedrp.MoneyPrinter;

public class RPListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		JailedRP.instance.getLoader().getPlayer(event.getPlayer());
		event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		JailedRP.instance.getJobLoader().load(JailedRP.instance.getLoader().getPlayer(event.getPlayer()));
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		JailedRP.instance.getJobLoader().remove(JailedRP.instance.getLoader().getPlayer(event.getPlayer()));
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		if (event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().hasLore()) {
			if (ChatColor.stripColor(event.getItem().getItemStack().getItemMeta().getLore().get(0)).equals("[Coin]")) {
				int amount = event.getItem().getItemStack().getAmount();
				
				if (event.getItem().getItemStack().getItemMeta().getLore().size() >= 2) {
					String s = event.getItem().getItemStack().getItemMeta().getLore().get(1);
					for (MoneyPrinter printer : JailedRP.instance.getLoader().getPrinters()) {
						if (printer.getUUID().equals(s)) {
							amount = printer.getAmount();
							printer.setAmount(0);
						}
					}
				}
				
				event.getPlayer().playSound(event.getItem().getLocation(), Sound.NOTE_PLING, 0.3F, 2F);
				event.getPlayer().sendMessage(PREFIX + "" + PICKUP_DM.toString().replace("{amount}", String.valueOf(amount * 10)));
				JailedRP.instance.getEconomyAPI().depositPlayer(event.getPlayer().getName(), 10 * amount);
				event.setCancelled(true);
				event.getItem().remove();
			}
		}
	}
	
	@EventHandler
	public void onInventoryMove(InventoryPickupItemEvent event) {
		if (event.getInventory().getType().equals(InventoryType.HOPPER)) {
			if (event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().hasLore() && ChatColor.stripColor(event.getItem().getItemStack().getItemMeta().getLore().get(0)).equals("[Coin]")) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!event.isCancelled() && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getClickedBlock().getType().equals(Material.WOODEN_DOOR)) {
				Location location = event.getClickedBlock().getLocation();
				if (event.getClickedBlock().getRelative(BlockFace.UP).getType().equals(Material.WOODEN_DOOR)) {
					location = event.getClickedBlock().getRelative(BlockFace.UP).getLocation();
				}
				
				if (JailedRP.instance.getLoader().getDoor(location).isLocked()) {
					if (!(event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().getType().equals(Material.NAME_TAG) && event.getPlayer().getItemInHand().hasItemMeta() && event.getPlayer().getItemInHand().getItemMeta().hasLore() && ChatColor.stripColor(event.getPlayer().getItemInHand().getItemMeta().getLore().get(0)).equalsIgnoreCase("[LockPick]")))
						event.getPlayer().sendMessage(PREFIX + "" + DOOR_LOCKED_RPKEY);
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerBreak(BlockBreakEvent event) {
		if (!event.isCancelled()) {
			if (event.getBlock().getType().equals(Material.WOODEN_DOOR)) {
				event.setCancelled(true);
			}
		}
	}
	
}
