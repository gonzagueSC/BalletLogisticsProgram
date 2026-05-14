package Entities.ObjectEntities;

import Core.Databases.EntityMapper;
import Core.Databases.JSONObjectEntity;
import Core.Utilities.AppWarning;
import Core.Utilities.AttendanceCalculator;
import Core.Utilities.EntityField;
import Core.Utilities.FilePaths;
import Entities.TopLevelEntities.StudentsRegistryEntity;
import org.jetbrains.annotations.NotNull;

import static Core.Utilities.DataConstants.*;

import java.io.*;
import java.lang.instrument.IllegalClassFormatException;
import java.time.*;
import java.util.*;

public class StudentEntity extends JSONObjectEntity {
	
	public static final String PROFILEDATA = "PROFILE_DATA";
	public static final String ATTENDANCE = "ATTENDANCE";
	public static final String LEVELCHANGE = "LEVEL_CHANGE";
	public static final String CASTING = "CASTING";
	public static final String FINANCIAL = "FINANCIAL";
	public static final String ADMINNOTE = "ADMIN_NOTE";
	
	public static final String ProfileChangeFormat = PROFILEDATA + " %s" + DELIMITER + " %s Changed to %s";
	public static final String ChangeTuitionPlanFormat = FINANCIAL + " %s" + DELIMITER + " Tuition Plan Changed to " +
		   "%s";
	public static final String ChangeAccountBalanceFormat = FINANCIAL + " %s" + DELIMITER + " Account Balance Changed" +
		   " to %s";
	public static final String ChangeLevelFormat = LEVELCHANGE + " %s" + DELIMITER + " Moved to %s";
	public static final String CastingFormat = CASTING + " %s" + DELIMITER + " Casted as %s in cast(s) %s";
	public static final String AttendanceEnteredFormat = ATTENDANCE + " %s" + DELIMITER + " %s" + DELIMITER + " %s";
	public static final String AttendanceExitFormat = ATTENDANCE + " %s" + DELIMITER + " %s %s" + DELIMITER + " %s";
	public static final String TeachersNoteFormat = ADMINNOTE + " %s" + DELIMITER + " %s - %s";
	
	public static final String ACTIVE = "ACTIVE";
	public static final String INACTIVE = "INACTIVE";
	public static final String MEDICAL_LEAVE = "MEDICAL LEAVE";
	
	public static final File folder = FilePaths.StudentsFolder;
	
	@SuppressWarnings("unused")
	public static final String[] STATUSES = { ACTIVE, INACTIVE, MEDICAL_LEAVE };
	
	public StudentEntity ( String studentID ) throws IOException, IllegalClassFormatException, AppWarning {
		
		super(studentID, folder);
		
		ENTITY_FIELDS = new EntityField[]{
			   FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, LEVEL, EMAIL, PHONE_NUMBER, MEDICAL_CONDITIONS, GENDER,
			   DATE_JOINED, ADDRESS, ACTIVITY_STATUS, TUITION_PLAN, BALANCE, HEIGHT, GIRTH, WAIST, HIPS, BUST_CHEST
			   , INSEAM, SLEEVE_LENGTH, NECK, BACK_LENGTH, SHOE_SIZE, CLASSES_PER_WEEK, AUDIT_LOG
		};
		
		initializeComponents();
		
	}
	
	@SuppressWarnings("unused")
	public Map<EntityField, String> getStudentData () {
		
		return this.getObjectValues();
		
	}
	
	public String getDisplay () {
		
		return this.getFieldValue(FIRST_NAME) + " " + getFieldValue(LAST_NAME);
		
	}
	
	public String getDisplay ( boolean longFormat ) {
		
		return getDisplay() + ((longFormat) ? " " + this.getFieldType(DATE_OF_BIRTH) : "");
		
	}
	
	
	public void saveFields ( @NotNull Map<EntityField, String> fieldToSaveMap, boolean startUp ) throws IllegalClassFormatException, IOException, AppWarning {
		
		if ( (fieldToSaveMap.containsKey(FIRST_NAME) || fieldToSaveMap.containsKey(LAST_NAME) || fieldToSaveMap.containsKey(DATE_OF_BIRTH)) && !startUp ) {
			
			StudentsRegistryEntity registry = EntityMapper.getInstance().getEntity(StudentsRegistryEntity.class);
			
			String ID = registry.getStudentID(this.getFieldValue(FIRST_NAME), this.getFieldValue(LAST_NAME),
				   this.getFieldValue(DATE_OF_BIRTH));
			
			
			
			registry.changeStudentValue(ID, FIRST_NAME, fieldToSaveMap.get(FIRST_NAME));
			registry.changeStudentValue(ID, LAST_NAME, fieldToSaveMap.get(LAST_NAME));
			registry.changeStudentValue(ID, DATE_OF_BIRTH, fieldToSaveMap.get(DATE_OF_BIRTH));
			
		}
		
		super.saveFields(fieldToSaveMap);
	}
	
