package Core;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static Core.DataConstants.Months;

public class FilePaths {
	
	public static final String userHome = System.getProperty("user.home");
	public static final File libraryFolder = new File(userHome, "Library");
	public static final File applicationSupportFolder = new File(libraryFolder, "Application Support");
	public static final File configFolder = new File(applicationSupportFolder, "Data Management App");
	public static final File desktopFolder = new File(userHome, "Desktop");
	public static final File storageFolder = new File(desktopFolder, "EPBTData");
	public static final File AttendanceFolder = new File(storageFolder, "Attendance");
	public static final File StudentsFolder = new File(storageFolder, "StudentData");
	public static final File AdminFolder = new File(storageFolder, ".AdminDetails");
	public static final File AdminUsersAndPassword = new File(AdminFolder, "util");
	public static final File Levels = new File(AdminFolder, "levels");
	public static final File Studios = new File(AdminFolder, "studios");
	public static final File Teachers = new File(AdminFolder, "teachers");
	public static final File ClassSchedules = new File(AdminFolder, "schedules");
	public static final File ProductionsFolder = new File(storageFolder, "Productions");
	public static final File ProductionsFile = new File(ProductionsFolder, "AllProductions");
	public static final File RegisteredStudents = new File(StudentsFolder, "RegisteredStudents");
	public static File MonthFolder = new File(AttendanceFolder,
		   Months[Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).split("/")[0])
				 - 1] + " " + LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).split("/")[2]);
	public static File AttendanceToday = new File(MonthFolder, "Attendance:" + Date.valueOf(LocalDate.now()));
	public static final File[] folders = { storageFolder, AttendanceFolder, StudentsFolder, MonthFolder, AdminFolder,
		   ProductionsFolder };
	public static final File[] files = { AttendanceToday, RegisteredStudents, AdminUsersAndPassword, ProductionsFile,
		   Levels, Studios, Teachers, ClassSchedules};
	
}
