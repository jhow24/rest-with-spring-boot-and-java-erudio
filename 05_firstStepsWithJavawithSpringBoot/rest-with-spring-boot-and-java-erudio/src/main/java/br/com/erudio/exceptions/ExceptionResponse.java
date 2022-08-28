package br.com.erudio.exceptions;

import java.io.Serializable;
import java.sql.Date;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date timestap;
	private String message;
	private String details;
	
	public ExceptionResponse(Date timestap, String message, String details) {
		this.timestap = timestap;
		this.message = message;
		this.details = details;
	}

	public Date getTimestap() {
		return timestap;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
	
}
