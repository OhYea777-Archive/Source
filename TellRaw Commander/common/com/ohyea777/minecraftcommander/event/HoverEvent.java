package com.ohyea777.minecraftcommander.event;

public class HoverEvent {

	private Type action;
	private String value;
	
	public HoverEvent(Type type, String command) {
		action = type;
		value = command;
	}
	
	public Type getAction() {
		return action;
	}
	
	public String getValue() {
		return value;
	}
	
	public enum Type { 
		
		show_text, show_achievement, show_item
		
	}
	
}
