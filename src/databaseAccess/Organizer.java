package databaseAccess;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.ChronoUnit;

import util.Globals;

public class Organizer {

	/**
	 * Checks if a student's full name starts with the given prefix string.
	 *
	 * @param Student The individual student data file.
	 * @param start   The prefix string to check against the student's full name.
	 * @return true if the student's name starts with the prefix, false otherwise.
	 */
	public static boolean runByFilters(File Student, String start) {
	
		boolean result = true;
	
		String[] StudentDetails = new String[Globals.StudentInfo.length];
		BufferedReader StudentInfo;
	
		try {
	
			StudentInfo = new BufferedReader(new FileReader(Student));
	
			// read the first 23 lines (core student details)
			for (int p = 0; p < 23; p++) {
	
				StudentDetails[p] = StudentInfo.readLine();
	
			}
	
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			StudentInfo.close();
	
		} catch (IOException e) {
	
			e.printStackTrace();
	
		}
	
		// check if the combined first and last name starts with the 'start' string
		if ((StudentDetails[0] + " " + StudentDetails[1]).substring(0, start.length()).equals(start))
			return true;
		return false;
	
	}

	/**
	 * Checks if a specific student file matches all non-empty criteria in the
	 * filters array.
	 *
	 * @param Student The individual student data file.
	 * @param filters An array of filter values.
	 * @return true if the student matches all filters, false otherwise.
	 */
	public static boolean runByFilters(File Student, String[] filters) {
	
		boolean result = true;
	
		String[] StudentDetails = new String[Globals.StudentInfo.length];
		BufferedReader StudentInfo;
	
		try {
	
			// open a BufferedReader for the student file
			StudentInfo = new BufferedReader(new FileReader(Student));
	
			// read the first 23 lines (core student details)
			for (int p = 0; p < 23; p++) {
	
				StudentDetails[p] = StudentInfo.readLine();
	
			}
	
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			StudentInfo.close();
	
		} catch (IOException e) {
	
			e.printStackTrace();
	
		}
	
		// iterate through the filters array
		for (int i = 0; i < filters.length; i++) {
	
			// only process non-null and non-empty filters
			if (filters[i] != null && !filters[i].isEmpty()) {
	
				try {
	
					// switch on the field name (from Globals.filters) corresponding to the filter
					// index
					switch (Globals.filters[i]) {
	
					case "First Name":
						if (!StudentDetails[0].equals(filters[i])) {
	
							return false;
	
						}
						break;
	
					case "Last Name":
						if (!StudentDetails[1].equals(filters[i])) {
	
							return false;
	
						}
						break;
	
					case "Medical Conditions":
						// filter matches if Medical Conditions is NOT "None"
						if (StudentDetails[5].equals("None")) {
	
							return false;
	
						}
						break;
	
					case "Age":
						// calculate age in years and check against filter value
						String date = StudentDetails[6];
						LocalDate usedDate = DatabaseCore.isValidDate(date);
						Period life = Period.between(usedDate, LocalDate.now());
						int Years = life.getYears();
						if (Years != Integer.parseInt(filters[i])) {
	
							return false;
	
						}
						break;
	
					case "Gender":
						if (!StudentDetails[7].toLowerCase().equals(filters[i])) {
	
							return false;
	
						}
						break;
	
					case "Level":
						if (!StudentDetails[2].toLowerCase().equals(filters[i])) {
	
							return false;
	
						}
						break;
	
					case "Activity Status":
						if (!StudentDetails[10].toLowerCase().equals(filters[i])) {
	
							return false;
	
						}
						break;
	
					case "Owes Money":
						// filter matches if the balance is positive or zero (not owing money), unless
						// balance is -1
						if (Integer.parseInt(StudentDetails[12]) < 0 && Integer.parseInt(StudentDetails[12]) != -1) {
	
							return false;
	
						}
						break;
	
					}
	
				} catch (Exception e) {
	
					e.printStackTrace();
	
				}
	
			}
	
		}
	
		return result;
	
	}

	/**
	 * Compares the first elements of an array against an array of expected
	 * arguments.
	 *
	 * @param array     The array containing the values (e.g., split line from a
	 *                  file).
	 * @param arguments The array of arguments to check against the values.
	 * @return true if all non-null arguments match the corresponding elements in
	 *         the array; false otherwise.
	 */
	
	/**
	 * Checks if a student entry (Name and Birth Date) already exists in the
	 * registered students file.
	 *
	 * @param database         The registered students file to check.
	 * @param studentName      The student's full name (FirstName LastName).
	 * @param studentBirthDate The student's birth date.
	 * @return true if a duplicate is found, false otherwise.
	 */
	
	/**
	 * Checks if an administrator entry (Admin Name and ID) exists in the admin
	 * credentials file.
	 *
	 * @param database  The admin file to check.
	 * @param AdminName The admin's name.
	 * @param ID        The admin's ID/password.
	 * @return true if a matching entry is found, false otherwise.
	 */
	
