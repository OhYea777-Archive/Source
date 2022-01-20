package com.ohyea777.minecraftcommander.util;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ohyea777.minecraftcommander.command.TellRaw;

public class Util {

	private static Gson gson;
	
	static {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		gson = builder.create();
	}
	
	public static Gson getGson() {
		return gson;
	}
	
	public static String escape(String s) {
		return StringEscapeUtils.escapeJava(s);
	}
	
	public static String escape(TellRaw command) {
		return "/tellraw @a " + Util.getGson().toJson(command).replace("\n", "");
	}
	
}
