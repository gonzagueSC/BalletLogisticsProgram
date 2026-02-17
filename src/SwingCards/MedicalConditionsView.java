package SwingCards;

import java.util.Scanner;

import javax.swing.JPanel;

import control.Main;
import control.PromptsService;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.Input;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;

import databaseAccess.*;

public class MedicalConditionsView extends JPanel {

	private TitleLabel title;
	private TitleLabel medicalConditionsLab;
	private Input medicalConditions;
	private Button Save;

	public MedicalConditionsView() {

		this.setBackground(MainGray);
		title = new TitleLabel("Enrollment Details", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);

		medicalConditions = new Input(175, 175, 850, 300, INPUTFORM1INPUTFONTSIZE, TextColor,
				INPUTFORM1ARCRAD);
		Save = new Button("Save", null, ConfirmButtonD, ConfirmButtonX, ConfirmButtonY, ConfirmButtonColor,
				ConfirmButtonArcRad, ConfirmButtonFontSize);
		
		this.add(medicalConditions);
		this.add(Save);
		this.add(title);

		this.setLayout(null);
		
	}

	public void Update(String name) {

		String[] nameSplit = name.split(" ");

		String studentFullName = nameSplit[0] + " " + nameSplit[1];

		String studentBirthDate = nameSplit[2];

		String[] studentData = StudentsModule.getStudentInfo(studentFullName, studentBirthDate);

		medicalConditions.setText(studentData[5]);
		
		this.remove(Save);
		
		Save = new Button("Save", null, ConfirmButtonD, ConfirmButtonX, ConfirmButtonY, ConfirmButtonColor,
				ConfirmButtonArcRad, ConfirmButtonFontSize);

		Save.addActionListener(e -> {
			if (medicalConditions.getText().split(" ").length > 0 && medicalConditions.getText().split(" ")[0] != null
					&& !medicalConditions.getText().split(" ")[0].isBlank()) {

				try {

					String Full = medicalConditions.getText();

					Scanner medCond = new Scanner(Full);

					medCond.nextLine();

					if (medCond.hasNextLine()) {
						medCond.close();
						throw new Exception("tooManyLines");
					}

					studentData[5] = medicalConditions.getText();
					StudentsModule.changeStudentMedical(studentFullName, studentBirthDate, medicalConditions.getText());

					medCond.close();

				} catch (Exception ex) {

					PromptsService.FailurePrompt("Please use a single line");
					return;

				}

			} else {

				PromptsService.FailurePrompt("Required Fields are missing");
				return;

			}

			StudentsModule.writeToFile(studentFullName, studentBirthDate, studentData);
			Main.Router.showView(ViewConstants.INDIVIDUAL_STUDENT_VIEW);

		});
		
		this.add(Save);

		this.revalidate();
		this.repaint();

	}

}
