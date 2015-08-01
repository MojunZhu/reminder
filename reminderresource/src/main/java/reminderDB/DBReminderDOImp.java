package reminderDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.bson.Document;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mojun.reminder.exceptions.DataNotFoundException;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderUser;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class DBReminderDOImp implements DBReminderDAO {
		private static DBReminderDOImp DB_REMINDER_DOIMPL = null;
		private static String DB_NAME = "ReminderDB";
		private static MongoClient MG_CLIENT;
		private static MongoDatabase MG_DB;
		private static MongoCollection<Document> EVENT_COLLECTION;
		private static MongoCollection<Document> USER_COLLECTION;
		
		private DBReminderDOImp() {
			MG_CLIENT = new MongoClient("127.0.0.1", 27017);
			MG_DB = MG_CLIENT.getDatabase("ReminderDB");
			EVENT_COLLECTION = MG_DB.getCollection("EventCollection");
			USER_COLLECTION = MG_DB.getCollection("UserCollection");
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
			return upsertUser(user);
		}
		
		public ReminderEvent getEventById(String userId, String reminderEventId) throws DataNotFoundException, JsonParseException, JsonMappingException, IOException {
			assert userId != null;
			assert reminderEventId != null;
			Document document = fetchEvent(userId, reminderEventId);
			if(document == null) {
				throw new DataNotFoundException("Cannot find Event Data user:" + userId + " Event:" + reminderEventId);
			}
			ReminderEvent reminderEvent = new ObjectMapper().readValue(document.toJson(), ReminderEvent.class);
			return reminderEvent;
		}
		
		public ReminderUser getUserById(String userId) throws DataNotFoundException, JsonParseException, JsonMappingException, IOException {
			assert userId != null;
			Document document = fetchUser(userId);
			if(document == null) {
				throw new DataNotFoundException("Cannot find user Data User: " + userId); 
			}
			ReminderUser reminderUser = new ObjectMapper().readValue(document.toJson(), ReminderUser.class);
			return reminderUser;
		}
		
		public List<ReminderEvent> getEventsById(String userId) throws DataNotFoundException, JsonParseException, JsonMappingException, IOException {
			assert userId != null;
			List<Document> documents = fetchEvents(userId);
			if(documents == null || documents.isEmpty()) {
				throw new DataNotFoundException("Cannot find user Events, User: " + userId);
			}
			List<ReminderEvent> result = new ArrayList<>();
			for(Document document : documents) {
				result.add(new ObjectMapper().readValue(document.toJson(), ReminderEvent.class));
			}
			return result;
		}
		
		public ReminderEvent upsertDBReminderEvent(ReminderEvent reminderEvent) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			assert reminderEvent != null;			
			return upsertEvent(reminderEvent);
		}
		
		public void deleteReminderEvent(String userId, String reminderEventId) {
			assert userId != null;
			assert reminderEventId != null;
			removeEvent(userId, reminderEventId);
		}
		
		public void deleteReminderUser(String userId) {
			assert userId != null;
			removeUser(userId);
		}
		
		public ReminderUser updateReminderUser(ReminderUser user) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			assert user != null;
			return updateUser(user);
		}
		
		public ReminderEvent updateReminderEvent(ReminderEvent reminderEvent) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			assert reminderEvent != null;
			return updateEvent(reminderEvent);
		}
		
		private Document fetchEvent(String userId, String reminderEventId) {
			BasicDBObject cond = new BasicDBObject("userId", userId);
			cond.append("reminderEventId", reminderEventId);
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
			Document document = Document.parse(JSON.serialize(user));
			USER_COLLECTION.insertOne(document);
			return getUserById(user.getUserId());
		}
		
		private ReminderEvent upsertEvent(ReminderEvent event) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			/*DocumentDBEvent dbEvent = (DocumentDBEvent) event;
			dbEvent.set_id(dbEvent.getUserID() + dbEvent.getEventID());
			Document document = Document.parse(JSON.serialize(dbEvent));
			EVENT_COLLECTION.insertOne(document);
			return getEventById(event.getUserID(), event.getEventID());*/
			Document document = Document.parse(JSON.serialize(event));
			EVENT_COLLECTION.insertOne(document);
			return getEventById(event.getUserID(), event.getEventID());
		}
		
		private ReminderUser updateUser(ReminderUser user) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			Document documentFilter = fetchUser(user.getUserId());
			Document updateDocument = Document.parse(JSON.serialize(user));
			USER_COLLECTION.updateOne(documentFilter, updateDocument);
			return getUserById(user.getUserId());
		}
		
		private ReminderEvent updateEvent(ReminderEvent event) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
			Document documentFilter = fetchEvent(event.getUserID(), event.getEventID());
			Document updateDocument = Document.parse(JSON.serialize(event));
			EVENT_COLLECTION.updateOne(documentFilter, updateDocument);
			return getEventById(event.getUserID(), event.getEventID());
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
