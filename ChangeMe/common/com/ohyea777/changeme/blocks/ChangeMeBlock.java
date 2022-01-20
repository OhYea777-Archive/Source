package com.ohyea777.changeme.blocks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

public class ChangeMeBlock {
	
	private Material Type;
	private Material TypeDropped;
	private Short DamageDropped;
	private Map<Integer, SubBlock> SubBlocks;
	
	public ChangeMeBlock() { }
	
	public ChangeMeBlock(Material type) {
		Type = type;
		SubBlocks = new HashMap<Integer, SubBlock>();
		for (int i = 0; i <= getMaxData(type); i ++) {
			SubBlocks.put(i, new SubBlock());
		}
	}
	
	public Map<Integer, SubBlock> getSubBlocks() {
		return SubBlocks;
	}
	
	public Material getType() {
		return Type;
	}
	
	public boolean contains(int data) {
		return SubBlocks.containsKey(data);
	}
	
	public SubBlock get(int data) {
		return contains(data) ? SubBlocks.get(data) : null;
	}
	
	public Material getTypeDropped(int data) {
		if (contains(data) && get(data) != null) {
			if (get(data).TypeDropped != null) {
				return get(data).TypeDropped;
			} else if (TypeDropped != null) {
				return TypeDropped;
			}
		}
		return null;
	}
	
	public Short getDamageDropped(int data) {
		if (contains(data) && get(data) != null) {
			if (get(data).DamageDropped != null) {
				return get(data).DamageDropped;
			} else if (DamageDropped != null) {
				return DamageDropped;
			}
		}
		return null;
	}
	
	public static int getMaxData(Material type) {
		if (type.toString().contains("_STAIRS"))
			return 7;
		
		switch (type) {
		case WOOD: return 15;
		case FIRE: return 15;
		case LEAVES: return 15;
		case JUKEBOX: return 12;
		case SAPLING: return 15;
		case CACTUS: return 15;
		case SUGAR_CANE_BLOCK: return 15;
		case WATER: return 15;
		case LAVA: return 15;
		case SAND: return 1;
		case SOIL: return 8;
		case CROPS: return 7;
		case NETHER_WARTS: return 3;
		case PUMPKIN_STEM: return 7;
		case MELON_STEM: return 7;
		case WOOL: return 15;
		case STAINED_CLAY: return 15;
		case CARPET: return 15;
		case STAINED_GLASS_PANE: return 15;
		case STAINED_GLASS: return 15;
		case COMMAND: return 1;
		case TORCH: return 5;
		case REDSTONE_TORCH_ON: return 5;
		case REDSTONE_TORCH_OFF: return 5;
		case RAILS: return 9;
		case ACTIVATOR_RAIL: return 9;
		case DETECTOR_RAIL: return 9;
		case POWERED_RAIL: return 9;
		case LEVER: return 15;
		case WOODEN_DOOR: return 15;
		case IRON_DOOR_BLOCK: return 15;
		case STONE_BUTTON: return 15;
		case WOOD_BUTTON: return 15;
		case SIGN: return 5;
		case SIGN_POST: return 15;
		case CHEST: return 1;
		case TRAPPED_CHEST: return 5;
		case ENDER_CHEST: return 5;
		case LADDER: return 5;
		case FURNACE: return 5;
		case DISPENSER: return 15;
		case DROPPER: return 15;
		case HOPPER: return 15;
		case PUMPKIN: return 3;
		case JACK_O_LANTERN: return 3;
		case COAL: return 1;
		case WOOD_PLATE: return 1;
		case STONE_PLATE: return 1;
		case IRON_PLATE: return 1;
		case GOLD_PLATE: return 1;
		case STEP: return 15;
		case DOUBLE_STEP: return 15;
		case BED_BLOCK: return 15;
		case CAKE_BLOCK: return 5;
		case SNOW_BLOCK: return 7;
		case REDSTONE_COMPARATOR: return 15;
		case REDSTONE_COMPARATOR_OFF: return 15;
		case REDSTONE_COMPARATOR_ON: return 15;
		case REDSTONE_WIRE: return 15;
		case DIODE: return 15;
		case DIODE_BLOCK_OFF: return 15;
		case DIODE_BLOCK_ON: return 15;
		case GRASS: return 2;
		case TRAP_DOOR: return 7;
		case PISTON_BASE: return 13;
		case PISTON_EXTENSION: return 13;
		case PISTON_MOVING_PIECE: return 13;
		case PISTON_STICKY_BASE: return 13;
		case SMOOTH_BRICK: return 3;
		case HUGE_MUSHROOM_1: return 10;
		case HUGE_MUSHROOM_2: return 10;
		case VINE: return 15;
		case FENCE_GATE: return 7;
		case BREWING_STAND: return 7;
		case CAULDRON: return 3;
		case ENDER_PORTAL_FRAME: return 7;
		case COBBLE_WALL: return 1;
		case FLOWER_POT: return 11;
		case SKULL: return 4;
		case ANVIL: return 2;
		case WOOD_STEP: return 5;
		case WOOD_DOUBLE_STEP: return 5;
		case QUARTZ_BLOCK: return 4;
		default: return 0;
		}
	}
	
	public class SubBlock {
		
		private Material TypeDropped;
		private Short DamageDropped;
		
	}
	
}
