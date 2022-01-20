package com.mcjailed.jailedcore.api;

import org.bukkit.plugin.java.JavaPlugin;

public interface IModule {

	public JavaPlugin getInstance();
	
	public void onInit();
	
	public void onDeinit();
	
	public void onReload();
	
}
