package SwingCards;

import static util.SwingConstants.*;

import control.DialogGenerator;
import control.Main;
import control.PromptsService;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.Panel;
import systemSwing.TitleLabel;

import databaseAccess.*;

public class StudentView extends Panel {

	private String studentID = "";
	private TitleLabel title;
	private Button CheckInOut;
	private Button Reservations;

	public StudentView() {

		this.setLayout(null);

		title = new TitleLabel("Welcome" + "!", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		CheckInOut = new Button("Check In/Out",
				!DatabaseCore.isStudentIn(studentID) ? (e -> DialogGenerator.createCheckInDialog(studentID, this)) : (e -> {

					AttendanceModule.CheckStudentOut(studentID);
					PromptsService.SuccessPrompt("Successfully checked out");
					Update(studentID);

				}), BUTTONPANELBUTTONDIM, BUTTONPANELCENTERX, BUTTONFORM1STARTY, GreenColor, BUTTONPANELARCRAD,
				BUTTONPANELFONTSIZE);
		Reservations = new Button("Reservations", null, BUTTONPANELBUTTONDIM, BUTTONPANELCENTERX,
				BUTTONFORM1STARTY + BUTTONFORM1GAP, RedColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Button MainMenu = new Button("Log out", (e -> Main.Router.showDynamicView(ViewConstants.ATTENDANCE_VIEW)), ChangeButtonD, LeftChangeButtonX,
				ChangeButtonY, TextColor, ChangeButtonArcRad, ChangeButtonFontSize - 5);

		this.setLayout(null);
		this.add(title);
		this.add(MainMenu);
		this.add(CheckInOut);
		this.add(Reservations);

	}

	public void Update(String studentID) {

		this.studentID = studentID;

		title.setText("Welcome, " + StudentsModule.getStudentDisplayByID(studentID).split(" ")[0] + "!");
		
		if (CheckInOut != null) {
			
			this.remove(CheckInOut);
			
		}
		
		CheckInOut = new Button(DatabaseCore.isStudentIn(studentID) ? "Check Out" : "Check In",
				!DatabaseCore.isStudentIn(studentID) ? (e -> DialogGenerator.createCheckInDialog(studentID, this)) : (e -> {

					AttendanceModule.CheckStudentOut(studentID);
					PromptsService.SuccessPrompt("Successfully checked out");
					Update(studentID);

				}), BUTTONPANELBUTTONDIM, BUTTONPANELCENTERX, BUTTONFORM1STARTY, GreenColor, BUTTONPANELARCRAD,
				BUTTONPANELFONTSIZE);
		this.add(CheckInOut);
		this.revalidate();
		this.repaint();

	}

}