	public void addLog ( String logValue ) throws IOException, AppWarning {
		
		String logs = getFieldValue(AUDIT_LOG);
		
		String newLogValue = logs + logValue + "\n";
		
		saveField(AUDIT_LOG, newLogValue, false);
		
		save();
		
	}
	
	@SuppressWarnings("unused")
	public void changeProfileData ( EntityField field, String newValue ) throws IOException, AppWarning {
		
		String log = String.format(ProfileChangeFormat, LocalDate.now(), getFieldValue(field), newValue);
		
		addLog(log);
		
	}
	
	@SuppressWarnings("unused")
	public void changeFinancialData ( EntityField field, String newValue ) throws IOException, AppWarning {
		
		String log;
		
		if ( field == BALANCE ) {
			
			log = String.format(ChangeAccountBalanceFormat, LocalDate.now(), newValue);
			
		} else if ( field == TUITION_PLAN ) {
			
			log = String.format(ChangeTuitionPlanFormat, LocalDate.now(), newValue);
			
		} else {
			
			throw new IllegalArgumentException("Financial Data Logs must be either account balance changes or " +
				   "tuition plan changes");
			
		}
		
		addLog(log);
		
	}
	
	@SuppressWarnings("unused")
	public void changeLevel ( String newLevel ) throws IOException, AppWarning {
		
		String log = String.format(ChangeLevelFormat, LocalDate.now(), newLevel);
		
		addLog(log);
		
	}
	
	@SuppressWarnings("unused")
	public void CastStudent ( String role, String[] casts ) throws IOException, AppWarning {
		
		String log = String.format(CastingFormat, LocalDate.now(), role, readArrayNaturally(casts));
		
		addLog(log);
		
	}
	
	@SuppressWarnings("unused")
	public void signStudentIn ( String classAttending ) throws IOException, AppWarning {
		
		String log = String.format(AttendanceEnteredFormat, LocalDate.now(), LocalTime.now().format(DateFormat),
			   classAttending);
		
		addLog(log);
		
	}
	
	@SuppressWarnings("unused")
	public void signStudentOut ( boolean hourBuffer ) throws IOException, AppWarning {
		
		String allLogs = getFieldValue(AUDIT_LOG);
		
		String[] Logs = allLogs.split("\n");
		
		int logsIndex;
		
		if ( Logs[Logs.length - 1].isBlank() ) {
			
			logsIndex = Logs.length - 2;
			
		} else {
			
			logsIndex = Logs.length - 1;
			
		}
		
		String[] logSplits = Logs[logsIndex].split(DELIMITER);
		
		String date = logSplits[0];
		String timeIn = logSplits[1];
		String className = logSplits[2];
		
		String log = String.format(AttendanceExitFormat, date, timeIn, LocalTime.now(), className);
		
		addLog(log);
		
	}
	
	@SuppressWarnings("unused")
	public void addTeachersNote ( String teacher, String Note ) throws IOException, AppWarning {
		
		String log = String.format(TeachersNoteFormat, LocalDate.now(), Note, teacher);
		
		addLog(log);
		
	}
	
	@SuppressWarnings("unused")
	public String[] getAllRecords () {
		
		String allLogs = this.getFieldValue(AUDIT_LOG);
		
		Scanner logParser = new Scanner(allLogs);
		
		String nextLine;
		
		ArrayList<String> fullFile = new ArrayList<>();
		
		while ( (nextLine = logParser.nextLine()) != null ) {
			
			fullFile.add(nextLine);
			
		}
		
		return fullFile.toArray(new String[0]);
		
	}
	
	public String[] getAllOfRecords ( String[] allowedTypes ) {
		
		String allLogs = this.getFieldValue(AUDIT_LOG);
		
		Scanner logParser = new Scanner(allLogs);
		
		String nextLine;
		
		ArrayList<String> fullFile = new ArrayList<>();
		
		while ( (nextLine = logParser.nextLine()) != null ) {
			
			for ( String DataType : allowedTypes ) {
				
				if ( nextLine.matches(DataType + ".*") ) {
					
					fullFile.add(nextLine);
					
				}
				
			}
			
		}
		
		return fullFile.toArray(new String[0]);
		
	}
	
	@SuppressWarnings("unused")
	public String[] getAllProfileRecords () {
		
		String[] allowedTypes = { PROFILEDATA, ADMINNOTE };
		
		return getAllOfRecords(allowedTypes);
		
	}
	
	@SuppressWarnings("unused")
	public String[] getAllProductionRecords () {
		
		String[] allowedTypes = { CASTING };
		
		return getAllOfRecords(allowedTypes);
		
	}
	
	@SuppressWarnings("unused")
	public String[] getAllAdminRecords () {
		
		String[] allowedTypes = { LEVELCHANGE, FINANCIAL };
		
		return getAllOfRecords(allowedTypes);
		
	}
	
	public double getHoursOverRange ( String startDate, String endDate ) throws Exception {
		
		int minutes = 0;
		
		String[] attendanceRecords = this.getAllOfRecords(new String[]{ ATTENDANCE });
		
		return AttendanceCalculator.getHoursOverRange(attendanceRecords, startDate, endDate);
		
	}
	
	
}
