package Core.Utilities;

import Core.Databases.DBEntity;
import Core.Databases.EntityMapper;
import Entities.TopLevelEntities.SchedulesRegistry;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import static Core.Utilities.DataConstants.*;

public class SchedulingManager {
	
	public static void checkForConflicts ( Map<EntityField, String> details, boolean Rehearsal ) throws AppWarning,
		   IllegalClassFormatException, InvocationTargetException, NoSuchMethodException, InstantiationException,
		   IllegalAccessException {
		
		String[] daysOfTheWeek;
		
		SchedulesRegistry schedulesRegistry = EntityMapper.getInstance().getEntity(SchedulesRegistry.class);
		
		if ( Rehearsal ) {
			
			String rehearsalDate = details.get(DATE);
			daysOfTheWeek = new String[]{ Chronos.getDayOfWeek(rehearsalDate).toString() };
			
			Map<String, Map<EntityField, String>> rehearsals =
				   schedulesRegistry.getAllRehearsalsForFutureDays(DBEntity.readArray(daysOfTheWeek));
			
			Map<String, Map<EntityField, String>> schedules =
				   schedulesRegistry.getAllSchedulesForFutureDays(DBEntity.readArray(daysOfTheWeek));
			
			for ( String key : rehearsals.keySet() ) {
				
				Map<EntityField, String> rehearsal = rehearsals.get(key);
				
				hasConflictsRehearsals(rehearsal, details);
				
			}
			
			for ( String key : schedules.keySet() ) {
				
				Map<EntityField, String> schedule = schedules.get(key);
				
				hasConflictsMixed(details, schedule);
				
			}
			
		} else {
			
			String daysOfWeek = details.get(DAYS_OF_THE_WEEK);
			
			Map<String, Map<EntityField, String>> rehearsals =
				   schedulesRegistry.getAllRehearsalsForFutureDays(daysOfWeek);
			
			Map<String, Map<EntityField, String>> schedules =
				   schedulesRegistry.getAllSchedulesForFutureDays(daysOfWeek);
			
			for ( String key : rehearsals.keySet() ) {
				
				Map<EntityField, String> rehearsal = rehearsals.get(key);
				
				hasConflictsMixed(rehearsal, details);
				
			}
			
			for ( String key : schedules.keySet() ) {
				
				Map<EntityField, String> schedule = schedules.get(key);
				
				hasConflictSchedules(schedule, details);
				
			}
			
		}
		
	}
	
	public static void hasConflictsRehearsals ( Map<EntityField, String> rehearsal1,
	                                       Map<EntityField, String> rehearsal2 ) throws AppWarning {
		
		String rehearsal1StartTime = rehearsal1.get(START_TIME);
		String rehearsal2StartTime = rehearsal2.get(START_TIME);
		String rehearsal1EndTime = rehearsal1.get(END_TIME);
		String rehearsal2EndTime = rehearsal2.get(END_TIME);
		
		if ( Chronos.isBetween(rehearsal1StartTime, rehearsal1EndTime, rehearsal2StartTime) || Chronos.isBetween(rehearsal1StartTime, rehearsal1EndTime, rehearsal2EndTime) ) {
			
			if ( rehearsal1.get(TEACHER).equals(rehearsal2.get(TEACHER)) )
				throw new AppWarning("Teacher is already booked at this time");
			if ( rehearsal1.get(LOCATION).equals(rehearsal2.get(LOCATION)) )
				throw new AppWarning("Studio is already booked at this time");
			
			for ( String casting : rehearsal1.get(CAST).split(",") ) {
				if ( rehearsal2.get(CAST).contains(casting) ) {
					throw new AppWarning("There is a casting conflict");
				}
			}
			
		}
		
	}
	
	public static void hasConflictsMixed ( Map<EntityField, String> rehearsal, Map<EntityField, String> schedule ) throws AppWarning {
		
		String rehearsalStartTime = rehearsal.get(START_TIME);
		String scheduleStartTime = schedule.get(START_TIME);
		String rehearsalEndTime = rehearsal.get(END_TIME);
		String scheduleEndTime = schedule.get(END_TIME);
		
		if ( Chronos.isBetween(rehearsalStartTime, rehearsalEndTime, scheduleStartTime) || Chronos.isBetween(rehearsalStartTime, rehearsalEndTime, scheduleEndTime) ) {
			
			String rehearsalTeacher = rehearsal.get(TEACHER);
			String rehearsalLocation = rehearsal.get(LOCATION);
			String scheduleTeacher = schedule.get(TEACHER);
			String scheduleLocation = schedule.get(LOCATION);
			
			if ( rehearsalTeacher.equals(scheduleTeacher) )
				throw new AppWarning("Teacher is already booked for this time");
			
			if ( rehearsalLocation.equals(scheduleLocation) )
				throw new AppWarning("Studio is already booked for this time");
			
		}
		
	}
	
	public static void hasConflictSchedules ( Map<EntityField, String> schedule1,
	                                         Map<EntityField, String> schedule2 ) throws AppWarning {
		
		String schedule1StartTime = schedule1.get(START_TIME);
		String schedule2StartTime = schedule2.get(START_TIME);
		String schedule1EndTime = schedule1.get(END_TIME);
		String schedule2EndTime = schedule2.get(END_TIME);
		
		if ( Chronos.isBetween(schedule1StartTime, schedule1EndTime, schedule2StartTime) || Chronos.isBetween(schedule1StartTime, schedule1EndTime, schedule2EndTime) ) {
			
			String schedule1Teacher = schedule1.get(TEACHER);
			String schedule1Location = schedule1.get(LOCATION);
			String schedule2Teacher = schedule2.get(TEACHER);
			String schedule2Location = schedule2.get(LOCATION);
			
			if ( schedule1Teacher.equals(schedule2Teacher) )
				throw new AppWarning("Teacher is already booked for this time");
			
			if ( schedule1Location.equals(schedule2Location) )
				throw new AppWarning("Studio is already booked for this time");
			
		}
		
	}
	
}
