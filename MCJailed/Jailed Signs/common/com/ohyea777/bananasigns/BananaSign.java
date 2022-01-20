package com.ohyea777.bananasigns;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.ohyea777.bananasigns.api.IBananaSign;

public class BananaSign implements IBananaSign {

	private String Name;
	private double Price;
	private String Permission;
	private String[] Lines;
	
	@Override
	public String getName() {
		return Name;
	}

	@Override
	public double getPrice() {
		return Price;
	}

	private String getPermission() {
		return Permission != null ? "bananasigns." + Permission : "";
	}
	
	@Override
	public boolean hasPermission(Player player) {
		return player.hasPermission(getPermission()) || player.hasPermission("bananasigns.*");
	}

	@Override
	public void updateSign(Sign sign) {
		sign.setLine(0, Lines[0] != null ? _(Lines[0]) : "");
		sign.setLine(1, Lines[1] != null ? _(Lines[1]) : "");
		sign.setLine(2, Lines[2] != null ? _(Lines[2]) : "");
		sign.setLine(3, Lines[3] != null ? _(Lines[3]) : "");
		sign.update();
	}

	private String _(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
}
