package com.ohyea777.loanme;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.loanme.Loan.LoanType;

public class LoanMe extends JavaPlugin implements Listener {
	
	public static LoanMe instance;
	private ConfigManager config;
	private LoanPlayerLoader loader;
	
	@Override
	public void onEnable() {
		instance = this;
		config = new ConfigManager(instance);
		loader = new LoanPlayerLoader();
		
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public ConfigManager getConfigManager() {
		return config;
	}
	
	public LoanPlayerLoader getLoader() {
		return loader;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (!getLoader().contains(event.getPlayer().getName()))
			getLoader().put(event.getPlayer().getName(), new LoanPlayer(event.getPlayer().getName()));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			getLoader().get(sender.getName()).addLoan("OhYea777", new Loan("OhYea777", "OhYea777", LoanType.COMPOUND, 10000, 5, 0.10, 20));
		}
		
		return false;
	}
	
}
