package SwingCards;

import static util.SwingConstants.MainPurple;
import static util.SwingConstants.SecondaryPurple;
import static util.SwingConstants.TextColor;
import static util.SwingConstants.TransparentBackground;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

import databaseAccess.*;
import systemSwing.Button;
import systemSwing.Checkbox;
import systemSwing.FilterPanel;
import systemSwing.Panel;
import systemSwing.TitleLabel;

public class LevelSelectionDialog extends JDialog {
	
	private JScrollPane LevelScroller;
	private Panel ScrollPanel;
	private String[] Levels;
	private String StudentID;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 570;
	private int BUTTONX = 15;
	private int BUTTONHEIGHT = 70;
	private int BUTTONGAP = 30;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 20;

	public LevelSelectionDialog(NewClassDialog parent, String[] levels) {

		super(parent, "", true);
		
		Levels = levels;

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		ScrollPanel = new Panel(false, false, false, false);
		ScrollPanel.setBackground(MainPurple);

		FilterPanel LevelSelectionPanel = new FilterPanel();

		TitleLabel title = new TitleLabel("Select Levels", 50, 20, 700, 30, 30, TextColor);

		LevelScroller = new JScrollPane(ScrollPanel);

		String[] allLevels = SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.LEVELS);
		Checkbox[] LevelSelection = new Checkbox[allLevels.length];

		int panelHeight = 300;
		for (int i = 0; i < allLevels.length; i++) {

			int finalI = i;
			final String LevelName = allLevels[finalI].split(", ")[0];

			Checkbox Production = new Checkbox(LevelName, new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX,
					i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP, TextColor, 35);
			Production.setHorizontalAlignment(JCheckBox.LEFT);
			Production.setIconTextGap(40);
			
			for (int j = 0; j < Levels.length; j++) {
				
				if (Levels[j].equals(LevelName)) {
					
					Production.setSelected(true);
					
				}
				
			}
			
			if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {
				panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;
			}

			LevelSelection[i] = Production;

			ScrollPanel.add(Production);

		}
		ScrollPanel.setPreferredSize(new Dimension(572, panelHeight));

		Button Save = new Button("Save", (e -> {
			
			ArrayList<String> selectedLevels = new ArrayList<String>();
			
			for (int i = 0; i < LevelSelection.length; i++) {
				
				if (LevelSelection[i].isSelected()) {
					
					selectedLevels.add(LevelSelection[i].getText());
					
				}
				
			}
			
			parent.setLevels(selectedLevels.toArray(new String[0]));

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(300, 50), 250, 435, TextColor, 50, 24);

		LevelScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		LevelScroller.getVerticalScrollBar().setUnitIncrement(5);
		
		LevelScroller.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, SecondaryPurple.darker()));
		
		LevelSelectionPanel.setLayout(null);
		LevelSelectionPanel.add(title);

		LevelSelectionPanel.add(Save);
		LevelScroller.setBounds(110, 100, 580, 300);
		LevelSelectionPanel.add(LevelScroller);

		this.add(LevelSelectionPanel);
		this.setSize(800, 533);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
