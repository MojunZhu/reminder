package reminderlib;

import java.util.List;

import reminderDB.DBReminderDAO;

import com.mojun.reminder.reminderdataobj.CreateReminderEventRequest;
import com.mojun.reminder.reminderdataobj.CreateReminderUserRequest;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderEventList;
import com.mojun.reminder.reminderdataobj.ReminderUser;

public class ReminderProcessor {
	
	/*public static final String SERVICE_CONFIG_PATH = "reminderresource/service/config"; 
	
	public static ReminderProcessor getDefaultProcessor() {
		return new ReminderProcessor();
	}
	
	public ReminderEvent createReminderEvent(String userId, CreateReminderEventRequest request) {
		assert userId != null;
		assert request != null;
		
		DBReminderDAO reminderEventDAO = DBReminderDAO.getInstance();
		
		ReminderEvent result = createEvent(request);
		result = reminderEventDAO.upsertDBReminderEvent(result);
		
		return result;
	}
	
	public ReminderUser createReminderUser(CreateReminderUserRequest request) {
		assert request != null;
		
		DBReminderDAO reminderEventDAO = DBReminderDAO.getInstance();

		ReminderUser result = createUser(request);
		result = reminderEventDAO.upsertDBReminderUser(result);
		 
		
		return result;
	}
	
	public ReminderEvent getSingleReminderEvent(String userId, String reminderEventId) {
		assert userId != null;
		assert reminderEventId != null;
		
		DBReminderDAO reminderEventDAO = DBReminderDAO.getInstance();
		ReminderEvent result = reminderEventDAO.getEventById(userId, reminderEventId);
				
		return result;
	}
	
	public ReminderEventList getReminderEventList(String userId) {
		assert userId != null;
		
		DBReminderDAO dbReminderEventDAO = DBReminderDAO.getInstance();
		List<ReminderEvent> ReminderEventlist = dbReminderEventDAO.getEventsById(userId);
		
		ReminderEventList result = constructEventList(ReminderEventlist);
		
		return result;
	}
	
	public ReminderEvent updateSingleEvent(String userId, String reminderEventId, CreateReminderEventRequest request) {
		assert userId != null;
		assert reminderEventId != null;
		assert request != null;
		
		DBReminderDAO dbReminderEventDAO = DBReminderDAO.getInstance();
		ReminderEvent dbReminderEvent = dbReminderEventDAO.getEventById(userId, reminderEventId);
		
		dbReminderEvent = updateEvent(dbReminderEvent, request);
		
		ReminderEvent result = dbReminderEventDAO.upsertDBReminderEvent(dbReminderEvent);
		return result;
	}
	
	public ReminderUser updateReminderUser(String userId, CreateReminderUserRequest request) {
		assert userId != null;
		assert request != null;
		
		DBReminderDAO dbReminderEventDAO = DBReminderDAO.getInstance();
		ReminderUser dbReminderUser = dbReminderEventDAO.getUserById(userId);
		
		dbReminderUser = updateUser(dbReminderUser, request);
		
		ReminderUser result = dbReminderEventDAO.upsertDBReminderUser(dbReminderUser);
		
		return result;
	}

	public void deleteSingleEvent(String userId, String reminderEventId) {
		assert userId != null;
		assert reminderEventId != null;
		
		DBReminderDAO dbReminderEventDAO = DBReminderDAO.getInstance();
		
		dbReminderEventDAO.deleteReminderEvent(userId, reminderEventId);
	}
	
	public void deleteUser(String userId) {
		assert userId != null;
		assert userId != null;
		
		DBReminderDAO dbReminderEventDAO = DBReminderDAO.getInstance();
		//TO DO 
		//loop delete all user events first then delete user
		dbReminderEventDAO.deleteReminderUser(userId);
	}*/

}
