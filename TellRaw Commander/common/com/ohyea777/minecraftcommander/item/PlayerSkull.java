package com.ohyea777.minecraftcommander.item;

public class PlayerSkull extends Item {

	protected String SkullOwner;
	
	public PlayerSkull(String username) {
		SkullOwner = username;
	}
	
	public void setOwner(String owner) {
		SkullOwner = owner;
	}
	
}
