package com.meritamerica.assignment5.model;


// Using DecimalFormat Class, one can format a value into specific pattern using its format() method
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "transactions" })
public class BankAccount {
	
	// member variables of BankAccount class
	protected long accountNumber;
	private double balance;
	@NotNull
	private double interestRate;
	private Date openDate;
	private List<Transaction> transactions;
	
	// independent constructor
	public BankAccount() {
		this.transactions = new ArrayList<>();
		this.openDate = new Date();
		this.accountNumber = MeritBank.getNextAccountNumber();
	}
	
	BankAccount(double balance, double interestRate) {
		this(MeritBank.getNextAccountNumber(), balance, interestRate, new Date());
	}
	
	BankAccount(double balance, double interestRate, Date accountOpenedOn) {
		this(MeritBank.getNextAccountNumber(), balance, interestRate, accountOpenedOn);
	}
	
	BankAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn) {
		// assume that we don't have to check if the account number that is passed in the parameter is valid (unique)
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.interestRate = interestRate;
		this.openDate = accountOpenedOn;
		
		transactions = new ArrayList<>();
	}
	
	// don't know the purpose of using BankAccount static readFromString
	public static BankAccount readFromString(String accountData) throws ParseException {
		String[] data = accountData.split(",");
		
		// Create a date formatter
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		int accNumb = Integer.parseInt(data[0]);
		double balance = Double.parseDouble(data[1]);
		double interestRate = Double.parseDouble(data[2]);
		Date openDate = formatter.parse(data[3]);	// parse the date into date object
	    
	    return new BankAccount(accNumb, balance, interestRate, openDate);
	}
	
	public String writeToString() {
		DecimalFormat df = new DecimalFormat("#.####");
		String data = this.getAccountNumber() + "," + df.format(this.getBalance()) + "," 
				+ df.format(this.getInterestRate()) + "," + MeritBank.formatDate(this.getOpenedOn());
		return data;
	}
	
	public boolean withdraw(double amount) {
		if (amount <= 0) {
			System.out.println("The amount needs to be more than 0");
			return false;
		} else if (amount > this.balance) {
			System.out.println("The amount need to be smaller or equal to the balance");
			return false;
		} else {
			this.balance -= amount;
			return true;
		}
	}
	
	public boolean deposit(double amount) {
		if (amount <= 0) {
			System.out.println("The deposit amount needs to be larger than 0");
			return false;
		} else {
			this.balance += amount;
			return true;
		}
	}
	
	public double futureValue(int years) {
		double futureVal = this.balance * Math.pow(1 + getInterestRate(), years);
		
		return futureVal;
	}
	
	public void addTransaction(Transaction tran){
		System.out.println("Transaction thing");
		System.out.println(tran);
		transactions.add(tran);
	}
	
	public List<Transaction> getTransactions() {
		return this.transactions;
	}
	
	

	public long getAccountNumber() {
		return this.accountNumber;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public Date getOpenedOn() {
		return this.openDate;
	}
	
	public void setBalance(double balance){
		this.balance = balance;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	
}