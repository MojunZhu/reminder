package com.mojun.reminder.reminderdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.commons.collections.IteratorUtils;
import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mojun.reminder.exceptions.DataNotFoundException;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderUser;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBReminderDOImp implements DBReminderDAO {
		private static DBReminderDOImp DB_REMINDER_DOIMPL = null;
		private static String DB_NAME = "ReminderDB";
		private static MongoClient MG_CLIENT;
		private static MongoDatabase MG_DB;
		private static MongoCollection<Document> EVENT_COLLECTION;
		private static MongoCollection<Document> USER_COLLECTION;
		private static ObjectMapper OBJ_MAP;
		
		private DBReminderDOImp() {
			MG_CLIENT = new MongoClient("127.0.0.1", 27017);
			MG_DB = MG_CLIENT.getDatabase("ReminderDB");
			EVENT_COLLECTION = MG_DB.getCollection("EventCollection");
			USER_COLLECTION = MG_DB.getCollection("UserCollection");
			OBJ_MAP = new ObjectMapper();
			OBJ_MAP.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		}
	
		public static DBReminderDOImp getInstance() {
			if (DB_REMINDER_DOIMPL == null) {
				return new DBReminderDOImp();
			}
			else {
				return DB_REMINDER_DOIMPL;
			}		 
		}
		
		public ReminderUser upsertDBReminderUser(ReminderUser user) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			assert user != null;
			if(getUserById(user.getUserId()) == null) {
				return upsertUser(user);
			} else {
				return null;
			}
		}
		
		public ReminderEvent getEventById(String userId, String reminderEventId) throws IOException {
			assert userId != null;
			assert reminderEventId != null;
			Document document = null;
			try {
				document = fetchEvent(userId, reminderEventId);
			} catch (NoSuchElementException e) {
				return null;
			}
			ReminderEvent reminderEvent = new ObjectMapper().readValue(document.toJson(), ReminderEvent.class);
			return reminderEvent;
		}
		
		public ReminderUser getUserById(String userId) throws JsonParseException, JsonMappingException, IOException {
			assert userId != null;
			Document document = null;
			try {
				document = fetchUser(userId);
			} catch (NoSuchElementException e) {
				return null;
			}
			ReminderUser reminderUser = new ObjectMapper().readValue(document.toJson(), ReminderUser.class);
			return reminderUser;
		}
		
		public List<ReminderEvent> getEventsById(String userId) throws DataNotFoundException, JsonParseException, JsonMappingException, IOException {
			assert userId != null;
			List<Document> documents = null;
			try {
				documents = fetchEvents(userId);
			} catch (Exception e) {
				return null;
			}
			List<ReminderEvent> result = new ArrayList<>();
			for(Document document : documents) {
				result.add(new ObjectMapper().readValue(document.toJson(), ReminderEvent.class));
			}
			return result;
		}
		
		public ReminderEvent upsertDBReminderEvent(ReminderEvent reminderEvent) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			assert reminderEvent != null;	
			if(getUserById(reminderEvent.getUserId()) != null && getEventById(reminderEvent.getUserId(), reminderEvent.getEventId())== null) {
				return upsertEvent(reminderEvent);
			} else {
				return null;
			}
		}
		
		public ReminderEvent deleteReminderEvent(String userId, String reminderEventId) throws IOException {
			assert userId != null;
			assert reminderEventId != null;
			removeEvent(userId, reminderEventId);
			
			return getEventById(userId, reminderEventId);
		}
		
		public ReminderUser deleteReminderUser(String userId) throws JsonParseException, JsonMappingException, IOException {
			assert userId != null;
			removeUser(userId);
			
			return getUserById(userId);
		}
		
		public ReminderUser updateReminderUser(ReminderUser user) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			assert user != null;
			return updateUser(user);
		}
		
		public ReminderEvent updateReminderEvent(ReminderEvent reminderEvent) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			assert reminderEvent != null;
			return updateEvent(reminderEvent);
		}
		
		public void closeDBClient() {
			MG_CLIENT.close();
		}
		
		private Document fetchEvent(String userId, String reminderEventId) {
			BasicDBObject cond = new BasicDBObject("userId", userId);
			cond.append("eventId", reminderEventId);
			FindIterable<Document> cursor = EVENT_COLLECTION.find(cond);
			Document result = cursor.iterator().next();
			return result;
		}
		
		private Document fetchUser(String userId) {
			BasicDBObject cond = new BasicDBObject("userId", userId);
			FindIterable<Document> cursor = USER_COLLECTION.find(cond);
			Document result = cursor.iterator().next();
			return result;
		}
		
		@SuppressWarnings("unchecked")
		private List<Document> fetchEvents(String userId) {
			BasicDBObject cond = new BasicDBObject("userId", userId);
			FindIterable<Document> cursor = EVENT_COLLECTION.find(cond);
			//how to fix this?
			List<Document> result = IteratorUtils.toList(cursor.iterator());
			return result;
		}
		
		private ReminderUser upsertUser(ReminderUser user) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			/*DocumentDBUser dbUser = (DocumentDBUser)user;
			dbUser.set_id(dbUser.getUserId());
			Document document = Document.parse(JSON.serialize(user));
			USER_COLLECTION.insertOne(document);
			return getUserById(user.getUserId());*/
			Document document = Document.parse(OBJ_MAP.writeValueAsString(user));
			USER_COLLECTION.insertOne(document);
			return getUserById(user.getUserId());
		}
		
		private ReminderEvent upsertEvent(ReminderEvent event) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			/*DocumentDBEvent dbEvent = (DocumentDBEvent) event;
			dbEvent.set_id(dbEvent.getUserID() + dbEvent.getEventID());
			Document document = Document.parse(JSON.serialize(dbEvent));
			EVENT_COLLECTION.insertOne(document);
			return getEventById(event.getUserID(), event.getEventID());*/
			Document document = Document.parse(OBJ_MAP.writeValueAsString(event));
			EVENT_COLLECTION.insertOne(document);
			return getEventById(event.getUserId(), event.getEventId());
		}
		
		private ReminderUser updateUser(ReminderUser user) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			Document filterDocument = new Document("userId", user.getUserId());
			Document updateDocument = Document.parse(OBJ_MAP.writeValueAsString(user));
			USER_COLLECTION.replaceOne(filterDocument, updateDocument);
			return getUserById(user.getUserId());
		}
		
		private ReminderEvent updateEvent(ReminderEvent event) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			Document filterDocument = new Document("userId", event.getUserId());
			filterDocument.append("eventId", event.getEventId());
			Document updateDocument = Document.parse(OBJ_MAP.writeValueAsString(event));
			EVENT_COLLECTION.replaceOne(filterDocument, updateDocument);
			return getEventById(event.getUserId(), event.getEventId());
		}
		
		private void removeUser(String userId) {
			Document documentFilter = fetchUser(userId);
			USER_COLLECTION.deleteOne(documentFilter);
			Document eventFilter = new Document("userId", userId);
			EVENT_COLLECTION.deleteMany(eventFilter);
		}
		
		private void removeEvent(String userId, String eventId) {
			Map<String, Object> filterMap = new HashMap<>();
			filterMap.put("userId", userId);
			filterMap.put("eventId", eventId);
			EVENT_COLLECTION.deleteOne(new Document(filterMap));
		}
}
