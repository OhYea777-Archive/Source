package com.ohyea777.minecraftcommander.entity;

public class Sheep extends MobBreed {

	protected Byte Sheared;
	protected Byte Color;
	
	public void setSheared(boolean sheared) {
		Sheared = (byte) (sheared ? 1 : 0);
	}
	
	public void setColor(Byte color) {
		Color = color;
	}
	
}
