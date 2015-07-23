package reminderlib;

import com.mojun.reminder.reminderdataobj.CreateReminderEventRequest;
import com.mojun.reminder.reminderdataobj.CreateReminderUserRequest;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderEventList;
import com.mojun.reminder.reminderdataobj.ReminderUser;

public class ReminderProcessor {
	
	public static ReminderProcessor getDefaultProcessor() {
		return new ReminderProcessor();
	}
	
	public ReminderEvent createReminderEvent(String userId, CreateReminderEventRequest request) {
		//TO DO
		return new ReminderEvent();
	}
	
	public ReminderUser createReminderUser(CreateReminderUserRequest request) {
		//TO DO
		return new ReminderUser();
	}
	
	public ReminderEvent getSingleReminderEvent(String userId, String reminderEventId) {
		//TO DO
		return new ReminderEvent();
	}
	
	public ReminderEventList getReminderEventList(String userId) {
		//TO DO
		return new ReminderEventList();
	}
	
	public ReminderEvent updateSingleEvent(String userId, String reminderEventId, CreateReminderEventRequest request) {
		//TO DO
		return new ReminderEvent();
	}
	
	public ReminderUser updateReminderUser(String userId, CreateReminderUserRequest request) {
		//TO DO
		return new ReminderUser();
	}

	public void deleteSingleEvent(String userId, String reminderEventId) {
		//TO DO
	}
	
	public void deleteUser(String userId) {
		//TO DO
	}

}
