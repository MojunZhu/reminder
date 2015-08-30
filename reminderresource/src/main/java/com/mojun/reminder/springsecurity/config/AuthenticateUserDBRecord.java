package com.mojun.reminder.springsecurity.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateUserDBRecord {
	public String userId;
	public String passowrd;
	public String roles;
	
	public AuthenticateUserDBRecord() {}
	
	public AuthenticateUserDBRecord(AuthticateUsers user) {
		this.userId = user.getUserId();
		this.passowrd = user.getPassowrd();
		String rolesStr = null;
		for(AuthorityRoles role : user.getRoles()) {
			rolesStr = rolesStr + "," + role.getAuthority();
		}
		this.roles = rolesStr;
	}
	
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

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
