package com.ohyea777.loanme;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

public class LoanPlayer {

	private String username;
	private Map<String, Loan> loans;
	private transient Map<String, Integer> tasks;
	
	public LoanPlayer() {
		tasks = new HashMap<String, Integer>();
	}
	
	public LoanPlayer(String username) {
		this();
		
		this.username = username;
		loans = new HashMap<String, Loan>();
	}
	
	public void addLoan(String payee, Loan loan) {
		if (loan != null && loan.getUsername().equals(username) && !loans.containsKey(payee.toLowerCase())) {
			loans.put(payee.toLowerCase(), loan);
			LoanMe.instance.getLoader().save(username);
			schedulePayments();
		}
	}
	
	public void removeLoan(String payee) {
		if (payee != null && loans.containsKey(payee.toLowerCase())) {
			loans.remove(payee.toLowerCase());
			LoanMe.instance.getLoader().save(username);
		}
	}
	
	public boolean doesOwe(String payee) {
		return payee != null && loans.containsKey(payee.toLowerCase());
	}
	
	public void schedulePayments() {
		for (final String s : loans.keySet()) {
			if (!tasks.containsKey(s)) {
				int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(LoanMe.instance, new Runnable() {
					@Override
					public void run() {
						if (makePayment(s)) {
							cancelTask(s);
						}
					}
				}, LoanMe.instance.getConfigManager().getTermLength(), LoanMe.instance.getConfigManager().getTermLength());
				tasks.put(s, i);
			}
		}
	}
	
	public boolean makePayment(String s) {
		if (doesOwe(s)) {
			Loan l = loans.remove(s);
			if (l.hasMadeNextPayment()) {
				LoanMe.instance.getLoader().save(username);
				return true;
			} else {
				loans.put(s, l);
				LoanMe.instance.getLoader().save(username);
			}
		}
		
		return false;
	}
	
	public void cancelTask(String s) {
		if (tasks.containsKey(s)) {
			Bukkit.getScheduler().cancelTask(tasks.get(s));
		}
	}
	
}
