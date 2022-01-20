package com.mcjailed.jailedrp.jobs;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.mcjailed.jailedrp.JailedRP;
import com.mcjailed.jailedrp.RPPlayer;

import static com.mcjailed.jailedrp.util.Messages.*;

public class JobLoader implements Listener {

	private final int duration = 20;

	private static IJob[] jobs;
	private Map<RPPlayer, IJob> players;
	private Map<RPPlayer, Integer> tasks;

	public JobLoader() {
		jobs = new IJob[] { new JobCitizen() };
		players = new HashMap<RPPlayer, IJob>();
		tasks = new HashMap<RPPlayer, Integer>();
	}

	public void load(final RPPlayer player) {
		players.put(player, player.getJob().getJob());
		tasks.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(JailedRP.instance, new Runnable() {
			@Override
			public void run() {
				payPlayer(player);
			}
		}, duration * 20, duration * 20));
		if (!player.hasRespawned()) {
			if (player.getPlayer() != null) {
				if (player.getJob().getJob().getItems() != null && !player.getJob().getJob().getItems().isEmpty()) {
					player.getPlayer().getInventory().setContents((ItemStack[]) player.getJob().getJob().getItems().toArray());
				}
				if (player.getJob().getJob().getArmor() != null && !player.getJob().getJob().getArmor().isEmpty()) {
					player.getPlayer().getInventory().setArmorContents((ItemStack[]) player.getJob().getJob().getArmor().toArray());
				}
				player.setHasRespawned(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (players.containsKey(JailedRP.instance.getLoader().getPlayer(event.getPlayer()))) {
			RPPlayer player = JailedRP.instance.getLoader().getPlayer(event.getPlayer());
			if (!player.hasRespawned()) {
				if (player.getJob().getJob().getItems() != null && !player.getJob().getJob().getItems().isEmpty()) {
					player.getPlayer().getInventory().setContents((ItemStack[]) player.getJob().getJob().getItems().toArray());
				}
				if (player.getJob().getJob().getArmor() != null && !player.getJob().getJob().getArmor().isEmpty()) {
					player.getPlayer().getInventory().setArmorContents((ItemStack[]) player.getJob().getJob().getArmor().toArray());
				}
				player.setHasRespawned(true);
			}
		}
	}

	private void payPlayer(RPPlayer player) {
		if (players.containsKey(player) && player.getPlayer() != null) {
			if (players.get(player).getSalary() > 0) {
				if (JailedRP.instance.getEconomyAPI().depositPlayer(player.getName(), players.get(player).getSalary()).transactionSuccess()) {
					player.getPlayer().sendMessage(PREFIX + "" + PAID_JOBS.toString().replace("{job}", players.get(player).getJobTitle()).replace("{amount}", String.valueOf(players.get(player).getSalary())));
				} else {
					player.getPlayer().sendMessage(PREFIX + "" + ECONOMY_FAIL_JOBS.toString().replace("{job}", players.get(player).getJobTitle()));
				}
			} else {
				player.getPlayer().sendMessage(PREFIX + "" + NO_PAY_JOBS.toString().replace("{job}", players.get(player).getJobTitle()));
			}
		}
	}

	public void updateJob(RPPlayer player, Jobs job) {
		if (players.containsKey(player)) {
			remove(player);
			if (player.getPlayer() != null) {
				player.getPlayer().getInventory().clear();
				player.getPlayer().getInventory().setArmorContents(null);
				player.getPlayer().setHealth(0.0);
				player.setHasRespawned(false);
			}
			player.setJob(job);
			load(player);
		}
	}

	public void remove(RPPlayer player) {
		players.remove(player);
		if (tasks.get(player) != null) {
			Bukkit.getScheduler().cancelTask(tasks.get(player));
		}
	}

	public enum Jobs {
		CITIZEN;

		public IJob getJob() {
			return jobs[ordinal()];
		}
	}

}
