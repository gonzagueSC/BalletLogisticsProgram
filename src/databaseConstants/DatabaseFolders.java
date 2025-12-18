package databaseConstants;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatabaseFolders {
	
	public static final String userHome = System.getProperty("user.home");
	public static final File desktopFolder = new File(userHome, "Desktop");
	public static final File storageFolder = new File(desktopFolder, "EPBTData");
	public static final File AttendanceFolder = new File(storageFolder, "Attendance");
	public static final File StudentsFolder = new File(storageFolder, "StudentData");
	public static final File AdminFolder = new File(storageFolder, ".AdminDetails");
	public static final File ProductionsFolder = new File(storageFolder, "Productions");
	public static File MonthFolder = new File(AttendanceFolder,
			DatabaseUtilities.Months[Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).split("/")[0])
					- 1] + " " + LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).split("/")[2]);
	
	public static final File[] folders = { storageFolder, AttendanceFolder, StudentsFolder, MonthFolder, AdminFolder,
			ProductionsFolder };

}
