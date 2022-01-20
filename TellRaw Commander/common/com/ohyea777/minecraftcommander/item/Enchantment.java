package com.ohyea777.minecraftcommander.item;

public class Enchantment {

	protected Integer id;
	protected Integer lvl;
	protected Integer RepairCost;
	
	public Enchantment() { }
	
	public Enchantment(Type type, int lvl) {
		id = type.getId();
		this.lvl = lvl;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setLevel(int lvl) {
		this.lvl = lvl;
	}
	
	public void setRepairCost(int repairCost) {
		RepairCost = repairCost;
	}
	
	public enum Type {
		PROTECTION(0),
		FIRE_PROTECTION(1),
		FEATHER_FALLING(2),
		BLAST_PROTECTION(3),
		PROJECTILE_PROTECTION(4),
		RESPIRATION(5),
		AQUA_AFFINITY(6),
		THORNS(7),
		SHARPNESS(16),
		SMITE(17),
		BANE_OF_ARTHROPODS(18),
		KNOCKBACK(19),
		FIRE_ASPECT(20),
		LOOTING(21),
		EFFICIENCY(32),
		SILK_TOUCH(33),
		UNBREAKING(34),
		FORTUNE(35),
		POWER(48),
		PUNCH(49),
		FLAME(50),
		INFINITY(51),
		LUCK_OF_THE_SEA(61),
		LORE(62);
		
		private int id;
		
		Type(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
	}
	
}
