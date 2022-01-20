package com.ohyea777.minecraftcommander.item;

import java.util.ArrayList;

public class Display {

	protected Integer color;
	protected String Name;
	protected ArrayList<String> Lore;
	
	public void setColor(int red, int green, int blue) {
		color = red << 16 + green << 8 + blue;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	private void checkLore() {
		if (Lore == null)
			Lore = new ArrayList<String>();
	}
	
	public void addLore(String lore) {
		checkLore();
		
		Lore.add(lore);
	}
	
}
