package com.mcjailed.jailedmines;

import org.bukkit.plugin.java.JavaPlugin;

public class JailedMines extends JavaPlugin {

	public static JailedMines instance;
	
	@Override
	public void onEnable() {
		instance = this;
	}
	
	@Override
	public void onDisable() {
		
	}
	
}