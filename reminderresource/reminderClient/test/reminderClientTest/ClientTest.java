package reminderClientTest;

import org.testng.annotations.Test;

import com.mojun.reminder.reminderClient.ReminderClient;
import com.mojun.reminder.reminderdataobj.ReminderUser;

public class ClientTest {
	
	@Test
	public void logInOutTest() {
		ReminderClient client = new ReminderClient();
		int logIn = client.logIn("TestLogInV3", "123");
		ReminderUser user = client.getReminderUser("TestLogInV3");
		int logOut = client.logOut();
		System.out.println("LogIn==" + logIn + "  LogOut==" + logOut);
	}
	
}
