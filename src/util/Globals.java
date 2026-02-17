package util;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Globals {

	public static final String PROFILEDATA = "PROFILEDATA";
	public static final String ATTENDANCE = "ATTENDANCE";
	public static final String LEVELCHANGE = "LEVELCHANGE";
	public static final String CASTING = "CASTING";
	public static final String FINANCIAL = "FINANCIAL";
	public static final String ADMINNOTE = "ADMINNOTE";
	
	public static final String MINUTES = "minutes";
	public static final String HOURS = "hours";
	public static final String DAYS = "days";

	public static final String ChangeMedicalConditionsFormat = PROFILEDATA + " %s: Medical Conditions Changed to %s";
	public static final String ChangeFirstNameFormat = PROFILEDATA + " %s: First Name Modified to %s";
	public static final String ChangeLastNameFormat = PROFILEDATA + " %s: Last Name Modified to %s";
	public static final String ChangeEmailFormat = PROFILEDATA + " %s: Email Changed to %s";
	public static final String ChangeDateOfBirthFormat = PROFILEDATA + " %s: Date of Birth Modified to %s";
	public static final String ChangePhoneNumberFormat = PROFILEDATA + " %s: Phone number changed to %s";
	public static final String ChangeGenderFormat = PROFILEDATA + " %s: Gender Modified to %s";
	public static final String ChangeAddressFormat = PROFILEDATA + " %s: Address Changed to %s";
	public static final String ChangeCostumeMeasurementFormat = PROFILEDATA + " %s: %s Updated to %s";
	public static final String ChangeDateJoinedFormat = PROFILEDATA + " %s: Date Joined Modified to %s";
	public static final String ChangeActivityStatusFormat = PROFILEDATA + " %s: Status Changed to %s";
	public static final String ChangeTuitionPlanFormat = FINANCIAL + " %s: Tuition Plan Changed to %s";
	public static final String ChangeAccountBalanceFormat = FINANCIAL + " %s: Account Balance Changed to %s";
	public static final String ChangeLevelFormat = LEVELCHANGE + " %s: Moved to %s";
	public static final String CastingFormat = CASTING + " %s: Casted as %s in casts %s";
	public static final String AttendanceEnteredFormat = ATTENDANCE + " %s: %s: %s"; 
	public static final String AttendanceExitFormat = ATTENDANCE + " %s: %s %s: %s";
	public static final String TeachersNoteFormat = ADMINNOTE + " %s: %s - %s";
	public static final String ACTIVE = "ACTIVE";
	public static final String INACTIVE = "INACTIVE";
	public static final String MEDICAL_LEAVE = "MEDICAL LEAVE";
	public static final String[] Months = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };
	public static final String[] DaysOfTheWeek = { "Monday", "February", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	public static final String[] StudentInfo = { "First Name", "Last Name", "Level", "Email", "Phone Number",
			"Medical Conditions", "Date Of Birth", "Gender", "Date Joined", "Address", "Activity Status",
			"Tuition Plan", "Account Balance", "Height", "Girth", "Waist", "Hips", "Bust/Chest", "Inseam",
			"Sleeve Length", "Neck", "Back Length", "Shoe Size", "Classes Per Week"};
	public static final String[] filters = { "First Name", "Last Name", "Alphabetical First Name",
			"Alphabetical Last Name", "Medical Conditions", "Date Joined", "Age", "SortByAge", "Gender", "Level",
			"Activity Status", "Owes Money" };
	public static final String userHome = System.getProperty("user.home");
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
