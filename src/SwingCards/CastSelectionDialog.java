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

public class CastSelectionDialog extends JDialog {

	private JScrollPane CastScroller;
	private Panel ScrollPanel;
	private String[] casts;
	private String StudentID;

	// LOCAL VARIABLES

	private int BUTTONWIDTH = 570;
	private int BUTTONX = 15;
	private int BUTTONHEIGHT = 70;
	private int BUTTONGAP = 30;
	private int BUTTONARCRAD = 70;
	private int BUTTONFONTSIZE = 20;

	public CastSelectionDialog(AssignStudentDialog parent, String productionName, String role, String[] casts) {

		super(parent, "", true);

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		ScrollPanel = new Panel(false, false, false, false);
		ScrollPanel.setBackground(MainPurple);

		FilterPanel CastSelectionPanel = new FilterPanel();

		TitleLabel title = new TitleLabel("Select Casts", 50, 20, 700, 30, 30, TextColor);

		CastScroller = new JScrollPane(ScrollPanel);

		String[] allRoles = ProductionsModule.getAllCastsByProduction(productionName);
		Checkbox[] CastSelection = new Checkbox[allRoles.length];

		int panelHeight = 300;
		for (int i = 0; i < allRoles.length; i++) {

			int finalI = i;
			final String CastName = allRoles[finalI];

			Checkbox Production = new Checkbox(CastName, new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX,
					i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP, TextColor, 35);
			Production.setHorizontalAlignment(JCheckBox.LEFT);
			Production.setIconTextGap(40);
			
			for (int j = 0; j < casts.length; j++) {
				
				if (casts[j].equals(CastName)) {
					
					Production.setSelected(true);
					
				}
				
			}
			
			if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {
				panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;
			}

			CastSelection[i] = Production;

			ScrollPanel.add(Production);

		}
		ScrollPanel.setPreferredSize(new Dimension(572, panelHeight));

		Button Save = new Button("Save", (e -> {
			
			ArrayList<String> selectedCasts = new ArrayList<String>();
			
			for (int i = 0; i < CastSelection.length; i++) {
				
				if (CastSelection[i].isSelected()) {
					
					selectedCasts.add(CastSelection[i].getText());
					
				}
				
			}
			
			parent.setCasts(selectedCasts.toArray(new String[0]));

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(300, 50), 250, 435, TextColor, 50, 24);

		CastScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		CastScroller.getVerticalScrollBar().setUnitIncrement(5);
		
		CastScroller.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, SecondaryPurple.darker()));
		
		CastSelectionPanel.setLayout(null);
		CastSelectionPanel.add(title);

		CastSelectionPanel.add(Save);
		CastScroller.setBounds(110, 100, 580, 300);
		CastSelectionPanel.add(CastScroller);

		this.add(CastSelectionPanel);
		this.setSize(800, 533);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}
	
	public CastSelectionDialog(AddRemoveCastedDialog parent, String productionName, String role, String[] casts) {

		super(parent, "", true);

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		ScrollPanel = new Panel(false, false, false, false);
		ScrollPanel.setBackground(MainPurple);

		FilterPanel CastSelectionPanel = new FilterPanel();

		TitleLabel title = new TitleLabel("Select Casts", 50, 20, 700, 30, 30, TextColor);

		CastScroller = new JScrollPane(ScrollPanel);

		String[] allRoles = ProductionsModule.getAllCastsByProduction(productionName);
		Checkbox[] CastSelection = new Checkbox[allRoles.length];

		int panelHeight = 300;
		for (int i = 0; i < allRoles.length; i++) {

			int finalI = i;
			final String CastName = allRoles[finalI];

			Checkbox Production = new Checkbox(CastName, new Dimension(BUTTONWIDTH, BUTTONHEIGHT), BUTTONX,
					i * (BUTTONHEIGHT + BUTTONGAP) + BUTTONGAP, TextColor, 35);
			Production.setHorizontalAlignment(JCheckBox.LEFT);
			Production.setIconTextGap(40);
			
			for (int j = 0; j < casts.length; j++) {
				
				if (casts[j].equals(CastName)) {
					
					Production.setSelected(true);
					
				}
				
			}
			
			if (panelHeight < Production.getY() + BUTTONGAP + BUTTONHEIGHT) {
				panelHeight = Production.getY() + BUTTONGAP + BUTTONHEIGHT;
			}

			CastSelection[i] = Production;

			ScrollPanel.add(Production);

		}
		ScrollPanel.setPreferredSize(new Dimension(572, panelHeight));

		Button Save = new Button("Save", (e -> {
			
			ArrayList<String> selectedCasts = new ArrayList<String>();
			
			for (int i = 0; i < CastSelection.length; i++) {
				
				if (CastSelection[i].isSelected()) {
					
					selectedCasts.add(CastSelection[i].getText());
					
				}
				
			}
			
			parent.setCasts(selectedCasts.toArray(new String[0]));

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(300, 50), 250, 435, TextColor, 50, 24);

		CastScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		CastScroller.getVerticalScrollBar().setUnitIncrement(5);
		
		CastScroller.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, SecondaryPurple.darker()));
		
		CastSelectionPanel.setLayout(null);
		CastSelectionPanel.add(title);

		CastSelectionPanel.add(Save);
		CastScroller.setBounds(110, 100, 580, 300);
		CastSelectionPanel.add(CastScroller);

		this.add(CastSelectionPanel);
		this.setSize(800, 533);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
