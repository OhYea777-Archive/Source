package com.mcjailed.jailedrp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mcjailed.jailedrp.jobs.JobLoader.Jobs;

public class RPPlayer {

	private String name;
	private boolean isJailed;
	private Jobs job;
	private boolean hasRespawned;
	
	public RPPlayer() { }
	
	public RPPlayer(String name) {
		this.name = name;
		this.isJailed = false;
		job = Jobs.CITIZEN;
		hasRespawned = false;
	}
	
	public String getName() {
		return name;
	}
	
	public Player getPlayer() {
		return Bukkit.getOfflinePlayer(name).isOnline() ? Bukkit.getOfflinePlayer(name).getPlayer() : null;
	}
	
	public boolean isJailed() {
		return isJailed;
	}
	
	public void setJailed(boolean jailed) {
		isJailed = jailed;
	}
	
	public Jobs getJob() {
		return job;
	}
	
	public void setJob(Jobs job) {
		this.job = job;
		update();
	}
	
	public boolean hasRespawned() {
		return hasRespawned;
	}
	
	public void setHasRespawned(boolean hasRespawned) {
		this.hasRespawned = hasRespawned;
	}
	
	public void update() {
		JailedRP.instance.getLoader().updatePlayer(name);
	}
	
}
