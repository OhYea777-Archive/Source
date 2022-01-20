package com.ohyea777.minecraftcommander.entity;

public class SmallFireball extends Projectile {

	protected Double[] direction;
	
	public void setDirection(double x, double y, double z) {
		direction = new Double[] { x, y, z };
	}
	
}
