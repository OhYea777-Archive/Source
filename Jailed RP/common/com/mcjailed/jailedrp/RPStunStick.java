package com.mcjailed.jailedrp;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.mcjailed.jailedrp.util.Messages.*;

public class RPStunStick implements IItemUse {

	protected JailedRP plugin;

	@Override
	public void addItem(JailedRP plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerRightClickBlock(PlayerInteractEvent event) { }

	@Override
	public void onPlayerLeftClickBlock(PlayerInteractEvent event) { }

	@Override
	public void onPlayerRightClickAir(PlayerInteractEvent event) { }

	@Override
	public void onPlayerLeftClickAir(PlayerInteractEvent event) { }

	@Override
	public void onPlayerRightClickEntity(PlayerInteractEntityEvent event) { }

	@Override
	public void onPlayerLeftClickEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity && !event.isCancelled()) {
			LivingEntity e = (LivingEntity) event.getEntity();
			if (event.getEntity() instanceof Player) {
				Player p = (Player) event.getEntity();
				((Player) event.getDamager()).sendMessage(PREFIX + "" + STUNNED_RPSTUNSTICK.toString().replace("{player}", p.getName()));
				p.sendMessage(PREFIX + "" + BEEN_STUNNED_RPSTUNSTICK.toString().replace("{player}", ((Player) event.getDamager()).getName()));
			}
			e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 3));
			e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 * 20, 3));
			e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10 * 20, 3));
		}
	}

	@Override
	public boolean cancelAnvil() {
		return true;
	}

}
