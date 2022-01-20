package com.ohyea777.wg;

import org.bukkit.ChatColor;

public enum Messages {

	PREFIX("Messages.Prefix", "&8[&bWGAntiRightClickFlag&8] &7"),
	NO_PERM("Messages.NoPerm", "&4You Do Not Have Permission to do That!"),
	RELOADED("Messages.Reloaded", "Config Reloaded!");
	
	private final String loc;
	private final String def;
	
	private Messages(String loc, String def) {
		this.loc = loc;
		this.def = def;
	}
	
	@Override
	public String toString() {
		if (WGAntiRightClickFlag.instance.getConfig().getString(loc) != null) {
			return _(WGAntiRightClickFlag.instance.getConfig().getString(loc));
		} else {
			return _(def);
		}
	}
	
	public static String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
}
