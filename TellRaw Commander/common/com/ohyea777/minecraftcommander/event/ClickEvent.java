package com.ohyea777.minecraftcommander.event;

public class ClickEvent {
	
	private Type action;
	private String value;
	
	public ClickEvent(Type type, String command) {
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
		
		run_command, suggest_command, open_url
		
	}
	
}
