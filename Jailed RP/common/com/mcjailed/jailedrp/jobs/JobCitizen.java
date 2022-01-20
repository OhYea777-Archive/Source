package com.mcjailed.jailedrp.jobs;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class JobCitizen implements IJob {

	@Override
	public boolean isVIP() {
		return false;
	}

	@Override
	public boolean hasPermission(Player player) {
		return true;
	}

	@Override
	public double getSalary() {
		return 0;
	}

	@Override
	public List<ItemStack> getItems() {
		return null;
	}
	
	@Override
	public List<ItemStack> getArmor() {
		return null;
	}

	@Override
	public ChatColor getColour() {
		return ChatColor.GRAY;
	}

	@Override
	public String getJobTitle() {
		return "Citizen";
	}
	
	@Override
	public MaterialData getIcon() {
		return new MaterialData(Material.TRIPWIRE_HOOK);
	}
	
	@Override
	public String getDescription() {
		return "This is the default Jailed RP Job!";
	}

}
