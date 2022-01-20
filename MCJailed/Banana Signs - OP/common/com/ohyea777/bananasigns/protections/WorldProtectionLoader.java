package com.ohyea777.bananasigns.protections;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ohyea777.bananasigns.api.IWorldProtection;

public class WorldProtectionLoader implements IWorldProtection {

	private List<IWorldProtection> protections;
	
	public WorldProtectionLoader() {
		protections = new ArrayList<IWorldProtection>();
		
		init();
	}
	
	private void init() {
		registerWorldProtection(new WorldGuardProtection());
	}
	
	public boolean registerWorldProtection(IWorldProtection protection) {
		if (protection != null && !protections.contains(protection)) {
			return protections.add(protection);
		}
		return false;
	}

	@Override
	public boolean isBlockProtected(Player player, Location location) {
		for (IWorldProtection protection : protections) {
			if (protection.isBlockProtected(player, location)) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
