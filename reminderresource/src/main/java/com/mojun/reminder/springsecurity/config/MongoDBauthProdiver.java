package com.mojun.reminder.springsecurity.config;

import org.bson.Document;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojun.reminder.reminderdb.DBReminderDOImp;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class MongoDBauthProdiver extends AbstractUserDetailsAuthenticationProvider {
	private static String DB_NAME = "ReminderDB";
	private static String COLLECTION_NAME = "UserLogin";
	
	private static MongoClient MG_CLIENT = DBReminderDOImp.getMongoClient();
	private static MongoDatabase MG_DB = MG_CLIENT.getDatabase(DB_NAME);
	private static MongoCollection<Document> USER_LOGIN_COLLECTION = MG_DB.getCollection(COLLECTION_NAME);
	
	@Override 
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) 
			throws AuthenticationException {
    }
	
	@Override
	protected UserDetails retrieveUser(String userId, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		UserDetails loadedUser;
		Document userDocument;
		try {
			userDocument = fetchUser(userId);
			AuthticateUsers reminderUser = new ObjectMapper().readValue(userDocument.toJson(), AuthticateUsers.class);
			loadedUser = new User(reminderUser.getUserId(), reminderUser.getPassowrd(), reminderUser.getRoles());
		} catch (Exception e) {
			throw new InternalAuthenticationServiceException(e.getMessage(), e);
		}
		
		return loadedUser;
	}
	
	private Document fetchUser(String userId) {
		BasicDBObject cond = new BasicDBObject("userId", userId);
		FindIterable<Document> cursor = USER_LOGIN_COLLECTION.find(cond);
		Document result = cursor.iterator().next();
		return result;
	}
	
}
