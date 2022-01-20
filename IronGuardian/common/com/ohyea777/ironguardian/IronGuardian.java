package com.ohyea777.ironguardian;

import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.ironguardian.listener.GuardianListener;

public class IronGuardian extends JavaPlugin {

	private static IronGuardian instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		getServer().getPluginManager().registerEvents(new GuardianListener(), this);
	}
	
	public static IronGuardian getInstance() {
		return instance;
	}
	
}
