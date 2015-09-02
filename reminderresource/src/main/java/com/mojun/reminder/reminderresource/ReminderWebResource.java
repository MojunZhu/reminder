package com.mojun.reminder.reminderresource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderEventList;
import com.mojun.reminder.reminderdataobj.ReminderUser;
import com.mojun.reminder.reminderlib.ReminderProcessor;
import com.mojun.reminder.springsecurity.config.MongoDBauthProdiver;

@Service
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
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/images")
	public String getImage() {
		return "Got your image";
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event/{userId}")
	public Response createReminderEvent(@PathParam("userId") String userId, ReminderEvent event, @Context SecurityContext context) {
		if(userId == null || event == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("UserId can not be null").build();
		}
		if(!context.getUserPrincipal().getName().equals(userId)) {
			return Response.status(Response.Status.FORBIDDEN).entity(Response.Status.FORBIDDEN + "User id not match").build();
		}
		ReminderEvent reminderEvent = PROCESSOR.createReminderEvent(event);
		if(reminderEvent == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User :" + userId + " is not found or event:" + event.getEventId() + " is existed").build();
		}
		
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(reminderEvent), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("parsing json error" + e.getMessage()).build();			
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
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event/{userId}/{reminderEventId}")
	public Response getSingleEvent(@PathParam("userId") String userId, @PathParam("reminderEventId") String reminderEventId, @Context SecurityContext context) {
		
		if(userId == null || reminderEventId == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("userId cannot be null").build();
		}
		if(!context.getUserPrincipal().getName().equals(userId)) {
			return Response.status(Response.Status.FORBIDDEN).entity(Response.Status.FORBIDDEN + "User id not match").build();
		}
		ReminderEvent event = PROCESSOR.getSingleReminderEvent(userId, reminderEventId);
		if(event == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("event userId:" + userId + " eventId: " + reminderEventId + " is not found").build();
		}
		
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(event), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event/{userId}")
	public Response getEventList(@PathParam("userId") String userId, @Context SecurityContext context) {
		
		if(userId == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("userId cannot be null").build();
		}
		if(!context.getUserPrincipal().getName().equals(userId)) {
			return Response.status(Response.Status.FORBIDDEN).entity(Response.Status.FORBIDDEN + "User id not match").build();
		}
		ReminderEventList eventList = PROCESSOR.getReminderEventList(userId);
		if(eventList == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User :" + userId + " is not found").build();
		}
		
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(eventList), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/user/{userId}")
	public Response getUser(@PathParam("userId") String userId, @Context SecurityContext context) {
		if(userId == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("userId cannot be null").build();
		}
		if(!context.getUserPrincipal().getName().equals(userId)) {
			return Response.status(Response.Status.FORBIDDEN).entity(Response.Status.FORBIDDEN + "User id not match").build();
		}
		ReminderUser user = PROCESSOR.getReminderUser(userId);
		if(user == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User :" + userId + " is not found").build();
		}
		
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(user), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("parsing json error" + e.getMessage()).build();			
		}
	}
		
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event/{userId}/{reminderEventId}")
	public Response updateSingleEvent(@PathParam("userId") String userId, @PathParam("reminderEventId") String reminderEventId, ReminderEvent event, @Context SecurityContext context) {
		
		if(userId == null || reminderEventId == null || event == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("userId/reminderEventId/event cannot be null").build();
		}
		if(!context.getUserPrincipal().getName().equals(userId)) {
			return Response.status(Response.Status.FORBIDDEN).entity(Response.Status.FORBIDDEN + "User id not match").build();
		}
		ReminderEvent reminderEvent = PROCESSOR.updateSingleEvent(event);
		if(reminderEvent == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User/event not exist or upsert error").build();
		}
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(reminderEvent), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/user/{userId}") 
	public Response updateReminderUser(@PathParam("userId") String userId, ReminderUser user, @Context SecurityContext context) {
		
		if(userId == null || user == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("userId/user cannot be null").build();
		}
		if(!context.getUserPrincipal().getName().equals(userId)) {
			return Response.status(Response.Status.FORBIDDEN).entity(Response.Status.FORBIDDEN + "User id not match").build();
		}
		ReminderUser reminderUser = PROCESSOR.updateReminderUser(user);
		if(reminderUser == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User not exist or update error").build();
		}
		try {
			return Response.ok(OBJ_MAP.writeValueAsString(reminderUser), MediaType.APPLICATION_JSON).build();
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("parsing json error" + e.getMessage()).build();			
		}
	}
	
	@DELETE
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event/{userId}/{reminderEventId}")
	public Response deleteSingleEvent (@PathParam("userId") String userId, @PathParam("reminderEventId") String reminderEventId, @Context SecurityContext context) {
		if(userId == null || reminderEventId == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("userId/reminderEventId cannot be null").build();
		}
		if(!context.getUserPrincipal().getName().equals(userId)) {
			return Response.status(Response.Status.FORBIDDEN).entity(Response.Status.FORBIDDEN + "User id not match").build();
		}
		ReminderEvent reminderEvent = PROCESSOR.deleteSingleEvent(userId, reminderEventId);
		if(reminderEvent != null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Event not exist or delete error").build();
		}
		return Response.ok("delete complete").build();
	}
	
	@DELETE
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/user/{userId}")
	public Response deleteUser(@PathParam("userId") String userId, @Context SecurityContext context) {
		if(userId == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("userId cannot be null").build();
		}
		if(!context.getUserPrincipal().getName().equals(userId)) {
			return Response.status(Response.Status.FORBIDDEN).entity(Response.Status.FORBIDDEN + "User id not match").build();
		}
		ReminderUser reminderUser = PROCESSOR.deleteUser(userId);
		if(reminderUser != null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User not exist or delete error").build();
		}
		return Response.ok("delete complete").build();
	}
	
}
