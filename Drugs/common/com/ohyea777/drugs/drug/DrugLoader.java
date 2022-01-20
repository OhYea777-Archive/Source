package com.ohyea777.drugs.drug;

import java.util.ArrayList;

import org.bukkit.Material;

public class DrugLoader {

	private ArrayList<Drug> Drugs;

	private void checkDrugs() {
		if (Drugs == null)
			Drugs = new ArrayList<Drug>();
	}

	public void addDrug(Drug drug) {
		checkDrugs();

		Drugs.add(drug);
	}

	public boolean isDrug(Material material, int damage) {
		for (Drug d : Drugs) {
			try {
				if (d.getMaterial().equals(material) && d.getDamage().equals(damage)) {
					return true;
				}
			} catch (NullPointerException e) { }
		}
		return false;
	}

	public Drug getDrug(Material material, int damage) {
		if (isDrug(material, damage)) {
			try {
				for (Drug d : Drugs) {
					if (d.getMaterial().equals(material) && d.getDamage().equals(damage)) {
						return d;
					}
				}
			} catch (NullPointerException e) { }
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DrugLoader [Drugs=");
		builder.append(Drugs);
		builder.append("]");
		return builder.toString();
	}

}
