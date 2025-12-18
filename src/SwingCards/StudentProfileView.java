package SwingCards;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JPanel;

import control.Main;
import control.PromptsService;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.Input;
import systemSwing.TitleLabel;
import util.Globals;

import databaseAccess.*;

import static util.SwingConstants.*;

public class StudentProfileView extends JPanel {

	private TitleLabel title;
	private TitleLabel firstNameLab;
	private Input firstName;
	private TitleLabel lastNameLab;
	private Input lastName;
	private TitleLabel emailLab;
	private Input email;
	private TitleLabel DOBLab;
	private Input dateOfBirth;
	private TitleLabel GenderLab;
	private Input Gender;
	private TitleLabel AddressLab;
	private Input Address;
	private Button Save;

	public StudentProfileView() {

		this.setBackground(MainGray);
		title = new TitleLabel("Student Profile", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);

		firstNameLab = new TitleLabel(Globals.StudentInfo[0], INPUTPANELLEFTX, INPUTFORM3STARTY - INPUTFORM3LABELOFFSET,
				INPUTPANELINPUTWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM2INPUTFONTSIZE, TextColor);
		firstName = new Input(INPUTPANELLEFTX, INPUTFORM3STARTY, INPUTPANELINPUTWIDTH, INPUTFORM3INPUTHEIGHT,
				INPUTFORM3INPUTFONTSIZE, TextColor, INPUTFORM3ARCRAD);
		lastNameLab = new TitleLabel(Globals.StudentInfo[1], INPUTPANELRIGHTX, INPUTFORM3STARTY - INPUTFORM3LABELOFFSET,
				INPUTPANELINPUTWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM2INPUTFONTSIZE, TextColor);
		lastName = new Input(INPUTPANELRIGHTX, INPUTFORM3STARTY, INPUTPANELINPUTWIDTH, INPUTFORM3INPUTHEIGHT,
				INPUTFORM3INPUTFONTSIZE, TextColor, INPUTFORM3ARCRAD);
		emailLab = new TitleLabel(Globals.StudentInfo[3], INPUTPANELLEFTX,
				INPUTFORM3STARTY + INPUTFORM3GAP * 1 - INPUTFORM3LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM2INPUTFONTSIZE, TextColor);
		email = new Input(INPUTPANELLEFTX, INPUTFORM3STARTY + INPUTFORM3GAP * 1, INPUTPANELINPUTWIDTH,
				INPUTFORM3INPUTHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor, INPUTFORM3ARCRAD);
		DOBLab = new TitleLabel(Globals.StudentInfo[6], INPUTPANELRIGHTX,
				INPUTFORM3STARTY + INPUTFORM3GAP * 1 - INPUTFORM3LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM2INPUTFONTSIZE, TextColor);
		dateOfBirth = new Input(INPUTPANELRIGHTX, INPUTFORM3STARTY + INPUTFORM3GAP * 1, INPUTPANELINPUTWIDTH,
				INPUTFORM3INPUTHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor, INPUTFORM3ARCRAD);
		GenderLab = new TitleLabel(Globals.StudentInfo[7], INPUTPANELLEFTX,
				INPUTFORM3STARTY + INPUTFORM3GAP * 2 - INPUTFORM3LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM2INPUTFONTSIZE, TextColor);
		Gender = new Input(INPUTPANELLEFTX, INPUTFORM3STARTY + INPUTFORM3GAP * 2, INPUTPANELINPUTWIDTH,
				INPUTFORM3INPUTHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor, INPUTFORM3ARCRAD);
		AddressLab = new TitleLabel(Globals.StudentInfo[9], INPUTPANELRIGHTX,
				INPUTFORM3STARTY + INPUTFORM3GAP * 2 - INPUTFORM3LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor);
		Address = new Input(INPUTPANELRIGHTX, INPUTFORM3STARTY + INPUTFORM3GAP * 2, INPUTPANELINPUTWIDTH,
				INPUTFORM3INPUTHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor, INPUTFORM3ARCRAD);
		Save = new Button("Save", null, ConfirmButtonD, ConfirmButtonX, ConfirmButtonY, ConfirmButtonColor,
				ConfirmButtonArcRad, ConfirmButtonFontSize);

		this.setLayout(null);
		
		this.add(title);
		this.add(firstNameLab);
		this.add(firstName);
		this.add(lastNameLab);
		this.add(lastName);
		this.add(emailLab);
		this.add(email);
		this.add(DOBLab);
		this.add(dateOfBirth);
		this.add(GenderLab);
		this.add(Gender);
		this.add(AddressLab);
		this.add(Address);
		this.add(Save);
		
	}

