package SwingCards;

import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.Panel;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;

import control.Main;

public class MainMenuView extends Panel {

	public MainMenuView() {

		this.setLayout(null);

		TitleLabel title = new TitleLabel("EPBT Data Center", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		Button Attendance = new Button("Student", (e -> Main.Router.showDynamicView(ViewConstants.ATTENDANCE_VIEW)), BUTTONPANELBUTTONDIM,
				BUTTONPANELCENTERX, BUTTONFORM1STARTY, GreenColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Button AddStudents = new Button("Admin", (e -> Main.Router.showDynamicView(ViewConstants.LOGIN_VIEW)), BUTTONPANELBUTTONDIM,
				BUTTONPANELCENTERX, BUTTONFORM1STARTY + BUTTONFORM1GAP, RedColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);

		this.setLayout(null);
		this.add(title);
		this.add(Attendance);
		this.add(AddStudents);
		
	}

}
