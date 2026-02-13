package SwingCards;

import static util.SwingConstants.ChangeButtonFontSize;
import static util.SwingConstants.MainPurple;
import static util.SwingConstants.SecondaryPurple;
import static util.SwingConstants.TextColor;
import static util.SwingConstants.TransparentBackground;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import control.*;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.FilterPanel;
import systemSwing.Panel;
import systemSwing.TitleLabel;

import databaseAccess.*;

public class CheckInDialog extends JDialog {

	private JScrollPane Classescroller;
	private Panel ScrollPanel;
	private String StudentID;
	private Button[] allClassButtons;
	FilterPanel ClasseselectionPanel;
	private String className = "";

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 570;
	private int BUTTONX = 15;
	private int BUTTONHEIGHT = 70;
	private int BUTTONGAP = 30;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 20;

	public CheckInDialog(JFrame parent, String ID, StudentView panel) {

		super(parent, "", true);

		StudentID = ID;

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(100, 40), 20, 15, TextColor, 25, ChangeButtonFontSize - 3);

		ScrollPanel = new Panel(false, false, false, false);
		ScrollPanel.setBackground(MainPurple);

		ClasseselectionPanel = new FilterPanel();

		TitleLabel title = new TitleLabel("What class will you be attending?", 50, 20, 700, 30, 30, TextColor);

		Classescroller = new JScrollPane(ScrollPanel);

		String[] allClasses = StudentsModule.getUpcomingClassesToday(ID);

		allClassButtons = new Button[allClasses.length + 1];

		int panelHeight = 300;

		for (int i = 0; i < allClassButtons.length - 1; i++) {

			int finalI = i;
			final String ClassName = allClasses[finalI];

			Button Production = new Button(ClassName.split(", ")[0], (e -> setClass(ClassName.split(", ")[0])),
					new Dimension(500, BUTTONHEIGHT), 36, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					(ClassName.split(", ")[0].equals(className)) ? TextColor.darker() : TextColor, BUTTONARCRAD,
					BUTTONFONTSIZE);

			allClassButtons[i] = Production;

			if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {

				panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;

			}

			ScrollPanel.add(Production);

		}

		Button Production = new Button("Open Practice", (e -> setClass("Open Practice")),
				new Dimension(500, BUTTONHEIGHT), 36,
				(allClassButtons.length - 1) * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
				("Open Practice".equals(className)) ? TextColor.darker() : TextColor, BUTTONARCRAD, BUTTONFONTSIZE);

		allClassButtons[allClassButtons.length - 1] = Production;

		if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {

			panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;

		}

		ScrollPanel.add(Production);

		ScrollPanel.setPreferredSize(new Dimension(572, panelHeight));

		Button Save = new Button("Save", (e -> {

			Button btn = (Button) e.getSource();

			btn.setEnabled(false);

			if (!className.isBlank()) {

				try {

					AttendanceModule.CheckStudentIn(ID, className);
					this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					this.dispose();
					panel.Update(ID);
					PromptsService.SuccessPrompt("Successfully checked in!");

				} catch (Exception e1) {

					e1.printStackTrace();
					PromptsService.FailurePrompt("Database Error Code 2: Writing", CheckInDialog.this);
					btn.setEnabled(true);

				}

			} else {

				PromptsService.FailurePrompt("Please select a Class/Activity", CheckInDialog.this);

				btn.setEnabled(true);

			}
			
			Main.Router.showDynamicView(ViewConstants.STUDENT_VIEW);

		}), new Dimension(300, 50), 250, 435, TextColor, 50, 24);

		Classescroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		Classescroller.getVerticalScrollBar().setUnitIncrement(5);

		Classescroller.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, SecondaryPurple.darker()));

		ClasseselectionPanel.setLayout(null);
		ClasseselectionPanel.add(title);

		ClasseselectionPanel.add(Save);
		ClasseselectionPanel.add(Cancel);
		Classescroller.setBounds(110, 130, 580, 300);
		ClasseselectionPanel.add(Classescroller);

		this.add(ClasseselectionPanel);
		this.setSize(800, 533);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

	public void setClass(String className) {

		this.className = className;

		for (Button btn : allClassButtons) {

			btn.setBackground(btn.getText().split(", ")[0].equals(className) ? TextColor.darker() : TextColor);

		}
		
		this.revalidate();
		this.repaint();

	}

}
