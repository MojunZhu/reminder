package com.mojun.reminder.reminderresource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Path("/")
public class ReminderLogInResource {
	@GET
	@Path("loginfail")
	@Produces(MediaType.TEXT_PLAIN)
	public String logInFail() {
		return "LogIn failed!";
	}
	
	/*
	@POST
	@Path("/login")
	public void logIn(@QueryParam("username") String username, @QueryParam("password") String password) {
		AuthenticationManagerBuilder auth = new AuthenticationManagerBuilder(objectPostProcessor)
	}*/
	
	@GET
	@Path("/logout")
	public void logOut() {
		
	}
}
