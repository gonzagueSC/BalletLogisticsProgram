package databaseAccess;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Globals;

public class DatabaseCore {

	public static void skipLines(Scanner file, int lines) {

		for (int i = 0; i < lines; i++) {

			// ADVANCE THE SCANNER TO THE NEXT LINE

			file.nextLine();

		}

	}

	public static void skipLines(BufferedReader file, int lines) {

		for (int i = 0; i < lines; i++) {

			try {

				// READ AND DISCARD THE NEXT LINE

				file.readLine();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

	public static String[] returnAllFile(File readFile) throws IOException {

		// SINCE WE DO NOT KNOW THE FILE LENGTH, USE AN ARRAYLIST FIRST

		ArrayList<String> fullFile = new ArrayList<String>();

		// OPEN A FILEREADER FOR THE GIVEN FILE

		BufferedReader fileReader = new BufferedReader(new FileReader(readFile));

		// LOOP THROUGH THE ENTIRE FILE, ADDING EVERY LINE

		String nextLine;

		while ((nextLine = fileReader.readLine()) != null) {

			fullFile.add(nextLine);

		}

		// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

		fileReader.close();

		// SWITCH THE ARRAYLIST TO AN ARRAY TO RETURN

		return fullFile.toArray(new String[0]);

	}

	public static String[] returnFileExcerpt(File readFile, int startLine, int endLine)
			throws IOException, IndexOutOfBoundsException {

		// startLine and endLine must be referenced from 0

		// CREATE THE ARRAY BASED ON THE GIVEN EXCERPT LENGTH

		String[] fileExcerpt = new String[endLine - startLine];

		// OPEN A FILEREADER FOR THE GIVEN FILE

		BufferedReader fileReader = new BufferedReader(new FileReader(readFile));

		// JUMP TO THE EXCERPT AREA

		DatabaseCore.skipLines(fileReader, startLine);

		// LOOP THROUGH THE LENGTH OF THE EXCERPT TO GET ONLY THE EXCERPT

		for (int i = 0; i < fileExcerpt.length; i++) {

			String nextLine = fileReader.readLine();

			// IF THE LINE DOES NO EXIST, THROW AN EXCEPTION

			if (nextLine == null) {

				fileReader.close();
				throw new IndexOutOfBoundsException(
						"range " + startLine + " - " + endLine + " out of bounds for file " + readFile.getName());

			}

			// OTHERWISE SIMPLY ADD THE LINE

			fileExcerpt[i] = nextLine;

		}

		// RETURN THE GENERATED EXCERPT

		return fileExcerpt;

	}

	public static boolean checkForLine(File readFile, String regex, String... args)
			throws IOException, IndexOutOfBoundsException {

		// CREATE A FILE READER FOR THE FILE GIVEN

		BufferedReader fileReader = new BufferedReader(new FileReader(readFile));

		// LOOPS THROUGH THE ENTIRE FILE WORST CASE

		String nextLine;

		while ((nextLine = fileReader.readLine()) != null) {

			String[] line = nextLine.split(regex);

			// IF THERE IS A LINE THAT DOESN'T HAVE ENOUGH ARGUMENTS TO MATCH THE GIVEN
			// ARGUMENTS, THROW AN EXCEPTION

			if (line.length < args.length && !nextLine.isBlank()) {

				fileReader.close();
				throw new IndexOutOfBoundsException("Too many parsing arguments | line: " + Arrays.toString(line)
						+ ", args: " + Arrays.toString(args));

			}

			// IF THE LINE MATCHES THE GIVEN ARGUMENTS, THEN RETURN TRUE EARLY

			if (DatabaseCore.compareArguments(line, args)) {

				fileReader.close();
				return true;

			}

		}

		// CLOSE THE FILE READER AND RETURN FALSE IF THE LINE WAS NOT FOUND

		fileReader.close();
		return false;

	}

	public static int getLineIndex(File database, String line) throws IOException {

		BufferedReader fileReader = new BufferedReader(new FileReader(database));

		// LOOPS THROUGH THE ENTIRE FILE WORST CASE

		String nextLine;
		int index = 0;

		while ((nextLine = fileReader.readLine()) != null) {

			// IF THE LINE MATCHES THE GIVEN ARGUMENTS, THEN RETURN TRUE EARLY

			if (line.equals(nextLine)) {

				fileReader.close();
				return index;

			}

			index++;

		}

		// CLOSE THE FILE READER AND RETURN FALSE IF THE LINE WAS NOT FOUND

		fileReader.close();
		return -1;

	}

	public static int getLineIndex(File database, String regex, String... args)
			throws IOException, IndexOutOfBoundsException {
		
		// CREATE A FILE READER FOR THE FILE GIVEN

				BufferedReader fileReader = new BufferedReader(new FileReader(database));

				// LOOPS THROUGH THE ENTIRE FILE WORST CASE

				String nextLine;
				int index = 0;

				while ((nextLine = fileReader.readLine()) != null) {

					String[] line = nextLine.split(regex);

					// IF THERE IS A LINE THAT DOESN'T HAVE ENOUGH ARGUMENTS TO MATCH THE GIVEN
					// ARGUMENTS, THROW AN EXCEPTION

					if (line.length < args.length && !nextLine.isBlank()) {

						fileReader.close();
						throw new IndexOutOfBoundsException("Too many parsing arguments | line: " + Arrays.toString(line)
								+ ", args: " + Arrays.toString(args));

					}

					// IF THE LINE MATCHES THE GIVEN ARGUMENTS, THEN RETURN TRUE EARLY

					if (DatabaseCore.compareArguments(line, args)) {

						fileReader.close();
						return index;

					}
					
					index++;

				}

				// CLOSE THE FILE READER AND RETURN FALSE IF THE LINE WAS NOT FOUND

				fileReader.close();
				return -1;

	}

	public static int writeToDatabase(File database, String s) {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(database, true));

			writer.write(s + "\n");

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			writer.close();
			return 1;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return -1;

	}

	public static void editLine(File database, String originalLine, String newLine) {

		try {

			Scanner LineCopier = new Scanner(database);

			String fullFile = "";

			while (LineCopier.hasNext()) {

				fullFile += LineCopier.nextLine() + "\n";

			}

			LineCopier.close();

			LineCopier = new Scanner(fullFile);
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(database));

			String nextLine;

			while (LineCopier.hasNextLine()) {

				nextLine = LineCopier.nextLine();

				if (nextLine.equals(originalLine)) {

					fileWriter.write(newLine + "\n");

				} else {

					fileWriter.write(nextLine + "\n");

				}

			}

			LineCopier.close();
			fileWriter.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static File CreateFile(File folder, String name) {

		// GET FILE PATH

		File newFile = new File(folder, name);

		// CHECK IF IT EXISTS

		if (!newFile.exists()) {

			// CREATE THE FILE (AND CATCH THE EXCEPTION OF AN IO ERROR)

			try {

				newFile.createNewFile();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		// RETURN THE FILE THAT ALREADY EXISTED OR THE GENERATED FILE

		return newFile;

	}

	public static File CreateFolder(File folder, String name) {

		// GET PATH TO FOLDER

		File newFile = new File(folder, name);

		try {

			// IF IT EXISTS, GENERATE THE FOLDER

			if (!newFile.exists()) {

				newFile.mkdirs();

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		// RETURN THE FILE (EXISTING OR NEW)

		return newFile;

	}

	public static void Update() {

		// CHECK THAT EVERY IMPORTANT FOLDER MARKED BY THE GLOBALS FOLDER EXISTS SO THE
		// PROGRAM CAN FUNCTION

		for (File folder : Globals.folders) {

			if (!folder.exists()) {

				folder.mkdirs();

			}

		}

		// CHECK THAT EVERY IMPORTANT FILE MARKED BY THE GLBOALS FOLDER EXISTS SO THE
		// PROGRAM CAN FUNCIOTN

		for (File file : Globals.files) {

			if (file != null && !file.exists()) {

				try {

					file.createNewFile();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		}

		for (File MonthFile : Globals.AttendanceFolder.listFiles()) {

			if (!MonthFile.getName().equals(".DS_Store")) {

				for (File AttendanceFile : MonthFile.listFiles()) {

					if (!AttendanceFile.getName().equals(".DS_Store")) {

						AttendanceModule.checkForNotCheckedOut(AttendanceFile);

					}

				}

			}

		}

	}

	public static boolean compareArguments(String[] array, String[] arguments) {

		// iterate through the arguments array
		for (int i = 0; i < array.length; i++) {

			// if the argument is not null and does not match the corresponding array
			// element, return false
			if (i < arguments.length && !array[i].equals(arguments[i])) {

				return false;

			}

		}

		return true;

	}

	/**
	 * Checks if a file contains an exact match for a given line of text.
	 *
	 * @param data  The file to search within.
	 * @param check The exact string to search for.
	 * @return true if the line is found, false otherwise.
	 */
	public static boolean checkForSameLine(File data, String check) {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(data));
			String nextLine;

			// loop through all lines
			while ((nextLine = reader.readLine()) != null) {

				// check for exact match after stripping whitespace
				if (nextLine.strip().equals(check)) {

					// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
					reader.close();
					return true;

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			reader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}

	public static boolean isSameDayOfWeek(LocalDate date, DayOfWeek day) {

		DayOfWeek givenDay = date.getDayOfWeek();

		return day.equals(givenDay);

	}

	public static boolean isBetweenDates(String checkDate, String startDate, String endDate) {

		LocalDate toCheck = LocalDate.parse(checkDate);
		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);

		return !toCheck.isBefore(start) && !toCheck.isAfter(end);

	}

	public static LocalDate isValidDate(String dateString) throws Exception {

		final List<String> DATE_PATTERNS = Arrays.asList("MM/dd/yyyy", // 11/26/2025
				"M/d/yyyy", // 1/1/2025
				"MM-dd-yyyy", // 11-26-2025
				"yyyy-MM-dd", // 2025-11-26 (ISO Standard)
				"MMM dd, yyyy", // Nov 26, 2025
				"MMMM dd, yyyy", // November 26, 2025
				"M/d/yy", // 1/1/25 (Two-digit year)
				"dd-MMM-yyyy" // 26-Nov-2025 (European style)
		);

		if (dateString == null || dateString.trim().isEmpty()) {

			return null;

		}

		// Clean up the string to handle common small mistakes (like multiple spaces)
		String cleanedDateString = dateString.trim().replaceAll("\\s+", " ");

		for (String pattern : DATE_PATTERNS) {

			try {

				// 1. Create the formatter for the specific pattern
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

				// 2. Attempt to parse the cleaned string
				return LocalDate.parse(cleanedDateString, formatter);

			} catch (Exception e) {

				// Ignore the ParseException for this pattern and try the next one
				continue;

			}

		}

		throw new Exception("Date Incorrectly Formatted");

	}

	public static LocalTime isValidTime(String dateString) throws Exception {

		final List<String> DATE_PATTERNS = Arrays.asList("h:mma", // 1:30PM
				"hh:mm a", // 01:30 PM
				"HH:mm", // 13:30
				"hh:mma", // 01:30PM
				"HH:mm:ss", "h:mma", "h:mm a" // 1:30 PM
		);

		if (dateString == null || dateString.trim().isEmpty()) {

			return null;

		}

		// Clean up the string to handle common small mistakes (like multiple spaces)
		String cleanedDateString = dateString.trim().replaceAll("\\s+", " ");
		cleanedDateString.toUpperCase();

		for (String pattern : DATE_PATTERNS) {

			try {

				// 1. Create the formatter for the specific pattern
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

				// 2. Attempt to parse the cleaned string
				return LocalTime.parse(cleanedDateString, formatter);

			} catch (Exception e) {

				// Ignore the ParseException for this pattern and try the next one
				continue;

			}

		}

		throw new Exception("Date Incorrectly Formatted");

	}

	public static LocalDateTime isValidDateTime(String dateTimeString) throws Exception {

		final List<String> DATETIME_PATTERNS = Arrays.asList(
				// U.S. / Standard Formats
				"MM/dd/yyyy h:mm a", // 11/26/2025 7:30 PM
				"M/d/yyyy H:mm", // 1/1/2025 19:30 (24-hour)
				"MM-dd-yyyy hh:mm a", // 11-26-2025 07:30 PM
				"yyyy-MM-dd hh:mm a", "yyyy-MM-dd h:mm a", "yyyy-MM-dd H:mm a",

				// ISO / 24-Hour Formats
				"yyyy-MM-dd HH:mm:ss", // 2025-11-26 19:30:00 (Full ISO)
				"yyyy-MM-dd HH:mm", // 2025-11-26 19:30 (Partial ISO)

				// Long Formats
				"MMM dd, yyyy h:mm a", // Nov 26, 2025 7:30 PM
				"MMMM dd, yyyy h:mm a", // November 26, 2025 7:30 PM
				"dd-MMM-yyyy HH:mm" // 26-Nov-2025 19:30 (European/24hr)
		);

		if (dateTimeString == null || dateTimeString.trim().isEmpty()) {

			return null;

		}

		// 1. Clean up and standardize the input for robustness
		String cleanedDateTimeString = dateTimeString.trim().replaceAll("\\s+", " ").toUpperCase();

		for (String pattern : DATETIME_PATTERNS) {

			try {

				// 2. Create the formatter with specific rules for leniency and
				// internationalization
				DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(pattern)
						// Use ResolverStyle.LENIENT for forgiving parsing (e.g., 2025-02-30 ->
						// 2025-03-02)
						.toFormatter(Locale.US)
						// Locale.US is used to correctly parse English month names (MMM) and AM/PM
						// markers (a)
						.withResolverStyle(ResolverStyle.LENIENT);

				// 3. Attempt to parse the cleaned string
				return LocalDateTime.parse(cleanedDateTimeString, formatter);

			} catch (DateTimeParseException e) {

				// Ignore this pattern's failure and try the next one
				continue;

			}

		}

		// 4. If all patterns fail, throw a specific exception
		throw new DateTimeParseException("Date/Time incorrectly formatted or unparsable.", dateTimeString, 0);

	}

	public static boolean isStudentIn(String studentID) {

		File attendanceToday = Globals.AttendanceToday;

		try {

			String[] allAttendanceToday = DatabaseCore.returnAllFile(attendanceToday);

			for (String attendanceLog : allAttendanceToday) {

				if (attendanceLog.split(", ")[0].strip().equals(studentID)) {

					if (attendanceLog.split(", ").length == 3) {

						return true;

					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}

	/**
	 * Generates a simple, custom hash of a string. Note: This is NOT
	 * cryptographically secure, simply for low level security. You can't peek into
	 * the files and find a password
	 *
	 * @param s The string to hash.
	 * @return The resulting hash string in hexadecimal format.
	 */
	public static String hash(String s) {

		// convert string to character array

		char[] Init = s.toCharArray();
		String n = "";

		// perform bitwise NOT operation on each character

		for (int i = 0; i < Init.length; i++) {

			Init[i] = (char) ~Init[i];

		}

		// iterate over pairs of characters (for string length > 1)

		for (int i = 0; i < Init.length - 1; i++) {

			// calculate a weighted sum

			int Total = Init[i] * 3 + Init[i + 1] * 2 + Init.length;

			// perform bitwise NOT, then ensure positive

			Total = ~Total;
			if (Total < 0)
				Total *= -1;

			// append binary representation of the result

			n += Integer.toString(Total, 2);

		}

		// handle single-character strings

		if (s.length() == 1) {

			// calculate a hash for a single character

			int l = ~(Init[0] * 4);
			if (l < 0)
				l *= -1;

			// append binary representation

			n += Integer.toString(l, 2);

		}

		// padding: pad the binary string with zeros to make length a multiple of 16

		while (n.length() % 16 != 0) {

			n += "0";

		}

		String fin = "";

		// convert the binary string into hexadecimal groups

		for (int i = 0; i < n.length() / 16; i++) {

			// get 16-bit chunk

			String sub = n.substring(i * 16, i * 16 + 16);

			// convert binary chunk to integer

			int b = Integer.parseInt(sub, 2);
			char c = (char) b;

			// convert integer to hex string and append

			fin += Integer.toHexString(c);

		}

		// return final hash

		return fin;

	}

	public static boolean isValidPhoneNumber(String phoneNumber) throws Exception {

		final String PhoneNumberRegEx = "^(?:(?:\\+1[\\s-]?)?(?:\\(\\d{3}\\)|\\d{3})[ -.]?\\d{3}[ -.]?\\d{4}|\\d{10}|(?:\\+52[\\s-]?)?\\d{2}[\\s-]?\\d{4}[\\s-]?\\d{4})$";

		if (!phoneNumber.matches(PhoneNumberRegEx) && !phoneNumber.toLowerCase().equals("n/a")) {

			throw new Exception("Please format Phone Number correctly");

		}

		return true;

	}

	public static boolean isValidEmail(String email) throws Exception {

		final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

		final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

		Matcher matcher = EMAIL_PATTERN.matcher(email);

		if (!matcher.matches() && !email.toLowerCase().equals("n/a")) {

			throw new Exception("Please format Email correctly");

		}

		return true;

	}

	/**
	 * 
	 * @param line:     the line that the program will look for
	 * @param database: the database we will look for the line in
	 * @returns the value of the line that exists before the given line
	 */

	public static String getLineBeforeLine(String line, File database) {

		try {

			String[] allFile = DatabaseCore.returnAllFile(database);

			for (int i = 0; i < allFile.length; i++) {

				if (allFile[i].equals(line) && i != 0) {

					return allFile[i - 1];

				} else if (allFile[i].equals(line) && i == 0) {

					return line;

				}

			}

		} catch (IOException e) {

			e.printStackTrace();

		}

		return "404";

	}

	/**
	 * 
	 * @param line:     the line that the program will look for
	 * @param database: the database we will look for the line in
	 * @returns the value of the line that exists after the given line
	 */

	public static String getLineAfterLine(String line, File database) {

		try {

			String[] allFile = DatabaseCore.returnAllFile(database);

			for (int i = 0; i < allFile.length; i++) {

				if (allFile[i].equals(line) && i != allFile.length - 1) {

					return allFile[i + 1];

				} else if (allFile[i].equals(line) && i == allFile.length - 1) {

					return line;

				}

			}

		} catch (IOException e) {

			e.printStackTrace();

		}

		return "404";

	}
	
	public static int getUnitsBetweenTimes(String startTime, String endTime, String units) throws Exception {
		
		if (!units.equals(Globals.MINUTES) && !units.equals(Globals.HOURS) && !units.equals(Globals.DAYS)) {
			
			return -1;
			
		}
		
		LocalDateTime start = DatabaseCore.isValidDateTime(startTime);
		LocalDateTime end = DatabaseCore.isValidDateTime(endTime);
		
		long time = switch(units) {
		case Globals.MINUTES -> ChronoUnit.MINUTES.between(start, end);
		case Globals.HOURS -> ChronoUnit.HOURS.between(start, end);
		case Globals.DAYS -> ChronoUnit.DAYS.between(start, end);
		default ->
			throw new IllegalArgumentException("Unexpected value: " + units);
		};
		
		return Math.round(time);
		
	}

}