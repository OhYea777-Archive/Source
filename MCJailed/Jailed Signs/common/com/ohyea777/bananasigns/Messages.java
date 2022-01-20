package com.ohyea777.bananasigns;

import org.bukkit.ChatColor;

public enum Messages {

	PREFIX("messages.prefix", "&8[&eBananaSigns&8] &7");
	
	private String loc;
	private String def;
	
	private Messages(String loc, String def) {
		this.loc = loc;
		this.def = def;
	}
	
	private String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	@Override
	public String toString() {
		return BananaSigns.instance.getConfig().get(loc) != null && BananaSigns.instance.getConfig().get(loc) instanceof String ? _(BananaSigns.instance.getConfig().getString(loc)) : _(def);
	}
	
}
