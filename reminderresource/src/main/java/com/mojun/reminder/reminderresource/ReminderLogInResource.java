package com.mojun.reminder.reminderresource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojun.reminder.reminderdataobj.ReminderUser;
import com.mojun.reminder.reminderlib.ReminderProcessor;

@Path("/freemodel")
public class ReminderLogInResource {
	
	private static ReminderProcessor PROCESSOR = ReminderProcessor.getDefaultProcessor();
	private static ObjectMapper OBJ_MAP = new ObjectMapper();

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
	
	@GET
	@Path("/test") 
	public String getIt() {
		return "get It";
	}
	
	@POST
	@Path("/createuser") 
	public Response createUser(ReminderUser user) {
		if(user == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("UserId can not be null").build();
		}
		ReminderUser reminderUser = PROCESSOR.createReminderUser(user);
		if(reminderUser == null) {
			return Response.serverError().entity("User existed or upsert error").build();
		}
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(reminderUser), MediaType.APPLICATION_JSON).build();
		} catch (JsonParseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("parsing json error" + e.getMessage()).build();			
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("parsing json error" + e.getMessage()).build();			
		}
	}
}
