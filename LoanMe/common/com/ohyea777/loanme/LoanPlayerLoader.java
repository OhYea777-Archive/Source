package com.ohyea777.loanme;

import org.bukkit.plugin.java.JavaPlugin;

import com.ohyea777.gsonapi.GsonLoadClass;

public class LoanPlayerLoader extends GsonLoadClass<LoanPlayer> {

	public LoanPlayerLoader() {
		super(LoanPlayer.class);
	}

	@Override
	protected JavaPlugin getPlugin() {
		return LoanMe.instance;
	}

	@Override
	protected Boolean defaultSave() {
		return true;
	}

}
