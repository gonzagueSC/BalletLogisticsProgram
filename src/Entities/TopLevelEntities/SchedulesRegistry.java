package Entities.TopLevelEntities;

import Core.Databases.EntityMapper;
import Core.Utilities.*;
import Entities.ObjectEntities.RehearsalsRegistry;
import Entities.UtilityEntities.SchedulesAuditRegistry;
import Entities.UtilityEntities.SchedulesGeneralRegistry;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static Core.Utilities.DataConstants.*;

public class SchedulesRegistry extends SchedulesGeneralRegistry {
	
	private SchedulesAuditRegistry auditRegistry;
	
	private static final File SCHEDULES_REGISTRY = FilePaths.ClassSchedules;
	
	public SchedulesRegistry () throws IllegalClassFormatException, IOException, AppWarning {
		
		super(SCHEDULES_REGISTRY);
		
		cleanUp();
		
	}
	
	@Override
	public String createSchedule( String scheduleName, String scheduleStudio, String scheduleStartTime, String scheduleEndTime, String scheduleDaysOfTheWeek, String scheduleStart) throws AppWarning, IOException, IllegalClassFormatException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		Map<EntityField, String> schedule = SchedulesGeneralRegistry.setUpSchedule(scheduleName, scheduleStudio, scheduleStartTime, scheduleEndTime, scheduleDaysOfTheWeek, scheduleStart);
		
		SchedulingManager.checkForConflicts(schedule, false);
		
		return this.createNewEntry(schedule);
		
	}
	
	private SchedulesAuditRegistry auditRegistry () throws IllegalClassFormatException, IOException, AppWarning {
		
		if ( auditRegistry == null ) {
			auditRegistry = new SchedulesAuditRegistry();
		}
		
		return auditRegistry;
		
	}
	
	@SuppressWarnings("unused")
	public void editDetail ( String ID, EntityField field, String value ) throws AppWarning,
		   IllegalClassFormatException, IOException {
		
		ValidateID(ID);
		
		Map<EntityField, String> details = this.getMapForID(ID);
		
		String auditID = auditRegistry().createSchedule(details);
		
		auditRegistry().setScheduleEnd(auditID, LocalDate.now().toString());
		
		details.put(START_DATE, LocalDate.now().toString());
		
		details.put(field, value);
		
		this.createNewEntry(details);
		
		this.deleteRegister(ID);
		
	}
	
	public void endSchedule ( String ID ) throws IllegalClassFormatException, IOException, AppWarning {
		
		ValidateID(ID);
		
		Map<EntityField, String> details = this.getMapForID(ID);
		
		String auditID = auditRegistry().createSchedule(details);
		
		auditRegistry().setScheduleEnd(auditID, LocalDate.now().toString());
		
		this.deleteRegister(ID);
		
	}
	
	@SuppressWarnings("unused")
	public Map<String, Map<EntityField, String>> getAllSchedulesForDate ( String date ) throws AppWarning,
		   IOException, IllegalClassFormatException {
		
		HashMap<String, Map<EntityField, String>> events = new HashMap<>();
		
		DayOfWeek dayOfWeek = Chronos.getDayOfWeek(date);
		
		for ( String key : this.getDataMap().keySet() ) {
			
			String startDate = this.getFieldValue(key, START_DATE);
			String endDate = this.getFieldValue(key, END_DATE);
			List<String> daysOfWeek = new ArrayList<>(this.getFieldValue(key,
				   DAYS_OF_THE_WEEK));
			
			if ( Chronos.isBetween(startDate, endDate, date) && daysOfWeek.contains(dayOfWeek.toString()) ) {
				
				events.put(key, this.getMapForID(key));
				
			}
			
		}
		
		for ( String key : this.auditRegistry().getDataMap().keySet() ) {
			
			String startDate = this.auditRegistry().getFieldValue(key, START_DATE);
			String endDate = this.auditRegistry().getFieldValue(key, END_DATE);
			List<String> daysOfWeek = new ArrayList<>(this.auditRegistry().getFieldValue(key,
				   DAYS_OF_THE_WEEK));
			
			if ( Chronos.isBetween(startDate, endDate, date) && daysOfWeek.contains(dayOfWeek.toString()) ) {
				
				events.put(key, this.auditRegistry().getMapForID(key));
				
			}
			
		}
		
		return events;
		
	}
	
	public Map<String, Map<EntityField, String>> getAllSchedulesForFutureDays(String daysOfWeek) {
		
		HashMap<String, Map<EntityField, String>> events = new HashMap<>();
		
		String[] daysOfWeekList = daysOfWeek.split(", ");
		
		for ( String key : this.getDataMap().keySet() ) {
			
			List<String> IndexedDaysOfWeek = new ArrayList<>(this.getFieldValue(key,
				   DAYS_OF_THE_WEEK));
			
			for (String day: daysOfWeekList) {
				
				if (IndexedDaysOfWeek.contains(day)) {
					
					events.put(key, this.getMapForID(key));
					
				}
				
			}
			
		}
		
		return events;
	
	}
	
	@SuppressWarnings("unused")
	public Map<String, Map<EntityField, String>> getAllRehearsalsForDate ( String date ) throws IllegalClassFormatException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, AppWarning {
		
		HashMap<String, Map<EntityField, String>> events = new HashMap<>();
		
		ProductionsRegistryEntity productionsRegistry =
			   EntityMapper.getInstance().getEntity(ProductionsRegistryEntity.class);
		
		for ( String productionName : productionsRegistry.getAllProductions() ) {
			
			RehearsalsRegistry rehearsals =
				   productionsRegistry.getProduction(productionName).getRehearsalsRegistry();
			
			for ( String key : rehearsals.getRehearsals().keySet() ) {
				
				String rehearsalDate = rehearsals.getMapForID(key).get(DATE);
				
				if ( Chronos.isSameDay(rehearsalDate, date) ) {
					
					events.put(key, rehearsals.getMapForID(key));
					
				}
				
			}
			
		}
		
		return events;
		
	}
	
	public Map<String, Map<EntityField, String>> getAllRehearsalsForFutureDays(String daysOfWeek) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalClassFormatException, AppWarning {
		
		HashMap<String, Map<EntityField, String>> events = new HashMap<>();
		
		ProductionsRegistryEntity productionsRegistry =
			   EntityMapper.getInstance().getEntity(ProductionsRegistryEntity.class);
		
		for ( String productionName : productionsRegistry.getAllProductions() ) {
			
			RehearsalsRegistry rehearsals =
				   productionsRegistry.getProduction(productionName).getRehearsalsRegistry();
			
			for ( String key : rehearsals.getRehearsals().keySet() ) {
				
				String rehearsalDate = rehearsals.getMapForID(key).get(DATE);
				
				for (String day: daysOfWeek.split(", ")) {
					
					if ( Chronos.isSameDayOfWeek(rehearsalDate, day)) {
						
						events.put(key, rehearsals.getMapForID(key));
						
					}
					
				}
				
			}
			
		}
		
		return events;
		
	}
	
	public void cleanUp() throws AppWarning, IllegalClassFormatException, IOException {
		
		for (String key : this.getDataMap().keySet()) {
			
			String endDate = this.getFieldValue(key, END_DATE);
			
			if (endDate != null && Chronos.isPastDate(endDate)) {
				
				this.endSchedule(key);
				
			}
			
		}
		
	}
	
}
