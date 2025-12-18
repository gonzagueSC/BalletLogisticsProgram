package SwingCards;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import systemSwing.Button;
import systemSwing.FilterPanel;
import systemSwing.Input;
import systemSwing.Panel;
import systemSwing.TitleLabel;

import databaseAccess.*;

import static util.SwingConstants.*;

public class StudentSelectionDialog extends JDialog {

	private JScrollPane StudentScroller;
	private Panel ScrollPanel;
	private String StudentID;
	private Button[] allStudentButtons;
	FilterPanel StudentSelectionPanel;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 570;
	private int BUTTONX = 15;
	private int BUTTONHEIGHT = 70;
	private int BUTTONGAP = 30;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 20;

	public StudentSelectionDialog(AssignStudentDialog parent, String productionName, String role) {

		super(parent, "", true);

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		TitleLabel firstNameExp = new TitleLabel("Search by Name", 110, 70, 580, 30, 18, TextColor);
		firstNameExp.setHorizontalAlignment(JLabel.LEFT);
		Input firstName = new Input(110, 100, 580, 50, 27, TextColor, 15);

		firstName.getDocument().addDocumentListener(new DocumentListener() {

			public void updateLabel() {

				String text = firstName.getText();

				Filter(text);

			}

			@Override
			public void insertUpdate(DocumentEvent e) {

				updateLabel();

			}

			@Override
			public void removeUpdate(DocumentEvent e) {

				updateLabel();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});

		ScrollPanel = new Panel(false, false, false, false);
		ScrollPanel.setBackground(MainPurple);

		StudentSelectionPanel = new FilterPanel();

		TitleLabel title = new TitleLabel("Select Students", 50, 20, 700, 30, 30, TextColor);

		StudentScroller = new JScrollPane(ScrollPanel);

		String[] allStudents = StudentsModule.getAllStudentNames("");

		allStudentButtons = new Button[allStudents.length];

		int panelHeight = 300;
		for (int i = 0; i < allStudents.length; i++) {

			int finalI = i;
			final String StudentName = allStudents[finalI].split(" ")[0] + " " + allStudents[finalI].split(" ")[1]
					+ " | " + allStudents[finalI].split(" ")[2];

			Button Production = new Button(StudentName, (e -> setStudent(allStudents[finalI], finalI)),
					new Dimension(500, BUTTONHEIGHT), 36, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					(allStudents[finalI].split(" ")[3].equals(StudentID)) ? TextColor.darker() : TextColor,
					BUTTONARCRAD, BUTTONFONTSIZE);

			allStudentButtons[i] = Production;

			if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {
				panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;
			}

			ScrollPanel.add(Production);

		}
		ScrollPanel.setPreferredSize(new Dimension(572, panelHeight));

		Button Save = new Button("Save", (e -> {

			parent.setStudent(StudentID);

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(300, 50), 250, 435, TextColor, 50, 24);

		StudentScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		StudentScroller.getVerticalScrollBar().setUnitIncrement(5);

		StudentScroller.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, SecondaryPurple.darker()));

		StudentSelectionPanel.setLayout(null);
		StudentSelectionPanel.add(title);

		StudentSelectionPanel.add(Save);
		StudentScroller.setBounds(110, 130, 580, 300);
		StudentSelectionPanel.add(firstName);
		StudentSelectionPanel.add(firstNameExp);
		StudentSelectionPanel.add(StudentScroller);

		this.add(StudentSelectionPanel);
		this.setSize(800, 533);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

	public void setStudent(String studentName, int finalI) {

		String name = studentName.split(" ")[0] + " " + studentName.split(" ")[1];
		String birthDate = studentName.split(" ")[2];

		String ID = StudentsModule.getStudentID(name, birthDate);
		this.Update(finalI);
		StudentID = ID;

	}

	public void Update(int Button) {

		for (int i = 0; i < allStudentButtons.length; i++) {

			if (i == Button)
				allStudentButtons[i].setBackground(TextColor.darker());
			if (i != Button)
				allStudentButtons[i].setBackground(TextColor);

		}

		this.revalidate();
		this.repaint();

	}

	public void Filter(String name) {

		if (ScrollPanel != null)
			ScrollPanel.removeAll();

		String[] allStudents = StudentsModule.getAllStudentNames(name);

		allStudentButtons = new Button[allStudents.length];

		int panelHeight = 300;
		for (int i = 0; i < allStudents.length; i++) {

			int finalI = i;
			final String StudentName = allStudents[finalI].split(" ")[0] + " " + allStudents[finalI].split(" ")[1]
					+ " | " + allStudents[finalI].split(" ")[2];

			Button Production = new Button(StudentName, (e -> setStudent(allStudents[finalI], finalI)),
					new Dimension(500, BUTTONHEIGHT), 36, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					(allStudents[finalI].split(" ")[3].equals(StudentID)) ? TextColor.darker() : TextColor,
					BUTTONARCRAD, BUTTONFONTSIZE);

			allStudentButtons[i] = Production;

			if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {
				panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;
			}

			ScrollPanel.add(Production);

		}
		ScrollPanel.setPreferredSize(new Dimension(572, panelHeight));
		
		this.revalidate();
		this.repaint();

	}

}
