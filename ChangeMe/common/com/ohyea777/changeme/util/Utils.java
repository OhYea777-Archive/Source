package com.ohyea777.changeme.util;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;

public class Utils {

private static Gson gson;
	
	static {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		gson = builder.create();
	}
	
	public static Gson getGson() {
		return gson;
	}
	
}
