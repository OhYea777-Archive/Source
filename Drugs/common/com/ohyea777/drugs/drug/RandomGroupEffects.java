package com.ohyea777.drugs.drug;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.potion.PotionEffect;

public class RandomGroupEffects {

	private ArrayList<Effect> Effects;
	private Integer Chance;
	
	public RandomGroupEffects() {
		if (Chance == null)
			Chance = 50;
	}
	
	public boolean hasEffects() {
		if (Effects == null)
			Effects = new ArrayList<Effect>();
		return !Effects.isEmpty() && getRandPercent(Chance);
	}

	public void addEffect(Effect effect) {
		hasEffects();

		Effects.add(effect);
	}

	public ArrayList<PotionEffect> getEffects() {
		if (hasEffects()) {
			ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
			for (Effect e : Effects)
				effects.add(e.getEffect());
			return effects;
		} else {
			return null;
		}
	}
	
	private boolean getRandPercent(int percent) {
	    Random rand = new Random();
	    return rand.nextInt(100) <= percent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RandomGroupEffects [Effects=");
		builder.append(Effects);
		builder.append(", Chance=");
		builder.append(Chance);
		builder.append("]");
		return builder.toString();
	}
	
}
