package com.ohyea777.minecraftcommander.entity.util;

import java.util.ArrayList;

public class Attribute {

	protected String Name;
	protected Double Base;
	protected ArrayList<Modifier> Modifiers;

	public Attribute(String name) {
		this.Name = name;
	}
	
	public void setName(String name) {
		Name = name;
	}

	public void setBase(Double base) {
		Base = base;
	}
	
	private void checkModifiers() {
		if (Modifiers == null)
			Modifiers = new ArrayList<Modifier>();
	}
	
	public void addModifier(Modifier modifier) {
		checkModifiers();
		
		Modifiers.add(modifier);
	}

}
