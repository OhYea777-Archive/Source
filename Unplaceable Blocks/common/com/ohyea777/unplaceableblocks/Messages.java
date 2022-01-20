package com.ohyea777.unplaceableblocks;

import org.bukkit.ChatColor;

public enum Messages {

	PREFIX("&8[&6UnplaceableBlocks&8] &f"),
	RELOADED("Config Reloaded!"),
	NO_PERM_RELOAD("&4You Do not Have Permission to Reload the Config!"),
	NO_PERM_PLACE("&4You Can not Place an Unplaceable Block!");
	
	private String def;
	
	Messages(String def) {
		this.def = def;
	}
	
	private String getDef() {
		return def;
	}
	
	private String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	@Override
	public String toString() {
		switch (ordinal()) {
		case 1:
			return get("Reloaded");
		case 2:
			return get("No_Perm_Reload");
		case 3:
			return get("No_Perm_Place");
		default:
			return get("Prefix");
		}
	}
	
	private String get(String s) {
		if (UnplaceableBlocks.instance.getConfig().getString("Messages." + s) != null) {
			return _(UnplaceableBlocks.instance.getConfig().getString("Messages." + s));
		} else {
			return _(getDef());
		}
	}
	
}
