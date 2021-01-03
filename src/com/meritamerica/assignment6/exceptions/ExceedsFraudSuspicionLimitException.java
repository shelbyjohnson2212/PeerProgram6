package com.meritamerica.assignment6.exceptions;

public class ExceedsFraudSuspicionLimitException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExceedsFraudSuspicionLimitException() {
		super("Transaction value exceeds $1000.00 and needs to be reviewed by a fraud pervention team member");
	}
}