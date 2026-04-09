package databaseAccess;

import java.time.DayOfWeek;

public class ConflictChecker {
	
	public static boolean hasConflict(String date, String startTime, String endTime, String studio, String teacher,
			String[] roles) throws Exception {

		String studioBooked = isStudioBooked(date, startTime, endTime, studio);
		String teacherBooked = isTeacherBooked(date, startTime, endTime, teacher);
		String areRolesBooked = areRolesBooked(date, startTime, endTime, roles);

		if (!studioBooked.isBlank())
			throw new Exception("Studio is already Booked with " + studioBooked);

		if (!teacherBooked.isBlank())
			throw new Exception("Teacher is already Booked with " + teacherBooked);

		if (!areRolesBooked.isBlank())
			throw new Exception(
					areRolesBooked.split(", ")[0] + " is already Booked with " + areRolesBooked.split(", ")[1]);

		return false;

	}

	public static boolean hasConflict(String[] days, String startTime, String endTime, String studio, String teacher)
			throws Exception {

		String[] allFutureRehearsals = SchedulesModule.getAllUpcomingRehearsals();

		for (String rehearsal : allFutureRehearsals) {

			for (String day : days) {

				String studioBooked = isStudioBooked(rehearsal.split(", ")[1], startTime, endTime, studio);
				String teacherBooked = isTeacherBooked(rehearsal.split(", ")[1], startTime, endTime, teacher);

				DayOfWeek weekDay = switch (day) {

				case "Mon" -> DayOfWeek.MONDAY;
				case "Tue" -> DayOfWeek.TUESDAY;
				case "Wed" -> DayOfWeek.WEDNESDAY;
				case "Thu" -> DayOfWeek.THURSDAY;
				case "Fri" -> DayOfWeek.FRIDAY;
				case "Sat" -> DayOfWeek.SATURDAY;
				case "Sun" -> DayOfWeek.SUNDAY;
				default -> throw new IllegalArgumentException("Unexpected value: " + day);

				};

				if (DatabaseCore.isSameDayOfWeek(DatabaseCore.isValidDate(rehearsal.split(", ")[1]), weekDay)) {

					if (SchedulesModule.Overlaps(startTime, endTime, rehearsal.split(", ")[2],
							rehearsal.split(", ")[3])) {

						if (!studioBooked.isBlank())
							throw new Exception("Studio is already Booked with " + studioBooked);

						if (!teacherBooked.isBlank())
							throw new Exception("Teacher is already Booked with " + teacherBooked);

					}

				}

			}

		}

		String[] allClasses = SchedulesModule.getAllClasses();

		for (String classString : allClasses) {

			if (SchedulesModule.Overlaps(startTime, endTime, classString.split(", ")[2], classString.split(", ")[3])) {

				for (String selectedDay : days) {

					for (String classDay : classString.split(", ")[1].split(" ")) {

						if (selectedDay.equals(classDay)) {

							if (studio.equals(classString.split(",")[4])) {

								throw new Exception("Studio is already Booked with " + classString.split(", ")[0]
										+ " on " + selectedDay);

							}

							if (teacher.equals(classString.split(",")[5])) {

								throw new Exception("Teacher is already Booked with " + classString.split(", ")[0]
										+ " on " + selectedDay);

							}

						}

					}

				}

			}

		}

		return false;

	}
	
	public static String isStudioBooked(String date, String startTime, String endTime, String studio) {

		String[] rehearsalsOfDay = SchedulesModule.getScheduleByDate(date);

		for (String rehearsal : rehearsalsOfDay) {

			if (rehearsal.split(", ")[1].equals(date) && rehearsal.split(", ")[4].equals(studio)) {

				if (SchedulesModule.Overlaps(rehearsal.split(", ")[2], rehearsal.split(", ")[3], startTime, endTime))
					return rehearsal.split(", ")[0];

			}

		}

		return "";

	}

	public static String isTeacherBooked(String date, String startTime, String endTime, String teacher) {

		String[] rehearsalsOfDay = SchedulesModule.getScheduleByDate(date);

		for (String rehearsal : rehearsalsOfDay) {

			if (rehearsal.split(", ")[1].equals(date) && rehearsal.split(", ")[5].equals(teacher)) {

				if (SchedulesModule.Overlaps(rehearsal.split(", ")[2], rehearsal.split(", ")[3], startTime, endTime))
					return rehearsal.split(", ")[0];

			}

		}

		return "";

	}

	public static String areRolesBooked(String date, String startTime, String endTime, String[] roles) {

		String[] rehearsalsOfDay = SchedulesModule.getScheduleByDate(date);

		for (String rehearsal : rehearsalsOfDay) {

			if (rehearsal.split(", ")[1].equals(date)) {

				for (String role : roles) {

					for (String assignedRole : rehearsal.split(", ")[6].split("\\|")) {

						if (role.equals(assignedRole)) {

							if (SchedulesModule.Overlaps(rehearsal.split(", ")[2], rehearsal.split(", ")[3], startTime,
									endTime))
								return role + ", " + rehearsal.split(", ")[0];

						}

					}

				}

			}

		}

		return "";

	}

}
