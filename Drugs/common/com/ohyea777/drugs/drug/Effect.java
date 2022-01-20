package com.ohyea777.drugs.drug;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Effect {
	
	private String EffectType;
	private int Duration = 1;
	private Integer Strength;

	public void setEffectType(String effectType) {
		EffectType = effectType;
	}

	public void setDuration(int duration) {
		Duration = duration;
	}

	public void setStrength(Integer strength) {
		Strength = strength;
	}
	
	public PotionEffect getEffect() {
		if (toPotion(EffectType) != null)
			return new PotionEffect(toPotion(EffectType), Duration * 20, Strength);
		return null;
	}
	
	private PotionEffectType toPotion(String s){
		String string = s.toLowerCase();
		switch(string){
		case "absorbtion":
			return PotionEffectType.ABSORPTION;
		case "blind":
			return PotionEffectType.BLINDNESS;
		case "blindness":
			return PotionEffectType.BLINDNESS;
		case "confusion":
			return PotionEffectType.CONFUSION;
		case "nausea":
			return PotionEffectType.CONFUSION;
		case "resistance":
			return PotionEffectType.DAMAGE_RESISTANCE;
		case "damage resistance":
			return PotionEffectType.DAMAGE_RESISTANCE;
		case "damageresistance":
			return PotionEffectType.DAMAGE_RESISTANCE;
		case "damage_resistance":
			return PotionEffectType.DAMAGE_RESISTANCE;
		case "fastdig":
			return PotionEffectType.FAST_DIGGING;
		case "fastdigging":
			return PotionEffectType.FAST_DIGGING;
		case "fast_digging":
			return PotionEffectType.FAST_DIGGING;
		case "speeddigging":
			return PotionEffectType.FAST_DIGGING;
		case "speeddig":
			return PotionEffectType.FAST_DIGGING;
		case "speed_digging":
			return PotionEffectType.FAST_DIGGING;
		case "haste":
			return PotionEffectType.FAST_DIGGING;
		case "fire":
			return PotionEffectType.FIRE_RESISTANCE;
		case "fireres":
			return PotionEffectType.FIRE_RESISTANCE;
		case "fire_res":
			return PotionEffectType.FIRE_RESISTANCE;
		case "fireresistance":
			return PotionEffectType.FIRE_RESISTANCE;
		case "fire_resistance":
			return PotionEffectType.FIRE_RESISTANCE;
		case "harm":
			return PotionEffectType.HARM;
		case "heal":
			return PotionEffectType.HEAL;
		case "health":
			return PotionEffectType.HEAL;
		case "healthboost":
			return PotionEffectType.HEALTH_BOOST;
		case "health_boost":
			return PotionEffectType.HEALTH_BOOST;
		case "health boost":
			return PotionEffectType.HEALTH_BOOST;
		case "boost":
			return PotionEffectType.HEALTH_BOOST;
		case "hunger":
			return PotionEffectType.HUNGER;
		case "highdmg":
			return PotionEffectType.INCREASE_DAMAGE;
		case "high dmg":
			return PotionEffectType.INCREASE_DAMAGE;
		case "high_dmg":
			return PotionEffectType.INCREASE_DAMAGE;
		case "highdamage":
			return PotionEffectType.INCREASE_DAMAGE;
		case "high damage":
			return PotionEffectType.INCREASE_DAMAGE;
		case "high_damage":
			return PotionEffectType.INCREASE_DAMAGE;
		case "increasedmg":
			return PotionEffectType.INCREASE_DAMAGE;
		case "increase dmg":
			return PotionEffectType.INCREASE_DAMAGE;
		case "increase_dmg":
			return PotionEffectType.INCREASE_DAMAGE;
		case "increasedamage":
			return PotionEffectType.INCREASE_DAMAGE;
		case "increase damage":
			return PotionEffectType.INCREASE_DAMAGE;
		case "increase_damage":
			return PotionEffectType.INCREASE_DAMAGE;
		case "strength":
			return PotionEffectType.INCREASE_DAMAGE;
		case "invisible":
			return PotionEffectType.INVISIBILITY;
		case "invisibility":
			return PotionEffectType.INVISIBILITY;
		case "jump":
			return PotionEffectType.JUMP;
		case "jumpboost":
			return PotionEffectType.JUMP;
		case "jump boost":
			return PotionEffectType.JUMP;
		case "jump_boost":
			return PotionEffectType.JUMP;
		case "nightvision":
			return PotionEffectType.NIGHT_VISION;
		case "night vision":
			return PotionEffectType.NIGHT_VISION;
		case "night_vision":
			return PotionEffectType.NIGHT_VISION;
		case "poison":
			return PotionEffectType.POISON;
		case "regeneration":
			return PotionEffectType.REGENERATION;
		case "saturation":
			return PotionEffectType.SATURATION;
		case "slow":
			return PotionEffectType.SLOW;
		case "slowdigging":
			return PotionEffectType.SLOW_DIGGING;
		case "slow digging":
			return PotionEffectType.SLOW_DIGGING;
		case "slow_digging":
			return PotionEffectType.SLOW_DIGGING;
		case "speed":
			return PotionEffectType.SPEED;
		case "waterbreathing":
			return PotionEffectType.WATER_BREATHING;
		case "water breathing":
			return PotionEffectType.WATER_BREATHING;
		case "water_breathing":
			return PotionEffectType.WATER_BREATHING;
		case "underwaterbreathing":
			return PotionEffectType.WATER_BREATHING;
		case "underwater breathing":
			return PotionEffectType.WATER_BREATHING;
		case "underwater_breathing":
			return PotionEffectType.WATER_BREATHING;
		case "weak":
			return PotionEffectType.WEAKNESS;
		case "weakness":
			return PotionEffectType.WEAKNESS;
		case "wither":
			return PotionEffectType.WITHER;
		case "withereffect":
			return PotionEffectType.WITHER;
		case "wither effect":
			return PotionEffectType.WITHER;
		case "wither_effect":
			return PotionEffectType.WITHER;
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Effect [EffectType=");
		builder.append(EffectType);
		builder.append(", Duration=");
		builder.append(Duration);
		builder.append(", Strength=");
		builder.append(Strength);
		builder.append("]");
		return builder.toString();
	}
	
}
