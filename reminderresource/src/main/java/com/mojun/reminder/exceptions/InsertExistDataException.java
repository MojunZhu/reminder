package com.mojun.reminder.exceptions;

public class InsertExistDataException extends Exception{
	
	public InsertExistDataException(String message) {
		super(message);
	}
	
	public InsertExistDataException(String message, Throwable e) {
		super(message, e);
	}
}
