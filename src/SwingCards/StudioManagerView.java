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

public class StudioManagerView extends JPanel {

	private TitleLabel title;
	private JScrollPane scrollPane;
	private Button MainMenu;
	private Button sortByUpcoming;
	private Button sortByPast;
	private Button addNewStudio;
	private Button allStudios;
	private Button UpcomingStudios;
	private Button PastStudios;
	private Panel ScrollPanel;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 1100;
	private int BUTTONX = 50;
	private int BUTTONHEIGHT = 100;
	private int BUTTONGAP = 50;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 30;

	public StudioManagerView() {

		this.setOpaque(false);
		this.setLayout(null);
		this.setBackground(MainGray);
		title = new TitleLabel("All Studios", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		MainMenu = new Button("Back", (e -> Main.Router.showView(ViewConstants.SYSTEM_SETTINGS_VIEW)), ChangeButtonD, LeftChangeButtonX,
				ChangeButtonY, TextColor, ChangeButtonArcRad, ChangeButtonFontSize);
		addNewStudio = new Button("+ ADD NEW STUDIO", (e -> DialogGenerator.createNewStudioFrame(this)),
				new Dimension(ADDBUTTONWIDTH, ADDBUTTONHEIGHT), ADDBUTTONX, ADDBUTTONY, SecondaryPurple.brighter(),
				ADDBUTTONARCRAD, ADDBUTTONFONTSIZE);

		this.add(title);
		this.add(MainMenu);
		this.add(addNewStudio);

		this.repaint();

	}

	public void Update() {

		if (scrollPane != null)
			this.remove(scrollPane);

		String[] allStudios = SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.STUDIOS);

		ScrollPanel = new Panel(false, false, false, false);
		int panelHeight = 600;

		for (int i = 0; i < allStudios.length; i++) {

			int finalI = i;
			final String StudioName = allStudios[finalI];

			Button Studio = new Button(StudioName, (null),
					new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					SecondaryPurple.brighter(), BUTTONARCRAD, BUTTONFONTSIZE);
			Studio.setEnabled(false);

			Studio.setHorizontalAlignment(JLabel.LEFT);

			Button Remove = new Button("Remove", (e -> {

				SystemSettingsModule.removeSystemDetail("Studios", StudioName);
				Update();

			}), new Dimension(100, 80), BUTTONX + 890, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP + 10, RedColor,
					BUTTONARCRAD - 5, BUTTONFONTSIZE - 10);

			if (panelHeight < Studio.getY() + BUTTONGAP + BUTTONHEIGHT) {

				panelHeight = Studio.getY() + BUTTONGAP + BUTTONHEIGHT;

			}

			ScrollPanel.add(Remove);

			ScrollPanel.add(Studio);

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
