package databaseAccess;

import java.io.*;
import java.util.Scanner;

import databaseConstants.*;

public class SystemSettingsModule {
	
	public static final String TEACHERS = "Teachers";
	public static final String STUDIOS = "Studios";
	public static final String LEVELS = "Levels";
	public static final String ADMINS = "Admins";

	/**
	 * Looks through a system details file, which is determined by detailType and
	 * looks to see if value is already in that file
	 * 
	 * @param detailType The type of detail it's looking for
	 * @param value      that we are checking for
	 * @return true if it already exists within the file
	 */

	public static boolean checkForSystemDetail(String detailType, String value) {

		// select the correct settings file based on type
		File SettingsFile = switch (detailType) {

		case TEACHERS -> Databases.Teachers;
		case STUDIOS -> Databases.Studios;
		case LEVELS -> Databases.Levels;
		default -> throw new IllegalArgumentException("Unexpected value: " + detailType);

		};

		// checks if the line exists in the detailsFile and returns the value

		return DatabaseCore.checkForSameLine(SettingsFile, value);

	}

	// =====RETRIEVE ALL THE LEVELS=====

	public static String[] getAllOfSystemDetail(String type) {

		// select the correct settings file based on type
		File SettingsFile = switch (type) {

		case TEACHERS -> Databases.Teachers;
		case STUDIOS -> Databases.Studios;
		case LEVELS -> Databases.Levels;
		case ADMINS -> Databases.AdminUsersAndPassword;
		default -> throw new IllegalArgumentException("Unexpected value: " + type);

		};

		// return the entire file of the found type

		try {

			return DatabaseCore.returnAllFile(SettingsFile);

		} catch (IOException e) {

			e.printStackTrace();

		}

		// return null if an exception happens

		return null;

	}

