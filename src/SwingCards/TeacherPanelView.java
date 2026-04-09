package SwingCards;

import static util.SwingConstants.MainGray;

import databaseAccess.StudentsModule;
import systemSwing.*;

public class TeacherPanelView extends Panel {

	private AuditLog auditLog;

	public TeacherPanelView() {

		super(false, false, true, true);

		this.setBackground(MainGray.brighter());
		this.setLayout(null);
		this.setBounds(0, 200, 1200, 800);

		auditLog = new AuditLog(this);
		auditLog.setPosition(30, 30);
		auditLog.setSize(1140, 740);
		this.add(auditLog);

	}

	public void Update(String name) {

		String[] nameSplit = name.split(" ");

		String studentFullName = nameSplit[0] + " " + nameSplit[1];

		String studentBirthDate = nameSplit[2];

		String studentID = StudentsModule.getStudentID(studentFullName, studentBirthDate);

		if (auditLog != null) {
			auditLog.setStudent(studentID);
			auditLog.repaint();
		}

	}

}
