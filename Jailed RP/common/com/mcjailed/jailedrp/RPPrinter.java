package com.mcjailed.jailedrp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mcjailed.jailedrp.MoneyPrinter.PrinterType;

import static com.mcjailed.jailedrp.util.Messages.*;

public class RPPrinter implements IItemUse, Listener {

	protected JailedRP plugin;
	private Map<Location, ItemStack> locations;

	@Override
	public void addItem(JailedRP plugin) {
		this.plugin = plugin;
		locations = new HashMap<Location, ItemStack>();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onHangingPlace(HangingPlaceEvent event) {
		if (event.getEntity().getType().equals(EntityType.ITEM_FRAME))	
			if (locations.containsKey(event.getBlock().getLocation())) {
				String s = ChatColor.stripColor(locations.get(event.getBlock().getLocation()).getItemMeta().getDisplayName()).replace("[", "").replace("]", "");
				plugin.getLoader().getPrinter((ItemFrame) event.getEntity(), PrinterType.getFromString(s));
				locations.remove(event.getBlock().getLocation());
				event.getPlayer().sendMessage(PREFIX + "" + PLACE_RPPRINTER.toString().replace("{type}", PrinterType.getFromString(s).getFriendlyName()));
			}
	}

	@EventHandler
	public void onHangingRemove(HangingBreakEvent event) {
		if (event.getEntity() instanceof ItemFrame) {
			if (plugin.getLoader().isPrinter((ItemFrame) event.getEntity())) {
				//Location loc = event.getEntity().getLocation();
				//event.getEntity().getWorld().createExplosion(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 5F, false, false);
			}
			plugin.getLoader().removePrinter(event.getEntity().getUniqueId().toString());
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof ItemFrame) {
			if (plugin.getLoader().isPrinter((ItemFrame) event.getRightClicked())) {
				event.setCancelled(true);
				if (event.getPlayer().isSneaking()) {
					MoneyPrinter frame = plugin.getLoader().getPrinter((ItemFrame) event.getRightClicked());
					String name = "[" + frame.getType().getFriendlyName() + "]";
					event.getPlayer().sendMessage(PREFIX + "" + PICKUP_RPPRINTER.toString().replace("{type}", frame.getType().getFriendlyName()));
					ItemStack item = new ItemStack(Material.ITEM_FRAME);
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(name);
					meta.setLore(new ArrayList<>(Arrays.asList(new String[] { "[Printer]" })));
					item.setItemMeta(meta);
					event.getPlayer().getInventory().addItem(item);
					event.getRightClicked().remove();
					plugin.getLoader().removePrinter(event.getRightClicked().getUniqueId().toString());
				} else {
					String type = plugin.getLoader().getPrinter((ItemFrame) event.getRightClicked()).getType().getFriendlyName();
					event.getPlayer().sendMessage(PREFIX + "" + TYPE_RPPRINTER.toString().replace("{type}", type));
				}
			}
		}
	}

	@Override
	public void onPlayerRightClickAir(PlayerInteractEvent event) { }

	@Override
	public void onPlayerLeftClickBlock(PlayerInteractEvent event) { }

	@Override
	public void onPlayerRightClickEntity(PlayerInteractEntityEvent event) { }

	@Override
	public void onPlayerRightClickBlock(PlayerInteractEvent event) {
		if (!locations.containsKey(event.getClickedBlock().getLocation()) && event.getItem().getItemMeta().hasDisplayName());
		locations.put(event.getClickedBlock().getLocation(), event.getItem());
	}

	@Override
	public void onPlayerLeftClickAir(PlayerInteractEvent event) { }

	@Override
	public void onPlayerLeftClickEntity(EntityDamageByEntityEvent event) { }

	@Override
	public boolean cancelAnvil() {
		return true;
	}

}
