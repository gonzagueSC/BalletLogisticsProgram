package SwingCards;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;

import control.Main;
import systemSwing.Button;
import systemSwing.Checkbox;
import systemSwing.FilterPanel;
import systemSwing.Input;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;

public class FilterDialog extends JDialog {

	public FilterDialog(JFrame parent, int page, String[] filters) {

		super(parent, "Filter Students", true);

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		FilterPanel filterSelection = new FilterPanel();

		TitleLabel title = new TitleLabel("Filters", 50, 20, 700, 30, 30, TextColor);
		Checkbox MedicalConditions = new Checkbox("Medical Conditions", new Dimension(300, 30), 75, 370, TextColor, 24);
		MedicalConditions.setSelected(!(filters[4] == null));
		Checkbox MoneyOwed = new Checkbox("Money Owed", new Dimension(300, 30), 425, 370, TextColor, 24);
		MoneyOwed.setSelected(!(filters[7] == null));

		TitleLabel FirstNameLabel = new TitleLabel("First Name", 75, 70, 300, 30, 20, TextColor);
		Input FirstName = new Input(75, 100, 300, 30, 20, TextColor, 15);
		FirstName.setText(filters[0]);

		TitleLabel LastNameLabel = new TitleLabel("Last Name", 425, 70, 300, 30, 20, TextColor);
		Input LastName = new Input(425, 100, 300, 30, 20, TextColor, 15);
		LastName.setText(filters[1]);

		TitleLabel AgeLabel = new TitleLabel("Age", 75, 170, 300, 30, 20, TextColor);
		Input Age = new Input(75, 200, 300, 30, 20, TextColor, 15);
		Age.setText(filters[6]);

		TitleLabel GenderLabel = new TitleLabel("Gender", 425, 170, 300, 30, 20, TextColor);
		Input Gender = new Input(425, 200, 300, 30, 20, TextColor, 15);
		Gender.setText(filters[8]);

		TitleLabel LevelLabel = new TitleLabel("Level", 75, 270, 300, 30, 20, TextColor);
		Input Level = new Input(75, 300, 300, 30, 20, TextColor, 15);
		Level.setText(filters[9]);

		TitleLabel ActivityLabel = new TitleLabel("Activity Status", 425, 270, 300, 30, 20, TextColor);
		Input Activity = new Input(425, 300, 300, 30, 20, TextColor, 15);
		Activity.setText(filters[10]);

		Button Save = new Button("Save", (e -> {
			String[] newFilters = new String[filters.length];
			newFilters[2] = filters[2];
			newFilters[3] = filters[3];
			newFilters[5] = filters[5];
			newFilters[7] = filters[7];
			if (MedicalConditions.isSelected())
				newFilters[4] = "Yes";
			if (MoneyOwed.isSelected())
				newFilters[7] = "Yes";

			if (!FirstName.getText().equals(""))
				newFilters[0] = FirstName.getText();
			if (!LastName.getText().equals(""))
				newFilters[1] = LastName.getText();
			if (!Age.getText().equals(""))
				newFilters[6] = Age.getText();
			if (!Gender.getText().equals(""))
				newFilters[8] = Gender.getText().toLowerCase();
			if (!Level.getText().equals(""))
				newFilters[9] = Level.getText().toLowerCase();
			if (!Activity.getText().equals(""))
				newFilters[10] = Activity.getText().toLowerCase();

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

			Main.Router.showStudentDirectoryView(0, newFilters);
		}), new Dimension(300, 50), 250, 435, TextColor, 50, 24);

		filterSelection.setLayout(null);
		filterSelection.add(title);
		filterSelection.add(MedicalConditions);
		filterSelection.add(MoneyOwed);

		filterSelection.add(FirstNameLabel);
		filterSelection.add(FirstName);
		filterSelection.add(LastNameLabel);
		filterSelection.add(LastName);
		filterSelection.add(AgeLabel);
		filterSelection.add(Age);
		filterSelection.add(GenderLabel);
		filterSelection.add(Gender);
		filterSelection.add(LevelLabel);
		filterSelection.add(Level);
		filterSelection.add(ActivityLabel);
		filterSelection.add(Activity);

		filterSelection.add(Save);
		
		this.add(filterSelection);
		this.setSize(800, 533);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
