package com.ohyea777.bananasigns;

import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.bananasigns.protections.WorldProtectionLoader;

public class BananaSigns extends JavaPlugin {

	public static BananaSigns instance;
	private SignLoader loader;
	private VaultUtil vault;
	private WorldProtectionLoader worldProtections;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		
		instance = this;
		loader = SignLoader.load();
		vault = new VaultUtil(instance);
		worldProtections = new WorldProtectionLoader();
		
		getServer().getPluginManager().registerEvents(new BananaSignEvent(instance), instance);
		getCommand("signs").setExecutor(new SignCommand());
	}
	
	public VaultUtil getVaultUtil() {
		return vault;
	}
	
	public SignLoader getLoader() {
		return loader;
	}
	
	public WorldProtectionLoader getWorldProtections() {
		return worldProtections;
	}
	
	public void reload() {
		reloadConfig();
		
		loader = null;
		loader = SignLoader.load();
	}

}
