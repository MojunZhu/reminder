package com.mojun.reminder.springsecurity.config;

public class PreAuthUserIdCheck {
	static boolean checkUserId(String passedUserId, String urlUserId) {
		if(passedUserId.equals(urlUserId))
			return true;
		else return false;
	}
}
