package com.ohyea777.minecraftcommander.entity;

public class VillagerGolem extends Mob {

	protected Byte PlayerCreated;
	
	public void setPlayerCreated(boolean playerCreated) {
		PlayerCreated = (byte) (playerCreated ? 1 : 0);
	}
	
}
