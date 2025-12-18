package SwingCards;

import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.swing.JPanel;

import control.Main;
import control.PromptsService;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.DropDownMenu;
import systemSwing.Input;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;

import databaseAccess.*;

public class EnrollmentDetailsView extends JPanel {

	private TitleLabel title;
	private TitleLabel dateJoinedLab;
	private Input dateJoined;
	private TitleLabel activityStatusLab;
	private Input activityStatus;
	private TitleLabel tuitionPlanLab;
	private Input tuitionPlan;
	private TitleLabel accountBalanceLab;
	private Input accountBalance;
	private TitleLabel levelLab;
	private DropDownMenu level;
	private Button Save;

	public EnrollmentDetailsView() {

		this.setBackground(MainGray);
		title = new TitleLabel("Enrollment Details", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);

		dateJoinedLab = new TitleLabel("Date Joined", INPUTPANELLEFTX, INPUTFORM3STARTY - INPUTFORM3LABELOFFSET,
				INPUTPANELINPUTWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor);
		dateJoined = new Input(INPUTPANELLEFTX, INPUTFORM3STARTY, INPUTPANELINPUTWIDTH, INPUTFORM3INPUTHEIGHT,
				INPUTFORM3INPUTFONTSIZE, TextColor, INPUTFORM3ARCRAD);

		activityStatusLab = new TitleLabel("Activity Status", INPUTPANELRIGHTX,
				INPUTFORM3STARTY - INPUTFORM3LABELOFFSET, INPUTPANELINPUTWIDTH, INPUTPANELLABELHEIGHT,
				INPUTFORM3INPUTFONTSIZE, TextColor);
		activityStatus = new Input(INPUTPANELRIGHTX, INPUTFORM3STARTY, INPUTPANELINPUTWIDTH, INPUTFORM3INPUTHEIGHT,
				INPUTFORM3INPUTFONTSIZE, TextColor, INPUTFORM3ARCRAD);

		tuitionPlanLab = new TitleLabel("Tuition Plan", INPUTPANELLEFTX,
				INPUTFORM3STARTY + INPUTFORM3GAP * 1 - INPUTFORM3LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor);
		tuitionPlan = new Input(INPUTPANELLEFTX, INPUTFORM3STARTY + INPUTFORM3GAP * 1, INPUTPANELINPUTWIDTH,
				INPUTFORM3INPUTHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor, INPUTFORM3ARCRAD);

		accountBalanceLab = new TitleLabel("Account Balance", INPUTPANELRIGHTX,
				INPUTFORM3STARTY + INPUTFORM3GAP * 1 - INPUTFORM3LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor);
		accountBalance = new Input(INPUTPANELRIGHTX, INPUTFORM3STARTY + INPUTFORM3GAP * 1, INPUTPANELINPUTWIDTH,
				INPUTFORM3INPUTHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor, INPUTFORM3ARCRAD);

		levelLab = new TitleLabel("Level", INPUTPANELCENTERX,
				INPUTFORM3STARTY + INPUTFORM3GAP * 2 - INPUTFORM3LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM3INPUTFONTSIZE, TextColor);
		String[] Levels = Arrays.stream(SystemSettingsModule.getAllOfSystemDetail("Levels")).map(s -> s.split(", ")[0]).toArray(String[]::new);
		level = new DropDownMenu(Levels);
		level.SetPos(INPUTPANELCENTERX, INPUTFORM5STARTY + INPUTFORM2GAP * 2 + 10);
		level.SetDim(INPUTPANELINPUTWIDTH, INPUTFORM3INPUTHEIGHT);
		level.setFont(new Font(Font.DIALOG, 0, INPUTFORM3INPUTFONTSIZE));

		Save = new Button("Save", null, ConfirmButtonD, ConfirmButtonX, ConfirmButtonY, ConfirmButtonColor,
				ConfirmButtonArcRad, ConfirmButtonFontSize);

		this.setLayout(null);
		this.add(title);
		this.add(dateJoinedLab);
		this.add(dateJoined);
		this.add(activityStatusLab);
		this.add(activityStatus);
		this.add(tuitionPlanLab);
		this.add(tuitionPlan);
		this.add(accountBalanceLab);
		this.add(accountBalance);
		this.add(levelLab);
		this.add(level);
		this.add(Save);

	}

	public void Update(String name) {

		String[] nameSplit = name.split(" ");

		String studentFullName = nameSplit[0] + " " + nameSplit[1];

		String studentBirthDate = nameSplit[2];

		String[] studentData = StudentsModule.getStudentInfo(studentFullName, studentBirthDate);

		dateJoined.setText(studentData[8]);
		activityStatus.setText(studentData[10]);
		if (!studentData[11].equals("-1"))
			tuitionPlan.setText(studentData[11]);
		accountBalance.setText(studentData[12]);
		level.setSelectedItem(studentData[2]);

		this.remove(Save);

		Save = new Button("Save", null, ConfirmButtonD, ConfirmButtonX, ConfirmButtonY, ConfirmButtonColor,
				ConfirmButtonArcRad, ConfirmButtonFontSize);

		Save.addActionListener((e -> {
			if (!level.getSelectedItem().toString().isBlank()) {

				studentData[2] = level.getSelectedItem().toString();

			} else {

				PromptsService.FailurePrompt("No Levels Exist");
				return;

			}
			if (activityStatus.getText().split(" ").length > 0 && activityStatus.getText().split(" ")[0] != null
					&& !activityStatus.getText().split(" ")[0].isBlank()
					&& activityStatus.getText().split(" ").length < 2) {

				studentData[10] = activityStatus.getText();

			} else {

				PromptsService.FailurePrompt("Required Fields are missing");
				return;

			}
			if (tuitionPlan.getText().split(" ").length > 0 && tuitionPlan.getText().split(" ")[0] != null
					&& !tuitionPlan.getText().split(" ")[0].isBlank()) {

				studentData[11] = tuitionPlan.getText();

			} else {

				studentData[11] = "-1";

			}
			if (dateJoined.getText().split(" ").length > 0 && dateJoined.getText().split(" ")[0] != null
					&& !dateJoined.getText().split(" ")[0].isBlank() && dateJoined.getText().split(" ").length < 2) {

				try {

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String date = dateJoined.getText();
					LocalDate check = LocalDate.parse(date, formatter);
					studentData[8] = dateJoined.getText();

				} catch (Exception ex) {

					PromptsService.FailurePrompt("Please format date as: yyyy-MM-dd");
					return;

				}

			} else {

				PromptsService.FailurePrompt("Required Fields are missing");
				return;

			}
			if (accountBalance.getText().split(" ").length > 0 && accountBalance.getText().split(" ")[0] != null
					&& !accountBalance.getText().split(" ")[0].isBlank()
					&& accountBalance.getText().split(" ").length < 2) {

				try {

					String accBal = accountBalance.getText();
					Double accBalance = Double.parseDouble(accBal);
					studentData[12] = accountBalance.getText();

				} catch (Exception ex) {

					PromptsService.FailurePrompt("Please use Numbers for Balance");
					return;

				}

			} else {

				PromptsService.FailurePrompt("Required Fields are missing");
				return;

			}

			StudentsModule.writeToFile(studentFullName, studentBirthDate, studentData);
			Main.Router.showView(ViewConstants.INDIVIDUAL_STUDENT_VIEW);

		}));

		this.add(Save);

		this.revalidate();
		this.repaint();

	}

}
