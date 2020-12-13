package com.meritamerica.assignment5.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transaction {
	private BankAccount sourceAccount;
	private BankAccount targetAccount;
	private double amount;
	private Date date;
	
	public Transaction() {
		
	}
	
	public Transaction(BankAccount sourceAccount, BankAccount targetAccount, double amount, Date date) {
		this.sourceAccount = sourceAccount;
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.setTransactionDate(date);
	}
	
	// this constructor is used when an account is created so there is no source account
	public Transaction(BankAccount targetAccount, double amount, Date date) {
		this(null, targetAccount, amount, date);
	}
	                   
	public BankAccount getSourceAccount() {
		return this.sourceAccount;
	}
	public void setSourceAccount(BankAccount account) {
		this.sourceAccount = account;
	}
	public BankAccount getTargetAccount() {
		return this.targetAccount;
	}
	public void setTargetAccount(BankAccount account) {
		this.targetAccount = account;
	}
	public double getAmount() {
		return this.amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public java.util.Date getTransactionDate() {
		return this.date;
	}
	public void setTransactionDate(java.util.Date date) {
		this.date = date;
	}
	
	public String  writeToString() {
		String sourceAccNumb = sourceAccount != null ? String.valueOf(this.sourceAccount.getAccountNumber()) : "-1";
		String targetAccNumb = String.valueOf(this.targetAccount.getAccountNumber());
		String amount = String.valueOf(this.amount);
		String date = MeritBank.formatDate(this.date);
		
		if (this instanceof WithdrawTransaction) {
			amount = "-" + amount;
		}
		
		String data = sourceAccNumb + "," + targetAccNumb + "," + amount + "," + date + "\n";
		
		return data;
	};
	
	// -1,2,5000,01/02/2020
	public static Transaction readFromString(String transactionDataString) throws ParseException {
		String[] data = transactionDataString.split(",");
		long sourceID = Long.valueOf(data[0]);
		long targetID = Long.valueOf(data[1]);
		double amount = Double.valueOf(data[2]);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	// Create a date formatter
		Date date = formatter.parse(data[3]);
		
		BankAccount sourceAcc;
		BankAccount targetAcc = MeritBank.findAccount(targetID);
		Transaction tran = null;
		
		
		
		// if this is not a transfer transaction
		if (data[0].equals("-1")) {
			// if found that account
			if (targetAcc != null) {
				if (amount > 0) {
					tran = new DepositTransaction(targetAcc, amount, date);
					return tran;
				} else {
					tran = new WithdrawTransaction(targetAcc, 0 - amount, date);
					return tran;
				}
			}
		} else {
			sourceAcc = MeritBank.findAccount(sourceID);
			if (sourceAcc != null) {
				tran = new TransferTransaction(sourceAcc, targetAcc, amount, date);
				
			}
		}
		
		return tran;
	}

	@Override
	public String toString() {
		return "Account number is: " + targetAccount.getAccountNumber() + " " + this.amount;
	}
	
	
	
}