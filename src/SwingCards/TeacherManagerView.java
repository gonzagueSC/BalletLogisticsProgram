package SwingCards;

import static util.SwingConstants.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import control.DialogGenerator;
import control.Main;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.Panel;
import systemSwing.TitleLabel;

import databaseAccess.*;

public class TeacherManagerView extends JPanel {

	private TitleLabel title;
	private JScrollPane scrollPane;
	private Button MainMenu;
	private Button sortByUpcoming;
	private Button sortByPast;
	private Button addNewTeacher;
	private Button allTeachers;
	private Button UpcomingTeachers;
	private Button PastTeachers;
	private Panel ScrollPanel;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 1100;
	private int BUTTONX = 50;
	private int BUTTONHEIGHT = 100;
	private int BUTTONGAP = 50;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 30;

	public TeacherManagerView() {

		this.setOpaque(false);
		this.setLayout(null);
		this.setBackground(MainGray);
		title = new TitleLabel("All Teachers", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		MainMenu = new Button("Back", (e -> Main.Router.showView(ViewConstants.SYSTEM_SETTINGS_VIEW)), ChangeButtonD, LeftChangeButtonX,
				ChangeButtonY, TextColor, ChangeButtonArcRad, ChangeButtonFontSize);
		addNewTeacher = new Button("+ ADD NEW TEACHER", (e -> DialogGenerator.createNewTeacherFrame(this)),
				new Dimension(ADDBUTTONWIDTH, ADDBUTTONHEIGHT), ADDBUTTONX, ADDBUTTONY, SecondaryPurple.brighter(),
				ADDBUTTONARCRAD, ADDBUTTONFONTSIZE);

		this.add(title);
		this.add(MainMenu);
		this.add(addNewTeacher);

		this.repaint();

	}

	public void Update() {

		if (scrollPane != null)
			this.remove(scrollPane);

		String[] allTeachers = SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.TEACHERS);

		ScrollPanel = new Panel(false, false, false, false);
		int panelHeight = 600;

		for (int i = 0; i < allTeachers.length; i++) {

			int finalI = i;
			final String TeacherName = allTeachers[finalI];

			Button Teacher = new Button(TeacherName, (null),
					new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					SecondaryPurple.brighter(), BUTTONARCRAD, BUTTONFONTSIZE);
			Teacher.setEnabled(false);

			Teacher.setHorizontalAlignment(JLabel.LEFT);

			Button Remove = new Button("Remove", (e -> {

				SystemSettingsModule.removeSystemDetail("Teachers", TeacherName);
				Update();

			}), new Dimension(200, 80), BUTTONX + 890, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP + 10, RedColor,
					BUTTONARCRAD - 5, BUTTONFONTSIZE - 10);

			if (panelHeight < Teacher.getY() + BUTTONGAP + BUTTONHEIGHT) {

				panelHeight = Teacher.getY() + BUTTONGAP + BUTTONHEIGHT;

			}

			ScrollPanel.add(Remove);

			ScrollPanel.add(Teacher);

		}

		ScrollPanel.setPreferredSize(new Dimension(1200, panelHeight));
		ScrollPanel.setBackground(MainPurple.brighter());

		scrollPane = new JScrollPane(ScrollPanel);
		scrollPane.setOpaque(false);
		scrollPane.setBounds(0, 200, 1200, 600);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(5);
		scrollPane.setBackground(SecondaryPurple);

		this.add(scrollPane);
		this.setComponentZOrder(scrollPane, 0);

		this.revalidate();
		this.repaint();

	}

}
