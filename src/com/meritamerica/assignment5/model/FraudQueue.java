package com.meritamerica.assignment5.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class FraudQueue{
	private LinkedList<Transaction> transactions;
	
	public FraudQueue() {
		transactions = new LinkedList<>();
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.push(transaction);
	}
	
	public Transaction getTransaction() {
		return transactions.pop();
	}
	
	public int getSize() {
		return transactions.size();
	}
}