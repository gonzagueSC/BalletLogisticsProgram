package SwingCards;

import javax.swing.JPanel;

import systemSwing.Button;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;
import control.*;
import swingConstants.*;

public class IndividualStudentView extends JPanel {

	private TitleLabel title;
	private TitleLabel Student;
	private Button Profile;
	private Button Enrollment;
	private Button Medical;
	private Button Costumes;
	private Button Teacher;
	private Button AttendanceLog;
	private Button MainMenu;

	public IndividualStudentView() {

		this.setBackground(MainGray);
		title = new TitleLabel("Student", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);

		Student = new TitleLabel("", TITLELABELX, 140, TITLELABELWIDTH, 100, LOGINSIGNUPLABELFONTSIZE, TextColor);

		Profile = new Button("Student Profile", null, BUTTONPANELBUTTONDIM, BUTTONPANELLEFTX, BUTTONFORM2STARTY,
				TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Enrollment = new Button("Enrollment Details", null, BUTTONPANELBUTTONDIM, BUTTONPANELRIGHTX, BUTTONFORM2STARTY,
				TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Medical = new Button("Medical Information", null, BUTTONPANELBUTTONDIM, BUTTONPANELLEFTX,
				BUTTONFORM2STARTY + BUTTONFORM2GAP * 1, TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Costumes = new Button("Costume Measurements", null, BUTTONPANELBUTTONDIM, BUTTONPANELRIGHTX,
				BUTTONFORM2STARTY + BUTTONFORM2GAP * 1, TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Teacher = new Button("Teacher Panel", null, BUTTONPANELBUTTONDIM, BUTTONPANELLEFTX,
				BUTTONFORM2STARTY + BUTTONFORM2GAP * 2, SecondaryPurple, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		AttendanceLog = new Button("Attendance", null, BUTTONPANELBUTTONDIM, BUTTONPANELRIGHTX,
				BUTTONFORM2STARTY + BUTTONFORM2GAP * 2, SecondaryPurple, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);

		MainMenu = new Button("Back", null, ChangeButtonD, LeftChangeButtonX, ChangeButtonY, TextColor, ChangeButtonArcRad,
				ChangeButtonFontSize);

		this.setLayout(null);
		this.add(title);
		this.add(Student);
		this.add(Profile);
		this.add(Enrollment);
		this.add(Medical);
		this.add(Costumes);
		this.add(Teacher);
		this.add(AttendanceLog);
		this.add(MainMenu);

		this.revalidate();
		this.repaint();

	}

	public void Update(String name) {
		
		this.remove(Profile);
		this.remove(Enrollment);
		this.remove(Medical);
		this.remove(Costumes);
		this.remove(Teacher);
		this.remove(AttendanceLog);
		this.remove(MainMenu);
		
		Profile = new Button("Student Profile", null, BUTTONPANELBUTTONDIM, BUTTONPANELLEFTX, BUTTONFORM2STARTY,
				TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Enrollment = new Button("Enrollment Details", null, BUTTONPANELBUTTONDIM, BUTTONPANELRIGHTX, BUTTONFORM2STARTY,
				TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Medical = new Button("Medical Information", null, BUTTONPANELBUTTONDIM, BUTTONPANELLEFTX,
				BUTTONFORM2STARTY + BUTTONFORM2GAP * 1, TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Costumes = new Button("Costume Measurements", null, BUTTONPANELBUTTONDIM, BUTTONPANELRIGHTX,
				BUTTONFORM2STARTY + BUTTONFORM2GAP * 1, TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Teacher = new Button("Teacher Panel", null, BUTTONPANELBUTTONDIM, BUTTONPANELLEFTX,
				BUTTONFORM2STARTY + BUTTONFORM2GAP * 2, SecondaryPurple, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		AttendanceLog = new Button("Attendance", null, BUTTONPANELBUTTONDIM, BUTTONPANELRIGHTX,
				BUTTONFORM2STARTY + BUTTONFORM2GAP * 2, SecondaryPurple, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);

		MainMenu = new Button("Back", null, ChangeButtonD, LeftChangeButtonX, ChangeButtonY, TextColor, ChangeButtonArcRad,
				ChangeButtonFontSize);

		Student.setText(name.split(" ")[0] + " " + name.split(" ")[1]);
		Profile.addActionListener(e -> Main.Router.showDynamicView(ViewConstants.STUDENT_PROFILE_VIEW, name));
		Enrollment.addActionListener(e -> Main.Router.showDynamicView(ViewConstants.ENROLLMENT_DETAILS_VIEW, name));
		Medical.addActionListener(e -> Main.Router.showDynamicView(ViewConstants.MEDICAL_CONDITIONS_VIEW, name));
		Costumes.addActionListener(e -> Main.Router.showDynamicView(ViewConstants.COSTUME_MEASUREMENTS_VIEW, name));
		Teacher.addActionListener(e -> Main.Router.showDynamicView(ViewConstants.TEACHER_VIEW, name));
		AttendanceLog.addActionListener(e -> Main.Router.showDynamicView(ViewConstants.ADMIN_ATTENDANCE_VIEW, name));
		MainMenu.addActionListener(e -> {
			
			Main.Router.showView(ViewConstants.STUDENT_DIRECTORY_VIEW);
			
		});

		this.revalidate();
		this.repaint();
		
		this.add(Profile);
		this.add(Enrollment);
		this.add(Medical);
		this.add(Costumes);
		this.add(Teacher);
		this.add(AttendanceLog);
		this.add(MainMenu);

	}

}
