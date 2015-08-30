package com.mojun.reminder.springsecurity.config;

import java.io.IOException;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mojun.mongodb.MongoDBBasic;
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
	
	public AuthticateUsers getAuthenticateUserById(String userId) throws JsonParseException, JsonMappingException, IOException {
		Document userDocument = fetchUser(userId);
		AuthenticateUserDBRecord userDBRecord = OBJ_MAP.readValue(userDocument.toJson(), AuthenticateUserDBRecord.class);
		return new AuthticateUsers(userDBRecord);
	}
	
	private Document fetchUser(String userId) {
		Document cond = new Document("userId", userId);
		FindIterable<Document> cursor = USER_COLLECTION.find(cond);
		Document result = cursor.iterator().next();
		return result;
	}
}
