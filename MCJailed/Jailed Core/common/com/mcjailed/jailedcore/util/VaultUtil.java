package com.mcjailed.jailedcore.util;

import org.bukkit.plugin.RegisteredServiceProvider;

import com.mcjailed.jailedcore.JailedCore;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultUtil {

	private JailedCore plugin;
	
	public static Permission permission = null;
	public static Economy economy = null;
	public static Chat chat = null;

	public VaultUtil(JailedCore plugin) {
		this.plugin = plugin;
		
		setupPermissions();
		setupEconomy();
		setupChat();
	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = plugin.getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
	
	public Permission getPermissionsAPI() {
		return permission;
	}
	
	public Economy getEconomyAPI() {
		return economy;
	}
	
	public Chat getChatAPI() {
		return chat;
	}

}
