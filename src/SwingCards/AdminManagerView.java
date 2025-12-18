package SwingCards;

import static util.SwingConstants.*;

import java.awt.Dimension;

import javax.swing.*;

import control.Main;
import swingConstants.ViewConstants;
import systemSwing.*;

import databaseAccess.*;

public class AdminManagerView extends JPanel {

	private TitleLabel title;
	private JScrollPane scrollPane;
	private Button MainMenu;
	private Button sortByUpcoming;
	private Button sortByPast;
	private Button addNewAdmin;
	private Button allAdmins;
	private Button UpcomingAdmins;
	private Button PastAdmins;
	private Panel ScrollPanel;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 1100;
	private int BUTTONX = 50;
	private int BUTTONHEIGHT = 100;
	private int BUTTONGAP = 50;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 30;

	public AdminManagerView() {

		this.setOpaque(false);
		this.setLayout(null);
		this.setBackground(MainGray);
		title = new TitleLabel("All Admins", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		MainMenu = new Button("Back", (e -> Main.Router.showView(ViewConstants.SYSTEM_SETTINGS_VIEW)), ChangeButtonD, LeftChangeButtonX,
				ChangeButtonY, TextColor, ChangeButtonArcRad, ChangeButtonFontSize);
		addNewAdmin = new Button("+ ADD NEW ADMIN", (e -> Main.Router.showView(ViewConstants.ADMIN_CREATION_VIEW)),
				new Dimension(ADDBUTTONWIDTH, ADDBUTTONHEIGHT), ADDBUTTONX, ADDBUTTONY, SecondaryPurple.brighter(),
				ADDBUTTONARCRAD, ADDBUTTONFONTSIZE);

		this.add(title);
		this.add(MainMenu);
		this.add(addNewAdmin);

		this.repaint();

	}

	public void Update() {

		if (scrollPane != null)
			this.remove(scrollPane);

		String[] allAdmins = SystemSettingsModule.getAllOfSystemDetail("Admins");

		ScrollPanel = new Panel(false, false, false, false);
		int panelHeight = 600;

		for (int i = 0; i < allAdmins.length; i++) {

			int finalI = i;
			final String AdminName = allAdmins[finalI];

			Button Admin = new Button(AdminName.split(" ")[0], (null),
					new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP,
					SecondaryPurple.brighter(), BUTTONARCRAD, BUTTONFONTSIZE);
			Admin.setEnabled(false);

			Admin.setHorizontalAlignment(JLabel.LEFT);

			Button Remove = new Button("Remove", (e -> {

				SystemSettingsModule.removeSystemDetail(SystemSettingsModule.ADMINS, AdminName);
				Update();

			}), new Dimension(200, 80), BUTTONX + 890, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP + 10, RedColor,
					BUTTONARCRAD - 5, BUTTONFONTSIZE - 10);

			if (panelHeight < Admin.getY() + BUTTONGAP + BUTTONHEIGHT) {

				panelHeight = Admin.getY() + BUTTONGAP + BUTTONHEIGHT;

			}

			ScrollPanel.add(Remove);

			ScrollPanel.add(Admin);

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
