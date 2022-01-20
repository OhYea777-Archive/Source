package com.ohyea777.minecraftcommander.entity;

public class Creeper extends Mob {
	
	protected Byte powered;
	protected Byte ExplosionRadius;
	protected Short Fuse;
	protected Byte ignited;

	public void setId(String id) {
		this.id = id;
	}

	public void setPowered(boolean powered) {
		this.powered = (byte) (powered ? 1 : 0);
	}

	public void setExplosionRadius(Byte explosionRadius) {
		ExplosionRadius = explosionRadius;
	}

	public void setFuse(Short fuse) {
		Fuse = fuse;
	}

	public void setIgnited(boolean ignited) {
		this.ignited = (byte) (ignited ? 1 : 0);
	}
	
}
