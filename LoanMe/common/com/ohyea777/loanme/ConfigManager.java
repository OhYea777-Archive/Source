package com.ohyea777.loanme;

public class ConfigManager {

	private LoanMe plugin;
	
	public ConfigManager(LoanMe plugin) {
		this.plugin = plugin;
	}
	
	public int getTermLength() {
		if (plugin.getConfig().get("Options.TermLength") == null || !(plugin.getConfig().get("Options.TermLength") instanceof Integer)) {
			plugin.getConfig().set("Options.TermLength", 1200);
			plugin.saveConfig();
		}
		
		return plugin.getConfig().getInt("Options.TermLength");
	}
	
}
