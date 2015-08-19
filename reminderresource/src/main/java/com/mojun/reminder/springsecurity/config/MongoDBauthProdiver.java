package com.mojun.reminder.springsecurity.config;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class MongoDBauthProdiver extends AbstractUserDetailsAuthenticationProvider {
	private AuthenticateUserDOImp userDAL = AuthenticateUserDOImp.getInstance();
	
	@Override 
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) 
			throws AuthenticationException {
		if(userDetails == null || authentication == null) {
			throw new ReminderAuthenticationException("cannot retrive user information");
		}
		if(!userDetails.getPassword().equals(authentication.getCredentials().toString())){
			throw new ReminderAuthenticationException("Wrong Credentials");
		}
    }
	
	@Override
	protected UserDetails retrieveUser(String userId, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		UserDetails loadedUser;
		try {
			AuthticateUsers reminderUser = userDAL.getAuthenticateUserById(userId);
			loadedUser = new User(reminderUser.getUserId(), reminderUser.getPassowrd(), reminderUser.getRoles());
		} catch (Exception e) {
			throw new InternalAuthenticationServiceException(e.getMessage(), e);
		}
		
		return loadedUser;
	}
}
