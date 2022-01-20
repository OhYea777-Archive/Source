package com.ohyea777.drugs.drug;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.ohyea777.drugs.Drugs;
import com.ohyea777.drugs.util.Messages;

public class Drug {

	private String Name;
	private Material Material;
	private Integer Damage;
	private String UsageMessage;
	private String DruggedMessage;
	private String Permission;
	private ArrayList<Effect> Effects;
	private ArrayList<RandomEffect> RandomEffects;
	private ArrayList<RandomGroupEffects> RandomGroupEffects;
	private ArrayList<Swirl> Swirls;
	private ArrayList<DrugParticleEffect> ParticleEffects;
	private ArrayList<CustomSound> Sounds;
	private String[] Commands;
	private String[] Worlds;

	public Drug() {
		if (Damage == null)
			Damage = 0;
	}

	public Drug(String name, Material material, Integer damage,
			String usageMessage, String permission) {
		super();
		Name = name;
		Material = material;
		Damage = damage;
		UsageMessage = usageMessage;
		Permission = permission;
	}

	public String getDruggedMessage() {
		return DruggedMessage != null ? Messages.PREFIX + _(DruggedMessage.replace("{drug}", getName())) : Messages.DRUGGED.toString().replace("{drug}", getName());
	}
	
	public String getName() {
		return _(Name);
	}

	public void setName(String name) {
		Name = name;
	}

	public Material getMaterial() {
		return Material;
	}

	public void setMaterial(Material material) {
		Material = material;
	}

	public Integer getDamage() {
		return Damage;
	}

	public void setDamage(Integer damage) {
		Damage = damage;
	}

	public String getUsageMessage() {
		return UsageMessage != null ? _(Messages.PREFIX + UsageMessage.replace("{drug}", getName())) : _(Messages.DRUG_USE.toString().replace("{drug}", getName()));
	}

	public void setUsageMessage(String usageMessage) {
		UsageMessage = usageMessage;
	}

	public String getPermission() {
		return "drugs." + Permission;
	}

	public void setPermission(String permission) {
		Permission = permission;
	}

	public boolean hasEffects() {
		if (Effects == null)
			Effects = new ArrayList<Effect>();
		return !Effects.isEmpty();
	}

	public void addEffect(Effect effect) {
		hasEffects();

		Effects.add(effect);
	}

	public ArrayList<PotionEffect> getEffects() {
		try {
			if (hasEffects()) {
				ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
				for (Effect e : Effects)
					effects.add(e.getEffect());
				return effects;
			} else {
				return null;
			}
		} catch (NullPointerException e) { return null; }
	}

	public boolean hasRandomEffects() {
		if (RandomEffects == null)
			RandomEffects = new ArrayList<RandomEffect>();
		return !RandomEffects.isEmpty();
	}

	public void addRandomEffect(RandomEffect effect) {
		hasRandomEffects();

		RandomEffects.add(effect);
	}

	public ArrayList<PotionEffect> getRandomEffects() {
		try {
			if (hasRandomEffects()) {
				ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
				for (RandomEffect e : RandomEffects) {
					PotionEffect pE = e.getEffect();
					if (pE != null)
						effects.add(pE);
				}
				return effects;
			} else {
				return null;
			}
		} catch (NullPointerException e) { return null; }
	}

	public boolean hasRandomGroupEffects() {
		if (RandomGroupEffects == null)
			RandomGroupEffects = new ArrayList<RandomGroupEffects>();
		return !RandomGroupEffects.isEmpty();
	}

	public void addRandomGroupEffect(RandomGroupEffects effect) {
		hasRandomGroupEffects();

		RandomGroupEffects.add(effect);
	}

	public ArrayList<PotionEffect> getRandomGroupEffects() {
		try {
			if (hasRandomGroupEffects()) {
				ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
				for (RandomGroupEffects e : RandomGroupEffects) {
					if (e.hasEffects() && e.getEffects() != null && !e.getEffects().isEmpty()) {
						for (PotionEffect pE : e.getEffects()) {
							effects.add(pE);
						}
					}
				}
				return effects;
			} else {
				return null;
			}
		} catch (NullPointerException e) { return null; }
	}

