package com.ohyea777.drugs.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;

import com.ohyea777.drugs.Drugs;
import com.ohyea777.drugs.drug.Drug;
import com.ohyea777.drugs.drug.DrugLoader;
import com.ohyea777.drugs.drug.Effect;
import com.ohyea777.drugs.drug.RandomEffect;
import com.ohyea777.drugs.libs.org.apache.commons.io.FileUtils;

public class OldConfigConvert {

	private Drugs plugin;

	public OldConfigConvert() {
		plugin = Drugs.instance;
		DrugLoader loader = new DrugLoader();

		for (String s : plugin.getConfig().getConfigurationSection("Drugs").getKeys(false)) {
			Drug drug = convert(plugin.getConfig().getConfigurationSection("Drugs." + s), s);
			if (drug != null) {
				loader.addDrug(drug);
			}
		}

		Utils.saveDrugLoader(loader);
		updateConfig();
		Drugs.loader = Utils.loadDrugLoader();
	}

	private Drug convert(ConfigurationSection config, String s) {
		Drug drug = new Drug();
		if (s.contains(":")) {
			try {
				if (Integer.valueOf(s.split(":")[0]) != null && Integer.valueOf(s.split(":")[1]) != null) {
					if (MaterialIdLib.getMaterialById(Integer.valueOf(s.split(":")[0])) != null) {
						try {
							drug.setMaterial(MaterialIdLib.getMaterialById(Integer.valueOf(s.split(":")[0])));
							drug.setDamage(Integer.valueOf(s.split(":")[1]));
						} catch (NullPointerException ignored) {
							plugin.getLogger().severe(String.format("Drug: [%s] - Failed to Load, Skipping Drug!", s));
						}
					} else {
						plugin.getLogger().severe(String.format("Drug: [%s] - Unable to Convert Old ID to Material Type, Skipping Drug!", s));
						return null;
					}
				} else {
					plugin.getLogger().severe(String.format("Drug: [%s] - Unable to Parse ID & Damage, Skipping Drug!", s));
					return null;
				}
			} catch (NumberFormatException e) {
				plugin.getLogger().severe(String.format("Drug: [%s] - Unable to Parse ID & Damage, Skipping Drug!", s));
				return null;
			}
		} else {
			try {
				if (Integer.valueOf(s) != null) {
					try {
						if (MaterialIdLib.getMaterialById(Integer.valueOf(s.split(":")[0])) != null) {
							drug.setMaterial(MaterialIdLib.getMaterialById(Integer.valueOf(s.split(":")[0])));
						} else {
							plugin.getLogger().severe(String.format("Drug: [%s] - Unable to Convert Old ID to Material Type, Skipping Drug!", s));
							return null;
						}
					} catch (NumberFormatException e) {
						plugin.getLogger().severe(String.format("Drug: [%s] - Unable to Convert Old ID to Material Type, Skipping Drug!", s));
						return null;
					}
				} else {
					plugin.getLogger().severe(String.format("Drug: [%s] - Unable to Parse ID, Skipping Drug!", s));
					return null;
				}
			} catch (NumberFormatException e) {
				plugin.getLogger().severe(String.format("Drug: [%s] - Unable to Parse ID, Skipping Drug!", s));
				return null;
			}
		}

		ConfigurationSection e = config.getConfigurationSection("Effects");
		if (e != null) {
			for (String effect : e.getKeys(false)) {
				if (e.get(effect + ".Duration") != null) {
					if (e.get(effect + ".Strength") != null) {
						try {
							Effect newEffect = new Effect();
							newEffect.setEffectType(effect);
							newEffect.setDuration(e.getInt(effect + ".Duration"));
							newEffect.setStrength(e.getInt(effect + ".Strength"));
							drug.addEffect(newEffect);
						} catch (NullPointerException ignored) {
							plugin.getLogger().severe(String.format("Drug: [%s] - Effect: [%s] - Failed to Load, Skipping Effect!", s, effect));
						}
					} else {
						plugin.getLogger().warning(String.format("Drug: [%s] - Effect: [%s] - Strength Not Valid, Defaulting Strength to 0!", s, effect));
						try {
							Effect newEffect = new Effect();
							newEffect.setEffectType(effect);
							newEffect.setDuration(e.getInt(effect + ".Duration"));
							newEffect.setStrength(0);
							drug.addEffect(newEffect);
						} catch (NullPointerException ignored) {
							plugin.getLogger().severe(String.format("Drug: [%s] - Effect: [%s] - Failed to Load, Skipping Effect!", s, effect));
						}
					}
				} else {
					plugin.getLogger().severe(String.format("Drug: [%s] - Effect: [%s] - Duration Not Valid, Skipping Effect!", s, effect));
				}
			}
		} else {
			plugin.getLogger().warning(String.format("Drug: [%s] - Unable to Find Effects, Skipping Effects!", s));
		}
		
		ConfigurationSection rE = config.getConfigurationSection("Random_Effects");
		if (rE != null) {
			for (String effect : rE.getKeys(false)) {
				if (rE.get(effect + ".Duration") != null) {
					if (rE.get(effect + ".Strength") != null) {
						try {
							RandomEffect newEffect = new RandomEffect();
							newEffect.setEffectType(effect);
							newEffect.setDuration(rE.getInt(effect + ".Duration"));
							newEffect.setStrength(rE.getInt(effect + ".Strength"));
							if (rE.get(effect + ".Chance") != null && rE.get(effect + ".Chance") instanceof Integer)
								newEffect.setChance(rE.getInt(effect + ".Chance"));
							drug.addRandomEffect(newEffect);
						} catch (NullPointerException ignored) {
							plugin.getLogger().severe(String.format("Drug: [%s] - RandomEffect: [%s] - Failed to Load, Skipping RandomEffect!", s, effect));
						}
					} else {
						plugin.getLogger().warning(String.format("Drug: [%s] - RandomEffect: [%s] - Strength Not Valid, Defaulting Strength to 0!", s, effect));
						try {
							RandomEffect newEffect = new RandomEffect();
							newEffect.setEffectType(effect);
							newEffect.setDuration(rE.getInt(effect + ".Duration"));
							newEffect.setStrength(0);
							if (rE.get(effect + ".Chance") != null && rE.get(effect + ".Chance") instanceof Integer)
								newEffect.setChance(rE.getInt(effect + ".Chance"));
							drug.addRandomEffect(newEffect);
						} catch (NullPointerException ignored) {
							plugin.getLogger().severe(String.format("Drug: [%s] - RandomEffect: [%s] - Failed to Load, Skipping RandomEffect!", s, effect));
						}
					}
				} else {
					plugin.getLogger().severe(String.format("Drug: [%s] - RandomEffect: [%s] - Duration Not Valid, Skipping RandomEffect!", s, effect));
				}
			}
		} else {
			plugin.getLogger().warning(String.format("Drug: [%s] - Unable to Find RandomEffects, Skipping RandomEffects!", s));
		}
		
		if (config.getStringList("Commands") != null && !config.getStringList("Commands").isEmpty()) {
			drug.setCommands(config.getStringList("Commands").toArray(new String[] { }));
		}

		ConfigurationSection o = config.getConfigurationSection("Options");
		if (o != null) {
			if (o.getString("Nickname") != null) {
				drug.setName(o.getString("Nickname"));
			} else {
				plugin.getLogger().warning(String.format("Drug: [%s] - Unable to Find Nickname, Skipping Nickname!", s));
			}
			if (o.getString("Usage_Message") != null) {
				drug.setUsageMessage(o.getString("Usage_Message"));
			} else {
				plugin.getLogger().warning(String.format("Drug: [%s] - Unable to Find Usage Message, Skipping Usage Message!", s));
			}
		} else {
			plugin.getLogger().warning(String.format("Drug: [%s] - Unable to Find Options, Skipping Options!", s));
		}
		return drug;
	}

	protected void updateConfig() {
		try {
			FileUtils.copyFile(new File(plugin.getDataFolder(), "config.yml"), new File(plugin.getDataFolder(), "config.yml.old"));
		} catch (IOException e) { }
		
		plugin.getConfig().set("Drugs", null);
		plugin.getConfig().set("Options.Prefix", plugin.getConfig().getString("Options.Prefix") + " ");
		plugin.getConfig().set("Options.Max_Particle_Effects", 10);
		plugin.getConfig().set("Messages.Drugged", "You have just been drugged with {drug} by {player}");
		plugin.saveConfig();
	}

}
