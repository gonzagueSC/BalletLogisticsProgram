package SwingCards;

import java.awt.Dimension;

import javax.swing.*;

import control.PromptsService;

import systemSwing.*;

import databaseAccess.*;

import static util.SwingConstants.*;

public class AddRemoveCastedDialog extends JDialog {

	private String[] casts = new String[0];
	private String StudentID;

	public AddRemoveCastedDialog(JFrame parent, ShowRolesAndCastingRight panel, String productionName, String role,
			String studentID, String[] castList) {

		casts = castList;
		StudentID = studentID;
		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		Button Cancel = new Button("Cancel", (e -> {
			
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		Button Remove = new Button("Remove", (e -> {

			StudentsModule.removeStudentFromRole(studentID, productionName, role,
					ProductionsModule.getAllCastsByProduction(productionName));
			panel.Update(productionName, role);
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 600, 10, RedColor, 25, ChangeButtonFontSize);

		FilterPanel AddRemoveCastedSelection = new FilterPanel();
		TitleLabel title = new TitleLabel("Edit Casting", 50, 20, 700, 30, 30, TextColor);
		Button selectCasts = new Button("Select Casts",

				(e -> new CastSelectionDialog(this, productionName, role, casts)), new Dimension(400, 50), 200, 250,
				SecondaryPurple, 50, 20);

		Button Save = new Button("Save", (e -> {

			if (!StudentID.isBlank() && casts.length != 0) {
				
				StudentsModule.removeStudentFromRole(studentID, productionName, role,
						ProductionsModule.getAllCastsByProduction(productionName));
				StudentsModule.addStudentToRole(studentID, productionName, role, casts);
				panel.Update(productionName, role);
				
				this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				this.dispose();

			} else {

				PromptsService.FailurePrompt("Please select one of each", this);

			}

		}), new Dimension(300, 50), 250, 435, TextColor, 50, 24);

		AddRemoveCastedSelection.setLayout(null);
		AddRemoveCastedSelection.add(title);
		AddRemoveCastedSelection.add(Save);

		this.add(selectCasts);
		this.add(Cancel);
		this.add(Remove);
		this.add(AddRemoveCastedSelection);
		this.setSize(800, 533);

		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

	public void setCasts(String[] arr) {

		casts = arr;

	}

	public void setStudent(String ID) {

		StudentID = ID;

	}

}