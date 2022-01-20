package com.ohyea777.ironguardian.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.ohyea777.ironguardian.IronGuardian;
import com.ohyea777.ironguardian.util.EntityUtil;

public class GuardianListener implements Listener {

	private Map<UUID, Integer> events;
	
	public GuardianListener() {
		events = new HashMap<UUID, Integer>();
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof IronGolem) {
			final IronGolem entity = (IronGolem) event.getRightClicked();
			if (EntityUtil.isTamed(entity)) {
				Bukkit.broadcastMessage("Is Tamed");
				if (EntityUtil.isTamedBy(event.getPlayer(), entity)) {
					Bukkit.broadcastMessage("Tamed By Player");
				} else {
					Bukkit.broadcastMessage("Not Tamed By Player");
				}
			} else {
				Bukkit.broadcastMessage("Not Tamed");
				if (event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().getType().equals(Material.RED_ROSE) && event.getPlayer().getItemInHand().getDurability() == 0) {
					if (new Random().nextInt(3) == 2) {
						if (EntityUtil.setTamedBy(event.getPlayer(), entity)) {
							Bukkit.broadcastMessage("You Just Tamed an Iron Golem");
							
							int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(IronGuardian.getInstance(), new Runnable() {	
								private int i = 0;
								
								@Override
								public void run() {
									if (i < 5) {
										i ++;
										entity.getWorld().playEffect(entity.getLocation().add(0, 2.5, 0), Effect.HEART, 10);
									} else {
										Bukkit.getScheduler().cancelTask(events.get(entity.getUniqueId()));
									}
								}
							}, 20, 20);
							
							events.put(entity.getUniqueId(), i);
							entity.setRemoveWhenFarAway(false);
							
							if (event.getPlayer().getItemInHand().hasItemMeta() && event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
								entity.setCustomName(event.getPlayer().getItemInHand().getItemMeta().getDisplayName());
							}
							
							entity.setCustomNameVisible(true);
						} else {
							Bukkit.broadcastMessage("Failed to Tame!");
						}
					}

					if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
						if (event.getPlayer().getItemInHand().getAmount() <= 1) {
							event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
						} else {
							event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
						}
					}
				}
			}
		}
	}

}
