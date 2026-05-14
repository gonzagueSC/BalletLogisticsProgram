package Core.Utilities;

import util.Globals;
import static Core.Utilities.DataConstants.*;

public class AttendanceCalculator {
	
	public static double getHoursOverRange(String[] attendanceRecords, String startDate, String endDate) throws Exception {
		
		int minutes = 0;
		
		for (String record : attendanceRecords) {
			
			if ( Chronos.isBetween(record.split(DELIMITER)[0].split(" ")[1], startDate, endDate)) {
				
				String startTime = record.split(DELIMITER)[0].split(" ")[1] + " " + record.split(DELIMITER)[1].split(" ")[0];
				String endTime = record.split(DELIMITER)[0].split(" ")[1] + " " + record.split(DELIMITER)[1].split(" ")[1];
				
				int sessionMinutes = Chronos.unitsBetween(startTime, endTime, Globals.MINUTES);
				minutes += sessionMinutes;
				
			}
			
		}
		
		float preciseHours = minutes / 60.0f;
		
		return preciseHours;
		
	}

}
