package com.ohyea777.changeme;

import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.changeme.blocks.BlockRegistry;
import com.ohyea777.changeme.commands.ChangeMeCommandExecutor;
import com.ohyea777.changeme.listener.BlockListener;

public class ChangeMe extends JavaPlugin {

	private static ChangeMe instance;
	private ChangeMeCommandExecutor executor;
	private BlockRegistry blockRegistry;
	
	@Override
	public void onEnable() {
		instance = this;
		executor = new ChangeMeCommandExecutor();
		blockRegistry = new BlockRegistry();
		
		getServer().getPluginManager().registerEvents(new BlockListener(), instance);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static ChangeMe getInstance() {
		return instance;
	}
	
	public ChangeMeCommandExecutor getDefaultCommandExecutor() {
		return executor;
	}
	
	public BlockRegistry getBlockRegistry() {
		return blockRegistry;
	}
	
}
