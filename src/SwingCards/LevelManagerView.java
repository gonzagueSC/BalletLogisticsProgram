package SwingCards;

import static util.SwingConstants.*;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import control.DialogGenerator;
import control.Main;
import swingConstants.ViewConstants;
import systemSwing.*;

import databaseAccess.*;

public class LevelManagerView extends JPanel {

	private TitleLabel title;
	private JScrollPane scrollPane;
	private Button MainMenu;
	private Button sortByUpcoming;
	private Button sortByPast;
	private Button addNewLevel;
	private Button allLevels;
	private Button UpcomingLevels;
	private Button PastLevels;
	private Panel ScrollPanel;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 1100;
	private int BUTTONX = 50;
	private int BUTTONHEIGHT = 100;
	private int BUTTONGAP = 10;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 30;

	public LevelManagerView() {

		this.setOpaque(false);
		this.setLayout(null);
		this.setBackground(MainGray);
		title = new TitleLabel("All Levels", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		GeneralLabel hint = new GeneralLabel("Up means higher level", 40, 150, TITLELABELWIDTH/3, TITLELABELHEIGHT/2,
				TITLELABELFONTSIZE/3, TextColor);
		MainMenu = new Button("Back", (e -> Main.Router.showView(ViewConstants.SYSTEM_SETTINGS_VIEW)), ChangeButtonD, LeftChangeButtonX,
				ChangeButtonY, TextColor, ChangeButtonArcRad, ChangeButtonFontSize);
		addNewLevel = new Button("+ ADD NEW LEVEL", (e -> DialogGenerator.createNewLevelFrame(this)),
				new Dimension(ADDBUTTONWIDTH, ADDBUTTONHEIGHT), ADDBUTTONX, ADDBUTTONY, SecondaryPurple.brighter(),
				ADDBUTTONARCRAD, ADDBUTTONFONTSIZE);

		this.add(title);
		this.add(MainMenu);
		this.add(hint);
		this.add(addNewLevel);

		this.repaint();

	}

	public void Update() {
		
		Point currentView = new Point(0,0);
		
		if (scrollPane != null) currentView = scrollPane.getViewport().getViewPosition();

		if (scrollPane != null)
			this.remove(scrollPane);

		String[] allLevels = SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.LEVELS);

		ScrollPanel = new Panel(false, false, false, false);
		int panelHeight = 600;

		for (int i = 0; i < allLevels.length; i++) {

			int finalI = i;
			final String LevelName = allLevels[finalI].split(", ")[0];

			Button Level = new Button(LevelName, (null),
					new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					SecondaryPurple.brighter(), BUTTONARCRAD, BUTTONFONTSIZE);
			Level.setEnabled(false);

			Level.setHorizontalAlignment(JLabel.LEFT);

			Button Remove = new Button("Remove", (e -> {

				SystemSettingsModule.removeSystemDetail("Levels", allLevels[finalI]);
				Update();

			}), new Dimension(200, 80), BUTTONX + 890, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP + 10, RedColor,
					BUTTONARCRAD - 5, BUTTONFONTSIZE - 10);
			Button Up = new Button("↑", (e -> {

				SystemSettingsModule.moveSystemDetailUp("Levels", allLevels[finalI]);
				Update();

			}), new Dimension(100, 40), BUTTONX + 770, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP + 10, GreenColor,
					25, BUTTONFONTSIZE - 10);

			if (panelHeight < Level.getY() + BUTTONGAP + BUTTONHEIGHT) {

				panelHeight = Level.getY() + BUTTONGAP * 2 + BUTTONHEIGHT;

			}
			
			Button Down = new Button("↓", (e -> {

				SystemSettingsModule.moveSystemDetailDown("Levels", allLevels[finalI]);
				Update();

			}), new Dimension(100, 40), BUTTONX + 770, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP + 50, RedColor,
					25, BUTTONFONTSIZE - 10);

			ScrollPanel.add(Remove);
			ScrollPanel.add(Up);
			ScrollPanel.add(Down);

			ScrollPanel.add(Level);

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
		scrollPane.getViewport().setViewPosition(currentView);

		this.add(scrollPane);
		this.setComponentZOrder(scrollPane, 0);

		this.revalidate();
		this.repaint();

	}

}
