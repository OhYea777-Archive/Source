package com.ohyea777.minecraftcommander;

import com.ohyea777.minecraftcommander.item.AttributeModifier;
import com.ohyea777.minecraftcommander.item.Item;
import com.ohyea777.minecraftcommander.util.Util;


public class MinecraftCommander {

	private Item i;
	
	public MinecraftCommander() {
		i = new Item();
		
		i.setId((short) 313);
		
		AttributeModifier a = new AttributeModifier("speed", "generic.movementSpeed");
		a.setAmount((double) 40);
		
		i.addAttributeModifier(a);
		
		System.out.println(Util.getGson().toJsonTree(i));
	}
	
	public static void main(String[] args) {
		new MinecraftCommander();
	}
	
}
