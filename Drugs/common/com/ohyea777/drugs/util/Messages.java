package com.ohyea777.drugs.util;

import org.bukkit.ChatColor;

import com.ohyea777.drugs.Drugs;

public enum Messages {
	
	PREFIX, RELOAD, NO_PERM, DRUG_USE, DRUGGED;
	
	@Override
	public String toString() {
		switch (ordinal()) {
		case 0:
			return _(getString("Options.Prefix"));
		case 1:
			return _(PREFIX + getString("Message.Reload"));
		case 2:
			return _(PREFIX + getString("Message.No_Perm"));
		case 3:
			return _(PREFIX + getString("Message.Drug_Use"));
		case 4:
			return _(PREFIX + getString("Message.Drugged"));
		default:
			return "";
		}
	}
	
	private Drugs getPlugin() {
		return Drugs.instance;
	}
	
	private String getString(String s) {
		return getPlugin().getConfig().getString(s) != null ? getPlugin().getConfig().getString(s) : "";
	}
	
	private String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
}
