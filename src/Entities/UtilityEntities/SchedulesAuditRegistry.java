package Entities.UtilityEntities;

import Core.Utilities.AppWarning;
import Core.Utilities.FilePaths;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

public class SchedulesAuditRegistry extends SchedulesGeneralRegistry{
	
	private static final File SCHEDULES_REGISTRY = FilePaths.AuditSchedules;
	
	public SchedulesAuditRegistry () throws IllegalClassFormatException, IOException, AppWarning {
		
		super(SCHEDULES_REGISTRY);
		
	}
	
	

}
