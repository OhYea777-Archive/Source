package com.ohyea777.ordersign;

import java.util.HashMap;
import java.util.Set;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class OrderSign extends JavaPlugin implements Listener {

	private Economy economyApi = null;

	public HashMap<String, Boolean> boughtSign = new HashMap<String, Boolean>();
	public HashMap<String, String> identifyBoughtSign = new HashMap<String, String>();

	public void onDisable() {
		getLogger().info("Plugin Has Been Disabled!");
	}
	public void onEnable() {
		if (!registerEconomy()) {
			getLogger().severe("Failed to Load Economy Provider!");
			setEnabled(false);
		}

		loadConfig();
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("Plugin Has Been Enabled!");
	}

	private void loadConfig() {
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
	}

	private boolean registerEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economyApi = economyProvider.getProvider();
			return true;
		}
		return false;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("ordersign") && sender instanceof Player) {
			Player player = (Player) sender;
			String errorMessage = "Use /ordersign to see the available signs";
			Set<String> sign = getConfig().getConfigurationSection("signs").getKeys(false);
			Economy e = economyApi;

			double cost = 0.00;

			//If too many arguments are entered
			if(args.length > 1) {
				sender.sendMessage(colorHandler("&a[&bOrderSign&a] &4Too many arguments! &c" + errorMessage));
				return true;
			}

			//Information about the plugin
			if(args.length == 1 && (args[0].equalsIgnoreCase("about") || args[0].equalsIgnoreCase("version"))) {
				sender.sendMessage(colorHandler("&a[&bOrderSign&a]"));
				sender.sendMessage(colorHandler("&3Original Plugin Authors: &1Seemethere & JJKoletar is gay"));
				sender.sendMessage(colorHandler("&3Plugin Recoded By: &1zStaaR & OhYea777"));
				sender.sendMessage(colorHandler("&3Version: &11.7.2"));
				return true;
			}

			//If sign name is invalid
			if(sign == null || sign.isEmpty() || (args.length == 1 && !containsSign(sign, args[0]))) {
				sender.sendMessage(colorHandler("&a[&bOrderSign&a] &4Unknown sign! &c" + errorMessage));
				return true;
			}

			//If they enter an available name
			if(args.length == 1 && containsSign(sign, args[0])) {
				cost = this.getConfig().getDouble("signs." + args[0] + ".cost"); //What if it doesn't exist?

				//If player does not have enough money
				if(e.getBalance(player.getName()) < cost) {
					sender.sendMessage(colorHandler("&a[&bOrderSign&a] &4Insufficient Funds! This sign costs &a$" + cost));
					return true;
				}

				toggleBoughtSign(sender, true, args[0]);

				return true;
			}

			//Displays all available signs
			if(args.length == 0) {
				sender.sendMessage(ChatColor.GREEN + "[" + ChatColor.AQUA + "OrderSign" + ChatColor.GREEN + "] " + ChatColor.RED + "NOTE: All signs are case sensitive");
				sender.sendMessage(ChatColor.GREEN + "[" + ChatColor.AQUA + "OrderSign" + ChatColor.GREEN + "] " + ChatColor.DARK_AQUA + "Available Signs: ");
				for(String s : getConfig().getConfigurationSection("signs").getKeys(false)) {
					cost = getConfig().getDouble("signs." + s + ".cost");
					sender.sendMessage(ChatColor.BLUE + s + ChatColor.GREEN + " ($" + cost + ")");
				}
				return true;
			}
		}
		return true;

	}


	private void toggleBoughtSign(CommandSender sender, boolean toggle, String signBought) {
		try {
			String playerName = sender.getName();

			if(toggle == true) {
				boughtSign.put(playerName, true);
				identifyBoughtSign.put(playerName, signBought);
				sender.sendMessage(colorHandler("&a[&bOrderSign&a] &cRight click a blank sign to complete your order!"));
			}

			if(toggle == false) {
				boughtSign.remove(playerName);
				identifyBoughtSign.remove(playerName);
			}
		} catch (Exception e) {
			getLogger().severe("An error occured with toggleBoughtSign");
		}
	}

	private boolean containsSign(Set<String> signs, String sign) {
		for (String s : signs) {
			if (s.equalsIgnoreCase(sign)) {
				return true;
			}
		}
		return false;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Player player = event.getPlayer();
			Block block = event.getClickedBlock();

			if(block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();

				if(boughtSign.containsKey(player.getName())) {
					if(isEmpty(sign)) {
						String s = identifyBoughtSign.get(player.getName()) != null && !identifyBoughtSign.get(player.getName()).isEmpty() ? identifyBoughtSign.get(player.getName()) : "";
						double cost = getConfig().getDouble("signs." + s + ".cost");

						fillSign(player, block);

						if (economyApi.withdrawPlayer(player.getName(), cost).transactionSuccess()) {
							player.sendMessage(colorHandler("&a[&bOrderSign&a] &cTransaction complete! &a$" + cost + " &chas been charged from your account!"));
							toggleBoughtSign(player, false, "");
						} else {
							player.sendMessage(colorHandler("&a[&bOrderSign&a] &4Failed to process transaction!"));
						}
						return;
					} else {
						player.sendMessage(colorHandler("&a[&bOrderSign&a] &4Sign was not blank! Transaction Cancelled!"));
						toggleBoughtSign(player, false, "");
						return;
					} 
				}
			}
		}
	}

	public String colorHandler(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	private boolean isEmpty(Sign sign) {
		for (String s : sign.getLines()) {
			if (!s.isEmpty() || s != null || s != "") return true;
		}
		return true;
	}

	public void fillSign(Player player, Block block) {
		String s = identifyBoughtSign.get(player.getName());
		BlockState blockstate = block.getState();
		Sign sign = (Sign) blockstate;

		String line0 = colorHandler(getConfig().getString("signs." + s + ".1"));
		String line1 = colorHandler(getConfig().getString("signs." + s + ".2"));
		String line2 = colorHandler(getConfig().getString("signs." + s + ".3"));
		String line3 = colorHandler(getConfig().getString("signs." + s + ".4"));

		try {
			sign.setLine(0, line0);
			sign.setLine(1, line1);
			sign.setLine(2, line2);
			sign.setLine(3, line3);
			sign.update();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
