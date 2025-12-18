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

import control.DialogGenerator;
import control.PromptsService;
import systemSwing.Button;
import systemSwing.DropDownMenu;
import systemSwing.FilterPanel;
import systemSwing.GeneralLabel;
import systemSwing.Input;
import systemSwing.SingleSelectMovableCalendar;
import systemSwing.TitleLabel;

import static util.SwingConstants.*;

import databaseAccess.*;

public class NewRehearsalDialog extends JDialog {

	private String[] roles = new String[0];
	private String productionName = "";
	private Button selectRoles;
	DropDownMenu ProductionName;

	public NewRehearsalDialog(JFrame parent, SchedulesRehearsals panel) {

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		FilterPanel AssignStudentSelection = new FilterPanel();

		TitleLabel title = new TitleLabel("New Rehearsal", 50, 20, 900, 30, 30, TextColor);

		SingleSelectMovableCalendar calendarSelection = new SingleSelectMovableCalendar(970, 240);
		calendarSelection.setLocation(15, 70);

		GeneralLabel rehearsalNameLabel = new GeneralLabel("Rehearsal Name", 50, 320, 425, 30, 25, TextColor);
		Input rehearsalName = new Input(50, rehearsalNameLabel.getY() + 35, 425, 40, 25, TextColor, 50);

		GeneralLabel rehearsalTimeLabel = new GeneralLabel("Rehearsal Start Time - End Time", 525, 320, 425, 30, 25,
				TextColor);
		Input rehearsalTime = new Input(525, rehearsalTimeLabel.getY() + 35, 425, 40, 25, TextColor, 50);
		rehearsalTime.setPlaceholderText("hh:mm AM/PM - hh:mm AM/PM");

		GeneralLabel rehearsalStudioLabel = new GeneralLabel("Studio:", 50, 440, 425, 30, 25, TextColor);
		rehearsalStudioLabel.setHorizontalAlignment(JLabel.LEFT);
		DropDownMenu rehearsalStudio = new DropDownMenu(SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.STUDIOS));
		rehearsalStudio.SetPos(200, rehearsalStudioLabel.getY() + 3);
		rehearsalStudio.SetDim(275, rehearsalStudioLabel.getHeight());
		rehearsalStudio.setFont(new Font(Font.DIALOG, 0, rehearsalStudioLabel.getFont().getSize() - 5));

		GeneralLabel rehearsalTeacherLabel = new GeneralLabel("Teacher:", 525, 440, 425, 30, 25, TextColor);
		rehearsalTeacherLabel.setHorizontalAlignment(JLabel.LEFT);
		DropDownMenu rehearsalTeacher = new DropDownMenu(SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.TEACHERS));
		rehearsalTeacher.SetPos(675, rehearsalTeacherLabel.getY() + 3);
		rehearsalTeacher.SetDim(275, rehearsalTeacherLabel.getHeight());
		rehearsalTeacher.setFont(new Font(Font.DIALOG, 0, rehearsalTeacherLabel.getFont().getSize() - 5));

		GeneralLabel productionNameLabel = new GeneralLabel("Production:", 50, 510, 425, 30, 25, TextColor);
		productionNameLabel.setHorizontalAlignment(JLabel.LEFT);
		ProductionName = new DropDownMenu(ProductionsModule.getAllProductions(false, false));
		ProductionName.SetPos(productionNameLabel.getX() + 150, productionNameLabel.getY() + 3);
		ProductionName.SetDim(275, productionNameLabel.getHeight());
		ProductionName.setFont(new Font(Font.DIALOG, 0, productionNameLabel.getFont().getSize() - 5));
		ProductionName.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {

					String selectedItem = (String) e.getItem();

					if (!selectedItem.substring(0, selectedItem.length() - 11).equals(productionName)) {

						roles = new String[0];

					}

					String productionSelection = selectedItem.substring(0, selectedItem.length() - 11);

					setProductionName(productionSelection);

				}

			}

		});

		selectRoles = new Button("Select Roles",
				(e -> DialogGenerator.createRoleSelectionDialog(this,
						ProductionName.getSelectedItem().toString().substring(0,
								ProductionName.getSelectedItem().toString().length() - 11),
						this.roles)),
				new Dimension(225, 30), 625, 510, TextColor, 30, 20);

		Button Save = new Button("Save", (e -> {

			Button btn = (Button) e.getSource();
			btn.setEnabled(false);

			if (calendarSelection.getSelectedDate().isBlank()) {

				PromptsService.FailurePrompt("Please select a Date", this);
				btn.setEnabled(true);
				return;

			}

			if (rehearsalName.getText().isBlank()) {

				PromptsService.FailurePrompt("Please Add a Name", this);
				btn.setEnabled(true);
				return;

			}

			if (!isTimeValid(rehearsalTime.getText().trim().split(" - ")[0])) {

				PromptsService.FailurePrompt("Please Format Times as hh:mm AM/PM - hh:mm AM/PM", this);
				btn.setEnabled(true);
				return;

			}

			if (!isTimeValid(rehearsalTime.getText().trim().split(" - ")[1])) {

				PromptsService.FailurePrompt("Please Format Times as hh:mm AM/PM - hh:mm AM/PM", this);
				btn.setEnabled(true);
				return;

			}

			if (rehearsalStudio.getSelectedItem().toString().isBlank()) {

				PromptsService.FailurePrompt("Please Select a Studio", this);
				btn.setEnabled(true);
				return;

			}

			if (rehearsalTeacher.getSelectedItem().toString().isBlank()) {

				PromptsService.FailurePrompt("Please Select a Teacher", this);
				btn.setEnabled(true);
				return;

			}

			if (ProductionName.getSelectedItem().toString().isBlank()) {

				PromptsService.FailurePrompt("Please Select a Production", this);
				btn.setEnabled(true);
				return;

			}

			if (roles.length == 0 || roles.length < 2 && roles[0].isBlank()) {

				PromptsService.FailurePrompt("Please Select at Least One Role", this);
				btn.setEnabled(true);
				return;

			}

			boolean esc = false;

			try {

				ProductionsModule.addRehearsal(
						ProductionName.getSelectedItem().toString().substring(0,
								ProductionName.getSelectedItem().toString().length() - 11),
						rehearsalName.getText(), calendarSelection.getSelectedDate(),
						rehearsalStudio.getSelectedItem().toString(), rehearsalTeacher.getSelectedItem().toString(),
						roles, rehearsalTime.getText().trim().split(" - ")[0],
						rehearsalTime.getText().trim().split(" - ")[1]);

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

		this.add(rehearsalNameLabel);
		this.add(rehearsalName);
		this.add(rehearsalTimeLabel);
		this.add(rehearsalTime);
		this.add(rehearsalStudioLabel);
		this.add(rehearsalStudio);
		this.add(rehearsalTeacherLabel);
		this.add(rehearsalTeacher);
		this.add(productionNameLabel);
		this.add(ProductionName);
		this.add(selectRoles);
		this.add(calendarSelection);
		this.add(Cancel);

		this.add(AssignStudentSelection);
		this.setSize(1000, 700);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

	public void setRoles(String[] arr) {

		this.roles = arr;

	}

	public void setProductionName(String name) {

		this.productionName = name;

	}

	public boolean isTimeValid(String time) {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("[h]h:mm a");

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
