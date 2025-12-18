package databaseAccess;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import util.Globals;

public class ProductionsModule {

	/**
	 * Checks if a cast with the given name already exists in the Casts file for a
	 * production. Throws an Exception if a duplicate is found.
	 *
	 * @param Casts The Casts file for a specific production.
	 * @param name  The name of the cast to check.
	 * @throws Exception Throws "Cast already Exists" if found.
	 */
	public static void checkForCast(File Casts, String name) throws Exception {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(Casts));
			String nextLine;

			// loop through all lines in the casts file
			while ((nextLine = reader.readLine()) != null && !nextLine.isBlank()) {

				String line = nextLine;

				if (line.equals(name)) {

					// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
					reader.close();
					throw new Exception("Cast already Exists");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			reader.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * Checks if a role with the given name already exists in the Roles file for a
	 * production. Throws an Exception if a duplicate is found.
	 *
	 * @param Roles The Roles file for a specific production.
	 * @param name  The name of the role to check.
	 * @throws Exception Throws "Role already Exists" if found.
	 */
	public static void checkForRole(File Roles, String name) throws Exception {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(Roles));
			String nextLine;

			// loop through all lines in the roles file
			while ((nextLine = reader.readLine()) != null && !nextLine.isBlank()) {

				String line = nextLine;

				if (line.equals(name)) {

					// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
					reader.close();
					throw new Exception("Role already Exists");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			reader.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

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
	 * Checks if a specific student data field has been changed from its current
	 * value.
	 *
	 * @param name      The student's name.
	 * @param birthDate The student's birth date.
	 * @param info      The index of the field to check (0-22).
	 * @param newVal    The new value being proposed.
	 * @return true if the new value is different from the stored value and is not
	 *         an empty/default non-change.
	 */

	/**
	 * Checks if a production with the given name already exists in the Productions
	 * file. Throws an Exception if a duplicate is found.
	 *
	 * @param name The name of the production to check.
	 * @throws Exception Throws "Show already Exists" if found.
	 */
	public static void checkForShow(String name) throws Exception {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(Globals.ProductionsFile));
			String nextLine;

			// loop through all lines in the productions file
			while ((nextLine = reader.readLine()) != null && !nextLine.isBlank()) {

				// extract the production name (excluding the date at the end, which is 11 chars
				// long: MM/dd/yyyy)
				String line = nextLine.substring(0, nextLine.length() - 11);

				if (line.equals(name)) {

					// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
					reader.close();
					throw new Exception("Show already Exists");

				}

			}

			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK

			reader.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	// =====RETRIEVE ALL CAST NAMES FOR A GIVEN PRODUCTION=====

	public static String[] getAllCastsByProduction(String productionName) {

		// GET OR CREATE THE PRODUCTION FOLDER AND THE CASTS FILE

		File ProductionFolder = DatabaseCore.CreateFolder(Globals.ProductionsFolder, productionName);
		File ProductionCasts = DatabaseCore.CreateFile(ProductionFolder, "Casts");

		// USE returnAllFile TO GET ALL CASTS

		try {

			return DatabaseCore.returnAllFile(ProductionCasts);

		} catch (IOException e) {

			e.printStackTrace();

		}

		// RETURN NULL IF AN EXCEPTION OCCURS

		return null;

	}

	// =====RETRIEVE ALL CAST NAMES FOR A GIVEN PRODUCTION=====

	// @formatter:off
	// =====RETRIEVE CAST NAMES FOR A GIVEN PRODUCTION, INCLUDING "None" FOR=====
	//                         =====DROPDOWN MENUS=====
	// @formatter:on

	public static String[] getAllCastsForDropDown(String productionName) {

		// GET OR CREATE THE PRODUCTION FOLDER AND THE CASTS FILE

		File ProductionFolder = DatabaseCore.CreateFolder(Globals.ProductionsFolder, productionName);
		File ProductionCasts = DatabaseCore.CreateFile(ProductionFolder, "Casts");

		// ARRAYLIST TO STORE CASTS

		ArrayList<String> casts = new ArrayList<String>();

		// ADD THE DEFAULT OPTION "None"

		casts.add("None");

		try {

			// GET ALL CAST NAMES USING returnAllFile

			String[] base = DatabaseCore.returnAllFile(ProductionCasts);

			// ADD ALL CAST NAMES TO THE LIST

			for (int i = 0; i < base.length; i++) {

				casts.add(base[i]);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		// CONVERT THE ARRAYLIST TO AN ARRAY AND RETURN IT

		String[] result = casts.toArray(new String[0]);

		return result;

	}

	// =====RETRIEVE ALL CAST NAMES FOR A GIVEN PRODUCTION=====

	// @formatter:off
	// =====RETRIEVE CAST NAMES FOR A GIVEN PRODUCTION, INCLUDING "None" FOR=====
	//                         =====DROPDOWN MENUS=====
	// @formatter:on

	// =====RETRIEVE ALL PERFORMANCE NAMES FOR A GIVEN PRODUCTION=====

	public static String[] getAllPerformancesByProduction(String productionName) {

		// GET OR CREATE THE PRODUCTION FOLDER AND THE PERFORMANCES FILE

		File ProductionFolder = DatabaseCore.CreateFolder(Globals.ProductionsFolder, productionName);
		File ProductionPerformances = DatabaseCore.CreateFile(ProductionFolder, "Performances");

		// USE returnAllFile TO GET ALL PERFORMANCES

		try {

			return DatabaseCore.returnAllFile(ProductionPerformances);

		} catch (IOException e) {

			e.printStackTrace();

		}

		// RETURN NULL IF AN EXCEPTION OCCURS

		return null;

	}

	// =====RETRIEVE ALL THE DETAILS FOR A GIVEN STUDENT=====

	// =====RETRIEVE ALL REGISTERED STUDENT NAMES BASED ON FILTERS=====

	// =====RETRIEVE ALL REGISTERED STUDENT NAMES STARTING WITH A GIVEN STRING=====

	// =====RETRIEVE THE UNIQUE STUDENT ID (FILENAME) FOR A GIVEN NAME AND
	// BIRTHDATE=====

	// =====RETRIEVE ALL PRODUCTION NAMES, FILTERED BY UPCOMING OR PAST DATE=====

	public static String[] getAllProductions(boolean Upcoming, boolean Past) {

		// ARRAYLIST TO STORE MATCHING PRODUCTION NAMES

		ArrayList<String> names = new ArrayList<String>();

		// DATE FORMATTER FOR PRODUCTION DATES

		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		try {

			// OPEN THE PRODUCTIONS FILE

			BufferedReader reader = new BufferedReader(new FileReader(Globals.ProductionsFile));
			String nextLine;

			// ITERATE THROUGH ALL PRODUCTION LINES

			while ((nextLine = reader.readLine()) != null) {

				// CALCULATE THE DIFFERENCE IN DAYS BETWEEN TODAY AND THE PRODUCTION DATE

				LocalDate usedDate = DatabaseCore.isValidDate(nextLine.substring(nextLine.length() - 10));

				long time = ChronoUnit.DAYS.between(LocalDate.now(), usedDate);

				boolean include = true;

				// CHECK IF THE PRODUCTION IS IN THE FUTURE (TIME < 0)

				if (Upcoming) {

					if (time < 0) {

						include = false;

					}

					// CHECK IF THE PRODUCTION IS IN THE PAST (TIME > 0)

				} else if (Past) {

					if (time > 0) {

						include = false;

					}

				}

				// IF INCLUDED BY FILTERS, ADD THE PRODUCTION TO THE LIST

				if (include)
					names.add(nextLine);

			}

			// IMPORTANT: CLOSE THE READER TO AVOID RESOURCE LEAK

			reader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		// CONVERT THE ARRAYLIST TO AN ARRAY

		String[] result = names.toArray(new String[0]);

		// SORT THE RESULTS BY DATE

		return Organizer.sortProductionsByDate(result);

	}

	public static String[] getAllRehearsalsByShow(String productionName) {

		File productionFolder = new File(Globals.ProductionsFolder, productionName);
		File readFile = new File(productionFolder, "Rehearsals");

		try {

			return DatabaseCore.returnAllFile(readFile);

		} catch (IOException e) {

			e.printStackTrace();
			return null;

		}

	}

	// =====RETRIEVE ALL THE DETAILS FOR A GIVEN STUDENT=====

	// =====RETRIEVE ALL REGISTERED STUDENT NAMES BASED ON FILTERS=====

	// =====RETRIEVE ALL REGISTERED STUDENT NAMES STARTING WITH A GIVEN STRING=====

	// =====RETRIEVE THE UNIQUE STUDENT ID (FILENAME) FOR A GIVEN NAME AND
	// BIRTHDATE=====

	// =====RETRIEVE ALL PRODUCTION NAMES, FILTERED BY UPCOMING OR PAST DATE=====

	// =====RETRIEVE ALL ROLES FOR A GIVEN PRODUCTION=====

	public static String[] getAllRolesByProduction(String productionName) {

		if (productionName.isBlank()) {

			return new String[0];

		}

		// GET OR CREATE THE PRODUCTION FOLDER AND THE ROLES FILE

		File ProductionFolder = new File(Globals.ProductionsFolder, productionName);
		File ProductionRoles = DatabaseCore.CreateFile(ProductionFolder, "Roles");

		// ARRAYLIST TO STORE THE ROLES

		ArrayList<String> roles = new ArrayList<String>();

		try {

			// OPEN THE ROLES FILE

			BufferedReader reader = new BufferedReader(new FileReader(ProductionRoles));
			String nextLine;

			// READ ALL LINES (ROLES) FROM THE FILE

			while ((nextLine = reader.readLine()) != null) {

				roles.add(nextLine);

			}

			// IMPORTANT: CLOSE THE READER TO AVOID RESOURCE LEAK

			reader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		// CONVERT THE ARRAYLIST TO AN ARRAY AND RETURN IT

		String[] result = roles.toArray(new String[0]);

		return result;

	}

	// =====RETRIEVE ALL THE DETAILS FOR A GIVEN STUDENT=====

	// =====RETRIEVE ALL REGISTERED STUDENT NAMES BASED ON FILTERS=====

	// =====RETRIEVE ALL REGISTERED STUDENT NAMES STARTING WITH A GIVEN STRING=====

	// =====RETRIEVE THE UNIQUE STUDENT ID (FILENAME) FOR A GIVEN NAME AND
	// BIRTHDATE=====

	// =====RETRIEVE ALL PRODUCTION NAMES, FILTERED BY UPCOMING OR PAST DATE=====

	// =====RETRIEVE ALL ROLES FOR A GIVEN PRODUCTION=====

	// =====RETRIEVE ALL CAST NAMES FOR A GIVEN PRODUCTION=====

	// @formatter:off
	// =====RETRIEVE CAST NAMES FOR A GIVEN PRODUCTION, INCLUDING "None" FOR=====
	//                         =====DROPDOWN MENUS=====
	// @formatter:on

	// =====RETRIEVE ALL PERFORMANCE NAMES FOR A GIVEN PRODUCTION=====

	// @formatter:off
	// =====RETRIEVE ALL STUDENT IDs ASSIGNED TO A SPECIFIC ROLE IN A=====
	// 						=====PRODUCTION=====
	// @formatter:on

	// =====RETRIEVE A STUDENT'S DISPLAY NAME (FIRST AND LAST NAME) BY THEIR ID=====

	//@formatter:off
	// =====RETRIEVE ALL CAST NAMES A GIVEN STUDENT IS IN FOR A SPECIFIC ROLE IN A=====
	// 							=====PRODUCTION=====
	//@formatter:on

	// =====RETRIEVE THE CAST NAME ASSIGNED TO A SPECIFIC ROLE FOR A SPECIFIC
	// PERFORMANCE=====

	public static String getCastByRoleAndPerformance(String productionName, String Role, String PerformanceName) {

		// GET THE PRODUCTION FOLDER AND THE PERFORMANCE FILE

		File Production = new File(Globals.ProductionsFolder, productionName);
		File Performance = new File(Production, PerformanceName);

		try {

			// OPEN THE PERFORMANCE FILE

			BufferedReader PerformanceReader = new BufferedReader(new FileReader(Performance));

			String nextLine;

			// ITERATE THROUGH THE LINES (EACH LINE IS A ROLE AND CAST ASSIGNMENT)

			while ((nextLine = PerformanceReader.readLine()) != null) {

				// CHECK IF THE ROLE MATCHES THE GIVEN ROLE

				if (nextLine.split(", ")[0].equals(Role)) {

					// IF MATCH FOUND, CLOSE READER AND RETURN THE ASSIGNED CAST NAME (INDEX 1)

					PerformanceReader.close();
					return nextLine.split(", ")[1];

				}

			}

			// IMPORTANT: CLOSE THE READER TO AVOID RESOURCE LEAK IF NO MATCH FOUND

			PerformanceReader.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		// RETURN "None" IF NO CAST IS ASSIGNED TO THAT ROLE FOR THE PERFORMANCE

		return "None";

	}

	public static void UpdateProductions() {

		// FIND EVERY FILE IN THE PRODUCTIONS FOLDER

		for (File file : Globals.ProductionsFolder.listFiles()) {

			// ENSURE WE AREN'T TRYING TO ACCESS A FILE AS A FOLDER

			if (!file.getName().equals("AllProductions") && !file.getName().equals(".DS_Store")) {

				// CHECK EVERY CAST THAT EXISTS FOR THE PRODUCTION

				for (String cast : ProductionsModule.getAllCastsByProduction(file.getName())) {

					// CHECK EVERTY ROLE THAT EXISTS IN THE PRODUCTION

					File castFolder = DatabaseCore.CreateFolder(file, cast);

					for (String role : ProductionsModule.getAllRolesByProduction(file.getName())) {

						// ADD THE ROLES TO THE CASTS

						DatabaseCore.CreateFile(castFolder, role);

					}

				}

			}

		}

	}

	public static void addRehearsal(String productionName, String rehearsalName, String date, String studio,
			String teacher, String[] roles, String startTime, String endTime) throws Exception {
	
		File ProductionFolder = new File(Globals.ProductionsFolder, productionName);
		File ProductionRehearsals = new File(ProductionFolder, "Rehearsals");
		String rolesString = "";
	
		for (int i = 0; i < roles.length; i++) {
	
			rolesString += ((i != 0) ? "|" : "") + roles[i];
	
		}
	
		//@formatter:off
		// Rehearsal String format: [Rehearsal Name], [date (yyyy-MM-dd)], [start Time], [End Time], [studio], [teacher], [Roles formatted with spaces]
		//@formatter:on
	
		String fullRehearsalString = "";
		fullRehearsalString += rehearsalName + ", ";
		fullRehearsalString += date + ", ";
		fullRehearsalString += startTime + ", ";
		fullRehearsalString += endTime + ", ";
		fullRehearsalString += studio + ", ";
		fullRehearsalString += teacher + ", ";
		fullRehearsalString += rolesString;
	
		ConflictChecker.hasConflict(date, startTime, endTime, studio, teacher, roles);
	
		DatabaseCore.writeToDatabase(ProductionRehearsals, fullRehearsalString);
	
	}

	/**
	 * Adds a new role to a production, updates production structure, and adds the
	 * role to all existing performances.
	 *
	 * @param productionName The name of the production.
	 * @param roleName       The name of the new role.
	 */
	public static void addRole(String productionName, String roleName) {
	
		File Production = new File(Globals.ProductionsFolder, productionName);
		File Roles = new File(Production, "Roles");
	
		DatabaseCore.writeToDatabase(Roles, roleName);
		ProductionsModule.UpdateProductions();
	
		String[] allPerformances = ProductionsModule.getAllPerformancesByProduction(productionName);
	
		for (int i = 0; i < allPerformances.length; i++) {
	
			File Performance = new File(Production, allPerformances[i].split(", ")[0]);
	
			DatabaseCore.writeToDatabase(Performance, roleName + ", None");
	
		}
	
	}

	/**
	 * Creates a new performance file for a given production and populates it with
	 * default 'None' casts for all roles.
	 *
	 * @param productionName  The name of the production.
	 * @param PerformanceName The unique name/date of the new performance.
	 */
	public static void createPerformance(String productionName, String PerformanceName) {
	
		File productionFile = new File(Globals.ProductionsFolder, productionName);
		File performanceFile = DatabaseCore.CreateFile(productionFile, PerformanceName);
	
		String[] roles = ProductionsModule.getAllRolesByProduction(productionName);
	
		for (int i = 0; i < roles.length; i++) {
	
			DatabaseCore.writeToDatabase(performanceFile, roles[i] + ", None");
	
		}
	
	}

	/**
	 * 
	 * Sets the CreateProduction Function as a simple function in this file for
	 * easier reading Simply creates all the necessary files for creating a
	 * production and writes the name of the production to the productions file
	 * 
	 * @param productionName
	 * @param date
	 * @throws Exception
	 */
	
	public static void createProduction(String productionName, String date) throws Exception {
	
		LocalDate check = DatabaseCore.isValidDate(date);
		String consoleData = productionName + " " + date;
		File ProductionFolder = DatabaseCore.CreateFolder(Globals.ProductionsFolder, productionName);
		File ProductionRoles = DatabaseCore.CreateFile(ProductionFolder, "Roles");
		File ProductionPerformances = DatabaseCore.CreateFile(ProductionFolder, "Performances");
		File ProductionCasts = DatabaseCore.CreateFile(ProductionFolder, "Casts");
		File ProductionRehearsals = DatabaseCore.CreateFile(ProductionFolder, "Rehearsals");
		File ProductionCostumes = DatabaseCore.CreateFile(ProductionFolder, "Costumes");
		DatabaseCore.writeToDatabase(Globals.ProductionsFile, consoleData);
	
	}

	public static void removeRehearsal(String rehearsal) {
	
		File ProductionFolder = DatabaseCore.CreateFolder(Globals.ProductionsFolder, rehearsal.split(", ")[0]);
		File RehearsalsFile = DatabaseCore.CreateFile(ProductionFolder, "Rehearsals");
		String searchLine = rehearsal.substring(rehearsal.split(", ")[0].length() + 2);
	
		try {
	
			// read the entire current file content
			Scanner RoleReader = new Scanner(RehearsalsFile);
	
			String fullNamesFile = "";
	
			while (RoleReader.hasNext()) {
	
				fullNamesFile += RoleReader.nextLine() + "\n";
	
			}
	
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
	
			RoleReader.close();
	
			// prepare to re-write the file
			RoleReader = new Scanner(fullNamesFile);
			BufferedWriter studentsFixer = new BufferedWriter(new FileWriter(RehearsalsFile));
	
			String nextLine;
	
			// re-write all lines except the one matching the student ID
	
			while (RoleReader.hasNext()) {
	
				nextLine = RoleReader.nextLine();
	
				if (!nextLine.strip().equals(searchLine)) {
	
					studentsFixer.write(nextLine + "\n");
	
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

	/**
	 * Sets the assigned cast for a specific role within a specific performance.
	 *
	 * @param productionName  The name of the production.
	 * @param PerformanceName The name/date of the performance.
	 * @param Role            The role to update.
	 * @param Cast            The name of the cast to assign to the role.
	 */
	public static void setCastToPerformanceRole(String productionName, String PerformanceName, String Role,
			String Cast) {
	
		try {
	
			File Production = new File(Globals.ProductionsFolder, productionName);
			File Performance = new File(Production, PerformanceName);
			Scanner studentsReader = new Scanner(Performance);
	
			String fullNamesFile = "";
	
			while (studentsReader.hasNext()) {
	
				fullNamesFile += studentsReader.nextLine() + "\n";
	
			}
	
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
	
			studentsReader.close();
	
			studentsReader = new Scanner(fullNamesFile);
			BufferedWriter studentsFixer = new BufferedWriter(new FileWriter(Performance));
	
			String nextLine;
	
			// re-write all lines, updating only the target role line
	
			while (studentsReader.hasNext()) {
	
				nextLine = studentsReader.nextLine();
	
				if (nextLine.split(", ")[0].equals(Role)) {
	
					studentsFixer.write(Role + ", " + Cast + "\n");
	
				} else {
	
					studentsFixer.write(nextLine + "\n");
	
				}
	
			}
	
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
	
			studentsFixer.close();
			// IMPORTANT LINE: AVOIDS A RESOURCE LEAK
			studentsReader.close();
	
		} catch (Exception e) {
	
			e.printStackTrace();
	
		}
	
	}

}