	/**
	 * Removes a system-level detail (Teacher, Studio, Level, or Admin) from its
	 * respective settings file.
	 *
	 * @param type The type of detail ("Teachers", "Studios", "Levels", or
	 *             "Admins").
	 * @param name The name of the detail to remove.
	 */
	@SuppressWarnings("All")
	public static void removeSystemDetail(String type, String name) {

		// select the correct settings file based on type
		File SettingsFile = switch (type) {

		case TEACHERS -> Databases.Teachers;
		case STUDIOS -> Databases.Studios;
		case LEVELS -> Databases.Levels;
		case ADMINS -> Databases.AdminUsersAndPassword;
		default -> throw new IllegalArgumentException("Unexpected value: " + type);

		};

		try {

			Scanner FileReader = new Scanner(SettingsFile);

			String fullNamesFile = "";

			while (FileReader.hasNext()) {
				
				fullNamesFile += FileReader.nextLine() + "\n";

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			FileReader.close();

			FileReader = new Scanner(fullNamesFile);
			BufferedWriter studentsFixer = new BufferedWriter(new FileWriter(SettingsFile));

			String nextLine;

			// re-write all lines except the one matching the detail name

			while (FileReader.hasNext()) {

				nextLine = FileReader.nextLine();

				if (!nextLine.strip().equals(name)) {

					studentsFixer.write(nextLine + "\n");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			studentsFixer.close();
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			FileReader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Adds a system-level detail (Teacher, Studio, Level, or Admin) to its
	 * respective settings file.
	 *
	 * @param type The type of detail ("Teachers", "Studios", "Levels", or
	 *             "Admins").
	 * @param name The name of the detail to add.
	 */
	public static void addSystemDetail(String type, String name) {

		// select the correct settings file based on type
		File SettingsFile = switch (type) {

		case TEACHERS -> Databases.Teachers;
		case STUDIOS -> Databases.Studios;
		case LEVELS -> Databases.Levels;
		case ADMINS -> Databases.AdminUsersAndPassword;
		default -> throw new IllegalArgumentException("Unexpected value: " + type);

		};

		if (!DatabaseCore.checkForSameLine(SettingsFile, name))
			DatabaseCore.writeToDatabase(SettingsFile, name);

	}

	/**
	 * Takes two data values that exist within the System Detail and swaps their
	 * positions in the file Used mostly for moving levels around
	 * 
	 * @param type:       The Type Of System Detail you want to work with
	 * @param firstLine:  Value of one of the lines you want to swap
	 * @param secondLine: Value of the second line you want to swap
	 */

	public static void swapSystemDetail(String type, String firstLine, String secondLine) {

		File SettingsFile = switch (type) {

		case TEACHERS -> Databases.Teachers;
		case STUDIOS -> Databases.Studios;
		case LEVELS -> Databases.Levels;
		case ADMINS -> Databases.AdminUsersAndPassword;
		default -> throw new IllegalArgumentException("Unexpected value: " + type);

		};

		String FirstLineCue = "FirstLineCue";
		String SecondLineCue = "SecondLineCue";

		DatabaseCore.editLine(SettingsFile, firstLine, FirstLineCue);
		DatabaseCore.editLine(SettingsFile, secondLine, SecondLineCue);
		DatabaseCore.editLine(SettingsFile, FirstLineCue, secondLine);
		DatabaseCore.editLine(SettingsFile, SecondLineCue, firstLine);

	}

	/**
	 * Takes a line, which is expected to be in the system, and moves it up in the
	 * file by swapping it with the item above it
	 * 
	 * @param type: The Type Of System Detail you want to work with
	 * @param line: The value of the line you want to move up
	 */

	public static void moveSystemDetailUp(String type, String line) {

		File SettingsFile = switch (type) {

		case TEACHERS -> Databases.Teachers;
		case STUDIOS -> Databases.Studios;
		case LEVELS -> Databases.Levels;
		case ADMINS -> Databases.AdminUsersAndPassword;
		default -> throw new IllegalArgumentException("Unexpected value: " + type);

		};

		String secondLine = DatabaseCore.getLineBeforeLine(line, SettingsFile);
		if (secondLine.equals(line) || secondLine.equals("404")) return;
		
		swapSystemDetail(type, line, secondLine);

	}
	
	/**
	 * Takes a line, which is expected to be in the system, and moves it down in the
	 * file by swapping it with the item beneath it
	 * 
	 * @param type: The Type Of System Detail you want to work with
	 * @param line: The value of the line you want to move down
	 */

	public static void moveSystemDetailDown(String type, String line) {

		File SettingsFile = switch (type) {

		case TEACHERS -> Databases.Teachers;
		case STUDIOS -> Databases.Studios;
		case LEVELS -> Databases.Levels;
		case ADMINS -> Databases.AdminUsersAndPassword;
		default -> throw new IllegalArgumentException("Unexpected value: " + type);

		};

		String secondLine = DatabaseCore.getLineAfterLine(line, SettingsFile);
		if (secondLine.equals(line) || secondLine.equals("404")) return;
		
		swapSystemDetail(type, line, secondLine);

	}
	
	public static boolean isAboveInFile(String type, String detail1, String detail2) {
		
		File SettingsFile = switch (type) {

		case TEACHERS -> Databases.Teachers;
		case STUDIOS -> Databases.Studios;
		case LEVELS -> Databases.Levels;
		case ADMINS -> Databases.AdminUsersAndPassword;
		default -> throw new IllegalArgumentException("Unexpected value: " + type);

		};
		
		try {

			int detail1Index = DatabaseCore.getLineIndex(SettingsFile, ", ", detail1);
			int detail2Index = DatabaseCore.getLineIndex(SettingsFile, ", ", detail2);
			
			return detail1Index < detail2Index;

		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return false;
		
	}
	
	public static String getSystemDetail(String type, String detailData) {
		
		File SettingsFile = switch (type) {

		case TEACHERS -> Databases.Teachers;
		case STUDIOS -> Databases.Studios;
		case LEVELS -> Databases.Levels;
		case ADMINS -> Databases.AdminUsersAndPassword;
		default -> throw new IllegalArgumentException("Unexpected value: " + type);

		};
		
		try {

			int ind = DatabaseCore.getLineIndex(SettingsFile, ", ", detailData);
			String LevelData = DatabaseCore.getLine(SettingsFile, ind);
			return LevelData;

		} catch (Exception e) {
			
			e.printStackTrace();

		}
		return "";
		
	}

}