	public boolean hasSwirls() {
		if (Swirls == null)
			Swirls = new ArrayList<Swirl>();
		return !Swirls.isEmpty();
	}

	public void addSwirl(Swirl swirl) {
		hasSwirls();

		Swirls.add(swirl);
	}

	public ArrayList<Swirl> getSwirls() {
		hasSwirls();

		return Swirls;
	}

	public boolean hasParticleEffects() {
		if (ParticleEffects == null)
			ParticleEffects = new ArrayList<DrugParticleEffect>();
		return !ParticleEffects.isEmpty();
	}

	public void addParticleEffect(DrugParticleEffect effect) {
		hasParticleEffects();

		ParticleEffects.add(effect);
	}

	public ArrayList<DrugParticleEffect> getParticleEffects() {
		hasParticleEffects();

		return ParticleEffects;
	}
	
	public boolean hasSounds() {
		if (Sounds == null)
			Sounds = new ArrayList<CustomSound>();
		return !Sounds.isEmpty();
	}
	
	public void addSound(CustomSound sound) {
		hasSounds();
		
		Sounds.add(sound);
	}
	
	public ArrayList<CustomSound> getSounds() {
		hasSounds();
		
		return Sounds;
	}

	public boolean hasCommands() {
		return Commands != null && Commands.length > 0;
	}

	public void setCommands(String[] commands) {
		Commands = commands;
	}

	public String[] getCommands() {
		return Commands;
	}

	public boolean isEnabledInWorld(World world) {
		if (Worlds != null) {
			if (Worlds.length > 0)
				for (String s : Worlds) {
					if (s.toLowerCase().equals(world.getName().toLowerCase())) {
						return true;
					}
				}
			return false;
		}
		return true;
	}

	public void setWorlds(String[] worlds) {
		Worlds = worlds;
	}

	public void addWorld(String world) {
		if (Worlds != null) {
			Worlds[Worlds.length] = world;
		} else {
			Worlds = new String[] { world };
		}
	}

	public String[] getWorlds() {
		return Worlds;
	}

	public boolean hasPermission(Player player) {
		if (Drugs.instance.getConfig().get("Options.Use_Custom_Perms") != null && Drugs.instance.getConfig().get("Options.Use_Custom_Perms") instanceof Boolean && Drugs.instance.getConfig().getBoolean("Options.Use_Custom_Perms")) {
			if (Permission != null) {
				return player.hasPermission("drugs.use." + Permission) || player.hasPermission("drugs.use.*");
			} else {
				return player.hasPermission("drugs.use." + Name) || player.hasPermission("drugs.use.*");
			}
		} else if (Drugs.instance.getConfig().get("Options.UseCustomPerms") != null && Drugs.instance.getConfig().get("Options.UseCustomPerms") instanceof Boolean && Drugs.instance.getConfig().getBoolean("Options.UseCustomPerms")) {
			if (Permission != null) {
				return player.hasPermission("drugs.use." + Permission) || player.hasPermission("drugs.use.*");
			} else {
				return player.hasPermission("drugs.use." + Name.replace(" ", "_")) || player.hasPermission("drugs.use.*");
			}
		} else {
			return player.hasPermission("drugs.use") || player.hasPermission("drugs.use.*");
		}
	}

	private String _(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Drug [Name=");
		builder.append(Name);
		builder.append(", Material=");
		builder.append(Material);
		builder.append(", Damage=");
		builder.append(Damage);
		builder.append(", UsageMessage=");
		builder.append(UsageMessage);
		builder.append(", Permission=");
		builder.append(Permission);
		builder.append(", Effects=");
		builder.append(Effects);
		builder.append(", RandomEffects=");
		builder.append(RandomEffects);
		builder.append(", RandomGroupEffects=");
		builder.append(RandomGroupEffects);
		builder.append(", Commands=");
		builder.append(Arrays.toString(Commands));
		builder.append(", Worlds=");
		builder.append(Arrays.toString(Worlds));
		builder.append("]");
		return builder.toString();
	}

}
