package com.fywss.spring.userservice.exception;

import java.util.Date;

@SuppressWarnings("unused")
public class ErrorDetails {
	private Date timestamp;
	private String message;
	private String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
}
