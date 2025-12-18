package SwingCards;

import javax.swing.JPanel;

import systemSwing.Button;
import systemSwing.DropDownMenu;
import systemSwing.Input;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;

import java.awt.Font;
import java.time.LocalDate;
import java.util.Arrays;

import control.*;

import databaseAccess.*;
import swingConstants.ViewConstants;

public class StudentCreationView extends JPanel {

	private TitleLabel title;
	private Button Save;

	private TitleLabel firstNameLab, lastNameLab, levelLab, emailLab, phoneNumberLab, medicalConditionsLab,
			dateOfBirthLab, genderLab;

	private Input firstName, lastName, email, phoneNumber, medicalConditions, dateOfBirth;

	DropDownMenu level, gender;

	String[] genders = { "Female", "Male", "Other" };

	public StudentCreationView() {

		this.setBackground(MainGray);
		this.setLayout(null);

		title = new TitleLabel("New Student", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);

		firstNameLab = new TitleLabel("First Name", INPUTPANELLEFTX, INPUTFORM5STARTY - INPUTFORM5LABELOFFSET,
				INPUTPANELINPUTWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		firstName = new Input(INPUTPANELLEFTX, INPUTFORM5STARTY, INPUTPANELINPUTWIDTH, INPUTFORM5INPUTHEIGHT,
				INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		lastNameLab = new TitleLabel("Last Name", INPUTPANELRIGHTX, INPUTFORM5STARTY - INPUTFORM5LABELOFFSET,
				INPUTPANELINPUTWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		lastName = new Input(INPUTPANELRIGHTX, INPUTFORM5STARTY, INPUTPANELINPUTWIDTH, INPUTFORM5INPUTHEIGHT,
				INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		levelLab = new TitleLabel("Level", INPUTPANELLEFTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 1 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		String[] Levels = Arrays.stream(SystemSettingsModule.getAllOfSystemDetail("Levels")).map(s -> s.split(", ")[0]).toArray(String[]::new);
		level = new DropDownMenu(Levels);
		level.SetPos(INPUTPANELLEFTX, INPUTFORM5STARTY + INPUTFORM5GAP * 1);
		level.SetDim(INPUTPANELINPUTWIDTH, INPUTFORM5INPUTHEIGHT);
		level.setFont(new Font(Font.DIALOG, 0, INPUTFORM5INPUTFONTSIZE));

		emailLab = new TitleLabel("Email", INPUTPANELRIGHTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 1 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		email = new Input(INPUTPANELRIGHTX, INPUTFORM5STARTY + INPUTFORM5GAP * 1, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		phoneNumberLab = new TitleLabel("Phone No.", INPUTPANELLEFTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 2 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		phoneNumber = new Input(INPUTPANELLEFTX, INPUTFORM5STARTY + INPUTFORM5GAP * 2, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		medicalConditionsLab = new TitleLabel("Medical Conditions", INPUTPANELRIGHTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 2 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		medicalConditions = new Input(INPUTPANELRIGHTX, INPUTFORM5STARTY + INPUTFORM5GAP * 2, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		dateOfBirthLab = new TitleLabel("Date Of Birth", INPUTPANELLEFTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 3 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		dateOfBirth = new Input(INPUTPANELLEFTX, INPUTFORM5STARTY + INPUTFORM5GAP * 3, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		genderLab = new TitleLabel("Gender", INPUTPANELRIGHTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 3 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		gender = new DropDownMenu(genders);
		gender.SetPos(INPUTPANELRIGHTX, INPUTFORM5STARTY + INPUTFORM5GAP * 3);
		gender.SetDim(INPUTPANELINPUTWIDTH, INPUTFORM5INPUTHEIGHT);
		gender.setFont(new Font(Font.DIALOG, 0, INPUTFORM5INPUTFONTSIZE));
		Button MainMenu = new Button("Back", (e -> Main.Router.showView(ViewConstants.ADMIN_VIEW)), ChangeButtonD,
				LeftChangeButtonX, ChangeButtonY, TextColor, ChangeButtonArcRad, ChangeButtonFontSize);

		Save = new Button("Save", (e -> {

			Button btn = (Button) e.getSource();
			btn.setEnabled(true);

			if (firstName.getText().split(" ").length > 1 || firstName.getText().split(" ").length < 1) {

				PromptsService.FailurePrompt("Do not include whitespace");
				btn.setEnabled(true);
				return;

			}

			if (lastName.getText().split(" ").length > 1 || lastName.getText().split(" ").length < 1) {

				PromptsService.FailurePrompt("Do not include whitespace");
				btn.setEnabled(true);
				return;

			}

			if (level.getSelectedItem().toString().isBlank()) {

				PromptsService.FailurePrompt("No Levels Exist");
				btn.setEnabled(true);
				return;

			}

			try {
				
				DatabaseCore.isValidEmail(email.getText());

			} catch (Exception ex) {
				
				PromptsService.FailurePrompt("Please Format Email Correctly");
				btn.setEnabled(true);
				return;

			}

			try {
				
				DatabaseCore.isValidPhoneNumber(phoneNumber.getText());

			} catch (Exception ex) {
				
				PromptsService.FailurePrompt("Please Format Phone No. Correctly");
				btn.setEnabled(true);
				return;

			}

			try {
				
				String date = dateOfBirth.getText();
				LocalDate check = DatabaseCore.isValidDate(date);

			} catch (Exception ex) {
				
				PromptsService.FailurePrompt("Please format Date Correctly");
				btn.setEnabled(true);
				return;

			}
			
			try {
				
				StudentsModule.addStudent(firstName.getText(),
						lastName.getText(),
						level.getSelectedItem().toString(),
						email.getText(),
						phoneNumber.getText(),
						(medicalConditions.getText().isBlank())?"none":medicalConditions.getText(),
						DatabaseCore.isValidDate(dateOfBirth.getText()).toString(),
						gender.getSelectedItem().toString());
				
			} catch (Exception ex) {
				
				PromptsService.FailurePrompt("Some other error occured");
				ex.printStackTrace();
				btn.setEnabled(true);
				return;
				
			}

		}), ConfirmButtonD, ConfirmButtonX, ConfirmButtonY, ConfirmButtonColor, ConfirmButtonArcRad,
				ConfirmButtonFontSize);

		this.add(title);
		this.add(firstNameLab);
		this.add(firstName);
		this.add(lastNameLab);
		this.add(lastName);
		this.add(levelLab);
		this.add(level);
		this.add(emailLab);
		this.add(email);
		this.add(phoneNumberLab);
		this.add(phoneNumber);
		this.add(medicalConditionsLab);
		this.add(medicalConditions);
		this.add(dateOfBirthLab);
		this.add(dateOfBirth);
		this.add(genderLab);
		this.add(gender);
		this.add(Save);
		this.add(MainMenu);

	}

	public void Update() {
		this.remove(level);
		
		String[] Levels = Arrays.stream(SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.LEVELS)).map(s -> s.split(", ")[0]).toArray(String[]::new);
		level = new DropDownMenu(Levels);
		level.SetPos(INPUTPANELLEFTX, INPUTFORM5STARTY + INPUTFORM5GAP * 1);
		level.SetDim(INPUTPANELINPUTWIDTH, INPUTFORM5INPUTHEIGHT);
		level.setFont(new Font(Font.DIALOG, 0, INPUTFORM5INPUTFONTSIZE));

		firstName.setText("");
		lastName.setText("");
		level.setSelectedItem((SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.LEVELS).length > 0)
				? SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.LEVELS)[0]
				: "");
		email.setText("");
		phoneNumber.setText("");
		medicalConditions.setText("");
		dateOfBirth.setText("");
		gender.setSelectedItem("Female");
		
		this.add(level);

		this.revalidate();
		this.repaint();

	}

}
