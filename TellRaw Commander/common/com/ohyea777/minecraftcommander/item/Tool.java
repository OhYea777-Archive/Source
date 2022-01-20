package com.ohyea777.minecraftcommander.item;

public class Tool extends Item {

	protected Byte Unbreakable;
	
	public void setUnbreakable(boolean unbreakable) {
		Unbreakable = (byte) (unbreakable ? 1 : 0);
	}
	
}
