package com.mcjailed.jailedeconomy;

import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class JEconomy implements Economy {

	@Override
	public EconomyResponse bankBalance(String name) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public boolean createPlayerAccount(String player) {
		return true;
	}

	@Override
	public boolean createPlayerAccount(String player, String world) {
		return createPlayerAccount(player);
	}

	@Override
	public String currencyNamePlural() {
		return "Coins";
	}

	@Override
	public String currencyNameSingular() {
		return "Coin";
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse depositPlayer(String player, double amount) {
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String player, String world, double amount) {
		return depositPlayer(player, amount);
	}

	@Override
	public String format(double amount) {
		return null;
	}

	@Override
	public int fractionalDigits() {
		return 0;
	}

	@Override
	public double getBalance(String player) {
		return 0;
	}

	@Override
	public double getBalance(String player, String world) {
		return getBalance(player);
	}

	@Override
	public List<String> getBanks() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean has(String player, double amount) {
		return false;
	}

	@Override
	public boolean has(String player, String world, double amount) {
		return has(player, amount);
	}

	@Override
	public boolean hasAccount(String player) {
		return true;
	}

	@Override
	public boolean hasAccount(String player, String world) {
		return hasAccount(player);
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public EconomyResponse isBankMember(String name, String player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public EconomyResponse isBankOwner(String name, String player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "");
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public EconomyResponse withdrawPlayer(String player, double amount) {
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(String player, String world, double amount) {
		return null;
	}

}
