package reminderDB;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mojun.reminder.reminderdataobj.ReminderEvent;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentDBEvent extends ReminderEvent{
	private ObjectId _id;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = new ObjectId(_id);
	}
	
}
