package Core.Utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DataConstants {
	
	public static final String DELIMITER = "\\u001F";
	
	public static final String IDENTIFIER = "TYPE:";

	public static final String JSON_TYPE = "JSON";
	public static final String TEXT_TYPE = "RAW";
	public static final String COMPLEX_JSON = "COMPJSON";
	
	public static final int BEFORE = 1;
	public static final int AFTER = 2;
	
	public static final String[] Months = { "January", "February", "March", "April", "May", "June", "July", "August",
		   "September", "October", "November", "December" };
	
	public static final String nullValue = null;
	
	public static final DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	public static final EntityField FIRST_NAME = new EntityField("first_name", true, String.class, true);
	public static final EntityField LAST_NAME = new EntityField("last_name", true, String.class, true);
	public static final EntityField LAST_STUDENT_ID = new EntityField("last_student_id", true, Integer.class);
	public static final EntityField ATTENDANCE_HOURS = new EntityField("attendance_hours", true, Integer.class
		   , true);
	public static final EntityField REQUIRED_HOURS = new EntityField("required_hours", true, Integer.class,
		   true);
	public static final EntityField COLOR = new EntityField("color", true, String.class, true);
	public static final EntityField PRICE = new EntityField("price", true, Double.class, true);
	public static final EntityField LOCATION = new EntityField("location", true, String.class, false);
	public static final EntityField START_TIME = new EntityField("start_time", LocalTime.class, true);
	public static final EntityField DATE = new EntityField("date", LocalDate.class, true);
	public static final EntityField NAME = new EntityField("name", true, String.class);
	public static final EntityField START_DATE = new EntityField("start_date", true, LocalDate.class);
	public static final EntityField END_DATE = new EntityField("end_date", true, LocalDate.class);
	public static final EntityField END_TIME = new EntityField("end_time", true, LocalTime.class,
		   true);
	public static final EntityField MISC = new EntityField("notes", false, String.class);
	public static final EntityField CAST = new EntityField("cast", true, String.class);
	public static final EntityField ROLE = new EntityField("role", true, String.class);
	public static final EntityField ID = new EntityField("id", true, String.class);
	public static final EntityField TEACHER = new EntityField("teacher", true, String.class);
	public static final EntityField DAYS_OF_THE_WEEK = new EntityField("days_of_the_week", false,
		   String.class, true);
	public static final EntityField DATE_OF_BIRTH = new EntityField("date_of_birth", true, LocalDate.class);
	public static final EntityField LEVEL = new EntityField("level", true, String.class);
	public static final EntityField EMAIL = new EntityField("email", true, String.class);
	public static final EntityField PHONE_NUMBER = new EntityField("phone_number", true, String.class);
	public static final EntityField MEDICAL_CONDITIONS = new EntityField("medical_conditions", true, String.class);
	public static final EntityField GENDER = new EntityField("gender", true, String.class);
	public static final EntityField DATE_JOINED = new EntityField("date_joined", false, LocalDate.class);
	public static final EntityField ADDRESS = new EntityField("address", true, String.class);
	public static final EntityField ACTIVITY_STATUS = new EntityField("activity_status", true, String.class);
	public static final EntityField TUITION_PLAN = new EntityField("tuition_plan", true, String.class);
	public static final EntityField BALANCE = new EntityField("balance", true, Double.class);
	public static final EntityField HEIGHT = new EntityField("height", true, Double.class);
	public static final EntityField GIRTH = new EntityField("girth", true, Double.class);
	public static final EntityField WAIST = new EntityField("waist", true, Double.class);
	public static final EntityField HIPS = new EntityField("hips", true, Double.class);
	public static final EntityField BUST_CHEST = new EntityField("bust_chest", true, Double.class);
	public static final EntityField INSEAM = new EntityField("inseam", true, Double.class);
	public static final EntityField SLEEVE_LENGTH = new EntityField("sleeve_length", true, Double.class);
	public static final EntityField NECK = new EntityField("neck", true, Double.class);
	public static final EntityField BACK_LENGTH = new EntityField("back_length", true, Double.class);
	public static final EntityField SHOE_SIZE = new EntityField("shoe_size", true, Double.class);
	public static final EntityField CLASSES_PER_WEEK = new EntityField("classes_per_week", true, Integer.class);
	public static final EntityField AUDIT_LOG = new EntityField("audit_log", true, String.class);
	public static final EntityField IS_ACTIVE = new EntityField("is_active", true, Boolean.class
		   , true);
	public static final EntityField USERNAME = new EntityField("username", true, String.class, true);
	public static final EntityField PASSWORD = new EntityField("hashed_password", true, String.class,
		   false);
	public static final EntityField ADMIN_ROLE = new EntityField("admin_role", true, String.class, true);
	public static final EntityField TYPE = new EntityField("object_type", true, String.class);
	
	public static final EntityField[] Permissions = {};
	
	//All permissions shall be added here as they are created.

}
