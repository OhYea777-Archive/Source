package com.ohyea777.virtualvaults.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ohyea777.virtualvaults.VirtualVaults;
import com.ohyea777.virtualvaults.util.ItemSerialization;
import com.ohyea777.virtualvaults.util.VaultMessages;

public class Vault {
	
	private String Name;
	private Integer Size;
	private List<String> Items;
	
	public Vault(String name, Integer size) {
		Name = name;
		Size = size;
		Items = new ArrayList<String>();
	}
	
	public Vault(String name) {
		this(name, InventoryType.CHEST.getDefaultSize());
	}
	
	public String getName() {
		return Name != null ? Name : "";
	}
	
	public void setItems(List<ItemStack> items) {
		Items.clear();
		for (ItemStack i : items) {
			try {
				Items.add(ItemSerialization.toBase64(i));
			} catch (Exception e) {
				if (e instanceof NullPointerException) {
					try {
						Items.add(ItemSerialization.toBase64(new ItemStack(Material.AIR)));
						continue;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				VirtualVaults.getVaultsInstance().getLogger().severe("Failed to Add ItemStack To Vault: '" + Name + "'. Stacktrace Follows - ");
				e.printStackTrace();
			}
		}
	}
	
	private ItemStack[] getItems() {
		ItemStack[] items = new ItemStack[Size];
		int i = 0;
		for (String s : Items) {
			try {
				items[i] = ItemSerialization.fromBase64(s);
			} catch (Exception e) {
				if (e instanceof NullPointerException) {
					items[i] = new ItemStack(Material.AIR);
					continue;
				}
				VirtualVaults.getVaultsInstance().getLogger().severe("Failed to Load ItemStack From Vault: '" + Name + "'. Stacktrace Follows - ");
				e.printStackTrace();
			}
			i += 1;
		}
		return items;
	}
	
	public void setSize(int size) {
		Size = size;
	}
	
	public Inventory openInventory(Player p, int id) {
		Inventory inv = Bukkit.createInventory(p, Size, ChatColor.translateAlternateColorCodes('&', VaultMessages.PREFIX.getNoPrefixColoured() + Name));
		inv.setContents(getItems());
		p.openInventory(inv);
		VirtualVaults.getVaultsInstance().getLoader().add(inv, id);
		return inv;
	}

	public int getSize() {
		return Size != null ? Size : InventoryType.CHEST.getDefaultSize();
	}
	
}
