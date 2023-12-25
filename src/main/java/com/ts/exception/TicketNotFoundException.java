package com.ts.exception;

public class TicketNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1883300446494709944L;

	public TicketNotFoundException(Long id) {
		super("Could not found the ticket with id " + id);
	}
}
