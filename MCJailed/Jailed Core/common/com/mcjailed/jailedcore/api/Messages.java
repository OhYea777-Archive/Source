package com.mcjailed.jailedcore.api;

import org.bukkit.ChatColor;

public enum Messages {

	PREFIX;
	
	public String toString() {
		switch(name()) {
		case ("PREFIX"):
			return _("&8[&bJailed&8] &7");
		}
		return "";
	}
	
	public String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
}
