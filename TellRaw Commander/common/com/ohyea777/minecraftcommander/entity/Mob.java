package com.ohyea777.minecraftcommander.entity;

import java.util.ArrayList;

import com.ohyea777.minecraftcommander.entity.util.Attribute;
import com.ohyea777.minecraftcommander.item.Item;
import com.ohyea777.minecraftcommander.item.PotionEffect;

public class Mob extends Entity {

	protected Short Health;
	protected Float HealF;
	protected Float AbsorptionAmount;
	protected Short AttackTime;
	protected Short HurtTime;
	protected Short DeathTime;
	protected ArrayList<Attribute> Attributes;
	protected ArrayList<PotionEffect> ActivePotionEffects;
	protected Item[] Equipment;
	protected Float[] DropChances;
	protected Byte CanPickupLoot;
	protected Byte PersistenceRequired;
	protected String CustomName;
	protected Byte CustomNameVisible;

	public void setHealth(Short health) {
		Health = health;
	}

	public void setHealF(Float healF) {
		HealF = healF;
	}

	public void setAbsorptionAmount(Float absorptionAmount) {
		AbsorptionAmount = absorptionAmount;
	}

	public void setAttackTime(Short attackTime) {
		AttackTime = attackTime;
	}

	public void setHurtTime(Short hurtTime) {
		HurtTime = hurtTime;
	}

	public void setDeathTime(Short deathTime) {
		DeathTime = deathTime;
	}

	public void setAttributes(ArrayList<Attribute> attributes) {
		Attributes = attributes;
	}
	
	private void checkPotionEffects() {
		if (ActivePotionEffects == null)
			ActivePotionEffects = new ArrayList<PotionEffect>();
	}
	
	public void addPotionEffect(PotionEffect effect) {
		checkPotionEffects();
		
		ActivePotionEffects.add(effect);
	}

	public void setEquipment(Item hand, Item feet, Item legs, Item chest, Item head) {
		Equipment = new Item[] { hand, feet, legs, chest, head };
	}
	
	public void setDropChances(float hand, float feet, float legs, float chest, float head) {
		DropChances = new Float[] { hand, feet, legs, chest, head };
	}

	public void setCanPickupLoot(boolean canPickupLoot) {
		CanPickupLoot = (byte) (canPickupLoot ? 1 : 0);
	}

	public void setPersistenceRequired(boolean persistenceRequired) {
		PersistenceRequired = (byte) (persistenceRequired ? 1 : 0);
	}

	public void setCustomName(String customName) {
		CustomName = customName;
	}

	public void setCustomNameVisible(boolean customNameVisible) {
		CustomNameVisible = (byte) (customNameVisible ? 1 : 0);
	}
	
}
