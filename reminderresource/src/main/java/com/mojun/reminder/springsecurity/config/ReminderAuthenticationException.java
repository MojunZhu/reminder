package com.mojun.reminder.springsecurity.config;

import org.springframework.security.core.AuthenticationException;

public class ReminderAuthenticationException extends AuthenticationException{

	public ReminderAuthenticationException(String msg, Throwable t) {
		super(msg, t);
		// TODO Auto-generated constructor stub
	}
	
	public ReminderAuthenticationException(String msg) {
		super(msg);
		// TODO 
	}

}
