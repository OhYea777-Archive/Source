package com.ohyea777.minecraftcommander.entity;

public class Pig extends MobBreed {

	protected Byte Saddle;
	
	public void setSaddle(boolean saddle) {
		Saddle = (byte) (saddle ? 1 : 0);
	}
	
}
