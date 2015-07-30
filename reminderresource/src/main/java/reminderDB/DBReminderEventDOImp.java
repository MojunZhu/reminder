package reminderDB;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderUser;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
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
		
		public ReminderUser upsertDBReminderUser(ReminderUser user) {
			ReminderUser result = new ReminderUser();
			if(user == null) {
				return result;
			}
			
		}
		
		public ReminderEvent getEventById(String userId, String reminderEventId) {
			assert userId != null;
			assert reminderEventId != null;
			Document document = fetchEvent(userId, reminderEventId);
			ReminderEvent reminderEvent = new ObjectMapper().readValue(document.toJson(), ReminderEvent.class);
			return reminderEvent;
		}
		
		public ReminderUser getUserById(String userId) {
			assert userId != null;
		}
		
		public List<ReminderEvent> getEventsById(String userId);
		
		public ReminderEvent upsertDBReminderEvent(ReminderEvent reminderEvent);
		
		public void deleteReminderEvent(String userId, String reminderEventId);
		
		public void deleteReminderUser(String userId);
		
		private Document fetchEvent(String userId, String reminderEventId) {
			BasicDBObject cond = new BasicDBObject("userId", userId);
			cond.append("reminderEventId", reminderEventId);
			FindIterable<Document> cursor = EVENT_COLLECTION.find(cond);
			Document result = cursor.iterator().next();
		}
		
		private Document fetchUser(String userId) {
			BasicDBObject cond = new BasicDBObject("userId", userId);
			FindIterable<Document> cursor = USER_COLLECTION.find(cond);
			Document result = cursor.iterator().next();
			return result;
		}
		
		private List<Document> fetchEvents(String userId) {
			BasicDBObject cond = new BasicDBObject("userId", userId);
			FindIterable<Document> cursor = EVENT_COLLECTION.find(cond);
			//how to fix this?
			List<Document> result = IteratorUtils.toList(cursor.iterator());
			return result;
		}
}
