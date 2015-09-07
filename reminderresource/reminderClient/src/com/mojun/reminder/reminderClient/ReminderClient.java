package com.mojun.reminder.reminderClient;


import java.io.IOException;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojun.reminder.reminderdataobj.ReminderUser;


public class ReminderClient {
	static private String BASE_URL = "http://localhost:8080/reminderresource/";
	private String csrfToken = null;
	private Map<String, NewCookie> cookies = null;
	private Client client = ClientBuilder.newClient();
	private ObjectMapper OBJ_MAP = new ObjectMapper();

	//login operation
	// first get the csrf token
	
	public int logIn(String userName, String passWord) {
		// first get csrf token through get method
		WebTarget csrfTarget = client.target(BASE_URL).path("webapi/login");
		Invocation.Builder invocationBuilder = csrfTarget.request(MediaType.APPLICATION_XHTML_XML,MediaType.APPLICATION_XML,MediaType.TEXT_HTML);
		Response response = invocationBuilder.get();
		updateCookies(response);
		getSetCSRFToken(response);
		
		// login with csrf token and user credentials 
		WebTarget logInTarget = client.target(BASE_URL).path("webapi/login");
		logInTarget.property(ClientProperties.FOLLOW_REDIRECTS, false);
		MultivaluedHashMap<String, String> valueForm = new MultivaluedHashMap<>();
		valueForm.add("username", userName);
		valueForm.add("password", passWord);
		valueForm.add("submit", "LogIn");
		valueForm.add("_csrf", csrfToken);
		Invocation.Builder logInBuilder = logInTarget.request(MediaType.APPLICATION_XHTML_XML,MediaType.APPLICATION_XML,MediaType.TEXT_HTML);
		for(NewCookie cookie : cookies.values()) {
			logInBuilder.cookie(cookie);
		}
		Response logInResponse = logInBuilder.post(Entity.entity(valueForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		if(logInResponse.getStatus() == 302) {
			updateCookies(logInResponse);
		} else {
			return logInResponse.getStatus();
		}
		WebTarget redirectTarget = client.target(BASE_URL).path("webapi/reminder/getit");
		Invocation.Builder redirectBuilder = redirectTarget.request(MediaType.APPLICATION_XHTML_XML,MediaType.APPLICATION_XML,MediaType.TEXT_HTML);
		Response redirectResponse = redirectBuilder.get();
		if(redirectResponse.getStatus() == 200) {
			getSetCSRFToken(redirectResponse);
		} else {
			return redirectResponse.getStatus();
		}
		return redirectResponse.getStatus();
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
		
	//post operation
		
	//delete operation
	
	private void getSetCSRFToken(Response response) {
		if(response == null) {
			return;
		} else {
			String output = response.readEntity(String.class);
			Document document = Jsoup.parse(output);
			Elements elements = document.select("input[name=_csrf]");
			csrfToken = elements.attr("value");
		}
	}
	
	private void updateCookies(Response response) {
		if(response == null) {
			return;
		} else {
			cookies = response.getCookies();
		}
	}
	
}
