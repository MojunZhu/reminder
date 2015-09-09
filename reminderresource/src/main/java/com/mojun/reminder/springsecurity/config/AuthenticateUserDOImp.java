package com.mojun.reminder.springsecurity.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.bson.Document;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mojun.mongodb.MongoDBBasic;
import com.mojun.reminder.exceptions.DataNotFoundException;
import com.mojun.reminder.reminderdataobj.ReminderUser;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AuthenticateUserDOImp extends MongoDBBasic{
	private static AuthenticateUserDOImp AUTH_USER_INSTANCE;
	private static MongoDatabase MG_DB;
	private static MongoCollection<Document> USER_COLLECTION;
	private static String DB_NAME = "ReminderDB";
	private static String COLLECTION_NAME = "UserLogin";
	private static ObjectMapper OBJ_MAP;
	
	private AuthenticateUserDOImp() {
		super.MONGOCLIENTINITIALIZER();
		MG_DB = MG_CLIENT.getDatabase(DB_NAME);
		USER_COLLECTION = MG_DB.getCollection(COLLECTION_NAME);
		OBJ_MAP = new ObjectMapper();
		OBJ_MAP.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
	public static AuthenticateUserDOImp getInstance() {
		if(AUTH_USER_INSTANCE == null) {
			AUTH_USER_INSTANCE = new AuthenticateUserDOImp();
		}
		return AUTH_USER_INSTANCE;
	}
	
	public AuthticateUsers getAuthenticateUserById(String userId) {
		Document userDocument = fetchUser(userId);
		AuthenticateUserDBRecord userDBRecord;
		try {
			userDBRecord = OBJ_MAP.readValue(userDocument.toJson(), AuthenticateUserDBRecord.class);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
		return new AuthticateUsers(userDBRecord);
	}
	
	public AuthticateUsers createAuthenticationRecord(ReminderUser user) {
		assert user != null;
		if(getAuthenticateUserById(user.getUserId()) == null) {
			try {
				Document document = upsertUser(user);
				AuthenticateUserDBRecord userDBRecord = OBJ_MAP.readValue(document.toJson(), AuthenticateUserDBRecord.class);
				AuthticateUsers authticateUsers = new AuthticateUsers(userDBRecord);
				return authticateUsers;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		} 
	}
	
	public Document deleteUser(String userId) {
		assert userId != null;
		Document document = null;
		try {
			removeUser(userId);
			fetchUser(userId);
		} catch (NoSuchElementException e) {
			return null;
		}
		return document;
	}
	
	private Document fetchUser(String userId) {
		Document cond = new Document("userId", userId);
		FindIterable<Document> cursor = USER_COLLECTION.find(cond);
		Document result = cursor.iterator().next();
		return result;
	}
	
	private Document upsertUser(ReminderUser user) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
		AuthticateUsers authticateUsers = new AuthticateUsers();
		authticateUsers.setUserId(user.getUserId());
		authticateUsers.setPassowrd(user.getPassword());
		List<AuthorityRoles> roles = new ArrayList<>();
		AuthorityRoles role = new AuthorityRoles("ROLE_CUSTOMER");
	    roles.add(role);
		authticateUsers.setRoles(roles);
		AuthenticateUserDBRecord newDBrecord = new AuthenticateUserDBRecord(authticateUsers);
		Document document = Document.parse(OBJ_MAP.writeValueAsString(newDBrecord));
		USER_COLLECTION.insertOne(document);
		return fetchUser(user.getUserId());
	}
	
	private void removeUser(String userId) {
		Document documentFilter = fetchUser(userId);
		USER_COLLECTION.deleteOne(documentFilter);
	} 
}