	public void Update(String name) {

		String[] nameSplit = name.split(" ");

		String studentFullName = nameSplit[0] + " " + nameSplit[1];

		String studentBirthDate = nameSplit[2];

		String[] studentData = StudentsModule.getStudentInfo(studentFullName, studentBirthDate);

		firstName.setText(studentData[0]);
		lastName.setText(studentData[1]);
		email.setText(studentData[3]);
		dateOfBirth.setText(studentData[6]);
		Gender.setText(studentData[7]);
		if (!studentData[9].equals("-1"))
			Address.setText(studentData[9]);
		
		this.remove(Save);
		
		Save = new Button("Save", null, ConfirmButtonD, ConfirmButtonX, ConfirmButtonY, ConfirmButtonColor,
				ConfirmButtonArcRad, ConfirmButtonFontSize);

		Save.addActionListener((e -> {
			if (firstName.getText().split(" ").length > 0 && firstName.getText().split(" ")[0] != null
					&& !firstName.getText().split(" ")[0].isBlank() && firstName.getText().split(" ").length < 2) {

				studentData[0] = firstName.getText();

			} else {

				PromptsService.FailurePrompt("Required Fields are missing");
				return;

			}
			if (lastName.getText().split(" ").length > 0 && lastName.getText().split(" ")[0] != null
					&& !lastName.getText().split(" ")[0].isBlank() && lastName.getText().split(" ").length < 2) {

				studentData[1] = lastName.getText();

			} else {

				PromptsService.FailurePrompt("Required Fields are missing");
				return;

			}
			if (email.getText().split(" ").length > 0 && email.getText().split(" ")[0] != null
					&& !email.getText().split(" ")[0].isBlank() && email.getText().split(" ").length < 2) {

				studentData[3] = email.getText();

			} else {

				PromptsService.FailurePrompt("Required Fields are missing");
				return;

			}
			if (dateOfBirth.getText().split(" ").length > 0 && dateOfBirth.getText().split(" ")[0] != null
					&& !dateOfBirth.getText().split(" ")[0].isBlank() && dateOfBirth.getText().split(" ").length < 2) {

				try {

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
					String date = dateOfBirth.getText();
					LocalDate check = LocalDate.parse(date, formatter);
					studentData[6] = dateOfBirth.getText();

				} catch (Exception ex) {

					PromptsService.FailurePrompt("Please format date as: MM/dd/yyyy");
					return;

				}

			} else {

				PromptsService.FailurePrompt("Required Fields are missing");
				return;

			}
			if (Gender.getText().split(" ").length > 0 && Gender.getText().split(" ")[0] != null
					&& !Gender.getText().split(" ")[0].isBlank() && Gender.getText().split(" ").length < 2) {

				studentData[7] = Gender.getText().strip();

			} else {

				PromptsService.FailurePrompt("Required Fields are missing");
				return;

			}
			if (Address.getText().split(" ").length > 0 && Address.getText().split(" ")[0] != null
					&& !Address.getText().split(" ")[0].isBlank()) {

				studentData[9] = Address.getText().strip();

			} else {

				studentData[9] = "-1";

			}

			String newName = studentData[0] + " " + studentData[1] + " " + name.split(" ")[2] + " " + name.split(" ")[3];
			
			StudentsModule.writeToFile(studentFullName, studentBirthDate, studentData);
			Main.Router.showDynamicView(ViewConstants.INDIVIDUAL_STUDENT_VIEW, newName);

		}));
		
		this.add(Save);

		this.revalidate();
		this.repaint();

	}

}
