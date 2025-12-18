package databaseConstants;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;

public class Databases {
	
	public static File AttendanceToday = new File(DatabaseFolders.MonthFolder, "Attendance:" + Date.valueOf(LocalDate.now()));
	public static final File RegisteredStudents = new File(DatabaseFolders.StudentsFolder, "RegisteredStudents");
	public static final File AdminUsersAndPassword = new File(DatabaseFolders.AdminFolder, "util");
	public static final File ProductionsFile = new File(DatabaseFolders.ProductionsFolder, "AllProductions");
	public static final File Levels = new File(DatabaseFolders.AdminFolder, "levels");
	public static final File Studios = new File(DatabaseFolders.AdminFolder, "studios");
	public static final File Teachers = new File(DatabaseFolders.AdminFolder, "teachers");
	public static final File ClassSchedules = new File(DatabaseFolders.AdminFolder, "schedules");

	public static final File[] files = { AttendanceToday, RegisteredStudents, AdminUsersAndPassword, ProductionsFile,
			Levels, Studios, Teachers, ClassSchedules};
	
}
