package com.meritamerica.assignment6.model;

import java.util.Date;
import javax.persistence.Entity;

@Entity
public class SavingsAccount extends BankAccount {
	
	private static final double DEFAULT_INTEREST_RATE = .01;
	private long accountHolder;
	
	public SavingsAccount() {}

	public SavingsAccount(double openingBalance) {
		super(openingBalance, DEFAULT_INTEREST_RATE);
	}
	
	public SavingsAccount(double balance, double interestRate, long accountNumber, Date OpenedOn) {
		super(balance, interestRate, accountNumber, OpenedOn);
	}

	public SavingsAccount(BankAccount bankAccount) {
		super(bankAccount.getBalance(), bankAccount.getInterestRate(), bankAccount.getAccountNumber(), bankAccount.getOpenedOn());
	}
	
	public long getAccountHolder() {
		return this.accountHolder;
	}

	public void setAccountHolder(long number) {
		this.accountHolder = number;
	}

}