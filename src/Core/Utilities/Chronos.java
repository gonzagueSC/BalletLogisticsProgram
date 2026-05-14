package Core.Utilities;

import util.Globals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Chronos {
	
	public static final String DateFormat = "yyyy-MM-dd";
	public static String TimeFormat = "HH:mm:ss";
	public static final DateTimeFormatter standardizedDate = DateTimeFormatter.ofPattern(DateFormat);
	public static DateTimeFormatter standardizedTime = DateTimeFormatter.ofPattern(TimeFormat);
	
	public static LocalDate getStandardizedDate ( String date ) throws AppWarning {
		
		for ( String format : InputValidator.allDateFormats ) {
			
			DateTimeFormatter dateParser = DateTimeFormatter.ofPattern(format);
			
			try {
				
				return LocalDate.parse(date, dateParser);
				
			} catch ( Exception _ ) {
			
			}
			
		}
		
		throw new AppWarning("Date is un-parseable");
		
	}
	
	public static LocalTime getStandardizedTime ( String date ) throws AppWarning {
		
		for ( String format : InputValidator.allTimeFormats ) {
			
			DateTimeFormatter dateParser = DateTimeFormatter.ofPattern(format);
			
			try {
				
				return LocalTime.parse(date, dateParser);
				
			} catch ( Exception _ ) {
			
			}
			
		}
		
		throw new AppWarning("Time is un-parseable");
		
	}
	
	/**
	 *
	 * @param chrono time that you input to get a LocalDateTime
	 * @return A generalized Temporal Object that can handle either times, dates, or a combination of both, using
	 * LocalDateTime, but only to be used for quick use, as times will be set to the current day, and dates to the start of
	 * the day.
	 */
	public static LocalDateTime getStandardizedChrono(String chrono) throws AppWarning {
		try {
			return LocalDateTime.parse(chrono);
		} catch (Exception _) {}
		
		try {
			LocalDate date = getStandardizedDate(chrono);
			return date.atStartOfDay();
		} catch (Exception _) {}
		
		try {
			LocalTime time = getStandardizedTime(chrono);
			return time.atDate(LocalDate.now());
		} catch (Exception _) {}
		
		throw new AppWarning("Input could not be converted to a valid Chrono: " + chrono);
	}
	
	@SuppressWarnings("unused")
	public static String standardizeDate ( LocalDate date ) {
		
		return date.format(standardizedDate);
		
	}
	
	@SuppressWarnings("unused")
	public static String standardizeTime ( LocalTime time ) {
		
		return time.format(standardizedTime);
		
	}
	
	/**
	 Returns 1 if time1 comes before time2, 0 if they are the same, and -1 if time1 is after time2
	 **/
	public static int compareChrono(String chrono1, String chrono2) throws AppWarning {
		LocalDateTime dateTime1 = getStandardizedChrono(chrono1);
		LocalDateTime dateTime2 = getStandardizedChrono(chrono2);
		
		return compareChrono(dateTime1, dateTime2);
	}
	
	public static int compareChrono(LocalDateTime chrono1, LocalDateTime chrono2) throws AppWarning {
		
		if (chrono1.isBefore(chrono2)) {
			return 1;
		} else if (chrono1.isAfter(chrono2)) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public static boolean isBetween(String startDate, String endDate, String date) throws AppWarning {
		
		return compareChrono(startDate, date) == 1 && compareChrono(date, endDate) == 1 || compareChrono(startDate,
			   date) == 0 || compareChrono(date, endDate) == 0;
		
	}
	
	public static boolean isBetween(LocalDate startDate, LocalDate endDate, LocalDate date) throws AppWarning {
		
		LocalDateTime dateTime1 = startDate.atStartOfDay();
		LocalDateTime dateTime2 = endDate.atStartOfDay();
		LocalDateTime dateTarget = date.atStartOfDay();
		
		return compareChrono(dateTime1, dateTarget) == 1 && compareChrono(dateTarget, dateTime2) == 1 || compareChrono(dateTime1,
			   dateTarget) == 0 || compareChrono(dateTarget, dateTime2) == 0;
		
	}
	
	public static DayOfWeek getDayOfWeek(String date) throws AppWarning {
		
		return getDayOfWeek(getStandardizedDate(date));
		
	}
	
	public static DayOfWeek getDayOfWeek(LocalDate date) throws AppWarning {
		
		return date.getDayOfWeek();
		
	}
	
	public static boolean isSameDay(String date1, String date2) throws AppWarning {
		
		return compareChrono(date1, date2) == 0;
		
	}
	
	public static boolean isSameDayOfWeek(String date1, String date2) throws AppWarning {
		
		return getDayOfWeek(date1) == getDayOfWeek(date2);
		
	}
	
	public static boolean isSameDayOfWeek(LocalDate date1, LocalDate date2) throws AppWarning {
		
		return getDayOfWeek(date1) == getDayOfWeek(date2);
		
	}
	
	public static boolean isPastDate(String date) throws AppWarning {
		
		return isPastDate(getStandardizedDate(date));
		
	}
	
	public static boolean isPastDate(LocalDate date) throws AppWarning {
		
		return date.isBefore(LocalDate.now());
		
	}
	
	public static int unitsBetween(String startTime, String endTime, String units) throws Exception {
		
		if (!units.equals(Globals.MINUTES) && !units.equals(Globals.HOURS) && !units.equals(Globals.DAYS)) {
			
			return -1;
			
		}
		
		LocalDateTime start = Chronos.getStandardizedChrono(startTime);
		LocalDateTime end = Chronos.getStandardizedChrono(endTime);
		
		return unitsBetween(start, end, units);
		
	}
	
	public static int unitsBetween(LocalDateTime startTime, LocalDateTime endTime, String units) throws Exception {
		
		long time = switch (units) {
			
			case Globals.MINUTES -> ChronoUnit.MINUTES.between(startTime, endTime);
			case Globals.HOURS -> Math.round(ChronoUnit.MINUTES.between(startTime, endTime) / 60.0);
			case Globals.DAYS -> Math.round(ChronoUnit.HOURS.between(startTime, endTime) / 24.0);
			default -> throw new IllegalArgumentException("Unexpected value: " + units);
			
		};
		
		return Math.round(time);
		
	}
	
}
