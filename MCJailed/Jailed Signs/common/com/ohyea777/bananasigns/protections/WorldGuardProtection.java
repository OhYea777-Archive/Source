package com.ohyea777.bananasigns.protections;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ohyea777.bananasigns.api.IWorldProtection;

public class WorldGuardProtection implements IWorldProtection {
	
	private boolean isWorldGuardEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
	}

	private boolean checkWorldGuard(Player player, Location location) {
		if (isWorldGuardEnabled()) {
			Object worldGuard = Bukkit.getPluginManager().getPlugin("WorldGuard");
			try {
				Method method = worldGuard.getClass().getDeclaredMethod("canBuild", Player.class, Location.class);
				method.setAccessible(true);
				
				Object check = method.invoke(worldGuard, player, location);
				if (check instanceof Boolean && check != null) {
					return !(Boolean) check;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public boolean isBlockProtected(Player player, Location location) {
		return isWorldGuardEnabled() ? checkWorldGuard(player, location) : false;
	}

}
