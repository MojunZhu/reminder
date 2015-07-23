package com.mojun.reminder.reminderdataobj;

import java.util.Date;

public class ReminderEvent {
	private String userID;
	private String eventID;
	private String eventTitle;
	private EventCategory eventCategory;
	private Date creatTime;
	private Date startTime;
	private Date endTime;
	private Date deadLine;
	private EventPriority eventPriority;
	private String notes;
	private int postponeTime;
	private ReminderRemindMode remindMode;
	private EventStatus eventStatus;
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public EventCategory getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(EventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public EventPriority getEventPriority() {
		return eventPriority;
	}

	public void setEventPriority(EventPriority eventPriority) {
		this.eventPriority = eventPriority;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getPostponeTime() {
		return postponeTime;
	}

	public void setPostponeTime(int postponeTime) {
		this.postponeTime = postponeTime;
	}

	public ReminderRemindMode getRemindMode() {
		return remindMode;
	}

	public void setRemindMode(ReminderRemindMode remindMode) {
		this.remindMode = remindMode;
	}

	public EventStatus getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

}
