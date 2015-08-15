package com.mojun.reminder.springsecurity.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthticateUsers {
	
	public String userId;
	public String passowrd;
	public List<AuthorityRoles> roles;
	
	public AuthticateUsers() {}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassowrd() {
		return passowrd;
	}

	public void setPassowrd(String passowrd) {
		this.passowrd = passowrd;
	}

	public List<AuthorityRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<AuthorityRoles> roles) {
		this.roles = roles;
	}
}
