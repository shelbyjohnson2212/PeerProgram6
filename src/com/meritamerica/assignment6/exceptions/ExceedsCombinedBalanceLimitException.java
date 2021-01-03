package com.meritamerica.assignment6.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceedsCombinedBalanceLimitException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ExceedsCombinedBalanceLimitException(String message) {
		super(message);	
	}
}