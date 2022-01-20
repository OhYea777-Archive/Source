package com.mcjailed.jailedrp;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MoneyPrinter {

	private transient Location location;
	private transient ItemStack item;
	private transient int id = 0;
	private PrinterType type;
	private String uuid;
	private String world;
	private int amount;

	public MoneyPrinter() { }

	public MoneyPrinter(ItemFrame entity, PrinterType type) {
		location = entity.getLocation();
		uuid = entity.getUniqueId().toString();
		world = entity.getWorld().getName();
		amount = 0;
		this.type = type;
		schedule();
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public PrinterType getType() {
		return type;
	}

	public String getUUID() {
		return uuid;
	}

	public String getWorld() {
		return world;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
		update();
	}

	private void createItem() {
		item = new ItemStack(type.getMaterial());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b" + type.getFriendlyName() + " &7Money Printer: &b$") + getAmount() * 10);
		meta.setLore(Arrays.asList(new String[] { "[Coin]", uuid }));
		item.setItemMeta(meta);
		if (amount > 0)
			for (Entity e : location.getWorld().getEntities()) {
				if (e.getUniqueId().toString().equals(uuid)) {
					((ItemFrame) e).setItem(item);
					return;
				}
			}
	}

	private void add() {
		amount += 1;
		update();
		createItem();
		if (amount > 0)
			for (Entity e : location.getWorld().getEntities()) {
				if (e.getUniqueId().toString().equals(uuid)) {
					ItemFrame frame = (ItemFrame) e;
					frame.setItem(item);
					frame.setRotation(frame.getRotation().ordinal() < Rotation.values().length - 1 ? Rotation.values()[frame.getRotation().ordinal() + 1] : Rotation.NONE);
					return;
				}
			}
	}

	public void schedule() {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(JailedRP.instance, new Runnable() {
			@Override
			public void run() {
				add();
			}
		}, getTime() * 20, getTime() * 20);
	}
	
	private int getTime() {
		switch (type) {
		case IRON: return 20;
		case GOLD: return 15;
		case EMERALD: return 10;
		case DIAMOND: return 5;
		case OP: return 1;
		default: return 20;
		}
	}
	
	public void reSchedule() {
		Bukkit.getScheduler().cancelTask(id);
		schedule();
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(id);
	}

	public void update() {
		JailedRP.instance.getLoader().updatePrinter(this);
	}
	
	public enum PrinterType {
		
		IRON(Material.IRON_INGOT), GOLD(Material.GOLD_INGOT), EMERALD(Material.EMERALD), DIAMOND(Material.DIAMOND), OP(Material.NETHER_STAR);
		
		private Material material;
		
		private PrinterType(Material material) {
			this.material = material;
		}
		
		public Material getMaterial() {
			return material;
		}
		
		public static PrinterType getFromString(String s) {
			switch (s.toLowerCase()) {
			case "iron": return IRON;
			case "gold": return GOLD;
			case "emerald": return EMERALD;
			case "diamond": return DIAMOND;
			case "op": return OP;
			default: return null;
			}
		}
		
		public String getFriendlyName() {
			switch (ordinal()) {
			case 0: return "Iron";
			case 1: return "Gold";
			case 2: return "Emerald";
			case 3: return "Diamond";
			case 4: return "OP";
			default: return "";
			}
		}
		
	}

}
