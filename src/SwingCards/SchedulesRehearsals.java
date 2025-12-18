package SwingCards;

import static util.SwingConstants.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import control.DialogGenerator;
import systemSwing.Button;
import systemSwing.Panel;
import systemSwing.TitleLabel;

import databaseAccess.*;

public class SchedulesRehearsals extends Panel {

	private TitleLabel title;
	private JScrollPane scrollPane;
	private SchedulesMainView parent;
	private Button MainMenu;
	private Button sortByUpcoming;
	private Button sortByPast;
	private Button addNewRehearsal;
	private Button allRehearsals;
	private Button UpcomingRehearsals;
	private Button PastRehearsals;
	private Panel ScrollPanel;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 1100;
	private int BUTTONX = 50;
	private int BUTTONHEIGHT = 100;
	private int BUTTONGAP = 50;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 30;

	public SchedulesRehearsals(SchedulesMainView Panel) {

		super(false, false, true, true);

		parent = Panel;

		this.setBackground(MainGray.brighter());
		this.setLayout(null);
		this.setBounds(0, 200, 1200, 600);

	}

	public void Update() {

		if (scrollPane != null)
			this.remove(scrollPane);

		String[] allRehearsals = SchedulesModule.getAllUpcomingRehearsals();

		ScrollPanel = new Panel(false, false, false, false);
		int panelHeight = 500;

		for (int i = 0; i < allRehearsals.length; i++) {

			int finalI = i;
			final String RehearsalName = allRehearsals[finalI];

			Button Rehearsal = new Button(RehearsalName.split(", ")[1] + ": " + RehearsalName.split(", ")[2],
					(e -> DialogGenerator.newRehearsalDataDialog(RehearsalName)), new Dimension(BUTTONWIDTH, BUTTONHEIGHT),
					BUTTONX, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP, SecondaryPurple.brighter(), BUTTONARCRAD,
					BUTTONFONTSIZE - 10);

			Rehearsal.setHorizontalAlignment(JLabel.LEFT);

			Button Remove = new Button("Remove", (e -> {

				ProductionsModule.removeRehearsal(RehearsalName);
				Update();

			}), new Dimension(200, 80), BUTTONX + 890, i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP + 10, RedColor,
					BUTTONARCRAD - 5, BUTTONFONTSIZE - 10);

			if (panelHeight < Rehearsal.getY() + BUTTONGAP + BUTTONHEIGHT) {

				panelHeight = Rehearsal.getY() + BUTTONGAP + BUTTONHEIGHT;

			}

			ScrollPanel.add(Remove);

			ScrollPanel.add(Rehearsal);

		}

		ScrollPanel.setPreferredSize(new Dimension(1200, panelHeight));
		ScrollPanel.setBackground(MainPurple.brighter());

		scrollPane = new JScrollPane(ScrollPanel);
		scrollPane.setOpaque(false);
		scrollPane.setBounds(0, 0, 1200, 500);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(5);
		scrollPane.setBackground(SecondaryPurple);

		Button newRehearsal = new Button("+ Add Rehearsal", (e -> DialogGenerator.newRehearsalDialog(this)), BOTTOMADDDim, 1000, 515,
				TextColor, BOTTOMADDARCRAD, BOTTOMADDFONTSIZE);

		this.add(newRehearsal);

		this.add(scrollPane);
		this.setComponentZOrder(scrollPane, 0);

		this.revalidate();
		this.repaint();

	}

}
