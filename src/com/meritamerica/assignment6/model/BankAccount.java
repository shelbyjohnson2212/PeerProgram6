package com.meritamerica.assignment6.model;

import com.meritamerica.assignment6.exceptions.*;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@MappedSuperclass
@Table(name = "bankAccounts")
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long accountNumber;

	@Min(0)
	private double balance;
	private double interestRate;
	private Date OpenedOn;
	
	public BankAccount() {
		this.OpenedOn = new Date();
	}

	public BankAccount(double balance, double interestRate) {
		this.balance = balance;
		this.interestRate = interestRate;
	}
	
	public BankAccount(double balance, double interestRate, long accountNumber, Date accountOpenedOn) {
		this.balance = balance;
		this.interestRate = interestRate;
	}

	public boolean withdraw(double amount) throws ExceedsAvailableBalanceException, NotFoundException {
		if (amount > this.balance) {
			throw new ExceedsAvailableBalanceException("Withdraw Amount Exceeds Available Balance");
		}
		if (amount < 0) {
			throw new NotFoundException("Unable To Withdraw A Negative Value");
		}
		this.balance = this.balance - amount;
		return true;
	}

	public boolean deposit(double amount) throws NegativeAmountException{
		if (amount < 0)
			throw new NegativeAmountException("Unable To Deposit A Negative Value");
		this.balance = this.balance + amount;
		return true;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long Number) {
		this.accountNumber = Number;
	}

	public Date getOpenedOn() {
		return OpenedOn;
	}

	public void setOpenedOn(Date OpenedOn) {
		this.OpenedOn = OpenedOn;
	}
}