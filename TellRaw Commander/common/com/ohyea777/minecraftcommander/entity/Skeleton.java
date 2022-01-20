package com.ohyea777.minecraftcommander.entity;

public class Skeleton extends Mob {

	protected Byte SkeletonType;
	
	public void setWither(boolean wither) {
		SkeletonType = (byte) (wither ? 1 : 0);
	}
	
}
