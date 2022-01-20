package com.ohyea777.rp;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Rotation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ohyea777.rp.printers.IPrinterType;
import com.ohyea777.rp.util.Messages;
import com.ohyea777.rp.util.SQLTableClass;
import com.ohyea777.rp.util.Utils;

public class MoneyPrinter extends SQLTableClass {

	private transient Location location;
	private transient ItemStack item;
	private transient int id;
	private final String uuid;
	private final IPrinterType type;
	private int amount;
	private String world;
	
	public MoneyPrinter(ItemFrame entity, IPrinterType type) {
		super("MoneyPrinters", "Location");
		
		location = entity.getLocation();
		uuid = entity.getUniqueId().toString();
		this.type = type;
		amount = 0;
		world = location.getWorld().getName();
		updateClassData();
		schedule();
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public String getWorld() {
		return world;
	}
	
	public void schedule() {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(RP.instance, new Runnable() {
			@Override
			public void run() {
				add();
			}
		}, type.getTimeToPrint() * 20, type.getTimeToPrint() * 20);
	}
	
	private void add() {
		amount += 1;
		if (amount == 0)
			for (Entity e : location.getWorld().getEntities()) {
				if (e.getUniqueId().toString().equals(uuid) && e instanceof ItemFrame) {
					((ItemFrame) e).setItem(null);
				}
			}
		else
			addItem();
		updateClassData();
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(id);
	}
	
	private void addItem() {
		item = new ItemStack(type.getMaterial(), 1);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(new ArrayList<String>(Arrays.asList(new String[] { "[Coin]", uuid })));
		meta.setDisplayName(Messages._("&8[&b" + type.getName() + "&8]&7 Money Printer: &b$" + amount * 10));
		item.setItemMeta(meta);
		for (Entity e : location.getWorld().getEntities())
			if (e.getUniqueId().toString().equals(uuid) && e instanceof ItemFrame) {
				((ItemFrame) e).setItem(item);
				((ItemFrame) e).setRotation(((ItemFrame) e).getRotation().ordinal() < Rotation.values().length - 1 ? Rotation.values()[((ItemFrame) e).getRotation().ordinal() + 1] : Rotation.NONE);
			}
	}

	@Override
	protected void updateClassData() {
		if (!getClassData().isComplete())
			getClassData().setID(uuid);
		getClassData().setJson(Utils.getGson().toJson(this));
	}

}
