package com.mcjailed.jailedrp;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcjailed.jailedrp.jobs.JobLoader;
import com.mcjailed.jailedrp.jobs.JobMenu;
import com.mcjailed.jailedrp.listener.RPListener;
import com.mcjailed.jailedrp.util.ItemUseLoader;
import com.mcjailed.jailedrp.util.ProtocolUtil;
import com.mcjailed.jailedrp.util.SQLLoader;
import com.mcjailed.jailedrp.util.ScoreboardLoader;

public class JailedRP extends JavaPlugin {

	public static JailedRP instance;
	private SQLLoader loader;
	private JobLoader jobs;
	private ScoreboardLoader scoreboard;
	private JobMenu menu;
	
	private Economy economy;
	
	@Override
	public void onEnable() {
		instance = this;
		loader = new SQLLoader();
		jobs = new JobLoader();
		scoreboard = new ScoreboardLoader();
		menu = new JobMenu();
		
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		
		getServer().getPluginManager().registerEvents(jobs, instance);
		getServer().getPluginManager().registerEvents(new RPListener(), instance);
		getServer().getPluginManager().registerEvents(new ProtocolUtil(), instance);
		
		if (!setupEconomy()) {
			getLogger().severe("Failed to Load Economy! Disabling Plugin!");
			getPluginLoader().disablePlugin(instance);
		}
		
		new ItemUseLoader();
		
		CommandExecutor cmd = new RPCommandExecutor();
		getCommand("dm").setExecutor(cmd);
		getCommand("rp").setExecutor(cmd);
		getCommand("door").setExecutor(cmd);
		getCommand("test").setExecutor(cmd);
	}
	
	@Override
	public void onDisable() {
		loader.onDisable();
	}
	
	public SQLLoader getLoader() {
		return loader;
	}
	
	public JobLoader getJobLoader() {
		return jobs;
	}
	
	public ScoreboardLoader getScoreboardLoader() {
		return scoreboard;
	}
	
	public JobMenu getMenu() {
		return menu;
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
	
	public Economy getEconomyAPI() {
		return economy;
	}
	
}
