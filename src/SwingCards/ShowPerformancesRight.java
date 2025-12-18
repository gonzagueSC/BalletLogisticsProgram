package SwingCards;

import static util.SwingConstants.*;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import systemSwing.*;

import databaseAccess.*;

public class ShowPerformancesRight extends Panel {

	private JScrollPane RoleScroller;
	private Panel ScrollPanel;
	private String showName;
	private ShowPerformances parent;
	private String selected;
	private int Loc = 0;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 570;
	private int BUTTONX = 5;
	private int BUTTONHEIGHT = 70;
	private int BUTTONGAP = 30;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 20;

	public ShowPerformancesRight(ShowPerformances Panel) {

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
		selected = selectedRole;
		ScrollPanel = new Panel();

		if (RoleScroller != null)
			this.remove(RoleScroller);

		showName = name;

		String[] allRoles = ProductionsModule.getAllRolesByProduction(name);

		int panelHeight = 530;

		for (int i = 0; i < allRoles.length; i++) {

			int finalI = i;
			final String RoleName = allRoles[finalI];

			Button Production = new Button(RoleName, null, new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX,
					i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					(RoleName.equals(selected)) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(),
					BUTTONARCRAD, BUTTONFONTSIZE + 15);
			Production.setHorizontalAlignment(JButton.LEFT);

			String[] allCasts = ProductionsModule.getAllCastsForDropDown(name);

			DropDownMenu Casts = new DropDownMenu(allCasts);
			
			Casts.setSelectedItem(ProductionsModule.getCastByRoleAndPerformance(showName, RoleName, selectedRole.split(", ")[0]));

			Casts.SetPos(BUTTONX + BUTTONWIDTH / 2 + 20,
					i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP + BUTTONHEIGHT - 40);
			Casts.SetDim(BUTTONWIDTH / 2 - 20, 30);
			Casts.SetColors(TextColor, Production.getForeground());

			Casts.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {

					if (e.getStateChange() == ItemEvent.SELECTED) {

						String selectedCast = (String) e.getItem();
						ProductionsModule.setCastToPerformanceRole(showName, selectedRole.split(", ")[0], RoleName, selectedCast);

					}

				}

			});

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

		this.add(RoleScroller);
		this.setComponentZOrder(RoleScroller, 0);

		this.revalidate();
		this.repaint();

		SwingUtilities.invokeLater(() -> {
			RoleScroller.getVerticalScrollBar().setValue(Loc);
		});

	}

}
