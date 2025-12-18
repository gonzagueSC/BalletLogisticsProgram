package databaseAccess;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import control.Main;
import control.PromptsService;
import databaseConstants.*;
import swingConstants.ViewConstants;
import util.Globals;

public class LogInModule {
	
	public static boolean checkForAdmin(File database, String AdminName, String ID) {

		String regex = " ";

		try {

			// use DatabaseReader to check for a line matching AdminName and ID
			return DatabaseCore.checkForLine(database, regex, AdminName, ID);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}
	
	public static void createAdmin(String Name, String Password) {
		
		if (!Name.isBlank() && !Password.isBlank() && Name.split(" ").length < 2) {

			DatabaseCore.writeToDatabase(Globals.AdminUsersAndPassword,
					Name + " " + DatabaseCore.hash(Password));
			Main.Router.showView(ViewConstants.SYSTEM_SETTINGS_VIEW);

		} else if (Name.split(" ").length > 1) { 
			
			PromptsService.FailurePrompt("Username cannot contain spaces");
			
		} else {

			PromptsService.FailurePrompt("Cannot have blank boxes");

		}
		
	}
	
	public static void LogStudentIn(String studentName, String birthDate) {
		
		File AttendanceToday = Databases.AttendanceToday;
		File Students = Databases.RegisteredStudents;
		
		if (studentName.equals("")) return;
		try {
			try {
				
				LocalDate check = DatabaseCore.isValidDate(birthDate);
				
				if (StudentsModule.checkForDuplicateStudent(Students, studentName, birthDate)) {
					
					Main.Router.showDynamicView(ViewConstants.STUDENT_VIEW, StudentsModule.getStudentID(studentName, birthDate));
					return;
					
				}
			} catch (Exception ex) {
				
				ex.printStackTrace();
				
				
			}
		} catch (DateTimeParseException ex) {

			PromptsService.FailurePrompt("Please format as: MM/dd/YYYY");

		}
		PromptsService.FailurePrompt("Student not found in Student Database");
		
	}

}
