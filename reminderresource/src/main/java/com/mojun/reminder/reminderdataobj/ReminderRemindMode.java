package com.mojun.reminder.reminderdataobj;

import java.util.Date;

public class ReminderRemindMode {
	private Date remindTime;
	
	private RepetMode repetMode;
	
	private static enum RepetMode {
		ONE_TIME, EVERY_DAY, EVERY_WEEK;
	}
	
	public Date getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}

	public RepetMode getRepetMode() {
		return repetMode;
	}

	public void setRepetMode(RepetMode repetMode) {
		this.repetMode = repetMode;
	}
}
