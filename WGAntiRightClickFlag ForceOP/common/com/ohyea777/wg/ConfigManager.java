package com.ohyea777.wg;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class ConfigManager {

	private WGAntiRightClickFlag plugin;
	
	public ConfigManager(WGAntiRightClickFlag plugin) {
		this.plugin = plugin;
	}
	
	public boolean isItemEnabled(ItemStack item, ApplicableRegionSet set) {
		if (hasItems()) {
			if (itemEnabled("AllRegions", item)) {
				return true;
			}
		}
		
		for (ProtectedRegion r : set) {
			if (hasItemsForRegion(r.getId())) {
				if (itemEnabled(r.getId(), item)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isBlockEnabled(Block block, ApplicableRegionSet set) {
		if (hasBlocks()) {
			if (blockEnabled("AllRegions", block)) {
				return true;
			}
		}
		
		for (ProtectedRegion r : set) {
			if (hasBlocksForRegion(r.getId())) {
				if (blockEnabled(r.getId(), block)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean hasBlocks() {
		return plugin.getConfig().getConfigurationSection("AllRegions.Blocks") != null || globallyBlockMods();
	}
	
	private boolean hasItems() {
		return plugin.getConfig().getConfigurationSection("AllRegions.Items") != null;
	}
	
	private boolean hasBlocksForRegion(String r) {
		return plugin.getConfig().getConfigurationSection(r + ".Blocks") != null || globallyBlockMods();
	}
	
	private boolean hasItemsForRegion(String r) {
		return plugin.getConfig().getConfigurationSection(r + ".Items") != null;
	}
	
	@SuppressWarnings("deprecation")
	private boolean itemEnabled(String r, ItemStack item) {
		if (hasItemsForRegion(r)) {
			if (plugin.getConfig().getConfigurationSection(r + ".Items").getKeys(false) != null) {
				for (String s : plugin.getConfig().getConfigurationSection(r + ".Items").getKeys(false)) {
					int id = 0;
					int damage = 0;
					try {
						id = Integer.parseInt(s);
						damage = plugin.getConfig().getConfigurationSection(r + ".Items").getInt(s);
					} catch (Exception e) {
						return false;
					}
					if (id == item.getTypeId() && (damage == -1 ? true : damage == item.getDurability())) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private boolean blockEnabled(String r, Block block) {
		if (globallyBlockMods()) {
			if (block.getType().toString().startsWith("X")) {
				return true;
			}
		}
		if (hasBlocksForRegion(r)) {
			if (plugin.getConfig().getConfigurationSection(r + ".Blocks").getKeys(false) != null) {
				for (String s : plugin.getConfig().getConfigurationSection(r + ".Blocks").getKeys(false)) {
					int id = 0;
					int damage = 0;
					try {
						id = Integer.parseInt(s);
						damage = plugin.getConfig().getConfigurationSection(r + ".Blocks").getInt(s);
					} catch (Exception e) {
						return false;
					}
					if (id == block.getTypeId() && (damage == -1 ? true : damage == (int) block.getData())) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean globallyBlockMods() {
		return plugin.getConfig().get("Options.GloballyBlockMods") != null && plugin.getConfig().get("Options.GloballyBlockMods") instanceof Boolean ? plugin.getConfig().getBoolean("Options.GloballyBlockMods") : false;
	}
	
}
