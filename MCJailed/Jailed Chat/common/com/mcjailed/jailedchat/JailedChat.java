package com.mcjailed.jailedchat;

import org.bukkit.plugin.java.JavaPlugin;

import com.mcjailed.jailedcore.api.IModule;
import com.mcjailed.jailedcore.api.ModuleLoader;

public class JailedChat extends JavaPlugin implements IModule {
	
	public static JailedChat instance;

	@Override
	public void onEnable() {
		instance = this;
		ModuleLoader.getLoader().registerModule("Jailed Chat", instance);
	}
	
	@Override
	public JavaPlugin getInstance() {
		return JailedChat.instance;
	}

	@Override
	public void onInit() {
		ModuleLoader.getLoader().getLogger().info("[JailedChat] Module Loaded!");
	}

	@Override
	public void onDeinit() {
		ModuleLoader.getLoader().getLogger().info("[JailedChat] Module Disabled!");
	}

	@Override
	public void onReload() {
		ModuleLoader.getLoader().getLogger().info("[JailedChat] Module's Configs Reloaded!");
	}
	
}