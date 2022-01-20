package com.ohyea777.minecraftcommander.entity;

public class MobTame {

	protected String Owner;
	protected Byte Sitting;

	public void setOwner(String owner) {
		Owner = owner;
	}

	public void setSitting(boolean sitting) {
		Sitting = (byte) (sitting ? 1 : 0);
	}

}
