package com.ohyea777.virtualvaults.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.gsonapi.GsonLoadClass;
import com.ohyea777.virtualvaults.VirtualVaults;
import com.ohyea777.virtualvaults.player.VaultUser;

public class VaultLoader extends GsonLoadClass<VaultUser> {

	private Map<Inventory, Integer> inventories;
	
	public VaultLoader() {
		super(VaultUser.class);
		inventories = new ConcurrentHashMap<Inventory, Integer>();
	}
	
	public void put(Player player, VaultUser user) {
		put(player.getName(), user);
	}
	
	public VaultUser get(Player player) {
		return get(player.getName());
	}
	
	public void saveVault(Player player) {
		save(player.getName());
	}

	@Override
	protected JavaPlugin getPlugin() {
		return VirtualVaults.getVaultsInstance();
	}

	@Override
	protected Boolean defaultSave() {
		return true;
	}
	
	public void add(Inventory inventory, int id) {
		if (!inventories.containsKey(inventory)) {
			inventories.put(inventory, id);
		}
	}
	
	public Integer remove(Inventory inventory) {
		return inventories.remove(inventory);
	}

}
