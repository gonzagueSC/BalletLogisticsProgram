package SwingCards;

import java.awt.Dimension;

import javax.swing.JPanel;

import control.DialogGenerator;
import control.Main;
import swingConstants.*;
import systemSwing.Button;
import systemSwing.TitleLabel;

import databaseAccess.*;

import static util.SwingConstants.*;

public class StudentRepertoryView extends JPanel {

	TitleLabel title;
	TitleLabel SortBy;
	Button MainMenu;
	Button Next;
	Button Back;
	Button Filters;
	Button SortByFirstName;
	Button SortByLastName;
	Button SortByAge;
	Button SortByDateJoined;

	public StudentRepertoryView() {

		this.setBackground(MainGray);
		title = new TitleLabel("Student Directory", 100, 25, 1000, 100, 70, TextColor);

		SortBy = new TitleLabel("Sort By:", 400, 150, 100, 40, 20, TextColor);

		MainMenu = new Button("Back", (e -> Main.Router.showView(ViewConstants.ADMIN_VIEW)), ChangeButtonD, 30, 30, TextColor,
				ChangeButtonArcRad, ChangeButtonFontSize);

		Filters = new Button("Filter", null, new Dimension(100, 40), 50, 150, ConfirmButtonColor, 30, 20);

		SortByFirstName = new Button("First Name", null, new Dimension(155, 40), 500, 150, ConfirmButtonColor, 30, 20);
		SortByLastName = new Button("Last Name", null, new Dimension(155, 40), 665, 150, ConfirmButtonColor, 30, 20);
		SortByAge = new Button("Age", null, new Dimension(155, 40), 830, 150, ConfirmButtonColor, 30, 20);
		SortByDateJoined = new Button("Date Joined", null, new Dimension(155, 40), 995, 150, ConfirmButtonColor, 30,
				20);

		this.setLayout(null);

		this.add(Filters);

		this.add(MainMenu);
		this.add(title);
		this.add(SortBy);
		this.add(SortByFirstName);
		this.add(SortByDateJoined);
		this.add(SortByAge);
		this.add(SortByLastName);

		this.revalidate();
		this.repaint();

	}

	public void Update(int page, String[] filters) {

		String[] allStudents = StudentsModule.getAllStudentNames(filters);

		this.removeAll();

		SortByFirstName = new Button("First Name", null, new Dimension(155, 40), 500, 150,
				(filters[2] == null) ? ConfirmButtonColor : ConfirmButtonColor.darker(), 30, 20);
		SortByLastName = new Button("Last Name", null, new Dimension(155, 40), 665, 150,
				(filters[3] == null) ? ConfirmButtonColor : ConfirmButtonColor.darker(), 30, 20);
		SortByAge = new Button("Age", null, new Dimension(155, 40), 830, 150,
				(filters[7] == null) ? ConfirmButtonColor : ConfirmButtonColor.darker(), 30, 20);
		SortByDateJoined = new Button("Date Joined", null, new Dimension(155, 40), 995, 150,
				(filters[5] == null) ? ConfirmButtonColor : ConfirmButtonColor.darker(), 30, 20);

		Filters = new Button("Filter", null, new Dimension(100, 40), 50, 150, ConfirmButtonColor, 30, 20);
		Filters.addActionListener(e -> DialogGenerator.createFilterFrame(page, filters));

		if (page * PageTotal > allStudents.length)
			Main.Router.showStudentDirectoryView(page - 1, filters);

		if (allStudents.length > PageTotal * (page + 1)) {

			Next = new Button("→", (e -> Main.Router.showStudentDirectoryView(page + 1, filters)), ChangeButtonD,
					CoreVariables.SCREENWIDTH - MarginX - ChangeButtonD.width, 670, ConfirmButtonColor, ChangeButtonArcRad,
					ChangeButtonFontSize);
			this.add(Next);

		}

		if (page != 0) {

			Back = new Button("←", (e -> Main.Router.showStudentDirectoryView(page - 1, filters)), ChangeButtonD, MarginX, 670,
					ConfirmButtonColor, ChangeButtonArcRad, ChangeButtonFontSize);
			this.add(Back);

		}

		for (int i = PageTotal * (page); i < PageTotal * (page + 1) && i < allStudents.length; i++) {

			int finalI = i;

			int ButtonWidth = (((CoreVariables.SCREENWIDTH - 2 * MarginX) + PaddingX) / Columns) - PaddingX;
			int ButtonHeight = (((CoreVariables.SCREENHEIGHT - MarginY - StartY) + PaddingY) / Rows) - PaddingY;

			Button Student = new Button(allStudents[finalI].split(" ")[0] + " " + allStudents[finalI].split(" ")[1],
					(e -> Main.Router.showDynamicView(ViewConstants.INDIVIDUAL_STUDENT_VIEW, allStudents[finalI])),
					new Dimension(ButtonWidth, ButtonHeight), MarginX + ((i) % Columns) * (ButtonWidth + PaddingX),
					StartY + ((ButtonHeight + PaddingY) * ((i / Columns) - page * Rows)), TerciaryColor, StudentArcRad,
					ChangeButtonFontSize);
			this.add(Student);

		}

		SortByFirstName.addActionListener(e -> {
			String filter[] = filters.clone();
			filters[3] = null;
			filters[5] = null;
			filters[7] = null;
			if ((filters[2] == null) || !filters[2].equals("Yes")) {
				filters[2] = "Yes";

			} else {
				filters[2] = null;
			}
			Main.Router.showStudentDirectoryView(page, filters);
		});
		SortByLastName.addActionListener(e -> {
			String filter[] = filters.clone();
			filters[2] = null;
			filters[5] = null;
			filters[7] = null;
			if ((filters[3] == null) || !filters[3].equals("Yes")) {
				filters[3] = "Yes";
			} else {
				filters[3] = null;
			}
			Main.Router.showStudentDirectoryView(page, filters);
		});
		SortByAge.addActionListener(e -> {
			String filter[] = filters.clone();
			filters[2] = null;
			filters[5] = null;
			filters[3] = null;
			if ((filters[7] == null) || !filters[7].equals("Yes")) {
				filters[7] = "Yes";
			} else {
				filters[7] = null;
			}
			Main.Router.showStudentDirectoryView(page, filters);
		});
		SortByDateJoined.addActionListener(e -> {
			String filter[] = filters.clone();
			filters[3] = null;
			filters[2] = null;
			filters[7] = null;
			if ((filters[5] == null) || !filters[5].equals("Yes")) {
				filters[5] = "Yes";
			} else {
				filters[5] = null;
			}
			Main.Router.showStudentDirectoryView(page, filters);
		});

		this.add(Filters);

		this.add(MainMenu);
		this.add(title);
		this.add(SortBy);
		this.add(SortByFirstName);
		this.add(SortByDateJoined);
		this.add(SortByAge);
		this.add(SortByLastName);

		this.repaint();

	}

}
