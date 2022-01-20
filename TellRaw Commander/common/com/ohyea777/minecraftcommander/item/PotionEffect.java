package com.ohyea777.minecraftcommander.item;

public class PotionEffect {
	
	protected Byte Id;
	protected Byte Amplifier;
	protected Integer Duration;
	protected Byte Ambient;
	
	public PotionEffect(Type potion) {
		Id = potion.getId();
	}
	
	public PotionEffect setId(byte id) {
		Id = id;
		return this;
	}
	
	public PotionEffect setAmplifier(byte amplifier) {
		Amplifier = amplifier;
		return this;
	}
	
	public PotionEffect setDuration(int duration) {
		Duration = duration;
		return this;
	}
	
	public PotionEffect setAmbient(boolean ambient) {
		Ambient = (byte) (ambient ? 1 : 0);
		return this;
	}
	
	public enum Type {
		SPEED(1),
		SLOWNESS(2),
		HASTE(3),
		MINING_FATIGUE(4),
		STRENGTH(5),
		INSTANT_HEALTH(6),
		INSTANT_DAMAGE(7),
		JUMP_BOOST(8),
		NAUSEA(9),
		REGENERATION(10),
		RESISTANCE(11),
		FIRE_RESISTANCE(12),
		WATER_BREATHING(13),
		INVISIBILITY(14),
		BLINDNESS(15),
		NIGHT_VISION(15),
		HUNGER(17),
		WEAKNESS(18),
		POISON(19),
		WITHER(20),
		HEALTH_BOOST(21),
		ABSORBTION(22),
		SATURATION(23);
		
		private byte id;
		
		Type(int id) {
			this.id = (byte) id;
		}
		
		public byte getId() {
			return id;
		}
		
	}

}
