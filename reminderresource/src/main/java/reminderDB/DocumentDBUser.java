package reminderDB;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mojun.reminder.reminderdataobj.ReminderUser;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentDBUser extends ReminderUser{
	private ObjectId _id;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = new ObjectId(_id);
	}
	
}
