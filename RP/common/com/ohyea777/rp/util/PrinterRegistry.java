package com.ohyea777.rp.util;

import org.bukkit.Material;

import com.ohyea777.rp.printers.AllTypes;
import com.ohyea777.rp.printers.IPrinterType;

public class PrinterRegistry extends Registry<IPrinterType> {

	@Override
	protected void load() {
		AllTypes op = new AllTypes("OP", Material.NETHER_STAR, 1);
		register(op.getName(), op);
		
		AllTypes diamond = new AllTypes("Diamond", Material.DIAMOND, 1);
		register(diamond.getName(), diamond);
		
		AllTypes emerald = new AllTypes("Emerald", Material.EMERALD, 1);
		register(emerald.getName(), emerald);
		
		AllTypes gold = new AllTypes("Gold", Material.GOLD_INGOT, 1);
		register(gold.getName(), gold);
		
		AllTypes iron = new AllTypes("Iron", Material.IRON_INGOT, 1);
		register(iron.getName(), iron);
	}

}
