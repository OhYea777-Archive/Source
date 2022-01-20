package com.ohyea777.bananasigns.api;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public interface IBananaSign {

	public String getName();
	
	public double getPrice();
	
	public boolean hasPermission(Player player);
	
	public void updateSign(Sign sign);
	
}
