package com.meritamerica.assignment6.model;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class CheckingAccount extends BankAccount {

	static final double DEFAULT_INTEREST_RATE = .0001;
	private long accountHolder;

	public CheckingAccount() {
	}

	public CheckingAccount(double openingBalance) {
		super(openingBalance, DEFAULT_INTEREST_RATE);
	}
	
	public CheckingAccount(double balance, double interestRate, long accountNumber, Date OpenedOn) {
		super(balance, interestRate, accountNumber, OpenedOn);
	}

	public CheckingAccount(BankAccount bankAccount) {
		super(bankAccount.getBalance(), bankAccount.getInterestRate(), bankAccount.getAccountNumber(), bankAccount.getOpenedOn());

	}

	public long getAccountHolder() {
		return this.accountHolder;
	}

	public void setAccountHolder(long number) {
		this.accountHolder = number;
	}
}