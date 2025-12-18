package databaseAccess;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.*;

import util.Globals;

public class SchedulesModule {

	public static String[] getAllClasses() {

		File classes = Globals.ClassSchedules;

		ArrayList<String> futureClasses = new ArrayList<String>();
		String[] fullFile = new String[0];

		try {

			fullFile = DatabaseCore.returnAllFile(classes);

		} catch (IOException e) {

			e.printStackTrace();

		}

		for (String classString : fullFile) {

			if (classString.split(", ").length == 8) {

				futureClasses.add(classString);

			}

		}

		return futureClasses.toArray(new String[0]);

	}

	public static String[] getAllClassesByDate(String date) {

		File classSchedules = Globals.ClassSchedules;

		String[] allProductions = ProductionsModule.getAllProductions(true, false);

		ArrayList<String> allSchedules = new ArrayList<String>();

		LocalDate requiredDate = null;

		try {

			requiredDate = DatabaseCore.isValidDate(date);

		} catch (Exception e) {

			e.printStackTrace();

		}

		try {

			BufferedReader SchedulesReader = new BufferedReader(new FileReader(classSchedules));

			String nextLine;

			while ((nextLine = SchedulesReader.readLine()) != null) {

				String[] line = nextLine.split(", ");
				String[] Days = line[1].split(" ");

				for (int i = 0; i < Days.length; i++) {

					String dayOfWeek = switch (Days[i]) {

					case "Mon" -> "MONDAY";
					case "Tue" -> "TUESDAY";
					case "Wed" -> "WEDNESDAY";
					case "Thu" -> "THURSDAY";
					case "Fri" -> "FRIDAY";
					case "Sat" -> "SATURDAY";
					case "Sun" -> "SUNDAY";

					default -> throw new IllegalArgumentException("Unexpected value: " + Days[i]);

					};

					if (DatabaseCore.isSameDayOfWeek(requiredDate, DayOfWeek.valueOf(dayOfWeek)) && line.length == 8
							&& DatabaseCore.isBetweenDates(date, line[7], "5000-01-01")
							|| line.length == 9
									&& DatabaseCore.isSameDayOfWeek(requiredDate, DayOfWeek.valueOf(dayOfWeek))
									&& DatabaseCore.isBetweenDates(date, line[7], line[8])) {

						allSchedules.add(nextLine);

					}

				}

			}

			SchedulesReader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return allSchedules.toArray(new String[0]);

	}

	public static String[] getAllUpcomingClassesToday() {

		String date = LocalDate.now().toString();

		File classSchedules = Globals.ClassSchedules;

		String[] allProductions = ProductionsModule.getAllProductions(true, false);

		ArrayList<String> allSchedules = new ArrayList<String>();

		LocalDate requiredDate = null;

		try {

			requiredDate = DatabaseCore.isValidDate(date);

		} catch (Exception e) {

			e.printStackTrace();

		}

		try {

			BufferedReader SchedulesReader = new BufferedReader(new FileReader(classSchedules));

			String nextLine;

			while ((nextLine = SchedulesReader.readLine()) != null) {

				String[] line = nextLine.split(", ");
				String[] Days = line[1].split(" ");

				for (int i = 0; i < Days.length; i++) {

					String dayOfWeek = switch (Days[i]) {

					case "Mon" -> "MONDAY";
					case "Tue" -> "TUESDAY";
					case "Wed" -> "WEDNESDAY";
					case "Thu" -> "THURSDAY";
					case "Fri" -> "FRIDAY";
					case "Sat" -> "SATURDAY";
					case "Sun" -> "SUNDAY";

					default -> throw new IllegalArgumentException("Unexpected value: " + Days[i]);

					};

					if (DatabaseCore.isSameDayOfWeek(requiredDate, DayOfWeek.valueOf(dayOfWeek)) && line.length == 8
							&& DatabaseCore.isBetweenDates(date, line[7], "5000-01-01")
							&& LocalTime.now().isBefore(DatabaseCore.isValidTime(line[3]))
							|| line.length == 9
									&& DatabaseCore.isSameDayOfWeek(requiredDate, DayOfWeek.valueOf(dayOfWeek))
									&& DatabaseCore.isBetweenDates(date, line[7], line[8])
									&& LocalTime.now().isBefore(DatabaseCore.isValidTime(line[3]))) {

						allSchedules.add(nextLine);

					}

				}

			}

			SchedulesReader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return allSchedules.toArray(new String[0]);

	}

	public static String[] getAllUpcomingClassesBelowLevelToday(String level) {

		String date = LocalDate.now().toString();

		File classSchedules = Globals.ClassSchedules;

		String[] allProductions = ProductionsModule.getAllProductions(true, false);

		ArrayList<String> allSchedules = new ArrayList<String>();

		LocalDate requiredDate = null;

		try {

			requiredDate = DatabaseCore.isValidDate(date);

		} catch (Exception e) {

			e.printStackTrace();

		}

		try {

			BufferedReader SchedulesReader = new BufferedReader(new FileReader(classSchedules));

			String nextLine;

			while ((nextLine = SchedulesReader.readLine()) != null) {

				String[] line = nextLine.split(", ");
				String[] Days = line[1].split(" ");

				for (int i = 0; i < Days.length; i++) {

					String dayOfWeek = switch (Days[i]) {

					case "Mon" -> "MONDAY";
					case "Tue" -> "TUESDAY";
					case "Wed" -> "WEDNESDAY";
					case "Thu" -> "THURSDAY";
					case "Fri" -> "FRIDAY";
					case "Sat" -> "SATURDAY";
					case "Sun" -> "SUNDAY";

					default -> throw new IllegalArgumentException("Unexpected value: " + Days[i]);

					};

					for (String classLevel : line[6].split("\\|")) {

						if (DatabaseCore.isSameDayOfWeek(requiredDate, DayOfWeek.valueOf(dayOfWeek)) && line.length == 8
								&& DatabaseCore.isBetweenDates(date, line[7], "5000-01-01")
								&& LocalTime.now().isBefore(DatabaseCore.isValidTime(line[3]))
								|| line.length == 9
										&& DatabaseCore.isSameDayOfWeek(requiredDate, DayOfWeek.valueOf(dayOfWeek))
										&& DatabaseCore.isBetweenDates(date, line[7], line[8])
										&& LocalTime.now().isBefore(DatabaseCore.isValidTime(line[3]))
										&& SystemSettingsModule.isAboveInFile(SystemSettingsModule.LEVELS, level, classLevel)) {

							allSchedules.add(nextLine);

						}

					}

				}

			}

			SchedulesReader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return allSchedules.toArray(new String[0]);

	}

	public static String[] getAllUpcomingRehearsals() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate today = LocalDate.now();
		ArrayList<String> allRehearsals = new ArrayList<String>();
		String[] allProductions = ProductionsModule.getAllProductions(true, false);

		try {

			for (int i = 0; i < allProductions.length; i++) {

				String[] rehearsalsByProduction = ProductionsModule
						.getAllRehearsalsByShow(allProductions[i].substring(0, allProductions[i].length() - 11));

				for (int iter = 0; iter < rehearsalsByProduction.length; iter++) {

					String[] line = rehearsalsByProduction[iter].split(", ");
					LocalDateTime usedDate = DatabaseCore.isValidDateTime(line[1] + " " + line[2]);

					long time = ChronoUnit.MINUTES.between(LocalDateTime.now(), usedDate);

					if (time >= 0) {

						allRehearsals.add(allProductions[i].substring(0, allProductions[i].length() - 11) + ", "
								+ rehearsalsByProduction[iter]);

					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return allRehearsals.toArray(new String[0]);

	}

	public static String getMonday(String dateString) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate inputDate = LocalDate.parse(dateString, formatter);

		LocalDate monday = inputDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

		return monday.toString();

	}

	public static String[] getScheduleByDate(String date) {

		File classSchedules = Globals.ClassSchedules;

		String[] allProductions = ProductionsModule.getAllProductions(true, false);

		ArrayList<String> allSchedules = new ArrayList<String>();

		LocalDate requiredDate = null;

		try {

			requiredDate = DatabaseCore.isValidDate(date);

		} catch (Exception e) {

			e.printStackTrace();

		}

		try {

			BufferedReader SchedulesReader = new BufferedReader(new FileReader(classSchedules));

			String nextLine;

			while ((nextLine = SchedulesReader.readLine()) != null) {

				String[] line = nextLine.split(", ");
				String[] Days = line[1].split(" ");

				for (int i = 0; i < Days.length; i++) {

					String dayOfWeek = switch (Days[i]) {

					case "Mon" -> "MONDAY";
					case "Tue" -> "TUESDAY";
					case "Wed" -> "WEDNESDAY";
					case "Thu" -> "THURSDAY";
					case "Fri" -> "FRIDAY";
					case "Sat" -> "SATURDAY";
					case "Sun" -> "SUNDAY";

					default -> throw new IllegalArgumentException("Unexpected value: " + Days[i]);

					};

					if (DatabaseCore.isSameDayOfWeek(requiredDate, DayOfWeek.valueOf(dayOfWeek)) && line.length == 8
							&& DatabaseCore.isBetweenDates(date, line[7], "5000-01-01")
							|| line.length == 9
									&& DatabaseCore.isSameDayOfWeek(requiredDate, DayOfWeek.valueOf(dayOfWeek))
									&& DatabaseCore.isBetweenDates(date, line[7], line[8])) {

						allSchedules.add(nextLine);

					}

				}

			}

			SchedulesReader.close();

			for (int i = 0; i < allProductions.length; i++) {

				String[] rehearsalsByProduction = ProductionsModule
						.getAllRehearsalsByShow(allProductions[i].substring(0, allProductions[i].length() - 11));

				for (int iter = 0; iter < rehearsalsByProduction.length; iter++) {

					String[] line = rehearsalsByProduction[iter].split(", ");

					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

					LocalDate usedDate = DatabaseCore.isValidDate(line[1]);

					long time = ChronoUnit.DAYS.between(requiredDate, LocalDate.parse(line[1], format));

					if (time == 0) {

						allSchedules.add(rehearsalsByProduction[iter]);

					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return allSchedules.toArray(new String[0]);

	}

	public static String[] getWeekDatesFromDate(String dateString) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate inputDate = LocalDate.now();

		try {

			inputDate = DatabaseCore.isValidDate(dateString);

		} catch (Exception e) {

			e.printStackTrace();

		}

		LocalDate monday = inputDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

		String[] dateArray = new String[7];

		for (int i = 0; i < dateArray.length; i++) {

			LocalDate dayDate = monday.plusDays(i);

			dateArray[i] = dayDate.format(formatter);

		}

		return dateArray;

	}

	public static String jumpBackWeek(String dateString) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate inputDate = LocalDate.parse(dateString, formatter);

		LocalDate monday = inputDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

		return monday.minusDays(7).toString();

	}

	public static String jumpWeek(String dateString) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate inputDate = LocalDate.parse(dateString, formatter);

		LocalDate monday = inputDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

		return monday.plusDays(7).toString();

	}

	public static boolean Overlaps(String startTime, String endTime, String checkStartTime, String checkEndTime) {

		DateTimeFormatter main = DateTimeFormatter.ofPattern("h:mm a");

		try {

			LocalTime aStart = DatabaseCore.isValidTime(startTime);
			LocalTime bStart = DatabaseCore.isValidTime(checkStartTime);
			LocalTime aEnd = DatabaseCore.isValidTime(endTime);
			LocalTime bEnd = DatabaseCore.isValidTime(checkEndTime);

			return !(aEnd.isBefore(bStart) || bEnd.isBefore(aStart));

		} catch (Exception e) {

			e.printStackTrace();
			return true;

		}

	}

	public static void addClass(String className, String[] daysOfTheWeek, String studio, String teacher, String level,
			String startTime, String endTime) throws Exception {

		File Classes = Globals.ClassSchedules;
		String daysString = "";

		for (int i = 0; i < daysOfTheWeek.length; i++) {

			String day = switch (daysOfTheWeek[i]) {

			case "Mon" -> "Monday";
			case "Tue" -> "Tuesday";
			case "Wed" -> "Wednesday";
			case "Thu" -> "Thursday";
			case "Fri" -> "Friday";
			case "Sat" -> "Saturday";
			case "Sun" -> "Sunday";
			default -> throw new IllegalArgumentException("Unexpected value: " + daysOfTheWeek[i]);

			};
			daysString += ((i != 0) ? " " : "") + daysOfTheWeek[i];

		}

		//@formatter:off
		// Rehearsal String format: [Class Name], [days (formatted with spaces)], [start time], [end time], [studio], [teacher], [Level], [Date Started],
		// (When class is edited or removed): [Date Ended]
		//@formatter:on

		String fullClassString = "";
		fullClassString += className + ", ";
		fullClassString += daysString + ", ";
		fullClassString += startTime + ", ";
		fullClassString += endTime + ", ";
		fullClassString += studio + ", ";
		fullClassString += teacher + ", ";
		fullClassString += level + ", ";
		fullClassString += LocalDate.now().toString();

		ConflictChecker.hasConflict(daysOfTheWeek, startTime, endTime, studio, teacher);

		DatabaseCore.writeToDatabase(Classes, fullClassString);

	}

	public static void editClass(String classString, String className, String[] daysOfTheWeek, String studio,
			String teacher, String level, String startTime, String endTime) throws Exception {

		File ClassesFile = Globals.ClassSchedules;

		try {

			// read the entire current file content
			Scanner RoleReader = new Scanner(ClassesFile);

			String fullNamesFile = "";

			while (RoleReader.hasNext()) {

				fullNamesFile += RoleReader.nextLine() + "\n";

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			RoleReader.close();

			// prepare to re-write the file
			RoleReader = new Scanner(fullNamesFile);
			BufferedWriter studentsFixer = new BufferedWriter(new FileWriter(ClassesFile));

			String nextLine;

			// re-write all lines except the one matching the student ID

			while (RoleReader.hasNext()) {

				nextLine = RoleReader.nextLine();

				if (!nextLine.strip().equals(classString)) {

					studentsFixer.write(nextLine + "\n");

				} else {

					studentsFixer.write(classString + ", " + LocalDate.now() + "\n");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			studentsFixer.close();
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			RoleReader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		SchedulesModule.addClass(className, daysOfTheWeek, studio, teacher, level, startTime, endTime);

	}

	public static void pauseClass(String classString, String startPause, String pauseUntil) {

		File ClassesFile = Globals.ClassSchedules;

		try {

			// read the entire current file content
			Scanner RoleReader = new Scanner(ClassesFile);

			String fullNamesFile = "";

			while (RoleReader.hasNext()) {

				fullNamesFile += RoleReader.nextLine() + "\n";

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			RoleReader.close();

			// prepare to re-write the file
			RoleReader = new Scanner(fullNamesFile);
			BufferedWriter studentsFixer = new BufferedWriter(new FileWriter(ClassesFile));

			String nextLine;

			// re-write all lines except the one matching the student ID

			while (RoleReader.hasNext()) {

				nextLine = RoleReader.nextLine();

				if (!nextLine.strip().equals(classString)) {

					studentsFixer.write(nextLine + "\n");

				} else {

					studentsFixer.write(classString + ", " + startPause + "\n");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			studentsFixer.close();
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			RoleReader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		DatabaseCore.writeToDatabase(Globals.ClassSchedules,
				classString.substring(0, classString.lastIndexOf(", ")) + ", " + pauseUntil);

	}

	public static void removeClass(String classString) {

		File ClassesFile = Globals.ClassSchedules;

		try {

			// read the entire current file content
			Scanner RoleReader = new Scanner(ClassesFile);

			String fullNamesFile = "";

			while (RoleReader.hasNext()) {

				fullNamesFile += RoleReader.nextLine() + "\n";

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			RoleReader.close();

			// prepare to re-write the file
			RoleReader = new Scanner(fullNamesFile);
			BufferedWriter studentsFixer = new BufferedWriter(new FileWriter(ClassesFile));

			String nextLine;

			// re-write all lines except the one matching the student ID

			while (RoleReader.hasNext()) {

				nextLine = RoleReader.nextLine();

				if (!nextLine.strip().equals(classString)) {

					studentsFixer.write(nextLine + "\n");

				} else {

					studentsFixer.write(classString + ", " + LocalDate.now() + "\n");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			studentsFixer.close();
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			RoleReader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
