package com.ohyea777.plunder.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import com.ohyea777.plunder.PlunderTools;

public class ConfigManager {

	public static String getPrefix() {
		if (PlunderTools.instance.getConfig().getString("Options.Prefix") != null) {
			return _(PlunderTools.instance.getConfig().getString("Options.Prefix"));
		} else {
			PlunderTools.instance.getConfig().set("Options.Prefix", "&8[&3PlunderTools&8] &7");
			PlunderTools.instance.saveConfig();
			return _("&8[&3PlunderTools&8] &7");
		}
	}
	
	private static ConfigurationSection getRecreation() {
		if (PlunderTools.instance.getConfig().getConfigurationSection("RecreationalActivities") != null) {
			return PlunderTools.instance.getConfig().getConfigurationSection("RecreationalActivities");
		} else {
			ConfigurationSection section = PlunderTools.instance.getConfig().createSection("RecreationalActivities");
			PlunderTools.instance.saveConfig();
			return section;
		}
	}
	
	public static boolean isRecreationLocationSet() {
		return getRecreation().get("Location") != null;
	}
	
	public static Location getRecreationLocation() {
		if (isRecreationLocationSet()) {
			try {
				return PlunderSerialization.Location.deserialize(getRecreation().getString("Location"));
			} catch (Exception e) { }
		}
		return null;
	}
	
	public static void setRecreationLocation(String loc) {
		getRecreation().set("Location", loc);
		PlunderTools.instance.saveConfig();
	}
	
	public static int getRecreationWait() {
		if (getRecreation().get("Wait") != null) {
			return getRecreation().getInt("Wait") - getRecreationWarning();
		} else {
			getRecreation().set("Wait", 60);
			PlunderTools.instance.saveConfig();
			return 60 - getRecreationWarning();
		}
	}
	
	public static int getRecreationWarning() {
		if (getRecreation().get("Warning") != null) {
			return getRecreation().getInt("Warning");
		} else {
			getRecreation().set("Warning", 5);
			PlunderTools.instance.saveConfig();
			return 5;
		}
	}
	
	public static String getRecreationWarningMessage() {
		if (getRecreation().getString("WarningMessage") != null) {
			return _(getPrefix() + getRecreation().getString("WarningMessage"));
		} else {
			getRecreation().set("WarningMessage", "RECREATION TIME IN {warning} MIN(S)!");
			PlunderTools.instance.saveConfig();
			return getPrefix() + _("RECREATION TIME IN {warning} MIN(S)!");
		}
	}
	
	public static String getRecreationTeleportMessage() {
		if (getRecreation().getString("TeleportMessage") != null) {
			return _(getPrefix() + getRecreation().getString("TeleportMessage"));
		} else {
			getRecreation().set("TeleportMessage", "Teleported to Recreation Area!");
			PlunderTools.instance.saveConfig();
			return getPrefix() + _("Teleported to Recreation Area!");
		}
	}
	
	private static String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
}
