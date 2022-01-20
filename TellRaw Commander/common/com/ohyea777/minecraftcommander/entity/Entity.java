package com.ohyea777.minecraftcommander.entity;

public class Entity {

	protected String id;
	protected Double[] Pos;
	protected Double[] Motion;
	protected Float[] Rotation;
	protected Float FallDistance;
	protected Short Fire;
	protected Short Air;
	protected Byte Invulnerable;
	protected Entity Riding;

	public void setId(String id) {
		this.id = id;
	}

	public void setPos(double x, double y, double z) {
		Pos = new Double[] { x, y, z };
	}

	public void setMotion(double x, double y, double z) {
		Motion = new Double[] { x, y, z };
	}

	public void setRotation(float yaw, float pitch) {
		Rotation = new Float[] { yaw, pitch };
	}

	public void setFallDistance(float fallDistance) {
		FallDistance = fallDistance;
	}

	public void setFire(short fire) {
		Fire = fire;
	}

	public void setAir(short air) {
		Air = air;
	}

	public void setInvulnerable(boolean invulnerable) {
		Invulnerable = (byte) (invulnerable ? 1 : 0);
	}

	public void setRiding(Entity riding) {
		Riding = riding;
	}

}
