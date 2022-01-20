package com.ohyea777.drugs.drug;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import com.ohyea777.drugs.Drugs;
import com.ohyea777.drugs.listener.DrugListener;
import com.ohyea777.drugs.util.Utils;

public class DrugParticleEffect {

	private String ParticleEffect;
	private Integer Duration;
	private Integer Type;

	public DrugParticleEffect() {
		if (Duration == null) {
			Duration = 10;
		}
		if (ParticleEffect == null) {
			ParticleEffect = "Smoke";
		}
	}

	public int getDuration() {
		return Duration * 20;
	}

	public void setDuration(int duration) {
		duration = Duration;
	}

	protected Effect getEffect() {
		return toEffect(ParticleEffect);
	}

	public int doEffect(final Player player) {
		if (!DrugListener.particles.containsKey(player.getName()) || DrugListener.particles.containsKey(player.getName()) && DrugListener.particles.get(player.getName()) <= Utils.getMaxParticles()) {
			DrugListener.particles.put(player.getName(), DrugListener.particles.containsKey(player.getName()) ? DrugListener.particles.get(player.getName()) + 1 : 1);
			int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(
					Drugs.instance, new Runnable() {
						@Override
						public void run() {
							player.getWorld().playEffect(player.getLocation(),
									getEffect(), Type == null ? 0 : Type);
						}
					}, 5, 5);
			cancelLater(id, player);
			return id;
		}
		return 0;
	}

	private void cancelLater(final int id, final Player player) {
		Bukkit.getScheduler().runTaskLater(Drugs.instance, new Runnable() {
			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(id);
				if (DrugListener.particles.containsKey(player.getName()) && DrugListener.particles.get(player.getName()) > 0)
					DrugListener.particles.put(player.getName(), DrugListener.particles.containsKey(player.getName()) ? DrugListener.particles.get(player.getName()) - 1 : 0);
			}
		}, getDuration());
	}

	private Effect toEffect(String effect) {
		switch (effect.toLowerCase()) {
		case "ender":
			return Effect.ENDER_SIGNAL;
		case "endersignal":
			return Effect.ENDER_SIGNAL;
		case "ender signal":
			return Effect.ENDER_SIGNAL;
		case "ender_signal":
			return Effect.ENDER_SIGNAL;
		case "spawner":
			return Effect.MOBSPAWNER_FLAMES;
		case "mobspawner":
			return Effect.MOBSPAWNER_FLAMES;
		case "mob spawner":
			return Effect.MOBSPAWNER_FLAMES;
		case "mob_spawner":
			return Effect.MOBSPAWNER_FLAMES;
		case "flame":
			return Effect.MOBSPAWNER_FLAMES;
		case "flames":
			return Effect.MOBSPAWNER_FLAMES;
		case "mobspawnerflames":
			return Effect.MOBSPAWNER_FLAMES;
		case "mob spawnerflames":
			return Effect.MOBSPAWNER_FLAMES;
		case "mob_spawnerflames":
			return Effect.MOBSPAWNER_FLAMES;
		case "mobspawner flames":
			return Effect.MOBSPAWNER_FLAMES;
		case "mob spawner flames":
			return Effect.MOBSPAWNER_FLAMES;
		case "mob_spawner flames":
			return Effect.MOBSPAWNER_FLAMES;
		case "mobspawner_flames":
			return Effect.MOBSPAWNER_FLAMES;
		case "mob spawner_flames":
			return Effect.MOBSPAWNER_FLAMES;
		case "mob_spawner_flames":
			return Effect.MOBSPAWNER_FLAMES;
		default:
			return Effect.SMOKE;
		}
	}

}
