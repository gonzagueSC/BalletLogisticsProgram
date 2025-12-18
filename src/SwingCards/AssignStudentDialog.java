package SwingCards;
import java.awt.Dimension;

import javax.swing.*;

import control.PromptsService;
import systemSwing.*;

import static util.SwingConstants.*;

import databaseAccess.*;

public class AssignStudentDialog extends JDialog {

	private String[] casts = new String[0];
	private String StudentID;

	public AssignStudentDialog(JFrame parent, ShowRolesAndCastingRight panel, String productionName, String role) {

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		FilterPanel AssignStudentSelection = new FilterPanel();

		TitleLabel title = new TitleLabel("Assign Student", 50, 20, 700, 30, 30, TextColor);

		Button selectStudents = new Button("Select Students",
				(e -> new StudentSelectionDialog(this, productionName, role)), new Dimension(400, 50), 200, 180,
				SecondaryPurple, 50, 20);
		Button selectCasts = new Button("Select Casts",
				(e -> new CastSelectionDialog(this, productionName, role, casts)), new Dimension(400, 50), 200, 300,
				SecondaryPurple, 50, 20);

		Button Save = new Button("Assign", (e -> {

			if (!StudentID.isBlank() && casts.length != 0) {

				StudentsModule.UpdateStudentRoles(StudentID, productionName, role, casts);
				panel.Update(productionName, role);

				this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				this.dispose();
			} else {
				
				PromptsService.FailurePrompt("Please select one of each", this);
				
			}

		}), new Dimension(300, 50), 250, 435, TextColor, 50, 24);

		AssignStudentSelection.setLayout(null);
		AssignStudentSelection.add(title);

		AssignStudentSelection.add(Save);

		this.add(selectCasts);
		this.add(selectStudents);
		this.add(Cancel);

		this.add(AssignStudentSelection);
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
