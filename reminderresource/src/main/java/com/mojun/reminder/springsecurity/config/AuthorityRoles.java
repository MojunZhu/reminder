package com.mojun.reminder.springsecurity.config;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityRoles implements GrantedAuthority{
	private String role;
	
	public AuthorityRoles() {
		// TODO Auto-generated constructor stub
	}
	
	public AuthorityRoles(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role;
	}
}
