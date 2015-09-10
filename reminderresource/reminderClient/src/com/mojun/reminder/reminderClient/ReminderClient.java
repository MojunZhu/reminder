package com.mojun.reminder.reminderClient;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderUser;


public class ReminderClient {
	static private String BASE_URL = "http://localhost:8080/reminderresource/";
	//private String csrfToken = null;
	private Map<String, NewCookie> cookies = null;
	private Client client = ClientBuilder.newClient();
	private ObjectMapper OBJ_MAP = new ObjectMapper();

	//login operation
	// first get the csrf token
	
	public int logIn(String userName, String passWord) {
		// first get csrf token through get method
		/*WebTarget csrfTarget = client.target(BASE_URL).path("webapi/login");
		Invocation.Builder invocationBuilder = csrfTarget.request(MediaType.APPLICATION_XHTML_XML,MediaType.APPLICATION_XML,MediaType.TEXT_HTML);
		Response response = invocationBuilder.get();
		updateCookies(response);
		getSetCSRFToken(response);*/
		
		// login with csrf token and user credentials 
		WebTarget logInTarget = client.target(BASE_URL).path("webapi/login");
		logInTarget.property(ClientProperties.FOLLOW_REDIRECTS, false);
		MultivaluedHashMap<String, String> valueForm = new MultivaluedHashMap<>();
		valueForm.add("username", userName);
		valueForm.add("password", passWord);
		valueForm.add("submit", "LogIn");
		//valueForm.add("_csrf", csrfToken);
		Invocation.Builder logInBuilder = logInTarget.request(MediaType.APPLICATION_XHTML_XML,MediaType.APPLICATION_XML,MediaType.TEXT_HTML);
		/*for(NewCookie cookie : cookies.values()) {
			logInBuilder.cookie(cookie);
		}*/
		Response logInResponse = logInBuilder.post(Entity.entity(valueForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		if(logInResponse.getStatus() == 302) {
			updateCookies(logInResponse);
			//getSetCSRFToken(logInResponse);
		} 
		return logInResponse.getStatus();
		
	}
	
	//logout operation
	public int logOut() {
		WebTarget logOutTarget = client.target(BASE_URL).path("webapi/logout");
		Invocation.Builder logOutBuilder = logOutTarget.request(MediaType.APPLICATION_XHTML_XML,MediaType.APPLICATION_XML,MediaType.TEXT_HTML);
		for(NewCookie cookie : cookies.values()) {
			logOutBuilder.cookie(cookie);
		}
		Response logOutResponse = logOutBuilder.get();
		client.close();
		return logOutResponse.getStatus();
	}
	
	//get operation
	public ReminderUser getReminderUser(String userId) {
		WebTarget getUserTarget = client.target(BASE_URL).path("webapi/reminder/user/").path(userId);
		Invocation.Builder getUserBuilder = getUserTarget.request(MediaType.APPLICATION_JSON);
		for(NewCookie cookie : cookies.values()) {
			getUserBuilder.cookie(cookie);
		}
		Response getUserResponse = getUserBuilder.get();
		getUserResponse.bufferEntity();
		String response = getUserResponse.readEntity(String.class);
		ReminderUser user = null;
		try {
			user = OBJ_MAP.readValue(response, ReminderUser.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
	
	public List<ReminderEvent> getEventList(String userId) {
		WebTarget getEventTarget = client.target(BASE_URL).path("webapi/reminder/event/").path(userId);
		Invocation.Builder getEventListBuilder = getEventTarget.request(MediaType.APPLICATION_JSON);
		for(NewCookie cookie : cookies.values()) {
			getEventListBuilder.cookie(cookie);
		}
		Response getEventListResponse = getEventListBuilder.get();
		getEventListResponse.bufferEntity();
		String response = getEventListResponse.readEntity(String.class);
		List<ReminderEvent> eventList = new ArrayList<>();
		try {
			eventList = OBJ_MAP.readValue(response, TypeFactory.defaultInstance().constructCollectionType(List.class, ReminderEvent.class));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return eventList;
	}
		
	//post operation
	public int createUser(ReminderUser user) {
		WebTarget createUserTarget = client.target(BASE_URL).path("webapi/freemodel/createuser/");
		Invocation.Builder createUserBuilder = createUserTarget.request(MediaType.APPLICATION_JSON);
		for(NewCookie cookie : cookies.values()) {
			createUserBuilder.cookie(cookie);
		}
		String request;
		try {
			request = OBJ_MAP.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return 400;
		}
		Response createUserResponse = createUserBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));
		return createUserResponse.getStatus();
	}
	
	public int updateUser(ReminderUser user) {
		WebTarget updateUserTarget = client.target(BASE_URL).path("webapi/reminder/user").path(user.getUserId());
		Invocation.Builder updateUserBuilder = updateUserTarget.request(MediaType.APPLICATION_JSON);
		for(NewCookie cookie : cookies.values()) {
			updateUserBuilder.cookie(cookie);
		}
		String request;
		try {
			request = OBJ_MAP.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return 400;
		}
		Response updateUserResponse = updateUserBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));
		
		// redirect and get csrf token
		/*WebTarget redirectTarget = client.target(BASE_URL).path("webapi/reminder/getit");
		Invocation.Builder redirectBuilder = redirectTarget.request(MediaType.APPLICATION_XHTML_XML,MediaType.APPLICATION_XML,MediaType.TEXT_HTML);
		Response redirectResponse = redirectBuilder.get();
		if(redirectResponse.getStatus() == 200) {
			getSetCSRFToken(redirectResponse);
		} else {
			return redirectResponse.getStatus();
		}*/
		return updateUserResponse.getStatus();
	}
	
	public ReminderEvent createReminderEvent(ReminderEvent event) {
		WebTarget createEventTarget = client.target(BASE_URL).path("/event/").path(event.getUserId());
		Invocation.Builder createEventBuilder = createEventTarget.request(MediaType.APPLICATION_JSON);
		for(NewCookie cookie : cookies.values()) {
			createEventBuilder.cookie(cookie);
		}
		String request;
		try {
			request = OBJ_MAP.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		Response createEventResponse = createEventBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));
		ReminderEvent newEvent;
		try {
			newEvent = OBJ_MAP.readValue(createEventResponse.readEntity(String.class), ReminderEvent.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return newEvent;
	}
	
	public ReminderEvent updateReminderEvent(ReminderEvent event) {
		WebTarget createEventTarget = client.target(BASE_URL).path("/event/").path(event.getUserId()).path(event.getEventId());
		Invocation.Builder createEventBuilder = createEventTarget.request(MediaType.APPLICATION_JSON);
		for(NewCookie cookie : cookies.values()) {
			createEventBuilder.cookie(cookie);
		}
		String request;
		try {
			request = OBJ_MAP.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		Response createEventResponse = createEventBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));
		ReminderEvent newEvent;
		try {
			newEvent = OBJ_MAP.readValue(createEventResponse.readEntity(String.class), ReminderEvent.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return newEvent;
	}
		
	//delete operation
	
	public int deleteUser(ReminderUser user) {
		WebTarget deleteUserTarget = client.target(BASE_URL).path("/user/").path(user.getUserId());
		Invocation.Builder deleteUserBuilder = deleteUserTarget.request(MediaType.APPLICATION_JSON);
		for(NewCookie cookie : cookies.values()) {
			deleteUserBuilder.cookie(cookie);
		}
		Response deleteUserResponse = deleteUserBuilder.delete();
		return deleteUserResponse.getStatus();
	} 
	
	public int deleteEvent(ReminderEvent event) {
		WebTarget deleteEventTarget = client.target(BASE_URL).path("/event/").path(event.getUserId()).path(event.getEventId());
		Invocation.Builder deleteEventBuilder = deleteEventTarget.request(MediaType.APPLICATION_JSON);
		for(NewCookie cookie : cookies.values()) {
			deleteEventBuilder.cookie(cookie);
		}
		Response deleteEventResponse = deleteEventBuilder.delete();
		return deleteEventResponse.getStatus();
	}
	
	/*private void getSetCSRFToken(Response response) {
		if(response == null) {
			return;
		} else {
			MultivaluedMap<String, String> headers = response.getStringHeaders();
			List<String> csrfList = headers.get("X-CSRF-TOKEN");
			csrfToken = csrfList.get(0);
		}
	}*/
	
	private void updateCookies(Response response) {
		if(response == null) {
			return;
		} else {
			cookies = response.getCookies();
		}
	}
	
}
