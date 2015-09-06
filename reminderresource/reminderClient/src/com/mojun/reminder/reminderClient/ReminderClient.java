package com.mojun.reminder.reminderClient;


import java.util.Map;

import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.JerseyClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;


public class ReminderClient {
	static private String BASE_URL = "http://localhost:8080/reminderresource/";
	private String csrfToken = null;
	private String sessionId1 = null;
	private String sessionId2 = null;
	//login operation
	// first get the csrf token
	@Test
	public void logIn() {
		// first get csrf token through get method
		Client client = ClientBuilder.newClient();
		WebTarget csrfTarget = client.target(BASE_URL).path("webapi/login");
		Invocation.Builder invocationBuilder = csrfTarget.request(MediaType.APPLICATION_XHTML_XML,MediaType.APPLICATION_XML,MediaType.TEXT_HTML);
		Response response = invocationBuilder.get();
		Map<String, NewCookie> cookies = response.getCookies();
		String output = response.readEntity(String.class);
		Document document = Jsoup.parse(output);
		Elements elements = document.select("input[name=_csrf]");
		csrfToken = elements.attr("value");
		
		// login with csrf token and user credentials 
		WebTarget logInTarget = client.target(BASE_URL).path("webapi/login");
		MultivaluedHashMap<String, String> valueForm = new MultivaluedHashMap<>();
		valueForm.add("username", "LogInV1.3");
		valueForm.add("password", "password");
		valueForm.add("submit", "LogIn");
		valueForm.add("_csrf", csrfToken);
		Invocation.Builder logInBuilder = logInTarget.request(MediaType.APPLICATION_XHTML_XML,MediaType.APPLICATION_XML,MediaType.TEXT_HTML);
		for(NewCookie cookie : cookies.values()) {
			logInBuilder.cookie(cookie);
		}
		Response logInResponse = logInBuilder.post(Entity.entity(valueForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		System.out.println(logInResponse.getStatus());
	}
	
	
	
	//logout operation
	
	//get operation
	
	//post operation
	
	//put operation
	
	//delete operation
}
