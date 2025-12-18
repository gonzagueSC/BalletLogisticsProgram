package SwingCards;

import static util.SwingConstants.*;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import control.DialogGenerator;
import control.Main;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.Panel;
import systemSwing.TitleLabel;

import databaseAccess.*;

public class ShowDirectoryView extends JPanel {

	private TitleLabel title;
	private JScrollPane scrollPane;
	private Button MainMenu;
	private Button sortByUpcoming;
	private Button sortByPast;
	private Button addNewProduction;
	private Button AllProductions;
	private Button UpcomingProductions;
	private Button PastProductions;
	private boolean OnlyShowUpcoming = false;
	private boolean OnlyShowPast = false;
	private Panel ScrollPanel;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 1100;
	private int BUTTONX = 50;
	private int BUTTONHEIGHT = 100;
	private int BUTTONGAP = 50;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 30;

	public ShowDirectoryView() {

		this.setOpaque(false);
		this.setLayout(null);
		this.setBackground(MainGray);
		title = new TitleLabel("All Productions", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		MainMenu = new Button("Back", (e -> Main.Router.showView(ViewConstants.ADMIN_VIEW)), ChangeButtonD,
				LeftChangeButtonX, ChangeButtonY, TextColor, ChangeButtonArcRad, ChangeButtonFontSize);
		addNewProduction = new Button("+ ADD NEW PRODUCTION", (e -> DialogGenerator.createNewProductionFrame()),
				new Dimension(ADDBUTTONWIDTH, ADDBUTTONHEIGHT), ADDBUTTONX, ADDBUTTONY, SecondaryPurple.brighter(),
				ADDBUTTONARCRAD, ADDBUTTONFONTSIZE);
		AllProductions = new Button("All",
				(e -> Main.Router.showDynamicView(ViewConstants.SHOW_DIRECTORY_VIEW, "false", "false")),
				new Dimension(TABWIDTH, TABHEIGHT), TABSTARTX, TABY,
				(!OnlyShowUpcoming && !OnlyShowPast) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(),
				TABARCRAD, TABFONTSIZE);
		UpcomingProductions = new Button("Upcoming",
				(e -> Main.Router.showDynamicView(ViewConstants.SHOW_DIRECTORY_VIEW, "true", "false")),
				new Dimension(TABWIDTH, TABHEIGHT), TABSTARTX + (TABWIDTH + TABGAP) * 1, TABY,
				(OnlyShowUpcoming && !OnlyShowPast) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(),
				TABARCRAD, TABFONTSIZE);
		PastProductions = new Button("Past",
				(e -> Main.Router.showDynamicView(ViewConstants.SHOW_DIRECTORY_VIEW, "false", "true")),
				new Dimension(TABWIDTH, TABHEIGHT), TABSTARTX + (TABWIDTH + TABGAP) * 2, TABY,
				(!OnlyShowUpcoming && OnlyShowPast) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter(),
				TABARCRAD, TABFONTSIZE);

		this.add(title);
		this.add(MainMenu);
		this.add(UpcomingProductions);
		this.add(PastProductions);
		this.add(AllProductions);
		this.add(addNewProduction);

		this.repaint();

	}

	public void Update(boolean Upcoming, boolean Past) {

		OnlyShowUpcoming = Upcoming;
		OnlyShowPast = Past;
		Update();

	}

	public void Update() {

		AllProductions.setBackground((!OnlyShowUpcoming && !OnlyShowPast) ? SecondaryPurple.brighter().darker()
				: SecondaryPurple.brighter());
		UpcomingProductions.setBackground(
				(OnlyShowUpcoming && !OnlyShowPast) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter());
		PastProductions.setBackground(
				(!OnlyShowUpcoming && OnlyShowPast) ? SecondaryPurple.brighter().darker() : SecondaryPurple.brighter());

		if (scrollPane != null)
			this.remove(scrollPane);

		String[] allProductions = ProductionsModule.getAllProductions(OnlyShowUpcoming, OnlyShowPast);

		ScrollPanel = new Panel(false, false, false, false);
		int panelHeight = 600;

		for (int i = 0; i < allProductions.length; i++) {

			int finalI = i;
			final String ProductionName = allProductions[finalI].substring(0, allProductions[finalI].length() - 11);

			Button Production = new Button(ProductionName, (e -> Main.Router.showDynamicView(ViewConstants.SHOW_VIEW, ProductionName)),
					new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					SecondaryPurple.brighter(), BUTTONARCRAD, BUTTONFONTSIZE);

			if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {

				panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;

			}

			ScrollPanel.add(Production);

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
