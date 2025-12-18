package SwingCards;

import javax.swing.JPanel;

import control.Main;
import swingConstants.ViewConstants;
import systemSwing.*;
import util.Globals;
import static util.SwingConstants.*;

public class AdminView extends JPanel {

	public AdminView() {

		this.setBackground(MainGray);
		this.setSize(1200, 800);
		this.setLayout(null);
		this.setVisible(true);

		TitleLabel title = new TitleLabel("Admin", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		Button Schedules = new Button("Schedules", (e -> Main.Router.showDynamicView(ViewConstants.SCHEDULES_VIEW)), BUTTONPANELBUTTONDIM,
				BUTTONPANELLEFTX, BUTTONFORM1STARTY, TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Button AddStudents = new Button("Add Students", (e -> Main.Router.showDynamicView(ViewConstants.STUDENT_CREATION_VIEW)), BUTTONPANELBUTTONDIM,
				BUTTONPANELRIGHTX, BUTTONFORM1STARTY, TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Button StudentDirectory = new Button("Student Directory",
				(e -> Main.Router.showStudentDirectoryView(0, new String[Globals.filters.length])), BUTTONPANELBUTTONDIM,
				BUTTONPANELLEFTX, BUTTONFORM1STARTY + BUTTONFORM1GAP * 1, TerciaryColor, BUTTONPANELARCRAD,
				BUTTONPANELFONTSIZE);
		Button ShowsDirectory = new Button("Show Center", (e -> Main.Router.showDynamicView(ViewConstants.SHOW_DIRECTORY_VIEW, "false", "false")), BUTTONPANELBUTTONDIM,
				BUTTONPANELRIGHTX, BUTTONFORM1STARTY + BUTTONFORM1GAP * 1, TerciaryColor, BUTTONPANELARCRAD,
				BUTTONPANELFONTSIZE);
		Button AddNewAdmin = new Button("System Settings", (e -> Main.Router.showView(ViewConstants.SYSTEM_SETTINGS_VIEW)), BUTTONPANELBUTTONDIM,
				BUTTONPANELCENTERX, BUTTONFORM1STARTY + BUTTONFORM1GAP * 2, SecondaryPurple, BUTTONPANELARCRAD,
				BUTTONPANELFONTSIZE);
		Button MainMenu = new Button("Menu", (e -> Main.Router.showView(ViewConstants.MAIN_MENU)), ChangeButtonD, LeftChangeButtonX, ChangeButtonY, TextColor,
				ChangeButtonArcRad, ChangeButtonFontSize);

		this.setLayout(null);
		this.add(title);
		this.add(Schedules);
		this.add(AddStudents);
		this.add(StudentDirectory);
		this.add(ShowsDirectory);
		this.add(AddNewAdmin);
		this.add(MainMenu);

		this.revalidate();
		this.repaint();

	}

}
