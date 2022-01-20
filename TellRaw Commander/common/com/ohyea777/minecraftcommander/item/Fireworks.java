package com.ohyea777.minecraftcommander.item;

import java.util.ArrayList;

public class Fireworks {

	protected Byte Flight;
	protected ArrayList<FireworkExplosion> Explosions;
	
	public void setFlight(byte flight) {
		Flight = flight;
	}
	
	private void checkExplosions() {
		if (Explosions == null)
			Explosions = new ArrayList<FireworkExplosion>();
	}
	
	public void addExplosion(FireworkExplosion explosion) {
		checkExplosions();
		
		Explosions.add(explosion);
	}
	
}
