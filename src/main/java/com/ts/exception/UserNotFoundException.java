package com.ts.exception;

public class UserNotFoundException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6445685580711713135L;

	public UserNotFoundException(Long id){
		   super("Could not found the user with id "+ id);
	    }
}
