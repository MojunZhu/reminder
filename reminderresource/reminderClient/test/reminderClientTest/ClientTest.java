package reminderClientTest;

import org.testng.annotations.Test;

import com.mojun.reminder.reminderClient.ReminderClient;

public class ClientTest {
	
	@Test
	public void logInOutTest() {
		ReminderClient client = new ReminderClient();
		int logIn = client.logIn(null, null);
		int logOut = client.logOut();
		System.out.println("LogIn==" + logIn + "  LogOut==" + logOut);
	}
	
}
