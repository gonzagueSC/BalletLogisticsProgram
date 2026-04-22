package Entities;

import Core.*;
import org.jetbrains.annotations.NotNull;

import static Core.DataConstants.*;

import java.io.*;
import java.lang.instrument.IllegalClassFormatException;
import java.time.*;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class StudentEntity extends JSONObjectEntity {
	
	public static final File folder = FilePaths.StudentsFolder;
	public static final EntityField firstName = new EntityField("first_name", true, String.class);
	public static final EntityField lastName = new EntityField("last_name", true, String.class);
	public static final EntityField dateOfBirth = new EntityField("date_of_birth", true, String.class);
	public static final EntityField level = new EntityField("level", true, String.class);
	public static final EntityField email = new EntityField("email", true, String.class);
	public static final EntityField phoneNumber = new EntityField("phone_number", true, String.class);
	public static final EntityField medicalConditions = new EntityField("medical_conditions", true, String.class);
	public static final EntityField gender = new EntityField("gender", true, String.class);
	public static final EntityField dateJoined = new EntityField("date_joined", false, String.class);
	public static final EntityField address = new EntityField("address", true, String.class);
	public static final EntityField activityStatus = new EntityField("activity_status", true, String.class);
	public static final EntityField tuitionPlan = new EntityField("tuition_plan", true, String.class);
	public static final EntityField accountBalance = new EntityField("account_balance", true, Double.class);
	public static final EntityField height = new EntityField("height", true, Double.class);
	public static final EntityField girth = new EntityField("girth", true, Double.class);
	public static final EntityField waist = new EntityField("waist", true, Double.class);
	public static final EntityField hips = new EntityField("hips", true, Double.class);
	public static final EntityField bustChest = new EntityField("bust_chest", true, Double.class);
	public static final EntityField inseam = new EntityField("inseam", true, Double.class);
	public static final EntityField sleeveLength = new EntityField("sleeve_length", true, Double.class);
	public static final EntityField neck = new EntityField("neck", true, Double.class);
	public static final EntityField backLength = new EntityField("back_length", true, Double.class);
	public static final EntityField shoeSize = new EntityField("shoe_size", true, Double.class);
	public static final EntityField classesPerWeek = new EntityField("classes_per_week", true, Integer.class);
	public static final EntityField log = new EntityField("audit_log", true, String.class);
	
	public static final String PROFILEDATA = "PROFILEDATA";
	public static final String ATTENDANCE = "ATTENDANCE";
	public static final String LEVELCHANGE = "LEVELCHANGE";
	public static final String CASTING = "CASTING";
	public static final String FINANCIAL = "FINANCIAL";
	public static final String ADMINNOTE = "ADMINNOTE";
	
	public static final String ProfileChangeFormat = PROFILEDATA + " %s: %s Changed to %s";
	public static final String ChangeTuitionPlanFormat = FINANCIAL + " %s: Tuition Plan Changed to %s";
	public static final String ChangeAccountBalanceFormat = FINANCIAL + " %s: Account Balance Changed to %s";
	public static final String ChangeLevelFormat = LEVELCHANGE + " %s: Moved to %s";
	public static final String CastingFormat = CASTING + " %s: Casted as %s in cast(s) %s";
	public static final String AttendanceEnteredFormat = ATTENDANCE + " %s: %s: %s";
	public static final String AttendanceExitFormat = ATTENDANCE + " %s: %s %s: %s";
	public static final String TeachersNoteFormat = ADMINNOTE + " %s: %s - %s";
	
	public static final String ACTIVE = "ACTIVE";
	public static final String INACTIVE = "INACTIVE";
	public static final String MEDICAL_LEAVE = "MEDICAL LEAVE";
	
	public StudentEntity ( String studentID ) throws IOException, IllegalClassFormatException {
		
		super(studentID, folder);
		
		ENTITY_FIELDS = new EntityField[]{firstName, lastName, dateOfBirth, level, email, phoneNumber,
			   medicalConditions, gender, dateJoined, address, activityStatus, tuitionPlan, accountBalance, height,
			   girth, waist, hips, bustChest, inseam, sleeveLength, neck, backLength, shoeSize, classesPerWeek,
			   log};
		
		initializeComponents();
		
	}
	
	public Map<EntityField, String> getStudentData () {
		
		return this.getObjectValues();
		
	}
	
	public String getDisplay () {
		
		return this.getFieldValue(firstName) + " " + getFieldValue(lastName);
		
	}
	
	public String getDisplay ( boolean longFormat ) {
		
		return getDisplay() + ((longFormat) ? " " + this.getFieldType(dateOfBirth) : "");
		
	}
	
	
	@Override
	public void saveFields ( @NotNull Map<EntityField, String> fieldToSaveMap ) throws IllegalClassFormatException,
		   IOException {
		
		if (fieldToSaveMap.containsKey(firstName) || fieldToSaveMap.containsKey(lastName) || fieldToSaveMap.containsKey(dateOfBirth)) {
			
			String oldDisplay = this.getDisplay(true);
			String newDisplay = ((fieldToSaveMap.containsKey(firstName)) ? fieldToSaveMap.get(firstName) :
				   this.getFieldValue(firstName)) + " " + ((fieldToSaveMap.containsKey(lastName)) ?
				   fieldToSaveMap.get(lastName) : this.getFieldValue(lastName)) + " " + ((fieldToSaveMap.containsKey(dateOfBirth)) ? fieldToSaveMap.get(dateOfBirth) : this.getFieldValue(dateOfBirth));
			EntityMapper.getInstance().getEntity(StudentsRegistryEntity.class).changeStudentName(oldDisplay,
				   newDisplay);
			
		}
		
		super.saveFields(fieldToSaveMap);
	}
	
	public void addLog ( String logValue ) throws IOException {
		
		String logs = getFieldValue(log);
		
		String newLogValue = logs + logValue + "\n";
		
		saveField(log, newLogValue);
		
		save();
		
	}
	
	public void changeProfileData ( EntityField field, String newValue ) throws IOException {
		
		String log = String.format(ProfileChangeFormat, LocalDate.now(), getFieldValue(field), newValue);
		
		addLog(log);
		
	}
	
	public void changeFinancialData ( EntityField field, String newValue ) throws IOException {
		
		String log = "";
		
		if (field == accountBalance) {
			
			log = String.format(ChangeAccountBalanceFormat, LocalDate.now(), newValue);
			
		} else if (field == tuitionPlan) {
			
			log = String.format(ChangeTuitionPlanFormat, LocalDate.now(), newValue);
			
		} else {
			
			throw new IllegalArgumentException("Financial Data Logs must be either account balance changes or " +
				   "tuition plan changes");
			
		}
		
		addLog(log);
		
	}
	
	public void changeLevel ( String newLevel ) throws IOException {
		
		String log = String.format(ChangeLevelFormat, LocalDate.now(), newLevel);
		
		addLog(log);
		
	}
	
	public void CastStudent ( String role, String[] casts ) throws IOException {
		
		String log = String.format(CastingFormat, LocalDate.now(), role, readArrayNaturally(casts));
		
		addLog(log);
		
	}
	
	public String readArrayNaturally ( Object[] array ) {
		
		String result = "";
		
		for (int i = 0; i < array.length; i++) {
			
			result += array[i].toString() + ((i != array.length - 1) ? ", " : "");
			
		}
		
		return result;
		
	}
	
	public void signStudentIn ( String classAttending ) throws IOException {
		
		String log = String.format(AttendanceEnteredFormat, LocalDate.now(), LocalTime.now().format(DateFormat),
			   classAttending);
		
		addLog(log);
		
	}
	
	public void signStudentOut () throws IOException {
		
		String allLogs = getFieldValue(log);
		
		String[] Logs = allLogs.split("\n");
		
		int logsIndex;
		
		if (Logs[Logs.length - 1].isBlank()) {
			
			logsIndex = Logs.length - 2;
			
		} else {
			
			logsIndex = Logs.length - 1;
			
		}
		
		String[] logSplits = Logs[logsIndex].split(":\\s");
		
		String date = logSplits[0];
		String timeIn = logSplits[1];
		String className = logSplits[2];
		
		String log = String.format(AttendanceExitFormat, LocalDate.now(), timeIn, LocalTime.now(), className);
		
		addLog(log);
		
	}
	
	public void addTeachersNote ( String teacher, String Note ) throws IOException {
	
		String log = String.format(TeachersNoteFormat, LocalDate.now(), Note, teacher);
		
		addLog(log);
	
	}
	
	public String[] getAllRecords () {
		
		String allLogs = this.getFieldValue(log);
		
		Scanner logParser = new Scanner(allLogs);
		
		String nextLine;
		
		ArrayList<String> fullFile = new ArrayList<String>();
		
		while ((nextLine = logParser.nextLine()) != null) {
		
			fullFile.add(nextLine);
		
		}
		
		return fullFile.toArray(new String[0]);
		
	}
	
	public String[] getAllOfRecords(String[] allowedTypes) {
		
		String allLogs = this.getFieldValue(log);
		
		Scanner logParser = new Scanner(allLogs);
		
		String nextLine;
		
		ArrayList<String> fullFile = new ArrayList<String>();
		
		while ((nextLine = logParser.nextLine()) != null) {
			
			for (String DataType: allowedTypes) {
				
				if (nextLine.matches(DataType + ".*")) {
					
					fullFile.add(nextLine);
					
				}
				
			}
			
		}
		
		return fullFile.toArray(new String[0]);
	
	}
	
	public String[] getAllProfileRecords () {
		
		String[] allowedTypes = {PROFILEDATA, ADMINNOTE};
		
		return getAllOfRecords(allowedTypes);
		
	}
	
	public String[] getAllProductionRecords () {
		
		String[] allowedTypes = {CASTING};
		
		return getAllOfRecords(allowedTypes);
		
	}
	
	public String[] getAllAdminRecords () {
		
		String[] allowedTypes = {LEVELCHANGE, FINANCIAL};
		
		return getAllOfRecords(allowedTypes);
		
	}
	
}
