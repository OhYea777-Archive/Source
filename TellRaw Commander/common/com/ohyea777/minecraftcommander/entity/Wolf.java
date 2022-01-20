package com.ohyea777.minecraftcommander.entity;

public class Wolf extends MobBreedTame {

	protected Byte Angry;
	protected Byte CollarColor;

	public void setAngry(boolean angry) {
		Angry = (byte) (angry ? 1 : 0);
	}

	public void setCollarColor(Byte collarColor) {
		CollarColor = collarColor;
	}

}
