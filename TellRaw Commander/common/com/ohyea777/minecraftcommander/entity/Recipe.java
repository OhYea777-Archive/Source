package com.ohyea777.minecraftcommander.entity;

import com.ohyea777.minecraftcommander.item.Item;

public class Recipe {

	protected Integer maxUses;
	protected Integer uses;
	protected Item buy;
	protected Item buyB;
	protected Item sell;

	public void setMaxUses(Integer maxUses) {
		this.maxUses = maxUses;
	}

	public void setUses(Integer uses) {
		this.uses = uses;
	}

	public void setBuy(Item buy) {
		this.buy = buy;
	}

	public void setBuyB(Item buyB) {
		this.buyB = buyB;
	}

	public void setSell(Item sell) {
		this.sell = sell;
	}
	
}
