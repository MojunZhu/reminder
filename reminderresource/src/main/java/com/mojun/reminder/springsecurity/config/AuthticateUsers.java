package com.mojun.reminder.springsecurity.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class AuthticateUsers {
	
	public String userId;
	public String passowrd;
	public List<AuthorityRoles> roles;
	
	public AuthticateUsers() {}
	
	public AuthticateUsers(AuthenticateUserDBRecord dbRecord) {
		if(dbRecord == null) {
			return;
		}
		this.userId = dbRecord.getUserId();
		this.passowrd = dbRecord.getPassowrd();
		List<AuthorityRoles> roleList = new ArrayList<>();
		String[] roleArray = dbRecord.getRoles().split(",");
		for(String str : roleArray){
			roleList.add(new AuthorityRoles(str.trim()));
		}
		this.roles = roleList;
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

	public List<AuthorityRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<AuthorityRoles> roles) {
		this.roles = roles;
	}
}
