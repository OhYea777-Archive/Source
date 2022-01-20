package com.ohyea777.minecraftcommander.entity.util;

public class Modifier {

	protected String Name;
	protected Double Amount;
	protected Integer Operation;

	public Modifier(String name) {
		Name = name;
	}
	
	public void setName(String name) {
		Name = name;
	}

	public void setAmount(Double amount) {
		Amount = amount;
	}

	public void setOperation(Integer operation) {
		Operation = operation;
	}

}
