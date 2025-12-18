package SwingCards;

import static util.SwingConstants.*;

import java.awt.Dimension;

import javax.swing.JPanel;

import control.Main;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.TitleLabel;

public class SchedulesMainView extends JPanel {

	private TitleLabel title;
	private Button MainMenu;
	private Button sortByUpcoming;
	private Button sortByPast;
	private Button ClassesTab;
	private Button RehearsalsTab;
	private Button GeneralTab;
	private SchedulesClasses ClassesPanel;
	private SchedulesRehearsals SchedulesRehearsalsPanel;
	private SchedulesGeneral SchedulesGeneralPanel;
	private boolean Classes = true;
	private boolean Rehearsals = false;
	private boolean General = false;

	public SchedulesMainView() {

		this.setLayout(null);
		this.setBackground(MainGray);
		title = new TitleLabel("Schedule Center", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		MainMenu = new Button("Back", (e -> Main.Router.showView(ViewConstants.ADMIN_VIEW)), ChangeButtonD, LeftChangeButtonX, ChangeButtonY,
				TextColor, ChangeButtonArcRad, ChangeButtonFontSize);
		ClassesTab = new Button("Classes", (e -> Update(1)), new Dimension(TABWIDTH, TABHEIGHT),
				TABSTARTX, TABY, (Classes) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(),
				TABARCRAD, TABFONTSIZE);
		RehearsalsTab = new Button("Rehearsals", (e -> Update(2)), new Dimension(TABWIDTH, TABHEIGHT),
				TABSTARTX + (TABWIDTH + TABGAP) * 1, TABY,
				(Rehearsals) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(), TABARCRAD,
				TABFONTSIZE);
		GeneralTab = new Button("General", (e -> Update(3)), new Dimension(TABWIDTH, TABHEIGHT),
				TABSTARTX + (TABWIDTH + TABGAP) * 2, TABY,
				(General) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(), TABARCRAD, TABFONTSIZE);

		this.add(title);
		this.add(MainMenu);
		this.add(RehearsalsTab);
		this.add(GeneralTab);
		this.add(ClassesTab);

		this.repaint();

	}

	public void Update(int tab) {

		Classes = false;
		Rehearsals = false;
		General = false;

		switch (tab) {

		case 1:
			Classes = true;
			break;

		case 2:
			Rehearsals = true;
			break;

		case 3:
			General = true;
			break;

		}
		
		this.Update();

	}

	public void Update() {

		if (Classes) {

			if (ClassesPanel == null)
				ClassesPanel = new SchedulesClasses(this);
			ClassesPanel.Update();
			ClassesPanel.setVisible(true);
			this.add(ClassesPanel);
			this.setComponentZOrder(ClassesPanel, 0);

		} else if (ClassesPanel != null && ClassesPanel.isVisible()) {

			this.remove(ClassesPanel);
			ClassesPanel.setVisible(false);

		}

		if (Rehearsals) {

			if (SchedulesRehearsalsPanel == null)
				SchedulesRehearsalsPanel = new SchedulesRehearsals(this);
			SchedulesRehearsalsPanel.Update();
			SchedulesRehearsalsPanel.setVisible(true);
			this.add(SchedulesRehearsalsPanel);
			this.setComponentZOrder(SchedulesRehearsalsPanel, 0);

		} else if (SchedulesRehearsalsPanel != null && SchedulesRehearsalsPanel.isVisible()) {

			this.remove(SchedulesRehearsalsPanel);
			SchedulesRehearsalsPanel.setVisible(false);

		}

		if (General) {

			if (SchedulesGeneralPanel == null)
				SchedulesGeneralPanel = new SchedulesGeneral();
			SchedulesGeneralPanel.setVisible(true);
			this.add(SchedulesGeneralPanel);
			this.setComponentZOrder(SchedulesGeneralPanel, 0);

		} else if (SchedulesGeneralPanel != null && SchedulesGeneralPanel.isVisible()) {

			this.remove(SchedulesGeneralPanel);
			SchedulesGeneralPanel.setVisible(false);

		}

		ClassesTab.setBackground((Classes) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter());
		RehearsalsTab.setBackground((Rehearsals) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter());
		GeneralTab.setBackground((General) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter());

		this.revalidate();
		this.repaint();

	}

}
