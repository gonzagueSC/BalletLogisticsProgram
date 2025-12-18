package SwingCards;

import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import control.DialogGenerator;
import control.PromptsService;
import systemSwing.Button;
import systemSwing.DropDownMenu;
import systemSwing.FilterPanel;
import systemSwing.GeneralLabel;
import systemSwing.Input;
import systemSwing.MultipleSelectStaticCalendar;
import systemSwing.TitleLabel;

import static util.SwingConstants.*;

import databaseAccess.*;

public class NewClassDialog extends JDialog {

	private String productionName = "";
	private Button selectRoles;
	DropDownMenu ProductionName;

	String[] levels = new String[0];

	public NewClassDialog(JFrame parent, SchedulesClasses panel) {

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		FilterPanel AssignStudentSelection = new FilterPanel();

		TitleLabel title = new TitleLabel("New Class", 50, 20, 900, 30, 30, TextColor);

		MultipleSelectStaticCalendar calendarSelection = new MultipleSelectStaticCalendar(970, 240);
		calendarSelection.setLocation(15, 70);

		GeneralLabel ClassNameLabel = new GeneralLabel("Class Name", 50, 320, 425, 30, 25, TextColor);
		Input ClassName = new Input(50, ClassNameLabel.getY() + 35, 425, 40, 25, TextColor, 50);

		GeneralLabel ClassTimeLabel = new GeneralLabel("Class Start Time - End Time", 525, 320, 425, 30, 25, TextColor);
		Input ClassTime = new Input(525, ClassTimeLabel.getY() + 35, 425, 40, 25, TextColor, 50);
		ClassTime.setPlaceholderText("hh:mm AM/PM - hh:mm AM/PM");

		GeneralLabel ClassStudioLabel = new GeneralLabel("Studio:", 50, 440, 425, 30, 25, TextColor);
		ClassStudioLabel.setHorizontalAlignment(JLabel.LEFT);
		DropDownMenu ClassStudio = new DropDownMenu(
				SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.STUDIOS));
		ClassStudio.SetPos(200, ClassStudioLabel.getY() + 3);
		ClassStudio.SetDim(275, ClassStudioLabel.getHeight());
		ClassStudio.setFont(new Font(Font.DIALOG, 0, ClassStudioLabel.getFont().getSize() - 5));

		GeneralLabel ClassTeacherLabel = new GeneralLabel("Teacher:", 525, 440, 425, 30, 25, TextColor);
		ClassTeacherLabel.setHorizontalAlignment(JLabel.LEFT);
		DropDownMenu ClassTeacher = new DropDownMenu(
				SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.TEACHERS));
		ClassTeacher.SetPos(675, ClassTeacherLabel.getY() + 3);
		ClassTeacher.SetDim(275, ClassTeacherLabel.getHeight());
		ClassTeacher.setFont(new Font(Font.DIALOG, 0, ClassTeacherLabel.getFont().getSize() - 5));

		GeneralLabel productionNameLabel = new GeneralLabel("Level:", 50, 510, 425, 30, 25, TextColor);
		productionNameLabel.setHorizontalAlignment(JLabel.LEFT);
		Button selectLevels = new Button("Select Levels",
				(e -> DialogGenerator.createLevelSelectionDialog(this, levels)),
				new Dimension(275, productionNameLabel.getHeight()), productionNameLabel.getX() + 150,
				productionNameLabel.getY() + 3, SecondaryPurple, 50, 20);

		Button Save = new Button("Save", (e -> {

			Button btn = (Button) e.getSource();
			btn.setEnabled(false);

			if (calendarSelection.getSelectedDays().length < 1 || calendarSelection.getSelectedDays()[0].isBlank()) {

				PromptsService.FailurePrompt("Please select Day(s)", this);
				btn.setEnabled(true);
				return;

			}

			if (ClassName.getText().isBlank()) {

				PromptsService.FailurePrompt("Please Add a Name", this);
				btn.setEnabled(true);
				return;

			}

			if (!isTimeValid(ClassTime.getText().strip().split("-")[0].strip())) {

				PromptsService.FailurePrompt("Please Format Times as hh:mm AM/PM - hh:mm AM/PM", this);
				btn.setEnabled(true);
				return;

			}

			if (!isTimeValid(ClassTime.getText().strip().split("-")[1].strip())) {

				PromptsService.FailurePrompt("Please Format Times as hh:mm AM/PM - hh:mm AM/PM", this);
				btn.setEnabled(true);
				return;

			}

			if (ClassStudio.getSelectedItem().toString().isBlank()) {

				PromptsService.FailurePrompt("Please Select a Studio", this);
				btn.setEnabled(true);
				return;

			}

			if (ClassTeacher.getSelectedItem().toString().isBlank()) {

				PromptsService.FailurePrompt("Please Select a Teacher", this);
				btn.setEnabled(true);
				return;

			}

			if (levels.length == 0) {

				PromptsService.FailurePrompt("Please Select at least one Level", this);
				btn.setEnabled(true);
				return;

			}

			boolean esc = false;

			try {

				String levelsString = levels[0];

				for (int i = 1; i < levels.length; i++) {

					levelsString += "|" + levels[i];

				}

				SchedulesModule.addClass(ClassName.getText(), calendarSelection.getSelectedDays(),
						ClassStudio.getSelectedItem().toString(), ClassTeacher.getSelectedItem().toString(),
						levelsString, ClassTime.getText().trim().split(" - ")[0],
						ClassTime.getText().trim().split(" - ")[1]);

			} catch (Exception ex) {

				PromptsService.FailurePrompt(ex.getMessage(), this);
				esc = true;
				btn.setEnabled(true);
				return;

			}

			if (esc) {

				return;

			}

			panel.Update();
			PromptsService.SuccessPrompt("Successfully added Classes");

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(300, 50), 350, 602, TextColor, 50, 24);

		AssignStudentSelection.setLayout(null);
		AssignStudentSelection.add(title);

		AssignStudentSelection.add(Save);

		this.add(ClassNameLabel);
		this.add(ClassName);
		this.add(ClassTimeLabel);
		this.add(ClassTime);
		this.add(ClassStudioLabel);
		this.add(ClassStudio);
		this.add(ClassTeacherLabel);
		this.add(ClassTeacher);
		this.add(productionNameLabel);
		this.add(selectLevels);
		this.add(calendarSelection);
		this.add(Cancel);

		this.add(AssignStudentSelection);
		this.setSize(1000, 700);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

	public void setProductionName(String name) {

		this.productionName = name;

	}

	public boolean isTimeValid(String time) {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a");

		if (time == null) {

			return false;

		}

		try {

			// Attempt to parse the string into a LocalTime object
			LocalTime.parse(time.trim(), format);
			return true;

		} catch (Exception e) {

			// If parsing fails, the format is incorrect
			return false;

		}

	}

	public void setLevels(String[] arr) {

		levels = arr;

	}

}
