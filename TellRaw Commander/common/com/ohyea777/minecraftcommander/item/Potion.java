package com.ohyea777.minecraftcommander.item;

import java.util.ArrayList;

public class Potion extends Item {

	protected ArrayList<PotionEffect> CustomPotionEffects;
	
	private void checkPotionEffects() {
		if (CustomPotionEffects == null)
			CustomPotionEffects = new ArrayList<PotionEffect>();
	}
	
	public void addPotionEffect(PotionEffect effect) {
		checkPotionEffects();
		
		CustomPotionEffects.add(effect);
	}
	
}
