package com.mcjailed.jailedmines;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class MineTask implements ConfigurationSerializable {

	private Location location;
	private Material material;
	private byte data;
	
	public MineTask(Location location, Material material, byte data) {
		this.location = location;
		this.material = material;
		this.data = data;
		schedule();
	}
	
	public void schedule() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(JailedMines.instance, new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				location.getBlock().setType(material);
				location.getBlock().setData(data);
				location.getWorld().playEffect(location, Effect.TILE_BREAK, 1);
				location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 0);
			}
		}, 20 * 20);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> serialize = new HashMap<String, Object>();
		serialize.put("Material", material.toString());
		serialize.put("Data", data);
		serialize.put("World", location.getWorld().getName());
		serialize.put("X", location.getBlockX());
		serialize.put("Y", location.getBlockY());
		serialize.put("Z", location.getBlockZ());
		return serialize;
	}
	
	public MineTask deserialize(Map<String, Object> serialize) {
		if (serialize.get("Material") instanceof String && serialize.get("Data") instanceof Byte && serialize.get("World") instanceof String && serialize.get("X") instanceof Integer && serialize.get("Y") instanceof Integer && serialize.get("Z") instanceof Integer) {
			Material material = Material.getMaterial((String) serialize.get("Material"));
			byte data = (Byte) serialize.get("Data");
			World world = Bukkit.getWorld((String) serialize.get("World"));
			int x = (Integer) serialize.get("X");
			int y = (Integer) serialize.get("Y");
			int z = (Integer) serialize.get("Z");
			Location location = new Location(world, x, y, z);
			return new MineTask(location, material, data);
		} else {
			return null;
		}
	}

}
