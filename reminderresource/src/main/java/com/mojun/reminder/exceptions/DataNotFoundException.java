package com.mojun.reminder.exceptions;

public class DataNotFoundException extends Exception{
	
	public DataNotFoundException(String message) {
		super(message);
	}
	
	public DataNotFoundException(String message, Throwable e) {
		super(message, e);
	}
}
