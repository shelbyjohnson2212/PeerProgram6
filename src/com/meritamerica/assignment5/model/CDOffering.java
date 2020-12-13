package com.meritamerica.assignment5.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

// This CDoffering class gets the term and interest rate by two getter methods
public class CDOffering {
	@NotNull
	@Min(value = 1, message = " term must be >=1")
	private int term;
	@NotNull
	private double interestRate;
	
	public CDOffering() {
		
	}
	
	static CDOffering readFromString(String cdOfferingDataString) {
		String[] data = cdOfferingDataString.split(",");
		int term = Integer.parseInt(data[0]);
		double interestRate = Double.parseDouble(data[1]);
		
		return new CDOffering(term, interestRate);
	}
	
	// constructor and parameters
	public CDOffering(int term, double interestRate) {
		this.term = term;
		this.interestRate = interestRate;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public int getTerm() {
		return term;
	}
	
	public void setTerm(int years) {
		this.term = years;
	}
	
}
