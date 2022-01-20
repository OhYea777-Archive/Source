package com.ohyea777.minecraftcommander.entity;

public class Villager extends Mob {

	protected Integer Profession;
	protected Integer Riches;
	protected Offers Offers;

	public void setProfession(Integer profession) {
		Profession = profession;
	}

	public void setRiches(Integer riches) {
		Riches = riches;
	}
	
	public void setOffers(Offers offers) {
		Offers = offers;
	}
	
}
