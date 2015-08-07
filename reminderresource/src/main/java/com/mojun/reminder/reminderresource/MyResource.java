package com.mojun.reminder.reminderresource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.mojun.reminder.reminderdataobj.ReminderUser;
import com.mojun.reminder.reminderlib.ReminderProcessor;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("reminder")
public class MyResource {
	
	private static ReminderProcessor reminderProcessor = ReminderProcessor.getDefaultProcessor(); 

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    // get method for event/events
    
    // get method for user
   
    // put method for upsert user
    
    // put method for upsert event
    
    // put method for update user
    
    // put method for update event
    
    // delete user
    
    // delete event
}
