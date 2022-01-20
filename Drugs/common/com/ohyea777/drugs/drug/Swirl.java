package com.ohyea777.drugs.drug;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import com.ohyea777.drugs.Drugs;
import com.ohyea777.drugs.listener.DrugListener;
import com.ohyea777.drugs.util.Utils;

public class Swirl {

	private String Color;
	private Integer Duration;

	public Swirl() {
		if (Duration == null) {
			Duration = 10;
		}
		if (Color == null) {
			Color = "White";
		}
	}

	public int getDuration() {
		return Duration * 20;
	}

	public void setDuration(int duration) {
		duration = Duration;
	}

	protected int getColor() {
		return toColor(Color);
	}

	public int doSwirl(final Player player) {
		if (!DrugListener.swirls.containsKey(player.getName()) || DrugListener.swirls.containsKey(player.getName()) && DrugListener.swirls.get(player.getName()) <= Utils.getMaxParticles()) {
			DrugListener.swirls.put(player.getName(), DrugListener.swirls.containsKey(player.getName()) ? DrugListener.swirls.get(player.getName()) + 1 : 1);
			int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Drugs.instance, new Runnable() {
				@Override
				public void run() {
					player.getWorld().playEffect(player.getLocation(), Effect.POTION_BREAK, getColor());
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
				if (DrugListener.swirls.containsKey(player.getName()) && DrugListener.swirls.get(player.getName()) > 0)
					DrugListener.swirls.put(player.getName(), DrugListener.swirls.containsKey(player.getName()) ? DrugListener.swirls.get(player.getName()) - 1 : 0);
			}
		}, getDuration());
	}

	private int toColor(String color) {
		switch (color.toLowerCase()) {
		case "darkblue":
			return 16384;
		case "dark blue":
			return 16384;
		case "dark_blue":
			return 16384;
		case "blue":
			return 16386;
		case "aqua":
			return 16386;
		case "pink":
			return 16385;
		case "yellow":
			return 16387;
		case "orange":
			return 16387;
		case "red":
			return 16389;
		case "darkred":
			return 16389;
		case "dark red":
			return 16389;
		case "dark_red":
			return 16389;
		case "gray":
			return 16430;
		case "white":
			return 16430;
		case "lightgray":
			return 16430;
		case "light gray":
			return 16430;
		case "light_gray":
			return 16430;
		case "green":
			return 16388;
		case "lightgreen":
			return 16388;
		case "light green":
			return 16388;
		case "light_green":
			return 16388;
		case "darkgreen":
			return 16388;
		case "dark green":
			return 16388;
		case "dark_green":
			return 16388;
		case "black":
			return 16392;
		case "darkgray":
			return 16392;
		case "dark gray":
			return 16392;
		case "dark_gray":
			return 16392;
		case "cyan":
			return 16394;
		case "darkaqua":
			return 16394;
		case "dark aqua":
			return 16394;
		case "dark_aqua":
			return 16394;
		default:
			return 0;
		}
	}

}
