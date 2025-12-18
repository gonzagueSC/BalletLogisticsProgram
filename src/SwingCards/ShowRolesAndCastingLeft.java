package SwingCards;

import static util.SwingConstants.*;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import control.DialogGenerator;
import systemSwing.Button;
import systemSwing.Panel;

import databaseAccess.*;

public class ShowRolesAndCastingLeft extends Panel {

	private JScrollPane RoleScroller;
	private Panel ScrollPanel;
	private String showName;
	private ShowRolesAndCasting parent;
	private String selected;
	private int Loc = 0;
	private Button newRole;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 570;
	private int BUTTONX = 5;
	private int BUTTONHEIGHT = 70;
	private int BUTTONGAP = 30;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 20;

	public ShowRolesAndCastingLeft(ShowRolesAndCasting Panel) {

		super(false, false, true, true);

		parent = Panel;

		this.setBackground(MainGray.brighter());
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, SecondaryPurple.darker().darker().darker()));
		this.setLayout(null);
		this.setBounds(0, 0, 600, 600);

	}

	public void Update(String name, String selectedRole) {

		if (RoleScroller != null)
			Loc = RoleScroller.getVerticalScrollBar().getValue();
		if (newRole != null)
			this.remove(newRole);
		newRole = new Button("+ Add Role", (e -> DialogGenerator.createNewRoleFrame(name)), BOTTOMADDDim, BOTTOMADDLEFTX, BOTTOMADDY, TextColor,
				BOTTOMADDARCRAD, BOTTOMADDFONTSIZE);

		selected = selectedRole;
		ScrollPanel = new Panel();

		if (RoleScroller != null)
			this.remove(RoleScroller);

		showName = name;

		String[] allRoles = ProductionsModule.getAllRolesByProduction(showName);

		int panelHeight = 530;
		for (int i = 0; i < allRoles.length; i++) {

			int finalI = i;
			final String RoleName = allRoles[finalI];

			Button Production = new Button(RoleName, (e -> parent.Update(showName, RoleName)),
					new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					(RoleName.equals(selected)) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(),
					BUTTONARCRAD, BUTTONFONTSIZE);
			if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {
				panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;
			}

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
		
		this.add(newRole);

		this.add(RoleScroller);
		this.setComponentZOrder(RoleScroller, 0);

		this.revalidate();
		this.repaint();

		SwingUtilities.invokeLater(() -> {
			RoleScroller.getVerticalScrollBar().setValue(Loc);
		});

	}

}
