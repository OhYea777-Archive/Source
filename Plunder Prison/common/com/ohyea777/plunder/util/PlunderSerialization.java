package com.ohyea777.plunder.util;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class PlunderSerialization {

	public static class Location {
		
		public static String serialize(org.bukkit.Location loc) {
			return loc.getWorld().getName() + "=" + loc.getBlockX() + "=" + loc.getBlockY() + "=" + loc.getBlockZ();
		}
		
		public static org.bukkit.Location deserialize(String s) {
			String[] values = s.split("=");
			World world = Bukkit.getWorld(values[0]);
			int x = Integer.valueOf(values[1]);
			int y = Integer.valueOf(values[2]);
			int z = Integer.valueOf(values[3]);
			return new org.bukkit.Location(world, x, y, z);
		}
		
	}
	
}
