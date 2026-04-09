package SwingCards;

import control.Main;
import databaseAccess.StudentsModule;
import swingConstants.ViewConstants;
import systemSwing.*;

import static util.SwingConstants.*;
import static util.SwingConstants.BACKBUTTONARCRAD;
import static util.SwingConstants.BACKBUTTONFONTSIZE;
import static util.SwingConstants.BACKBUTTONY;
import static util.SwingConstants.TextColor;

public class TeacherPanelView extends Panel {

	private AuditLog auditLog;
	private TitleLabel Student;

	public TeacherPanelView() {

		super(false, false, true, true);

		this.setBackground(MainGray.brighter());
		this.setLayout(null);
		this.setBounds(0, 200, 1200, 800);

		auditLog = new AuditLog(this);
		auditLog.setPosition(30, 200);
		auditLog.setSize(1140, 550);
		this.add(auditLog);

		Button MainMenu = new Button("Back", (e -> Main.Router.showDynamicView(ViewConstants.INDIVIDUAL_STUDENT_VIEW)), BACKBUTTOND, BACKBUTTONX, BACKBUTTONY,
				TextColor, BACKBUTTONARCRAD, BACKBUTTONFONTSIZE);

		this.add(MainMenu);

		Student = new TitleLabel("", TITLELABELX, BACKBUTTONY, TITLELABELWIDTH, (int) BACKBUTTOND.getHeight(), BUTTONPANELFONTSIZE * 3, TextColor);

		this.add(Student);

	}

	public void Update(String name) {

		Student.setText(name.split(" ")[0] + " " + name.split(" ")[1]);

		String[] nameSplit = name.split(" ");

		String studentFullName = nameSplit[0] + " " + nameSplit[1];

		String studentBirthDate = nameSplit[2];

		String studentID = StudentsModule.getStudentID(studentFullName, studentBirthDate);

		if (auditLog != null) {
			auditLog.setStudent(studentID);
			auditLog.repaint();
		}

		this.revalidate();
		this.repaint();

	}

}
