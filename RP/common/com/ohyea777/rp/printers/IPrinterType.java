package com.ohyea777.rp.printers;

import org.bukkit.Material;

public interface IPrinterType {

	public String getName();
	
	public Material getMaterial();
	
	public int getTimeToPrint();
	
}
