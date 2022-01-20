package com.ohyea777.drugs.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.ohyea777.drugs.Drugs;
import com.ohyea777.drugs.drug.CustomSound;
import com.ohyea777.drugs.drug.Drug;
import com.ohyea777.drugs.drug.DrugParticleEffect;
import com.ohyea777.drugs.drug.Swirl;
import com.ohyea777.drugs.util.Messages;

public class DrugListener implements Listener {

	public static HashMap<String, Integer> swirls = new HashMap<String, Integer>();
	public static HashMap<String, Integer> particles = new HashMap<String, Integer>();
	public Map<Integer, ItemStack> entityMap = new HashMap<Integer, ItemStack>();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getPlayer().isSneaking() && event.getPlayer().getItemInHand() != null && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			ItemStack item = event.getPlayer().getItemInHand();

			short durability = 0;
			try {
				durability = item.getDurability();
				@SuppressWarnings("unused")
				int i = durability;
			} catch (NullPointerException e) { }

			if (Drugs.loader.getDrug(item.getType(), durability) != null) {
				Drug drug = Drugs.loader.getDrug(item.getType(), item.getDurability());
				if (drug.isEnabledInWorld(event.getPlayer().getWorld())) {
					if (drug.hasPermission(event.getPlayer())) {
						event.getPlayer().sendMessage(drug.getUsageMessage());
						if (drug.hasEffects())
							event.getPlayer().addPotionEffects(drug.getEffects());
						ArrayList<PotionEffect> rand = drug.getRandomEffects();
						if (drug.hasRandomEffects() && !rand.isEmpty())
							event.getPlayer().addPotionEffects(rand);
						if (drug.hasCommands()) {
							for (String s : drug.getCommands()) {
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{player}", event.getPlayer().getName()));
							}
						}
						if (drug.hasRandomGroupEffects()) {
							ArrayList<PotionEffect> randGroupEffects = drug.getRandomGroupEffects();
							if (randGroupEffects != null)
								event.getPlayer().addPotionEffects(randGroupEffects);
						}
						if (drug.hasSwirls())
							for (Swirl s : drug.getSwirls()) {
								s.doSwirl(event.getPlayer());
							}
						if (drug.hasParticleEffects())
							for (DrugParticleEffect e : drug.getParticleEffects()) {
								e.doEffect(event.getPlayer());
							}
						if (drug.hasSounds()) {
							for (CustomSound c : drug.getSounds())
								c.play(event.getPlayer());
						}

						if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
							if (event.getPlayer().getItemInHand().getAmount() > 1)
								event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
							else
								event.getPlayer().setItemInHand(null);
						}
						event.setCancelled(true);
					} else {
						event.getPlayer().sendMessage(Messages.NO_PERM.toString());
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof Player && event.getPlayer().isSneaking() && event.getPlayer().getItemInHand() != null) {
			Player player = (Player) event.getRightClicked();
			ItemStack item = event.getPlayer().getItemInHand();

			if (item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().get(0).contains(":")) {
				Material material;
				int damage;
				try {
					material = Material.getMaterial(ChatColor.stripColor(item.getItemMeta().getLore().get(0).split(":")[0].replace("-", "_")));
					damage = Integer.valueOf(ChatColor.stripColor(item.getItemMeta().getLore().get(0).split(":")[1]));

					if (Drugs.loader.getDrug(material, damage) != null) {
						Drug drug = Drugs.loader.getDrug(material, damage);
						if (drug.isEnabledInWorld(event.getPlayer().getWorld())) {
							if (drug.hasPermission(event.getPlayer())) {
								player.sendMessage(drug.getDruggedMessage().replace("{player}", event.getPlayer().getName()));
								if (drug.hasEffects())
									player.addPotionEffects(drug.getEffects());
								ArrayList<PotionEffect> rand = drug.getRandomEffects();
								if (drug.hasRandomEffects() && !rand.isEmpty())
									player.addPotionEffects(rand);
								if (drug.hasCommands()) {
									for (String s : drug.getCommands()) {
										Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{player}", player.getName()));
									}
								}
								if (drug.hasRandomGroupEffects()) {
									ArrayList<PotionEffect> randGroupEffects = drug.getRandomGroupEffects();
									if (randGroupEffects != null)
										player.addPotionEffects(randGroupEffects);
								}
								if (drug.hasSwirls())
									for (Swirl s : drug.getSwirls()) {
										s.doSwirl(player);
									}
								if (drug.hasParticleEffects())
									for (DrugParticleEffect e : drug.getParticleEffects()) {
										e.doEffect(player);
									}
								if (drug.hasSounds()) {
									for (CustomSound c : drug.getSounds())
										c.play(player);
								}

								if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
									if (event.getPlayer().getItemInHand().getAmount() > 1)
										event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
									else
										event.getPlayer().setItemInHand(null);
								}
								event.setCancelled(true);
							} else {
								event.getPlayer().sendMessage(Messages.NO_PERM.toString());
							}
						}
					}
				} catch (Exception e) { return; }
			}
		}
	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent event){
		if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player){
			if (((Arrow)event.getDamager()).getShooter() instanceof Player){
				Player p = (Player) ((Arrow)event.getDamager()).getShooter();
				Player player = (Player) event.getEntity();
				Arrow a = (Arrow) event.getDamager();
				if (entityMap.get(a.getEntityId()) != null){
					ItemStack item = entityMap.get(a.getEntityId());
					Material material = item.getType();
					Integer damage = (int) item.getDurability();

					if (Drugs.loader.getDrug(material, damage) != null) {
						Drug drug = Drugs.loader.getDrug(material, damage);
						if (drug.isEnabledInWorld(p.getWorld())) {
							if (drug.hasPermission(p)) {
								player.sendMessage(drug.getDruggedMessage().replace("{player}", p.getName()));
								if (drug.hasEffects())
									player.addPotionEffects(drug.getEffects());
								ArrayList<PotionEffect> rand = drug.getRandomEffects();
								if (drug.hasRandomEffects() && !rand.isEmpty())
									player.addPotionEffects(rand);
								if (drug.hasCommands()) {
									for (String s : drug.getCommands()) {
										Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{player}", player.getName()));
									}
								}
								if (drug.hasRandomGroupEffects()) {
									ArrayList<PotionEffect> randGroupEffects = drug.getRandomGroupEffects();
									if (randGroupEffects != null)
										player.addPotionEffects(randGroupEffects);
								}
								if (drug.hasSwirls())
									for (Swirl s : drug.getSwirls()) {
										s.doSwirl(player);
									}
								if (drug.hasParticleEffects())
									for (DrugParticleEffect e : drug.getParticleEffects()) {
										e.doEffect(player);
									}
								if (drug.hasSounds()) {
									for (CustomSound c : drug.getSounds())
										c.play(player);
								}

								event.setCancelled(true);
							} else {
								p.sendMessage(Messages.NO_PERM.toString());
							}
						}
					}
					entityMap.remove(a.getEntityId());
					return;
				}
			}
		}
	}

	@EventHandler
	public void onShoot(EntityShootBowEvent event){
		if (!(event.getEntity() instanceof Player) && (!event.getBow().hasItemMeta() || !event.getBow().getItemMeta().hasLore() || !event.getBow().getItemMeta().getLore().get(0).contains(":"))) {
			return;
		}

		try {
			String lore = ChatColor.stripColor(event.getBow().getItemMeta().getLore().get(0).replace("-", "_"));

			Material material = Material.getMaterial(lore.split(":")[0]);
			Short damage = Short.valueOf(lore.split(":")[1]);

			if (Drugs.loader.getDrug(material, damage) != null) {
				ItemStack item = new ItemStack(material, 1, damage);
				entityMap.put(event.getProjectile().getEntityId(), item);
			}
		} catch (Exception e) { }
	}

}
