package com.mojun.reminder.reminderresource;

import javax.net.ssl.SSLEngineResult.Status;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojun.reminder.reminderdataobj.CreateReminderEventRequest;
import com.mojun.reminder.reminderdataobj.CreateReminderUserRequest;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderEventList;
import com.mojun.reminder.reminderdataobj.ReminderUser;
import com.mojun.reminder.reminderlib.ReminderProcessor;

@Path("reminder")
public class ReminderWebResource {
	private static ReminderProcessor PROCESSOR = ReminderProcessor.getDefaultProcessor();
	private static ObjectMapper OBJ_MAP = new ObjectMapper();
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	@Path("/getit")
    public String getIt() {
        return "Got it!";
    }
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event/{userId}")
	public Response createReminderEvent(@PathParam("userId") String userId, ReminderEvent event) {
		if(userId == null || event == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("UserId can not be null").build();
		}
		ReminderEvent reminderEvent = PROCESSOR.createReminderEvent(event);
		if(reminderEvent == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User :" + userId + " is not found or event:" + event.getEventId() + " is existed").build();
		}
		
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(reminderEvent), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.serverError().entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/user")
	public Response createReminderUser(ReminderUser	user) {
		
		if(user == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("UserId can not be null").build();
		}
		ReminderUser reminderUser = PROCESSOR.createReminderUser(user);
		if(reminderUser == null) {
			return Response.serverError().entity("User existed or upsert error").build();
		}
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(reminderUser), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.serverError().entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event/{userId}/{reminderEventId}")
	public Response getSingleEvent(@PathParam("userId") String userId, @PathParam("reminderEventId") String reminderEventId) {
		
		if(userId == null || reminderEventId == null) {
			return Response.serverError().entity("userId cannot be null").build();
		}
		ReminderEvent event = PROCESSOR.getSingleReminderEvent(userId, reminderEventId);
		if(event == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("event userId:" + userId + " eventId: " + reminderEventId + " is not found").build();
		}
		
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(event), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.serverError().entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event/{userId}")
	public Response getEventList(@PathParam("userId") String userId) {
		
		if(userId == null) {
			return Response.serverError().entity("userId cannot be null").build();
		}
		ReminderEventList eventList = PROCESSOR.getReminderEventList(userId);
		if(eventList == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User :" + userId + " is not found").build();
		}
		
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(eventList), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.serverError().entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/user/{userId}")
	public Response getUser(@PathParam("userId") String userId) {
		if(userId == null) {
			return Response.serverError().entity("userId cannot be null").build();
		}
		ReminderUser user = PROCESSOR.getReminderUser(userId);
		if(user == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User :" + userId + " is not found").build();
		}
		
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(user), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.serverError().entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event/{userId}/{reminderEventId}")
	public Response updateSingleEvent(@PathParam("userId") String userId, @PathParam("reminderEventId") String reminderEventId, ReminderEvent event) {
		
		if(userId == null || reminderEventId == null || event == null) {
			return Response.serverError().entity("userId/reminderEventId/event cannot be null").build();
		}
		ReminderEvent reminderEvent = PROCESSOR.updateSingleEvent(event);
		if(reminderEvent == null) {
			return Response.serverError().entity("User/event not exist or upsert error").build();
		}
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(reminderEvent), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.serverError().entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/user/{userId}") 
	public Response updateReminderUser(@PathParam("userId") String userId, ReminderUser user) {
		
		if(userId == null || user == null) {
			return Response.serverError().entity("userId/user cannot be null").build();
		}
		ReminderUser reminderUser = PROCESSOR.updateReminderUser(user);
		if(reminderUser == null) {
			return Response.serverError().entity("User not exist or update error").build();
		}
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(reminderUser), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.serverError().entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	@DELETE
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event/{userId}/{reminderEventId}")
	public Response deleteSingleEvent (@PathParam("userId") String userId, @PathParam("reminderEventId") String reminderEventId) {
		if(userId == null || reminderEventId == null) {
			return Response.serverError().entity("userId/reminderEventId cannot be null").build();
		}
		ReminderEvent reminderEvent = PROCESSOR.deleteSingleEvent(userId, reminderEventId);
		if(reminderEvent != null) {
			return Response.serverError().entity("Event not exist or delete error").build();
		}
		return Response.ok("delete complete").build();
	}
	
	@DELETE
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/user/{userId}")
	public Response deleteUser(@PathParam("userId") String userId) {
		if(userId == null) {
			return Response.serverError().entity("userId cannot be null").build();
		}
		ReminderUser reminderUser = PROCESSOR.deleteUser(userId);
		if(reminderUser != null) {
			return Response.serverError().entity("User not exist or delete error").build();
		}
		return Response.ok("delete complete").build();
	}
	
	
}
