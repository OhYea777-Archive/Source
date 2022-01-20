package com.ohyea777.bananasigns.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IWorldProtection {

	public boolean isBlockProtected(Player player, Location location);
	
}
