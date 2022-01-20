package com.ohyea777.minecraftcommander.entity;

public class Zombie extends Mob {

	protected Byte IsVillager;
	protected Byte IsBaby;
	protected Integer ConversationTime;
	protected Byte CanBreakDoors;

	public void setIsVillager(boolean isVillager) {
		IsVillager = (byte) (isVillager ? 1 : 0);
	}

	public void setIsBaby(boolean isBaby) {
		IsBaby = (byte) (isBaby ? 1 : 0);
	}

	public void setConversationTime(Integer conversationTime) {
		ConversationTime = conversationTime;
	}

	public void setCanBreakDoors(boolean canBreakDoors) {
		CanBreakDoors = (byte) (canBreakDoors ? 1 : 0);
	}

}
