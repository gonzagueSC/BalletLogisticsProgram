package Entities.UtilityEntities;

import Core.Databases.SimpleRegistry;
import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import static Core.Utilities.DataConstants.*;

public abstract class SchedulesGeneralRegistry extends SimpleRegistry {
	
	protected static final String format = "SCHEDULE-%04d";
	
	public static final EntityField[] ENTITY_FIELDS = { NAME, LOCATION, START_TIME, END_TIME, DAYS_OF_THE_WEEK, START_DATE, END_DATE };
	
	
	
	public SchedulesGeneralRegistry (File file) throws IllegalClassFormatException, IOException, AppWarning {
		
		super(file, ENTITY_FIELDS, format);
		
	}
	
	@SuppressWarnings("unused")
	public String createSchedule ( String scheduleName, String scheduleStudio, String scheduleStartTime, String scheduleEndTime, String scheduleDaysOfTheWeek, String scheduleStart) throws AppWarning, IOException, IllegalClassFormatException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		HashMap<EntityField, String> schedule = setUpSchedule(scheduleName, scheduleStudio, scheduleStartTime, scheduleEndTime, scheduleDaysOfTheWeek, scheduleStart);
		
		return this.createNewEntry(schedule);
		
	}
	
	protected static HashMap<EntityField, String> setUpSchedule ( String scheduleName, String scheduleStudio, String scheduleStartTime, String scheduleEndTime, String scheduleDaysOfTheWeek, String scheduleStart ) {
		
		HashMap<EntityField, String> schedule = new HashMap<>();
		
		schedule.put(NAME, scheduleName);
		schedule.put(LOCATION, scheduleStudio);
		schedule.put(START_TIME, scheduleStartTime);
		schedule.put(END_TIME, scheduleEndTime);
		schedule.put(DAYS_OF_THE_WEEK, scheduleDaysOfTheWeek);
		schedule.put(START_DATE, scheduleStart);
		return schedule;
	}
	
	public String createSchedule( Map<EntityField, String> fields) throws AppWarning, IOException {
		
		return this.createNewEntry(fields);
		
	}
	
	public void setScheduleEnd(String ID, String end) throws AppWarning, IOException {
		
		this.changeRegisterValue(ID, END_DATE, end, false);
		
	}
	
}
