package SwingCards;

import javax.swing.JPanel;

import control.Main;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;

public class SystemSettingsView extends JPanel {

	public SystemSettingsView() {

		this.setBackground(MainGray);
		this.setSize(1200, 800);
		this.setLayout(null);
		this.setVisible(true);

		TitleLabel title = new TitleLabel("System Settings", TITLELABELX, TITLELABELY, TITLELABELWIDTH,
				TITLELABELHEIGHT, TITLELABELFONTSIZE, TextColor);
		Button Levels = new Button("Manage Levels",
				(e -> Main.Router.showDynamicView(ViewConstants.LEVEL_MANAGER_VIEW)), BUTTONPANELBUTTONDIM,
				BUTTONPANELLEFTX, BUTTONFORM1STARTY, TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Button Studios = new Button("Manage Studios",
				(e -> Main.Router.showDynamicView(ViewConstants.STUDIO_MANAGER_VIEW)), BUTTONPANELBUTTONDIM,
				BUTTONPANELRIGHTX, BUTTONFORM1STARTY, TerciaryColor, BUTTONPANELARCRAD, BUTTONPANELFONTSIZE);
		Button Admins = new Button("Admin Directory",
				(e -> Main.Router.showDynamicView(ViewConstants.ADMIN_MANAGER_VIEW)), BUTTONPANELBUTTONDIM,
				BUTTONPANELLEFTX, BUTTONFORM1STARTY + BUTTONFORM1GAP * 1, TerciaryColor, BUTTONPANELARCRAD,
				BUTTONPANELFONTSIZE);
		Button Teachers = new Button("Manage Teachers",
				(e -> Main.Router.showDynamicView(ViewConstants.TEACHER_MANAGER_VIEW)), BUTTONPANELBUTTONDIM,
				BUTTONPANELRIGHTX, BUTTONFORM1STARTY + BUTTONFORM1GAP * 1, TerciaryColor, BUTTONPANELARCRAD,
				BUTTONPANELFONTSIZE);
		Button MainMenu = new Button("Back", (e -> Main.Router.showView(ViewConstants.ADMIN_VIEW)), ChangeButtonD,
				LeftChangeButtonX, ChangeButtonY, TextColor, ChangeButtonArcRad, ChangeButtonFontSize);

		this.setLayout(null);
		this.add(title);
		this.add(Levels);
		this.add(Studios);
		this.add(Admins);
		this.add(Teachers);
		// this.add(AddNewAdmin);
		this.add(MainMenu);

		this.revalidate();
		this.repaint();

	}

}
