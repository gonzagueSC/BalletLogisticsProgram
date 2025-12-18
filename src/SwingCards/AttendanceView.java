package SwingCards;

import javax.swing.JPanel;

import control.Main;
import databaseAccess.LogInModule;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.Input;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;

public class AttendanceView extends JPanel {

	// FILE SPECIFIC VARIABLES

	int RegInputX = 350;
	int RegInputWidth = 500;
	int LabelOffset = 110;
	int LabelHeight = 50;
	
	Input textField;
	Input birthDateField;

	public AttendanceView() {

		this.setBackground(MainGray);
		TitleLabel title = new TitleLabel("Student Log-In", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);

		TitleLabel exp = new TitleLabel("Full Name", TITLELABELX, INPUTFORM1STARTY - INPUTFORM2GAP / 3 + LabelOffset,
				TITLELABELWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM1INPUTFONTSIZE, TextColor);

		textField = new Input(RegInputX, INPUTFORM1STARTY - INPUTFORM2GAP / 3, RegInputWidth, INPUTFORM1INPUTHEIGHT,
				INPUTFORM1INPUTFONTSIZE, TextColor, INPUTFORM1ARCRAD);
		TitleLabel BirthDate = new TitleLabel("Birth Date", TITLELABELX, INPUTFORM2STARTY + LabelOffset - 20 + INPUTFORM2GAP,
				TITLELABELWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM1INPUTFONTSIZE, TextColor);

		birthDateField = new Input(RegInputX, INPUTFORM2STARTY + INPUTFORM2GAP, RegInputWidth, INPUTFORM2INPUTHEIGHT,
				INPUTFORM1INPUTFONTSIZE, TextColor, INPUTFORM1ARCRAD);
		Button AddStudents = new Button("Admin", (e -> Main.Router.showView(ViewConstants.LOGIN_VIEW)), ChangeButtonD, ChangeButtonX,
				ChangeButtonY, RedColor, ChangeButtonArcRad, ChangeButtonFontSize);
		Button MarkPresent = new Button("Log in", (e -> LogInModule.LogStudentIn(textField.getText(), birthDateField.getText())), ConfirmButtonD, ConfirmButtonX, ConfirmButtonY,
				ConfirmButtonColor, ConfirmButtonArcRad, ConfirmButtonFontSize);
		Button MainMenu = new Button("Menu", (e -> Main.Router.showView(ViewConstants.MAIN_MENU)), ChangeButtonD, LeftChangeButtonX,
				ChangeButtonY, TextColor, ChangeButtonArcRad, ChangeButtonFontSize);

		this.setLayout(null);
		this.add(title);
		this.add(textField);
		this.add(BirthDate);
		this.add(birthDateField);
		this.add(AddStudents);
		this.add(MarkPresent);
		this.add(MainMenu);
		this.add(exp);

		this.revalidate();
		this.repaint();

	}
	
	public void Update() {
		
		textField.setText("");
		birthDateField.setText("");
		this.revalidate();
		this.repaint();
		
	}

}
