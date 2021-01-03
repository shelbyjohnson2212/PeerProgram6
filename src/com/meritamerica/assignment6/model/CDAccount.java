package com.meritamerica.assignment6.model;

import java.util.Date;
import javax.persistence.Entity;

@Entity
public class CDAccount extends BankAccount{
	
	private int term;
	private long accountHolder;

	public CDAccount() {
		super();
	}
	
	public CDAccount(double Balance, double interestRate, int term) {
		super(Balance, interestRate);
		this.term = term;
	}
	
	public CDAccount(double balance, double interestRate, long accountNumber, Date OpenedOn) {
		super(balance, interestRate, accountNumber, OpenedOn);
	}
	
	@Override
	public boolean withdraw(double amount) {
		return false;
	}
	
	public boolean deposit(double amount) {
		return false;
	}
	
	public int getTerm() {
		return this.term;
	}

	public long getAccountHolder() {
		return this.accountHolder;
	}

	public void setAccountHolder(long number) {
		this.accountHolder = number;
	}
}
