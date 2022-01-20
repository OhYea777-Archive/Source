package com.ohyea777.minecraftcommander.item;

import java.util.ArrayList;

public class FireworkExplosion {

	protected Byte Flicker;
	protected Byte Trail;
	protected Byte Type;
	protected ArrayList<Integer> Colors;
	protected ArrayList<Integer> FadeColors;

	public void setFlicker(Byte flicker) {
		Flicker = flicker;
	}

	public void setTrail(Byte trail) {
		Trail = trail;
	}

	public void setType(Byte type) {
		Type = type;
	}

	private void checkColors() {
		if (Colors == null)
			Colors = new ArrayList<Integer>();
	}
	
	public void addColor(int color) {
		checkColors();
		
		Colors.add(color);
	}
	
	public void addColor(int red, int green, int blue) {
		checkColors();
		
		Colors.add(red << 16 + green << 8 + blue);
	}
	
	private void checkFadeColors() {
		if (FadeColors == null)
			FadeColors = new ArrayList<Integer>();
	}
	
	public void addFadeColor(int color) {
		checkFadeColors();
		
		FadeColors.add(color);
	}
	
	public void addFadeColor(int red, int green, int blue) {
		checkFadeColors();
		
		FadeColors.add(red << 16 + green << 8 + blue);
	}

}
