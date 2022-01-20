package com.ohyea777.minecraftcommander.item;

import java.util.ArrayList;

public class Item {

	protected Byte slot;
	protected Short id;
	protected Short damage;
	protected Byte count;
	protected ArrayList<Enchantment> ench;
	protected ArrayList<AttributeModifier> AttributeModifiers;
	protected Display display;

	public void setSlot(Byte slot) {
		this.slot = slot;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public void setDamage(Short damage) {
		this.damage = damage;
	}

	public void setCount(Byte count) {
		this.count = count;
	}
	
	private void checkEnchantments() {
		if (ench == null)
			ench = new ArrayList<Enchantment>();
	}
	
	public void addEnchantment(Enchantment enchantment) {
		checkEnchantments();
		
		ench.add(enchantment);
	}
	
	private void checkAttributes() {
		if (AttributeModifiers == null)
			AttributeModifiers = new ArrayList<AttributeModifier>();
	}
	
	public void addAttributeModifier(AttributeModifier attributeModifier) {
		checkAttributes();
		
		AttributeModifiers.add(attributeModifier);
	}
	
	public void setDisplay(Display display) {
		this.display = display;
	}

}
