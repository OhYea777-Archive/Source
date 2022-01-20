package com.ohyea777.minecraftcommander.entity;

public class Projectile extends Entity {

	protected Short xTile;
	protected Short yTile;
	protected Short zTile;
	protected Byte inTile;
	protected Byte Shake;
	protected Byte inGround;

	public void setxTile(Short xTile) {
		this.xTile = xTile;
	}

	public void setyTile(Short yTile) {
		this.yTile = yTile;
	}

	public void setzTile(Short zTile) {
		this.zTile = zTile;
	}

	public void setInTile(Byte inTile) {
		this.inTile = inTile;
	}

	public void setShake(Byte shake) {
		Shake = shake;
	}

	public void setInGround(boolean inGround) {
		this.inGround = (byte) (inGround ? 1 : 0);
	}
	
}
