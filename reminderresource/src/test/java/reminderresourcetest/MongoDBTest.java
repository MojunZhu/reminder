package reminderresourcetest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojun.reminder.exceptions.DataNotFoundException;
import com.mojun.reminder.reminderdataobj.EventCategory;
import com.mojun.reminder.reminderdataobj.EventPriority;
import com.mojun.reminder.reminderdataobj.EventStatus;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderUser;
import com.mojun.reminder.reminderdb.DBReminderDAO;
import com.mojun.reminder.reminderdb.DBReminderDOImp;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBTest {
	private static final String DB_URL = "127.0.0.1";
	private static final long DB_PORT = 27017;
	private static final String USER_COLLECTION_NAME = "UserCollection";
	private static final String EVENT_COLLECTION_NAME = "EventCollection";
	
	@SuppressWarnings("unused")
	@Test
	public void upsertIntoDB() throws JsonParseException, JsonMappingException, IOException {
		MongoClient mg_client = new MongoClient("127.0.0.1", 27017);
		MongoDatabase mg_db = mg_client.getDatabase("ReminderDB");
		MongoCollection<Document> event_collection= mg_db.getCollection("EventCollection");
		MongoCollection<Document> user_collection = mg_db.getCollection("UserCollection");
		System.out.println(mg_client.getAddress());
		ReminderUser user = new ReminderUser();
		user.setPassword("123");
		user.setEmailAddress("abc@xyz.com");
		user.setUserId("abc");
		ReminderEvent event = new ReminderEvent();
		Date createDate = new Date();
		Date deadLine = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 11, 11, 11, 20);
		deadLine = cal.getTime();
		event.setCreatTime(createDate);
		event.setDeadLine(deadLine);
		event.setEventCategory(EventCategory.WORK_EVENT);
		event.setEventId("TestMeEvent");
		event.setEventPriority(EventPriority.MUST_DONE_IN_TIME);
		event.setEventStatus(EventStatus.IN_PROCESS);
		event.setEventTitle("TestMeEvent");
		event.setUserId("test");
		//event.setRemindMode(R);
		//String parseResult = JSON.serialize(user);
		ObjectMapper newMap = new ObjectMapper();
		String parseResult = newMap.writeValueAsString(user);
		String eventResult = newMap.writeValueAsString(event);
		Document document = Document.parse(parseResult);
		Document documentEvent = Document.parse(eventResult);
		user_collection.insertOne(document);
		event_collection.insertOne(documentEvent);
		BasicDBObject cond = new BasicDBObject("eventId", event.getEventId());
		FindIterable<Document> cursor = event_collection.find(cond);
		Document result = cursor.iterator().next();
		ReminderEvent reminderEvent = new ObjectMapper().readValue(result.toJson(), ReminderEvent.class);
		mg_client.close();
		System.out.println("done");
	}
	
	@Test
	public void testDOIAPImp() throws JsonParseException, JsonMappingException, DataNotFoundException, IOException {
		DBReminderDAO dbReminderDAO = DBReminderDOImp.getInstance();
		// prepare for test data 
		ReminderUser user = new ReminderUser();
		user.setPassword("123");
		user.setEmailAddress("abc@xyz.com");
		user.setUserId("TestV2");
		user.setUserName("TestV2");
		ReminderEvent event = new ReminderEvent();
		Date createDate = new Date();
		Date deadLine = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 11, 11, 11, 20);
		deadLine = cal.getTime();
		event.setCreatTime(createDate);
		event.setDeadLine(deadLine);
		event.setEventCategory(EventCategory.WORK_EVENT);
		event.setEventId("TestEventV2");
		event.setEventPriority(EventPriority.MUST_DONE_IN_TIME);
		event.setEventStatus(EventStatus.IN_PROCESS);
		event.setEventTitle("TestEventV2");
		event.setUserId("TestV2");
		// insert
		ReminderUser reminderUser = dbReminderDAO.upsertDBReminderUser(user);
		ReminderEvent reminderEvent = dbReminderDAO.upsertDBReminderEvent(event);
		ReminderEvent reminderEvent2 = reminderEvent;
		reminderEvent2.setEventTitle("TestEventV2.2");
		reminderEvent2.setEventId("TestEventV2.2");
		reminderEvent2 = dbReminderDAO.upsertDBReminderEvent(reminderEvent2);
		// update
		reminderUser.setUserName("TestV2Update");
		reminderEvent.setEventCategory(EventCategory.PERSONAL_PROJECT);
		reminderEvent.setUserId("1122334");
		ReminderUser reminderUserUpdate = dbReminderDAO.updateReminderUser(reminderUser);
		ReminderEvent reminderEventUpdate = dbReminderDAO.updateReminderEvent(reminderEvent);
		// fetch
		List<ReminderEvent> reminderEventList = dbReminderDAO.getEventsById(reminderEvent.getUserId());
		// remove
		//dbReminderDAO.deleteReminderEvent(reminderEvent2.getUserId(), reminderEvent2.getEventId());
		//reminderEvent2 = dbReminderDAO.getEventById(reminderEvent2.getUserId(), reminderEvent2.getEventId());
		dbReminderDAO.deleteReminderUser(reminderEvent2.getUserId());
		// fetch
		reminderEvent = dbReminderDAO.getEventById(reminderEvent.getUserId(), reminderEvent.getEventId());
		reminderUser = dbReminderDAO.getUserById("jskjdkf");
	}
	
	@Test
	public void mappToJson() throws JsonProcessingException {
		ObjectMapper objMap = new ObjectMapper();
		ReminderUser user = new ReminderUser();
		user.setPassword("123");
		user.setEmailAddress("abc@xyz.com");
		user.setUserId("TestV2");
		user.setUserName("TestV2");
		ReminderEvent event = new ReminderEvent();
		Date createDate = new Date();
		Date deadLine = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 11, 11, 11, 20);
		deadLine = cal.getTime();
		event.setCreatTime(createDate);
		event.setDeadLine(deadLine);
		event.setEventCategory(EventCategory.WORK_EVENT);
		event.setEventId("TestEventV2");
		event.setEventPriority(EventPriority.MUST_DONE_IN_TIME);
		event.setEventStatus(EventStatus.IN_PROCESS);
		event.setEventTitle("TestEventV2");
		event.setUserId("TestV2");
		String userString = objMap.writeValueAsString(user);
		String eventString = objMap.writeValueAsString(event);
		System.out.println(userString);
		System.out.println(eventString);
		
		System.out.println("Done");
	}
}
