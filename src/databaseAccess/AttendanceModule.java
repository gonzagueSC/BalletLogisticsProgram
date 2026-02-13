package databaseAccess;

import java.io.*;
import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import databaseConstants.DatabaseUtilities;
import util.Globals;

public class AttendanceModule {

	public static void CheckStudentIn(String studentID, String className) throws Exception {

		File AttendanceFile = Globals.AttendanceToday;

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

		BufferedWriter CheckIn = new BufferedWriter(new FileWriter(AttendanceFile, true));

		CheckIn.write(studentID + ", " + className + ", " + LocalTime.now().format(timeFormat) + "\n");

		File StudentLog = new File(Globals.StudentsFolder, studentID);

		BufferedWriter LogCheckIn = new BufferedWriter(new FileWriter(StudentLog, true));

		LogCheckIn.write(String.format(Globals.AttendanceEnteredFormat, LocalDate.now().toString(),
				LocalTime.now().format(timeFormat), className) + "\n");

		LogCheckIn.close();

		CheckIn.close();

	}

	public static void CheckStudentOut(String studentID) {

		File AttendanceFile = Globals.AttendanceToday;

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

		try {

			// read the entire current file content
			Scanner RoleReader = new Scanner(AttendanceFile);

			String fullNamesFile = "";

			while (RoleReader.hasNext()) {

				fullNamesFile += RoleReader.nextLine() + "\n";

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			RoleReader.close();

			// prepare to re-write the file
			RoleReader = new Scanner(fullNamesFile);
			BufferedWriter studentsFixer = new BufferedWriter(new FileWriter(AttendanceFile));

			String nextLine;

			// re-write all lines except the one matching the student ID

			while (RoleReader.hasNext()) {

				nextLine = RoleReader.nextLine();

				if (!nextLine.split(", ")[0].strip().equals(studentID) || nextLine.split(", ").length == 4) {

					studentsFixer.write(nextLine + "\n");

				} else {

					studentsFixer.write(nextLine + ", " + LocalTime.now().format(timeFormat) + "\n");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			studentsFixer.close();
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			RoleReader.close();

			File studentFile = new File(Globals.StudentsFolder, studentID);

			Scanner AttendanceReader = new Scanner(studentFile);

			String fullFile = "";

			while (AttendanceReader.hasNext()) {

				fullFile += AttendanceReader.nextLine() + "\n";

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			AttendanceReader.close();

			// prepare to re-write the file
			AttendanceReader = new Scanner(fullFile);
			BufferedWriter writer = new BufferedWriter(new FileWriter(studentFile));

			String NextLine;

			// re-write all lines except the one matching the student ID

			while (AttendanceReader.hasNext()) {

				NextLine = AttendanceReader.nextLine();

				if (NextLine.split(":\\s").length > 1 && NextLine.split(":\\s")[1].split(" ").length < 2
						&& NextLine.split(" ")[0].equals(Globals.ATTENDANCE)) {

					writer.write(String.format(Globals.AttendanceExitFormat, NextLine.split(":\\s")[0].split(" ")[1],
							NextLine.split(":\\s")[1].split(" ")[0], LocalTime.now().format(timeFormat),
							NextLine.split(":\\s")[2]) + "\n");

				} else {

					writer.write(NextLine + "\n");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			writer.close();
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			AttendanceReader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static void CheckStudentOut(String studentID, File date, int timeAfterEntry) {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

		try {

			File DayAttendance = date;

			Scanner RoleReader = new Scanner(DayAttendance);

			String fullNamesFile = "";

			while (RoleReader.hasNext()) {

				fullNamesFile += RoleReader.nextLine() + "\n";

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			RoleReader.close();

			// prepare to re-write the file
			RoleReader = new Scanner(fullNamesFile);
			BufferedWriter studentsFixer = new BufferedWriter(new FileWriter(DayAttendance));

			String nextLine;

			// re-write all lines except the one matching the student ID

			while (RoleReader.hasNext()) {

				nextLine = RoleReader.nextLine();

				if (!nextLine.split(", ")[0].strip().equals(studentID) || nextLine.split(", ").length == 4
						|| date.getName().equals("Attendance:" + Date.valueOf(LocalDate.now()))) {

					studentsFixer.write(nextLine + "\n");

				} else {

					studentsFixer.write(nextLine + ", " + DatabaseCore.isValidTime(nextLine.split(", ")[2])
							.plusMinutes(timeAfterEntry).format(timeFormat) + "\n");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			studentsFixer.close();
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			RoleReader.close();

			File studentFile = new File(Globals.StudentsFolder, studentID);

			Scanner AttendanceReader = new Scanner(studentFile);

			String fullFile = "";

			while (AttendanceReader.hasNext()) {

				fullFile += AttendanceReader.nextLine() + "\n";

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			AttendanceReader.close();

			// prepare to re-write the file
			AttendanceReader = new Scanner(fullFile);
			BufferedWriter writer = new BufferedWriter(new FileWriter(studentFile));

			String NextLine;

			// re-write all lines except the one matching the student ID

			while (AttendanceReader.hasNext()) {

				NextLine = AttendanceReader.nextLine();

				if (NextLine.split(":\\s").length > 1 && NextLine.split(":\\s")[1].split(" ").length < 2
						&& NextLine.split(" ")[0].equals(Globals.ATTENDANCE)
						&& !DatabaseCore.isValidDate(NextLine.split(":\\s")[0].split(" ")[1]).toString()
								.equals(LocalDate.now().toString())) {

					writer.write(NextLine + " " + DatabaseCore.isValidTime(NextLine.split(":\\s")[1])
							.plusMinutes(timeAfterEntry).format(timeFormat) + "\n");

				} else {

					writer.write(NextLine + "\n");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			writer.close();
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			AttendanceReader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static void checkForNotCheckedOut(File attendanceToday) {

		// Global time variable

		int timeAfterEntryIfNotCheckedOut = 90;

		try {

			String[] allAttendanceToday = DatabaseCore.returnAllFile(attendanceToday);

			for (String attendanceLog : allAttendanceToday) {

				if (attendanceLog.split(", ").length == 3) {

					AttendanceModule.CheckStudentOut(attendanceLog.split(", ")[0], attendanceToday,
							timeAfterEntryIfNotCheckedOut);

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static String[] getAllAttendanceRecords(String studentID) throws Exception {

		File database = StudentsModule.getStudentFile(studentID);
		String[] allFile = DatabaseCore.returnAllFile(database);

		ArrayList<String> fullFile = new ArrayList<String>();

		for (String record : allFile) {

			if (record.matches(Globals.ATTENDANCE + ".*")) {

				fullFile.add(record);

			}

		}

		return fullFile.toArray(new String[0]);

	}

	public static String[] getAllStatusRecords(String studentID) throws Exception {

		File database = StudentsModule.getStudentFile(studentID);
		String[] allFile = DatabaseCore.returnAllFile(database);

		ArrayList<String> fullFile = new ArrayList<String>();

		for (String record : allFile) {

			if (record.matches(Globals.PROFILEDATA + ".*") && record.contains("Status Changed to")) {

				fullFile.add(record);

			}

		}

		return fullFile.toArray(new String[0]);

	}

	public static int getHoursOverRange(String startDate, String endDate, String studentID) throws Exception {

		int minutes = 0;

		String[] attendanceRecords = AttendanceModule.getAllAttendanceRecords(studentID);

		ArrayList<String> attendanceWithinRange = new ArrayList<String>();

		for (String record : attendanceRecords) {

			if (DatabaseCore.isBetweenDates(record.split(":\\s")[0].split(" ")[1], startDate, endDate)) {

				attendanceWithinRange.add(record);
				String startTime = record.split(":\\s")[1].split(" ")[0];
				String endTime = record.split(":\\s")[1].split(" ")[1];

				int sessionMinutes = DatabaseCore.getUnitsBetweenTimes(startTime, endTime, Globals.MINUTES);
				minutes += sessionMinutes;

			}

		}

		float preciseHours = minutes / 60.0f;

		return Math.round(preciseHours);

	}

	public static double getComplianceScoreOverRange(String startDate, String endDate, String studentID)
			throws Exception {

		int classesPerWeek = Integer
				.parseInt(
						SystemSettingsModule
								.getSystemDetail(SystemSettingsModule.LEVELS,
										StudentsModule.getStudentDetail(studentID, DatabaseUtilities.LEVEL))
								.split(", ")[2]);
		int daysBetweenDates = DatabaseCore.getUnitsBetweenTimes(startDate, endDate, Globals.DAYS);
		double weeks = daysBetweenDates / 7.0;

		String[] statusChanges = AttendanceModule.getAllStatusRecords(studentID);

		for (int i = 0; i < statusChanges.length; i++) {

			if (statusChanges[i].contains("Injured") || statusChanges[i].contains("Out")) {

				String changeDate = statusChanges[i].split(":\\s")[0].split(" ")[1];

				if (i == statusChanges.length - 1) {

					if (DatabaseCore.isValidDateTime(startDate).isBefore(DatabaseCore.isValidDateTime(changeDate))) {

						int daysSinceOut = DatabaseCore.getUnitsBetweenTimes(changeDate, endDate,
								Globals.DAYS);
						double weeksSinceOut = daysSinceOut / 7.0;
						weeks -= weeksSinceOut;

					} else {

						return 100.0;

					}

				} else {

					String statusReverted = statusChanges[i + 1];

					String RevertDate = statusReverted.split(":\\s")[0].split(" ")[1];

					int daysOut;

					if (DatabaseCore.isValidDateTime(changeDate).isBefore(DatabaseCore.isValidDateTime(startDate))
							&& DatabaseCore.isValidDateTime(RevertDate).isAfter(DatabaseCore.isValidDateTime(startDate))
							&& DatabaseCore.isValidDateTime(RevertDate)
									.isBefore(DatabaseCore.isValidDateTime(endDate))) {

						daysOut = DatabaseCore.getUnitsBetweenTimes(startDate, RevertDate, Globals.DAYS);

					}

					else if (DatabaseCore.isValidDateTime(changeDate).isBefore(DatabaseCore.isValidDateTime(startDate))
							&& DatabaseCore.isValidDateTime(RevertDate)
									.isAfter(DatabaseCore.isValidDateTime(endDate))) {

						return 100.0;

					}

					else if (DatabaseCore.isValidDateTime(changeDate).isAfter(DatabaseCore.isValidDateTime(startDate))
							&& DatabaseCore.isValidDateTime(RevertDate)
									.isAfter(DatabaseCore.isValidDateTime(endDate))) {

						daysOut = DatabaseCore.getUnitsBetweenTimes(changeDate, endDate, Globals.DAYS);

					}

					else if (DatabaseCore.isValidDateTime(changeDate).isAfter(DatabaseCore.isValidDateTime(startDate))
							&& DatabaseCore.isValidDateTime(RevertDate)
									.isBefore(DatabaseCore.isValidDateTime(endDate))) {

						daysOut = DatabaseCore.getUnitsBetweenTimes(changeDate, RevertDate, Globals.DAYS);

					} else {

						daysOut = 0;

					}

					double weeksOut = daysOut / 7.0;
					weeks -= weeksOut;

				}

			}

		}

		String[] attendanceRecords = AttendanceModule.getAllAttendanceRecords(studentID);
		
		int classesTaken = 0;

		for (String record: attendanceRecords) {
			
			if (DatabaseCore.isBetweenDates(record.split(":\\s")[0].split(" ")[1], startDate, endDate) && !record.split(":\\s")[2].equals("Open Practice")) {
				
				classesTaken++;
				
			}
			
		}
		
		double ReqClasses = weeks * classesPerWeek;
		
		if (ReqClasses <= 0) return 100.0;

		return (classesTaken/ReqClasses) * 100;

	}

}
