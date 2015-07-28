package reminderDB;


import java.util.List;

import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderUser;

public interface DBReminderDAO {
	
	public ReminderUser upsertDBReminderUser(ReminderUser user);
	
	public ReminderEvent getEventById(String userId, String reminderEventId);
	
	public ReminderUser getUserById(String userId);
	
	public List<ReminderEvent> getEventsById(String userId);
	
	public ReminderEvent upsertDBReminderEvent(ReminderEvent reminderEvent);
	
	public void deleteReminderEvent(String userId, String reminderEventId);
	
	public void deleteReminderUser(String userId);
}
