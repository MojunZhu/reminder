package com.mojun.reminder.reminderresource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import reminderlib.ReminderProcessor;

import com.mojun.reminder.reminderdataobj.CreateReminderEventRequest;
import com.mojun.reminder.reminderdataobj.CreateReminderUserRequest;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderEventList;
import com.mojun.reminder.reminderdataobj.ReminderUser;

public class ReminderWebResource {
	/*@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/reminderevent/{userId}")
	public ReminderEvent createReminderEvent(@PathParam("userId") String userId, CreateReminderEventRequest request) {
		
		ReminderEvent reminderEvent = getProcessor().createReminderEvent(userId, request);
		return reminderEvent;
	}
	
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/reminderevent")
	public ReminderUser createReminderUser(CreateReminderUserRequest request) {
		
		ReminderUser reminderUser = getProcessor().createReminderUser(request);
		return reminderUser;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/reminderevent/{userId}/{reminderEventId}")
	public ReminderEvent getSingleEvent(@PathParam("userId") String userId, @PathParam("reminderEventId") String reminderEventId) {
		
		ReminderEvent reminderEvent = getProcessor().getSingleReminderEvent(userId, reminderEventId);
		return reminderEvent;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/reminderevent/{userId}")
	public ReminderEventList getEventList(@PathParam("userId") String userId) {
		
		ReminderEventList reminderEventList = getProcessor().getReminderEventList(userId);
		return reminderEventList;
	}
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/reminderevent/{userId}/{reminderEventId}")
	public ReminderEvent updateSingleEvent(@PathParam("userId") String userId, @PathParam("reminderEventId") String reminderEventId, CreateReminderEventRequest request) {
		
		ReminderEvent reminderEvent = getProcessor().updateSingleEvent(userId, reminderEventId, request);
		return reminderEvent;
	}
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/reminderevent/{userId}") 
	public ReminderUser updateReminderUser(@PathParam("userId") String userId, CreateReminderUserRequest request) {
		
		ReminderUser reminderUser = getProcessor().updateReminderUser(userId, request);
		return reminderUser;
	}
	
	@DELETE
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/reminderevent/{userId}/{reminderEventId}")
	public void deleteSingleEvent (@PathParam("userId") String userId, @PathParam("reminderEventId") String reminderEventId) {
		
		getProcessor().deleteSingleEvent(userId, reminderEventId);
	}
	
	@DELETE
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/reminderevent/{userId}")
	public void deleteUser(@PathParam("userUd") String userId) {
		
		getProcessor().deleteUser(userId);
	}
	
	private static ReminderProcessor getProcessor() {
		return ReminderProcessor.getDefaultProcessor();
	}*/
}
