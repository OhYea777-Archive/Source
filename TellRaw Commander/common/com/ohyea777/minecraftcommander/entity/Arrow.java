package com.ohyea777.minecraftcommander.entity;

public class Arrow extends Projectile {

	protected Byte inData;
	protected Byte pickup;
	protected Byte player;
	protected Byte damage;

	public void setInData(Byte inData) {
		this.inData = inData;
	}

	public void setPickup(Byte pickup) {
		this.pickup = pickup;
	}

	public void setPlayer(boolean player) {
		this.player = (byte) (player ? 1 : 0);
	}

	public void setDamage(Byte damage) {
		this.damage = damage;
	}

}
