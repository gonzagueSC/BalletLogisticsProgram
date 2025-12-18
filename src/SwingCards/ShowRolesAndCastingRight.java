package SwingCards;

import static util.SwingConstants.*;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import control.DialogGenerator;
import systemSwing.Button;
import systemSwing.Panel;
import systemSwing.TitleLabel;

import databaseAccess.*;

public class ShowRolesAndCastingRight extends Panel {

	private JScrollPane RoleScroller;
	private Panel ScrollPanel;
	private String showName;
	private ShowRolesAndCasting parent;
	private String selected;
	private int Loc = 0;
	private Button assignStudent;
	private Button newCast;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 570;
	private int BUTTONX = 5;
	private int BUTTONHEIGHT = 70;
	private int BUTTONGAP = 30;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 20;

	public ShowRolesAndCastingRight(ShowRolesAndCasting Panel) {

		super(false, false, true, true);

		parent = Panel;

		this.setBackground(MainGray.brighter());
		this.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, SecondaryPurple.darker().darker().darker()));
		this.setLayout(null);
		this.setBounds(600, 0, 600, 600);

	}

	public void Update(String name, String selectedRole) {

		if (RoleScroller != null)
			Loc = RoleScroller.getVerticalScrollBar().getValue();
		if (assignStudent != null)
			this.remove(assignStudent);
		if (newCast != null)
			this.remove(newCast);
		newCast = new Button("+ Add Cast", (e -> DialogGenerator.createNewCastDialog(name)), BOTTOMADDDim, BOTTOMADDLEFTX,
				BOTTOMADDY, TextColor, BOTTOMADDARCRAD, BOTTOMADDFONTSIZE);
		if (!selectedRole.equals("N/A"))
			assignStudent = new Button("Assign Student", (e -> DialogGenerator.assignStudentDialog(name, selected, this)),
					BOTTOMADDDim, BOTTOMADDRIGHTX, BOTTOMADDY, TextColor, BOTTOMADDARCRAD, BOTTOMADDFONTSIZE);
		selected = selectedRole;
		ScrollPanel = new Panel();

		if (RoleScroller != null)
			this.remove(RoleScroller);

		showName = name;

		String[] allRoles = StudentsModule.getAllStudentsByRole(name, selectedRole);

		int panelHeight = 530;
		for (int i = 0; i < allRoles.length; i++) {

			int finalI = i;
			final String RoleName = allRoles[finalI];

			Button Production = new Button(StudentsModule.getStudentDisplayByID(RoleName),
					(e -> DialogGenerator.addRemoveDialog(name, selectedRole, this, RoleName,
							StudentsModule.getCastsByStudentRole(name, RoleName, selected))),
					new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					(RoleName.equals(selected)) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(),
					BUTTONARCRAD, BUTTONFONTSIZE+15);
			Production.setHorizontalAlignment(JButton.LEFT);
			Production.setVerticalAlignment(JButton.TOP);
			TitleLabel Casts = new TitleLabel(Arrays.toString(StudentsModule.getCastsByStudentRole(name, RoleName, selected)),
					BUTTONX + 20, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP + BUTTONHEIGHT - 50, BUTTONWIDTH - 40, BUTTONHEIGHT, BUTTONFONTSIZE - 5,
					Production.getForeground());
			Casts.setHorizontalAlignment(JLabel.RIGHT);
			if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {
				panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;
			}

			ScrollPanel.add(Casts);
			ScrollPanel.add(Production);

		}
		ScrollPanel.setPreferredSize(new Dimension(580, panelHeight));
		ScrollPanel.setBackground(MainPurple.brighter());

		RoleScroller = new JScrollPane(ScrollPanel);
		RoleScroller.getViewport().setBackground(MainPurple.brighter());

		RoleScroller.setBounds(10, 10, 580, 490);
		RoleScroller.setBorder(null);
		RoleScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		RoleScroller.getVerticalScrollBar().setUnitIncrement(5);
		RoleScroller.setBackground(SecondaryPurple);

		if (!selectedRole.equals("N/A"))
			this.add(assignStudent);
		this.add(newCast);

		this.add(RoleScroller);
		this.setComponentZOrder(RoleScroller, 0);

		this.revalidate();
		this.repaint();

		SwingUtilities.invokeLater(() -> {
			RoleScroller.getVerticalScrollBar().setValue(Loc);
		});

	}

}
