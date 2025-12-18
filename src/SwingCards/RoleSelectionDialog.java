package SwingCards;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

import systemSwing.Button;
import systemSwing.Checkbox;
import systemSwing.FilterPanel;
import systemSwing.Panel;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;

import databaseAccess.*;

public class RoleSelectionDialog extends JDialog {

	private JScrollPane RoleScroller;
	private Panel ScrollPanel;
	private String[] Roles;
	private String StudentID;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 570;
	private int BUTTONX = 15;
	private int BUTTONHEIGHT = 70;
	private int BUTTONGAP = 30;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 20;

	public RoleSelectionDialog(NewRehearsalDialog parent, String productionName, String[] roles) {

		super(parent, "", true);

		this.Roles = roles;

		String[] allRoles = ProductionsModule.getAllRolesByProduction(productionName);

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		ScrollPanel = new Panel(false, false, false, false);
		ScrollPanel.setBackground(MainPurple);

		FilterPanel RoleSelectionPanel = new FilterPanel();

		TitleLabel title = new TitleLabel("Select Roles", 50, 20, 700, 30, 30, TextColor);

		RoleScroller = new JScrollPane(ScrollPanel);
		Checkbox[] RoleSelection = new Checkbox[allRoles.length];

		int panelHeight = 300;

		for (int i = 0; i < allRoles.length; i++) {

			int finalI = i;
			final String RoleName = allRoles[finalI];

			Checkbox Production = new Checkbox(RoleName, new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX,
					i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP, TextColor, 35);
			Production.setHorizontalAlignment(JCheckBox.LEFT);
			Production.setIconTextGap(40);

			for (int j = 0; j < Roles.length; j++) {

				if (Roles[j].equals(RoleName)) {

					Production.setSelected(true);

				}

			}

			if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {

				panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;

			}

			RoleSelection[i] = Production;

			ScrollPanel.add(Production);

		}

		ScrollPanel.setPreferredSize(new Dimension(572, panelHeight));

		Button Save = new Button("Save", (e -> {

			ArrayList<String> selectedRoles = new ArrayList<String>();

			for (int i = 0; i < RoleSelection.length; i++) {

				if (RoleSelection[i].isSelected()) {

					selectedRoles.add(RoleSelection[i].getText());

				}

			}
			
			parent.setRoles(selectedRoles.toArray(new String[0]));

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(300, 50), 250, 435, TextColor, 50, 24);

		RoleScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		RoleScroller.getVerticalScrollBar().setUnitIncrement(5);

		RoleScroller.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, SecondaryPurple.darker()));

		RoleSelectionPanel.setLayout(null);
		RoleSelectionPanel.add(title);

		RoleSelectionPanel.add(Save);
		RoleScroller.setBounds(110, 100, 580, 300);
		RoleSelectionPanel.add(RoleScroller);

		if (allRoles.length == 0 || allRoles.length < 2 && allRoles[0].isBlank()) {

			RoleSelectionPanel.removeAll();
			title = new TitleLabel("No roles available", 50, 240, 700, 30, 30, TextColor);
			Button Cancel = new Button("Cancel", (e -> {

				this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				this.dispose();

			}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);
			RoleSelectionPanel.add(Cancel);
			RoleSelectionPanel.add(title);

		}

		this.add(RoleSelectionPanel);

		this.setSize(800, 533);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
