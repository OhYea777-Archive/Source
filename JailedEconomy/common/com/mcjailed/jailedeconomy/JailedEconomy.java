package com.mcjailed.jailedeconomy;

import java.lang.reflect.Field;

import net.milkbowl.vault.Vault;

import org.bukkit.plugin.java.JavaPlugin;

import com.mcjailed.jailedcore.api.IModule;

public class JailedEconomy extends JavaPlugin implements IModule {

	private static JailedEconomy instance;
	private JEconomy economy;
	
	@Override
	public void onEnable() {
		economy = new JEconomy();
		registerEconomy();
	}
	
	private void registerEconomy() {
		Vault vault = (Vault) getServer().getPluginManager().getPlugin("Vault");
		vault.getClass().getDeclaredMethod("", parameterTypes)
	}

	public static JailedEconomy getEconInstance() {
		return instance;
	}

	@Override
	public JavaPlugin getInstance() {
		return instance;
	}

	@Override
	public void onInit() {
		getLogger().info("Has Been Enabled!");
	}

	@Override
	public void onDeinit() {
		getLogger().info("Has Been Disabled!");
	}

	@Override
	public void onReload() { }
	
}
