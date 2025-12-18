package SwingCards;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

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

public class SchedulesClassesEditor extends JDialog {

	private String productionName = "";
	private Button selectRoles;
	DropDownMenu ProductionName;

	public SchedulesClassesEditor(JFrame parent, SchedulesClasses panel, String classString) {

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		FilterPanel AssignStudentSelection = new FilterPanel();

		TitleLabel title = new TitleLabel("", 50, 20, 900, 30, 30, TextColor);

		MultipleSelectStaticCalendar calendarSelection = new MultipleSelectStaticCalendar(970, 240);
		calendarSelection.setLocation(15, 70);

		for (String day : classString.split(", ")[1].split(" ")) {

			calendarSelection.UpdateSelected(day);
			
		}

		GeneralLabel ClassTimeLabel = new GeneralLabel("Class Start Time - End Time", 525, 320, 425, 30, 25, TextColor);
		Input ClassTime = new Input(525, ClassTimeLabel.getY() + 35, 425, 40, 25, TextColor, 50);
		ClassTime.setPlaceholderText("hh:mm AM/PM - hh:mm AM/PM");
		
		ClassTime.setText(classString.split(", ")[2] + " - " + classString.split(", ")[3]);

		GeneralLabel ClassStudioLabel = new GeneralLabel("Studio:", 50, 440, 425, 30, 25, TextColor);
		ClassStudioLabel.setHorizontalAlignment(JLabel.LEFT);
		DropDownMenu ClassStudio = new DropDownMenu(SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.STUDIOS));
		ClassStudio.SetPos(200, ClassStudioLabel.getY() + 3);
		ClassStudio.SetDim(275, ClassStudioLabel.getHeight());
		ClassStudio.setFont(new Font(Font.DIALOG, 0, ClassStudioLabel.getFont().getSize() - 5));
		
		ClassStudio.setSelectedItem(classString.split(", ")[4]);

		GeneralLabel ClassTeacherLabel = new GeneralLabel("Teacher:", 525, 440, 425, 30, 25, TextColor);
		ClassTeacherLabel.setHorizontalAlignment(JLabel.LEFT);
		DropDownMenu ClassTeacher = new DropDownMenu(SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.TEACHERS));
		ClassTeacher.SetPos(675, ClassTeacherLabel.getY() + 3);
		ClassTeacher.SetDim(275, ClassTeacherLabel.getHeight());
		ClassTeacher.setFont(new Font(Font.DIALOG, 0, ClassTeacherLabel.getFont().getSize() - 5));
		
		ClassTeacher.setSelectedItem(classString.split(", ")[5]);

		GeneralLabel productionNameLabel = new GeneralLabel("Level:", 50, 510, 425, 30, 25, TextColor);
		productionNameLabel.setHorizontalAlignment(JLabel.LEFT);
		ProductionName = new DropDownMenu(SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.LEVELS));
		ProductionName.SetPos(productionNameLabel.getX() + 150, productionNameLabel.getY() + 3);
		ProductionName.SetDim(275, productionNameLabel.getHeight());
		ProductionName.setFont(new Font(Font.DIALOG, 0, productionNameLabel.getFont().getSize() - 5));
		
		ProductionName.setSelectedItem(classString.split(", ")[6]);
		
		ProductionName.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {

					String selectedItem = (String) e.getItem();

					setProductionName(selectedItem);

				}

			}

		});

		Button Save = new Button("Save", (e -> {

			Button btn = (Button) e.getSource();
			btn.setEnabled(false);

			if (calendarSelection.getSelectedDays().length < 1 || calendarSelection.getSelectedDays()[0].isBlank()) {

				PromptsService.FailurePrompt("Please select Day(s)", this);
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

			if (ProductionName.getSelectedItem().toString().isBlank()) {

				PromptsService.FailurePrompt("Please Select a Production", this);
				btn.setEnabled(true);
				return;

			}

			boolean esc = false;

			try {

				SchedulesModule.editClass(classString, classString.split(", ")[0], calendarSelection.getSelectedDays(),
						ClassStudio.getSelectedItem().toString(), ClassTeacher.getSelectedItem().toString(),
						ProductionName.getSelectedItem().toString(), ClassTime.getText().trim().split(" - ")[0],
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

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(300, 50), 350, 602, TextColor, 50, 24);

		AssignStudentSelection.setLayout(null);
		AssignStudentSelection.add(title);

		AssignStudentSelection.add(Save);
		
		this.add(ClassTimeLabel);
		this.add(ClassTime);
		this.add(ClassStudioLabel);
		this.add(ClassStudio);
		this.add(ClassTeacherLabel);
		this.add(ClassTeacher);
		this.add(productionNameLabel);
		this.add(ProductionName);
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

}
