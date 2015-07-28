package reminderlib;

import java.util.List;

import com.mojun.reminder.reminderdataobj.CreateReminderEventRequest;
import com.mojun.reminder.reminderdataobj.CreateReminderUserRequest;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderEventList;
import com.mojun.reminder.reminderdataobj.ReminderUser;

public class ReminderProcessor {
	public static final String SERVICE_CONFIG_PATH = "reminderresource/service/config"; 
	
	public static ReminderProcessor getDefaultProcessor() {
		return new ReminderProcessor();
	}
	
	public ReminderEvent createReminderEvent(String userId, CreateReminderEventRequest request) {
		assert userId != null;
		assert request != null;
		
		DBReminderEventObj dbReminderEventObj = upsertDBReminderEvent(request);
		ReminderEvent result = constructEvent(dbReminderEventObj);
		
		return result;
	}
	
	public ReminderUser createReminderUser(CreateReminderUserRequest request) {
		assert request != null;
		
		DBReminderUser dbReminderUser = upsertDBReminderUser(request);
		ReminderUser result = constructUser(dbReminderUser);
		
		return result;
	}
	
	public ReminderEvent getSingleReminderEvent(String userId, String reminderEventId) {
		assert userId != null;
		assert reminderEventId != null;
		
		DBReminderEventDAO reminderEventDAO = DBReminderEventDAO.getinstance();
		DBReminderEvent dbReminderEvent = reminderEventDAO.getEventbyId(userId, reminderEventId);
		
		ReminderEvent result = constructEvent(dbReminderEventObj);
		return result;
	}
	
	public ReminderEventList getReminderEventList(String userId) {
		assert userId != null;
		
		DBReminderEventDAO dbReminderEventDAO = DBReminderEventDAO.getinstance();
		List<DBReminderEvent> dbReminderEventlist = dbReminderEventDAO.getEventsbyId(userId);
		
		ReminderEventList result = constructEventList(dbReminderEventlist);
		
		return result;
	}
	
	public ReminderEvent updateSingleEvent(String userId, String reminderEventId, CreateReminderEventRequest request) {
		assert userId != null;
		assert reminderEventId != null;
		assert request != null;
		
		DBReminderEventDAO dbReminderEventDAO = DBReminderEventDAO.getinstance();
		DBReminderEvent dbReminderEvent = dbReminderEventDAO.getEventById(userId, reminderEventId);
		
		DBReminderEventObj dbReminderEventObj = upsertDBReminderEvent(request);
		ReminderEvent result = constructEvent(dbReminderEventObj);
		return result;
	}
	
	public ReminderUser updateReminderUser(String userId, CreateReminderUserRequest request) {
		assert userId != null;
		assert request != null;
		
		return new ReminderUser();
	}

	public void deleteSingleEvent(String userId, String reminderEventId) {
		//TO DO
	}
	
	public void deleteUser(String userId) {
		//TO DO
	}

}