	/**
	 * Sorts an array of student file references based on a specified filter
	 * criterion.
	 *
	 * @param students An array of strings, where each string contains student
	 *                 reference data (e.g., Name, BirthDate, ID).
	 * @param filters  An array containing filter values, where the index determines
	 *                 the sort criteria.
	 * @return The sorted array of student file references.
	 */
	public static String[] sortByFilter(String[] students, String[] filters) {
	
		int comparator = 0;
		// create a copy of the input array to hold the reordered student references
		String[] studentRep = students.clone();
		// array to hold the comparison values (names, dates, etc.)
		String[] values = new String[students.length];
	
		// determine which filter index (if present) defines the sort comparator
		if (filters[2] != null && !filters[2].isEmpty())
			comparator = 1; // sort by Level (index 2 in filters) -> studentInfo[2]
		if (filters[3] != null && !filters[3].isEmpty())
			comparator = 2; // sort by Email (index 3 in filters) -> studentInfo[3]
		if (filters[5] != null && !filters[5].isEmpty())
			comparator = 3; // sort by Date Joined (index 5 in filters) -> studentInfo[8]
		if (filters[7] != null && !filters[7].isEmpty())
			comparator = 4; // sort by Date of Birth (index 7 in filters) -> studentInfo[6]
	
		// first loop: extract the value needed for comparison for each student
		for (int i = 0; i < students.length; i++) {
	
			// get the student file for the current student reference (ID is at index 3 of
			// the student string)
			File studentFile = new File(Globals.StudentsFolder, students[i].split(" ")[3]);
	
			try {
	
				// retrieve the core student information
				String[] studentInfo = StudentsModule.getStudentInfo(
						students[i].split(" ")[0] + " " + students[i].split(" ")[1], students[i].split(" ")[2]);
	
				// set the value array based on the determined comparator
				switch (comparator) {
	
				case 0:
					// if no valid filter, return the original array
					return students;
	
				case 1:
					// sort by First Name (or Level if Globals is misaligned)
					values[i] = studentInfo[0];
					break;
	
				case 2:
					// sort by Last Name (or Email if Globals is misaligned)
					values[i] = studentInfo[1];
					break;
	
				case 3:
					// sort by date joined (time difference from now in days, negative for past)
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String date = studentInfo[8];
					LocalDate usedDate = DatabaseCore.isValidDate(date);
					long p = ChronoUnit.DAYS.between(LocalDate.now(), usedDate);
					values[i] = Integer.toString((int) (p));
					break;
	
				case 4:
					// sort by age (time difference from birth date in months, negative for age)
					DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
					String Birth = studentInfo[6];
					LocalDate BirthDate = DatabaseCore.isValidDate(Birth);
					long Life = ChronoUnit.MONTHS.between(LocalDate.now(), BirthDate);
					values[i] = Integer.toString((int) (Life));
					break;
	
				}
	
			} catch (Exception e) {
	
				e.printStackTrace();
	
			}
	
		}
	
		// second loop: perform Bubble Sort on the student references (studentRep) using
		// the comparison values (values)
		for (int i = 0; i < students.length - 1; i++) {
	
			// change flag is redundant here but kept as per original logic
			boolean change = false;
	
			for (int j = 0; j < students.length - i - 1; j++) {
	
				// determine difference for comparison
				int diff = (comparator > 2) ? (Integer.parseInt(values[j]) - Integer.parseInt(values[j + 1])) * -1 // numerical
																													// comparison
																													// for
																													// dates
						: values[j].compareTo(values[j + 1]); // alphabetical/lexicographical comparison
	
				// if diff > 0, swap elements
				if (diff > 0) {
	
					// swap values array elements
					String temp = values[j];
					values[j] = values[j + 1];
					values[j + 1] = temp;
	
					// swap student reference array elements
					temp = studentRep[j];
					studentRep[j] = studentRep[j + 1];
					studentRep[j + 1] = temp;
	
				}
	
			}
	
		}
	
		return studentRep;
	
	}

	/**
	 * Sorts an array of production strings based on their date (MM/dd/yyyy) in
	 * ascending order.
	 *
	 * @param allProductions The array of production strings (Name Date).
	 * @return The array of production strings sorted by date.
	 */
	public static String[] sortProductionsByDate(String[] allProductions) {
	
		String[] Productions = allProductions.clone();
		// time array is not used in the final sorting logic, but kept for historical
		// context
		String[] time = new String[Productions.length];
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
		// perform Bubble Sort
		for (int i = 0; i < Productions.length - 1; i++) {
	
			boolean change = false;
	
			for (int j = 0; j < Productions.length - i - 1; j++) {
	
				// calculate the difference in months between two production dates
				long diff = ChronoUnit.MONTHS.between(
						// parse date from end of string for Production[j]
						LocalDate.parse(Productions[j].substring(Productions[j].length() - 10), format),
						// parse date from end of string for Production[j+1]
						LocalDate.parse(Productions[j + 1].substring(Productions[j + 1].length() - 10), format));
	
				// if diff < 0, it means Production[j]'s date is later than Production[j+1]'s
				// date, so swap them
				if (diff < 0) {
	
					// swap the (unused) time array elements
					String temp = time[j];
					time[j] = time[j + 1];
					time[j + 1] = temp;
	
					// swap the production array elements
					temp = Productions[j];
					Productions[j] = Productions[j + 1];
					Productions[j + 1] = temp;
					change = true;
	
				}
	
			}
	
			// optimization: if no swaps occurred, the array is sorted
			if (!change)
				return Productions;
	
		}
	
		return Productions;
	
	}

}
