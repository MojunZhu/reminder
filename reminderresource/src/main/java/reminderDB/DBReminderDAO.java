package reminderDB;


import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mojun.reminder.exceptions.DataNotFoundException;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderUser;

public interface DBReminderDAO {
	
	public ReminderUser upsertDBReminderUser(ReminderUser user) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException;
	
	public ReminderEvent getEventById(String userId, String reminderEventId) throws DataNotFoundException, JsonParseException, JsonMappingException, IOException;
	
	public ReminderUser getUserById(String userId) throws DataNotFoundException, JsonParseException, JsonMappingException, IOException;
	
	public List<ReminderEvent> getEventsById(String userId) throws DataNotFoundException, JsonParseException, JsonMappingException, IOException;
	
	public ReminderEvent upsertDBReminderEvent(ReminderEvent reminderEvent) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException;
	
	public ReminderUser updateReminderUser(ReminderUser user) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException;
	
	public ReminderEvent updateReminderEvent(ReminderEvent reminderEvent) throws JsonParseException, JsonMappingException, DataNotFoundException, IOException;
	
	public void deleteReminderEvent(String userId, String reminderEventId);
	
	public void deleteReminderUser(String userId);
}
