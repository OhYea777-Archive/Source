package com.ohyea777.drugs.drug;

import java.util.Random;

import org.bukkit.potion.PotionEffect;

public class RandomEffect extends Effect {

	protected Integer Chance = 50;
	
	public RandomEffect() {
		if (Chance == null)
			Chance = 50;
	}
	
	public void setChance(int chance) {
		Chance = chance;
	}
	
	private boolean getRandPercent(int percent) {
	    Random rand = new Random();
	    return rand.nextInt(100) <= percent;
	}
	
	@Override
	public PotionEffect getEffect() {
		if (getRandPercent(Chance)) {
			return super.getEffect();
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RandomEffect [Chance=");
		builder.append(Chance);
		builder.append("]");
		return builder.toString();
	}
	
}
