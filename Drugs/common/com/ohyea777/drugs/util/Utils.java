package com.ohyea777.drugs.util;

import java.io.File;
import java.io.FileNotFoundException;

import com.ohyea777.drugs.Drugs;
import com.ohyea777.drugs.drug.DrugLoader;
import com.ohyea777.drugs.libs.com.google.gson.Gson;
import com.ohyea777.drugs.libs.com.google.gson.GsonBuilder;
import com.ohyea777.drugs.libs.org.apache.commons.io.FileUtils;

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
	
	private static Drugs getPlugin() {
		return Drugs.instance;
	}
	
	public static void saveDrugLoader(DrugLoader loader) {
		try {
			File file = new File(getPlugin().getDataFolder(), "Drugs.json");
			String json = getGson().toJson(loader);
			FileUtils.writeStringToFile(file, json);
		} catch (FileNotFoundException e) {
			saveDefaultLoader();
		} catch (Exception e) {
			getPlugin().getLogger().severe("Failed to Save Drugs JSON - " + e.getMessage() + "! Printing Stacktrace: ");
			e.printStackTrace();
		}
	}
	
	public static void saveDefaultLoader() {
		if (!loaderExists())
			getPlugin().saveResource("Drugs.json", true);
	}
	
	public static DrugLoader loadDrugLoader() {
		try {
			File file = new File(getPlugin().getDataFolder(), "Drugs.json");
			if (!file.exists())
				saveDefaultLoader();
			String json = FileUtils.readFileToString(file);
			return getGson().fromJson(json, DrugLoader.class);
		} catch (Exception e) {
			getPlugin().getLogger().severe("Failed to Load Drugs JSON - " + e.getMessage() + "! Printing Stacktrace: ");
			e.printStackTrace();
		}
		return null;
	}
	
	public static DrugLoader loadDrugLoader(File file) {
		try {
			String json = FileUtils.readFileToString(file);
			return getGson().fromJson(json, DrugLoader.class);
		} catch (Exception e) {
			getPlugin().getLogger().severe("Failed to Load Drugs JSON - " + e.getMessage() + "! Printing Stacktrace: ");
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean loaderExists() {
		File file = new File(getPlugin().getDataFolder(), "Drugs.json");
		return file.exists();
	}
	
	public static int getMaxParticles() {
		return Drugs.instance.getConfig().get("Options.Max_Particle_Effects") != null && Drugs.instance.getConfig().get("Options.Max_Particle_Effects") instanceof Integer ? Drugs.instance.getConfig().getInt("Options.Max_Particle_Effects") : 10;
	}
	
}
