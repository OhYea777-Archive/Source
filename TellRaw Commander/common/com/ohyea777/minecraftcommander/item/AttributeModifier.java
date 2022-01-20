package com.ohyea777.minecraftcommander.item;

import com.ohyea777.minecraftcommander.entity.util.Modifier;

public class AttributeModifier extends Modifier {

	protected String AttibuteName;
	
	public AttributeModifier(String name, String attributeName) {
		super(name);
		AttibuteName = attributeName;
	}
	
}
