package com.ohyea777.drugs.drug;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ohyea777.drugs.Drugs;

@SuppressWarnings("deprecation")
public class CustomSound {

	private String Sound;
	private Float Volume;
	private Float Pitch;
	private Integer Duration;
	private Integer Repetitions;

	public CustomSound() {
		if (Volume == null)
			Volume = 1.0F;
		if (Pitch == null)
			Pitch = 1.0F;
	}
	
	public void setSound(String sound) {
		Sound = sound;
	}

	public int getDuration() {
		return Duration != null ? Duration * 20 : 10 * 20;
	}

	public void setDuration(int duration) {
		Duration = duration;
	}

	public void setRepetitions(int repetitions) {
		Repetitions = repetitions;
	}
	
	public void play(final Player player) {
		if (Repetitions != null && Repetitions > 1) {
			int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Drugs.instance, new Runnable() {
				@Override
				public void run() {
					player.playSound(player.getLocation(), Sound, Volume, Pitch);
				}
			}, 0L, getDuration());
			cancelLater(id);
		} else {
			player.playSound(player.getLocation(), Sound, Volume, Pitch);
		}
	}
	
	private void cancelLater(final int id) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Drugs.instance, new Runnable() {
			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(id);
			}
		}, getDuration() * Repetitions);
	}
	
}
