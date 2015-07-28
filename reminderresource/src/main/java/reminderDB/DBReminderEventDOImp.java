package reminderDB;

import com.mongodb.Mongo;

public class DBReminderDOImp implements DBReminderDAO {
		private static DBReminderDOImp DB_REMINDER_DOIMPL = null;
		private static String DB_NAME = "ReminderDB";
		private static String TABLE_NAME="ReminderTable";
		private Mongo mg;
		private DBReminderDOImp() {
			mg = new Mongo();
			
		}
	
		public static DBReminderDOImp getInstance() {
			if (DB_REMINDER_DOIMPL == null) {
				return DBReminderDOImp(tableName);
			}
			else {
				return DB_REMINDER_DOIMPL;
			}
				 
		}
}
