package com.mojun.reminder.reminderlib;

import java.io.IOException;

import org.aspectj.lang.annotation.Aspect;

import com.mojun.reminder.exceptions.DataNotFoundException;
import com.mojun.reminder.reminderdataobj.ReminderEvent;
import com.mojun.reminder.reminderdataobj.ReminderEventList;
import com.mojun.reminder.reminderdataobj.ReminderUser;
import com.mojun.reminder.reminderdb.DBReminderDAO;
import com.mojun.reminder.reminderdb.DBReminderDOImp;
import com.mojun.reminder.springsecurity.config.AuthenticateUserDOImp;
import com.mojun.reminder.springsecurity.config.AuthticateUsers;

@Aspect
public class ReminderProcessor {
	
	public static final String SERVICE_CONFIG_PATH = "reminderresource/service/config"; 
	public static DBReminderDAO REMINDER_DAO = DBReminderDOImp.getInstance();
	public static AuthenticateUserDOImp AUTH_DOI = AuthenticateUserDOImp.getInstance();
	
	public static ReminderProcessor getDefaultProcessor() {
		return new ReminderProcessor();
	}
	
	public ReminderEvent createReminderEvent(ReminderEvent event) {
		assert event != null;
		ReminderEvent result = null;
		try {
			result = REMINDER_DAO.upsertDBReminderEvent(event);
		} catch (DataNotFoundException e) {
			System.out.println("Data Not Found, no event, user: " + event.getUserId() + "; event: " + event.getEventId());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ReminderUser createReminderUser(ReminderUser user) {
		assert user != null;
		
		ReminderUser result = null;
		AuthticateUsers auResult = null;
		try {
			result = REMINDER_DAO.upsertDBReminderUser(user);
			auResult = AUTH_DOI.createAuthenticationRecord(user);
			if((!auResult.getUserId().equals(result.getUserId())) 
					|| (!auResult.getPassowrd().equals(result.getPassword()))) {
				throw new Exception("Auth and user miss match");
			}
		} catch (DataNotFoundException e) {
			System.out.println("Data Not Found, no event, user: " + user.getUserId());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	public ReminderEvent getSingleReminderEvent(String userId, String reminderEventId) {
		assert userId != null;
		assert reminderEventId != null;
		
		ReminderEvent result  = null;
		try {
			result = REMINDER_DAO.getEventById(userId, reminderEventId);
		} catch (DataNotFoundException e) {
			System.out.println("Data Not Found, no event, user: " + userId + "; event: " + reminderEventId);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
				
		return result;
	}
	
	public ReminderUser getReminderUser(String userId) {
		assert userId != null;
		
		ReminderUser result = null;
		try {
			result = REMINDER_DAO.getUserById(userId);
		} catch (DataNotFoundException e) {
			System.out.println("Data Not Found, no event, user: " + userId);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ReminderEventList getReminderEventList(String userId) {
		assert userId != null;
		
		ReminderEventList result = null;
		try {
			result = REMINDER_DAO.getEventsById(userId);
		} catch (DataNotFoundException e) {
			System.out.println("Data Not Found, no event, user: " + userId);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ReminderEvent updateSingleEvent(ReminderEvent event) {
		assert event != null;
		
		ReminderEvent result = null;
		try {
			result = REMINDER_DAO.getEventById(event.getUserId(), event.getEventId());
		} catch (DataNotFoundException e) {
			System.out.println("Data Not Found, no event, user: " + event.getUserId() + "; event: " + event.getEventId());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	public ReminderUser updateReminderUser(ReminderUser user) {
		assert user != null;
		
		ReminderUser result = null;
		AuthticateUsers authResult = null;
		try {
			result = REMINDER_DAO.updateReminderUser(user);
			authResult = AUTH_DOI.createAuthenticationRecord(user);
		} catch (DataNotFoundException e) {
			System.out.println("Data Not Found, no event, user: " + user.getUserId());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}

	public ReminderEvent deleteSingleEvent(String userId, String reminderEventId) {
		assert userId != null;
		assert reminderEventId != null;
		
		ReminderEvent result = null;
		try {
			result = REMINDER_DAO.deleteReminderEvent(userId, reminderEventId);
		} catch (IOException e) {
			result = new ReminderEvent();
			e.printStackTrace();
		}
		return result;
	}
	
	public ReminderUser deleteUser(String userId) {
		assert userId != null;
		ReminderUser result = null;
		try {
			REMINDER_DAO.deleteReminderUser(userId);
			AUTH_DOI.deleteUser(userId);
		} catch (IOException e) {
			result = new ReminderUser();
			e.printStackTrace();
		}
		return result;
	}

}
