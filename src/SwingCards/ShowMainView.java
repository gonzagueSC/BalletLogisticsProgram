package SwingCards;

import static util.SwingConstants.*;

import java.awt.Dimension;

import javax.swing.JPanel;

import control.Main;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.TitleLabel;

public class ShowMainView extends JPanel {

	private TitleLabel title;
	private Button MainMenu;
	private Button sortByUpcoming;
	private Button sortByPast;
	private Button RolesAndCastingTab;
	private Button PerformancesTab;
	private Button CostumesTab;
	private String name = "";
	private String role = "N/A";
	private ShowRolesAndCasting rolesCastingPanel;
	private ShowPerformances showPerformancesPanel;
	private ShowCostumesManager showCostumesPanel;
	private boolean RolesAndCasting = true;
	private boolean Performances = false;
	private boolean Costumes = false;

	public ShowMainView() {

		this.setLayout(null);
		this.setBackground(MainGray);
		title = new TitleLabel(name, TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT, TITLELABELFONTSIZE,
				TextColor);
		MainMenu = new Button("Back", (e -> Main.Router.showView(ViewConstants.SHOW_DIRECTORY_VIEW)), ChangeButtonD, LeftChangeButtonX, ChangeButtonY,
				TextColor, ChangeButtonArcRad, ChangeButtonFontSize);
		RolesAndCastingTab = new Button("Roles & Casting", (e -> Update(name, 1)),
				new Dimension(TABWIDTH, TABHEIGHT), TABSTARTX, TABY,
				(RolesAndCasting) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(), TABARCRAD,
				TABFONTSIZE);
		PerformancesTab = new Button("Performances", (e -> Update(name, 2)),
				new Dimension(TABWIDTH, TABHEIGHT), TABSTARTX + (TABWIDTH + TABGAP) * 1, TABY,
				(Performances) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(), TABARCRAD,
				TABFONTSIZE);
		CostumesTab = new Button("Costumes", (e -> Update(name, 3)), new Dimension(TABWIDTH, TABHEIGHT),
				TABSTARTX + (TABWIDTH + TABGAP) * 2, TABY,
				(Costumes) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(), TABARCRAD, TABFONTSIZE);

		this.add(title);
		this.add(MainMenu);
		this.add(PerformancesTab);
		this.add(CostumesTab);
		this.add(RolesAndCastingTab);

		this.repaint();

	}

	public void Update(String name, int tab) {

		RolesAndCasting = false;
		Performances = false;
		Costumes = false;

		switch (tab) {

		case 1:
			RolesAndCasting = true;
			break;
		case 2:
			Performances = true;
			break;
		case 3:
			Costumes = true;
			break;

		}
		Update(name);

	}

	public void Update(String name) {
		
		this.name = name;

		if (RolesAndCasting) {

			if (rolesCastingPanel == null)
				rolesCastingPanel = new ShowRolesAndCasting(this.name);
			rolesCastingPanel.setVisible(true);
			this.add(rolesCastingPanel);
			this.setComponentZOrder(rolesCastingPanel, 0);

		} else if (rolesCastingPanel.isVisible()) {

			this.remove(rolesCastingPanel);
			rolesCastingPanel.setVisible(false);

		}
		
		if (Performances) {

			if (showPerformancesPanel == null)
				showPerformancesPanel = new ShowPerformances(this.name);
			showPerformancesPanel.setVisible(true);
			this.add(showPerformancesPanel);
			this.setComponentZOrder(showPerformancesPanel, 0);

		} else if (showPerformancesPanel != null && showPerformancesPanel.isVisible()) {

			this.remove(showPerformancesPanel);
			showPerformancesPanel.setVisible(false);

		}

		title.setText(this.name);

		RolesAndCastingTab
				.setBackground((RolesAndCasting) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter());
		rolesCastingPanel.Update(this.name, role);
		PerformancesTab
				.setBackground((Performances) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter());
		CostumesTab.setBackground((Costumes) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter());

		this.revalidate();
		this.repaint();

	}
	
	public void setRoles(String role) {
		
		this.role = role;
		
	}

}
