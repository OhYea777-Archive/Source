package com.mcjailed.jailedcore.player;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.mcjailed.jailedcore.JailedCore;

public class JailedUser {

	private String Username;
	private transient boolean teleporting;
	private transient int teleportID;
	
	public JailedUser() {
		teleporting = false;
		teleportID = 0;
	}
	
	public JailedUser(String username) {
		Username = username;
		teleporting = false;
		teleportID = 0;
	}
	
	public String getUser() {
		return Username;
	}
	
	public boolean isTeleporting() {
		return false;
	}
	
	public void setTeleporting(boolean teleporting) {
		this.teleporting = teleporting;
	}
	
	public boolean requestTeleport(final Location location) {
		if (!isTeleporting()) {
			if (teleportID == 0 || !Bukkit.getScheduler().isQueued(teleportID) || !Bukkit.getScheduler().isCurrentlyRunning(teleportID)) {
				setTeleporting(true);
				if (Bukkit.getOfflinePlayer(Username).isOnline()) {
					Player player = Bukkit.getOfflinePlayer(Username).getPlayer();
					Location loc = player.getLocation();
					for (int i = 0; i < 5; i ++) {
						loc.setY(loc.getY() + i);
						player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 0);
					}
					player.getWorld().playSound(player.getLocation(), Sound.FIZZ, 0.5F, 0.5F);
				}
				teleportID = Bukkit.getScheduler().scheduleSyncDelayedTask(JailedCore.instance, new Runnable() {
					@Override
					public void run() {
						if (Bukkit.getOfflinePlayer(Username).isOnline())
							Bukkit.getOfflinePlayer(Username).getPlayer().teleport(location);
						setTeleporting(false);
					}
				}, 35);
				return true;
			}
		}
		return false;
	}
	
	public void cancelTeleport() {
		setTeleporting(false);
		if (teleportID != 0)
			Bukkit.getScheduler().cancelTask(teleportID);
	}
	
}
