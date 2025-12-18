package SwingCards;

import javax.swing.JPanel;

import control.Main;
import databaseAccess.*;
import swingConstants.*;
import systemSwing.*;

import static util.SwingConstants.*;

public class AdminCreationView extends JPanel {

	public AdminCreationView() {

		this.setBackground(MainGray);

		TitleLabel title = new TitleLabel("New Admin", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);

		TitleLabel nameExp = new TitleLabel("Username", LOGINSIGNUPX, LOGINSIGNUPTOPLABELY, LOGINSIGNUPWIDTH,
				LOGINSIGNUPLABELHEIGHT, LOGINSIGNUPLABELFONTSIZE, TextColor);

		Input Name = new Input(LOGINSIGNUPX, LOGINSIGNUPTOPINPUTY, LOGINSIGNUPWIDTH, LOGINSIGNUPINPUTHEIGHT,
				LOGINSIGNUPINPUTFONTSIZE, TextColor, LOGINSIGNUPINPUTARCRAD);

		TitleLabel PasswordExp = new TitleLabel("Password", LOGINSIGNUPX, LOGINSIGNUPBOTTOMLABELY, LOGINSIGNUPWIDTH,
				LOGINSIGNUPLABELHEIGHT, LOGINSIGNUPLABELFONTSIZE, TextColor);

		Input Password = new Input(LOGINSIGNUPX, LOGINSIGNUPBOTTOMINPUTY, LOGINSIGNUPWIDTH, LOGINSIGNUPINPUTHEIGHT,
				LOGINSIGNUPINPUTFONTSIZE, TextColor, LOGINSIGNUPINPUTARCRAD);
		Button OpenAdmin = new Button("Create Account", (e -> LogInModule.createAdmin(Name.getText(), Password.getText())), ConfirmButtonD, ConfirmButtonX, ConfirmButtonY,
				ConfirmButtonColor, ConfirmButtonArcRad, ConfirmButtonFontSize);

		Button MainMenu = new Button("Back", (e -> Main.Router.showDynamicView(ViewConstants.ADMIN_MANAGER_VIEW)), BACKBUTTOND, BACKBUTTONX, BACKBUTTONY,
				TextColor, BACKBUTTONARCRAD, BACKBUTTONFONTSIZE);

		this.setLayout(null);
		this.add(title);
		this.add(Name);
		this.add(Password);
		this.add(OpenAdmin);
		this.add(MainMenu);
		this.add(nameExp);
		this.add(PasswordExp);
		this.revalidate();
		this.repaint();

	}

}
