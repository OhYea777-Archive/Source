package com.ohyea777.minecraftcommander.entity;

public class Bat extends Mob {
	
	protected Byte BatFlags;
	
	public void setHanging(boolean hanging) {
		BatFlags = (byte) (hanging ? 1 : 0);
	}
	
}
