package com.ohyea777.rp;

import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.rp.util.JobRegistry;
import com.ohyea777.rp.util.SQLLoader;
import com.ohyea777.rp.util.Utils;

public class RP extends JavaPlugin {

	public static RP instance;
	
	private SQLLoader loader;
	private JobRegistry jobRegistry;
	
	@Override
	public void onEnable() {
		instance = this;
		
		loader = new SQLLoader();
		jobRegistry = new JobRegistry();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public SQLLoader getLoader() {
		return loader;
	}
	
	public JobRegistry getJobRegistry() {
		return jobRegistry;
	}
	
	public static void main(String[] args) {
		System.out.println(Utils.getGson().toJson(new RPPlayer("OhYea777")));
	}
	
}
