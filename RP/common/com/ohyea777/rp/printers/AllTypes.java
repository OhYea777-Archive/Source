package com.ohyea777.rp.printers;

import org.bukkit.Material;

public class AllTypes implements IPrinterType {

	private final String name;
	private final Material material;
	private final int timeToPrint;
	
	public AllTypes(String name, Material material, int timeToPrint) {
		this.name = name;
		this.material = material;
		this.timeToPrint = timeToPrint;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public int getTimeToPrint() {
		return timeToPrint;
	}

}
