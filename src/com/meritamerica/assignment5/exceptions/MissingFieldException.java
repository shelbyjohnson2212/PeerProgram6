package com.meritamerica.assignment5.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingFieldException extends Exception{
	public MissingFieldException(String s) {
		super("Not Found Balance Exception");
	}
}
