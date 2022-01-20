package com.ohyea777.minecraftcommander.entity;

public class EntityHorse extends Mob {

	protected Byte Bred;
	protected Byte ChestedHorse;
	protected Byte EatingHaystack;
	protected Byte HasReproduced;
	protected Byte Tame;
	protected Integer Temper;
	protected Integer Type;
	protected Integer Variant;
	protected String OwnerName;

	public void setBred(Byte bred) {
		Bred = bred;
	}

	public void setChestedHorse(boolean chestedHorse) {
		ChestedHorse = (byte) (chestedHorse ? 1 : 0);
	}

	public void setEatingHaystack(boolean eatingHaystack) {
		EatingHaystack = (byte) (eatingHaystack ? 1 : 0);
	}

	public void setHasReproduced(boolean hasReproduced) {
		HasReproduced = (byte) (hasReproduced ? 1 : 0);
	}

	public void setTame(boolean tame) {
		Tame = (byte) (tame ? 1 : 0);
	}

	public void setTemper(Integer temper) {
		Temper = temper;
	}

	public void setType(Integer type) {
		Type = type;
	}

	public void setVariant(Integer variant) {
		Variant = variant;
	}

	public void setOwnerName(String ownerName) {
		OwnerName = ownerName;
	}

}
