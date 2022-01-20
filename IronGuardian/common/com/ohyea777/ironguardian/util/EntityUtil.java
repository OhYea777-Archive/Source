package com.ohyea777.ironguardian.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.ohyea777.ironguardian.IronGuardian;

public class EntityUtil {

	public static boolean isTamed(Entity entity) {
		List<MetadataValue> meta = entity.getMetadata("guardianIsTamed");
		
		if (meta != null && !meta.isEmpty() && meta.get(0) != null && meta.get(0).value() instanceof Boolean) {
			return meta.get(0).asBoolean();
		}
		
		return false;
	}
	
	public static String getTamedBy(Entity entity) {
		if (isTamed(entity)) {
			List<MetadataValue> meta = entity.getMetadata("guardianTamedBy");
			
			if (meta != null && !meta.isEmpty() && meta.get(0) != null && meta.get(0).value() instanceof String) {
				return meta.get(0).asString();
			}
		}
		
		return null;
	}
	
	public static boolean isTamedBy(Player player, Entity entity) {
		Bukkit.broadcastMessage("Tamed By: " + getTamedBy(entity));
		return getTamedBy(entity) != null && getTamedBy(entity).equals(player.getName());
	}
	
	public static boolean setTamedBy(Player player, Entity entity) {
		if (!isTamed(entity)) {
			entity.setMetadata("guardianIsTamed", new FixedMetadataValue(IronGuardian.getInstance(), true));
			entity.setMetadata("guardianTamedBy", new FixedMetadataValue(IronGuardian.getInstance(), player.getName()));
			return true;
		}
		
		return false;
	}
	
}
