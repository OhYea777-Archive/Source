package com.mcjailed.jailedrp.jobs;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public interface IJob {

	public boolean isVIP();
	
	public boolean hasPermission(Player player);
	
	public double getSalary();
	
	public List<ItemStack> getItems();
	
	public List<ItemStack> getArmor();
	
	public ChatColor getColour();
	
	public String getJobTitle();
	
	public MaterialData getIcon();
	
	public String getDescription();
	
}
