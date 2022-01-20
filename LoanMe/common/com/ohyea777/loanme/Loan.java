package com.ohyea777.loanme;

import scala.math.BigDecimal;

public class Loan {

	private String payee;
	private String username;

	private LoanType type;

	private double amount;
	private int term;
	private double interest;
	private int payments;

	private double amountOwing;
	private int paymentNum;
	
	public Loan() { }
	
	public Loan(String payee, String username, LoanType type, double amount, int term, double interest, int payments) {
		this.payee = payee;
		this.username = username;
		this.type = type;
		this.amount = amount;
		this.term = term;
		this.interest = interest;
		this.payments = payments;
		this.amountOwing = amount;
		this.paymentNum = 0;
	}

	public boolean hasNextPayment() {
		return amountOwing > 0;
	}
	
	public boolean hasMadeNextPayment() {
		amountOwing -= getAmountForNextPaymentWithoutInterest();
		paymentNum += 1;
		
		return !hasNextPayment();
	}
	
	public double getAmountOwing() {
		return amountOwing;
	}

	public void setAmountOwing(double amountOwing) {
		this.amountOwing = amountOwing;
	}

	public int getPaymentNum() {
		return paymentNum;
	}

	public void setPaymentNum(int paymentNum) {
		this.paymentNum = paymentNum;
	}

	public String getPayee() {
		return payee != null ? payee : "";
	}

	public String getUsername() {
		return username != null ? username : "";
	}

	public double getAmount() {
		return amount;
	}

	public int getTerm() {
		return term;
	}

	public double getSimpleInterest() {
		return BigDecimal.valueOf(amount).$times(BigDecimal.valueOf(interest)).$times(BigDecimal.valueOf(payments)).doubleValue();
	}

	public double getSimpleInterestPerTerm() {
		return BigDecimal.valueOf(getSimpleInterest()).$div(BigDecimal.valueOf(payments)).doubleValue();
	}

	public double getCompoundInterestForCurrentTerm() {
		return BigDecimal.valueOf(amountOwing).$times(BigDecimal.valueOf(interest)).doubleValue();
	}

	public int getPayments() {
		return payments;
	}

	public double getAmountForNextPayment() {
		if (type != null)
			if (type.equals(LoanType.SIMPLE)) {
				return BigDecimal.valueOf(amountOwing).$div(BigDecimal.valueOf((payments - paymentNum) > 0 ? (payments - paymentNum) : 1)).$plus(BigDecimal.valueOf(getSimpleInterestPerTerm())).doubleValue();
			} else if (type.equals(LoanType.COMPOUND)) {
				return BigDecimal.valueOf(amountOwing).$div(BigDecimal.valueOf((payments - paymentNum) > 0 ? (payments - paymentNum) : 1)).$plus(BigDecimal.valueOf(getCompoundInterestForCurrentTerm())).doubleValue();
			} else if (type.equals(LoanType.NO_INTEREST)) {
				return BigDecimal.valueOf(amountOwing).$div(BigDecimal.valueOf((payments - paymentNum) > 0 ? (payments - paymentNum) : 1)).doubleValue();
			}

		return 0;
	}
	
	public double getAmountForNextPaymentWithoutInterest() {
		return BigDecimal.valueOf(amountOwing).$div(BigDecimal.valueOf((payments - paymentNum) > 0 ? (payments - paymentNum) : 1)).doubleValue();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Loan [payee=");
		builder.append(payee);
		builder.append(", username=");
		builder.append(username);
		builder.append(", type=");
		builder.append(type);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", term=");
		builder.append(term);
		builder.append(", interest=");
		builder.append(interest);
		builder.append(", payments=");
		builder.append(payments);
		builder.append(", amountOwing=");
		builder.append(amountOwing);
		builder.append(", paymentNum=");
		builder.append(paymentNum);
		builder.append("]");
		return builder.toString();
	}

	public enum LoanType {

		SIMPLE, COMPOUND, NO_INTEREST

	}

}
