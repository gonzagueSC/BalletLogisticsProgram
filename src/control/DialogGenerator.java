package control;

import SwingCards.*;

public class DialogGenerator {

	public static void createNewRoleFrame(String productionName) {

		new NewRoleDialog(Main.frame, productionName);

	}

	public static void createNewProductionFrame() {

		new NewProductionDialog(Main.frame);

	}
	
	public static void createNewLevelFrame(LevelManagerView panel) {

		new NewLevelDialog(Main.frame);

	}
	
	public static void createNewStudioFrame(StudioManagerView panel) {

		new NewStudioDialog(Main.frame);

	}
	
	public static void createNewTeacherFrame(TeacherManagerView panel) {

		new NewTeacherDialog(Main.frame);

	}

	public static void createNewCastDialog(String productionName) {

		new NewCastDialog(Main.frame, productionName);

	}

	public static void assignStudentDialog(String productionName, String role, ShowRolesAndCastingRight panel) {

		new AssignStudentDialog(Main.frame, panel, productionName, role);

	}

	public static void addRemoveDialog(String productionName, String role, ShowRolesAndCastingRight panel,
			String studentID, String[] casts) {

		new AddRemoveCastedDialog(Main.frame, panel, productionName, role, studentID, casts);

	}

	public static void newPerformanceDialog(String productionName, ShowPerformancesLeft panel) {

		new NewPerformanceDialog(Main.frame, panel, productionName);

	}
	
	public static void newRehearsalDataDialog(String rehearsal) {

		new showRehearsalDataDialog(Main.frame, rehearsal);

	}
	
	public static void newClassEditor(SchedulesClasses panel, String rehearsal) {
		
		new SchedulesClassesEditor(Main.frame, panel, rehearsal);
		
	}
	
	public static void newClassDialog(SchedulesClasses panel) {
		
		new NewClassDialog(Main.frame, panel);
		
	}
	
	public static void newRehearsalDialog(SchedulesRehearsals panel) {

		new NewRehearsalDialog(Main.frame, panel);

	}
	
	public static void createRoleSelectionDialog(NewRehearsalDialog parent, String productionName, String[] roles) {
		
		new RoleSelectionDialog(parent, productionName, roles);
		
	}
	
	public static void createPauseClassDialog(SchedulesClasses panel, String classString) {
		
		new SchedulesClassesPauseDialog(Main.frame, panel, classString);
		
	}
	
	public static void createCheckInDialog(String studentID, StudentView panel) {
		
		new CheckInDialog(Main.frame, studentID);
		
	}
	
	public static void createFilterFrame(int page, String[] filters) {

		new FilterDialog(Main.frame, page, filters);

	}
	
	public static void createLevelSelectionDialog(NewClassDialog parent, String[] levels) {
		
		new LevelSelectionDialog(parent, levels);
		
	}

}
