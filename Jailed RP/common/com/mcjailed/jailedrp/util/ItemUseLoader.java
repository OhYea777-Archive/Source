package com.mcjailed.jailedrp.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.mcjailed.jailedrp.IItemUse;
import com.mcjailed.jailedrp.JailedRP;
import com.mcjailed.jailedrp.RPKey;
import com.mcjailed.jailedrp.RPLockPick;
import com.mcjailed.jailedrp.RPPrinter;
import com.mcjailed.jailedrp.RPStunStick;

public class ItemUseLoader implements Listener {

	private Map<String, IItemUse> items;

	public ItemUseLoader() {
		items = new HashMap<String, IItemUse>();
		load();
		JailedRP.instance.getServer().getPluginManager().registerEvents(this, JailedRP.instance);
	}

	private void load() {
		registerItemUse(Material.TRIPWIRE_HOOK, 0, "[Key]", new RPKey());
		registerItemUse(Material.ITEM_FRAME, 0, "[Printer]", new RPPrinter());
		registerItemUse(Material.NAME_TAG, 0, "[LockPick]", new RPLockPick());
		registerItemUse(Material.BLAZE_ROD, 0, "[Stun]", new RPStunStick());
	}

	public void registerItemUse(Material material, int damage, String lore, IItemUse use) {
		String item = material.name() + ":" + damage + ":" + lore;
		items.put(item, use);
		use.addItem(JailedRP.instance);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.hasItem() && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLore()) {
			String item = event.getItem().getType().name() + ":" + event.getItem().getDurability() + ":" + ChatColor.translateAlternateColorCodes('&', event.getItem().getItemMeta().getLore().get(0));
			if (items.containsKey(item))
				if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					items.get(item).onPlayerRightClickBlock(event);
				} else if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					items.get(item).onPlayerRightClickAir(event);
				} else if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
					items.get(item).onPlayerLeftClickAir(event);
				} else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					items.get(item).onPlayerLeftClickBlock(event);
				}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		String item = "";
		if (event.getPlayer().getItemInHand() != null && !event.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
			if (event.getPlayer().getItemInHand().hasItemMeta() && event.getPlayer().getItemInHand().getItemMeta().hasLore()) {
				item = event.getPlayer().getItemInHand().getType().name() + ":" + event.getPlayer().getItemInHand().getDurability() + ":" + ChatColor.stripColor(event.getPlayer().getItemInHand().getItemMeta().getLore().get(0));
				if (items.containsKey(item))
					items.get(item).onPlayerRightClickEntity(event);
			}
		}
	}

	@EventHandler
	public void onPlayerHitEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			String item = "";
			if (((Player) event.getDamager()).getItemInHand() != null && !((Player) event.getDamager()).getItemInHand().getType().equals(Material.AIR)) {
				if (((Player) event.getDamager()).getItemInHand().hasItemMeta() && ((Player) event.getDamager()).getItemInHand().getItemMeta().hasLore()) {
					item = ((Player) event.getDamager()).getItemInHand().getType().name() + ":" + ((Player) event.getDamager()).getItemInHand().getDurability() + ":" + ChatColor.translateAlternateColorCodes('&', ((Player) event.getDamager()).getItemInHand().getItemMeta().getLore().get(0));
					if (items.containsKey(item))
						items.get(item).onPlayerLeftClickEntity(event);
				}
			}
		}

	}
	
	@EventHandler
	public void onRename(InventoryClickEvent event) {
		String item;
		if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getAction().equals(InventoryAction.COLLECT_TO_CURSOR)) {
			if (event.getSlotType().equals(SlotType.RESULT) && event.getInventory().getType().equals(InventoryType.ANVIL)) {
				if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore()) {
					item = event.getCurrentItem().getType().name() + ":" + event.getCurrentItem().getDurability() + ":" + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0));
					if (items.containsKey(item)) {
						if (items.get(item).cancelAnvil()) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

}