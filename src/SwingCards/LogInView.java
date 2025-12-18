package SwingCards;

import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.Input;
import systemSwing.Panel;
import systemSwing.TitleLabel;
import systemSwing.passwordInput;
import util.Globals;

import static util.SwingConstants.*;

import control.Main;
import control.PromptsService;

import databaseAccess.*;

public class LogInView extends Panel {
	
	Input Name;
	passwordInput Password;

	public LogInView() {

		TitleLabel title = new TitleLabel("Log-in", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		TitleLabel nameExp = new TitleLabel("Username", LOGINSIGNUPX, LOGINSIGNUPTOPLABELY, LOGINSIGNUPWIDTH,
				LOGINSIGNUPLABELHEIGHT, LOGINSIGNUPLABELFONTSIZE, TextColor);
		Name = new Input(LOGINSIGNUPX, LOGINSIGNUPTOPINPUTY, LOGINSIGNUPWIDTH, LOGINSIGNUPINPUTHEIGHT,
				LOGINSIGNUPINPUTFONTSIZE, TextColor, LOGINSIGNUPINPUTARCRAD);
		TitleLabel PasswordExp = new TitleLabel("Password", LOGINSIGNUPX, LOGINSIGNUPBOTTOMLABELY, LOGINSIGNUPWIDTH,
				LOGINSIGNUPLABELHEIGHT, LOGINSIGNUPLABELFONTSIZE, TextColor);
		Password = new passwordInput(LOGINSIGNUPX, LOGINSIGNUPBOTTOMINPUTY, LOGINSIGNUPWIDTH, LOGINSIGNUPINPUTHEIGHT,
				LOGINSIGNUPINPUTFONTSIZE, TextColor, LOGINSIGNUPINPUTARCRAD);
		Button OpenAdmin = new Button("Log in", null, ConfirmButtonD, ConfirmButtonX, ConfirmButtonY,
				ConfirmButtonColor, ConfirmButtonArcRad, ConfirmButtonFontSize);
		
		OpenAdmin.addActionListener(e -> {
			
			if (LogInModule.checkForAdmin(Globals.AdminUsersAndPassword,
					Name.getText(), DatabaseCore.hash(Password.getText()))) {
				Main.Router.showView(ViewConstants.ADMIN_VIEW);
				Password.setText("");
				Name.setText("");
			} else {
				PromptsService.FailurePrompt("Wrong Log-In");
				Password.setText("");
			}
			
		});
		
		Button MainMenu = new Button("Menu", (e -> Main.Router.showView(ViewConstants.MAIN_MENU)), BACKBUTTOND, BACKBUTTONX, BACKBUTTONY,
				TextColor, BACKBUTTONARCRAD, BACKBUTTONFONTSIZE);
		MainMenu.addActionListener(e -> {
			Password.setText("");
			Name.setText("");
		});
		
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
	
	public void Update() {
		
		Name.setText("");
		Password.setText("");
		this.revalidate();
		this.repaint();
		
	}
	
}
