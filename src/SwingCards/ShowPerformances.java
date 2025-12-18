package SwingCards;

import static util.SwingConstants.*;

import systemSwing.Panel;

public class ShowPerformances extends Panel {
	
	
	private String showName;
	private ShowPerformancesLeft leftPanel;
	private ShowPerformancesRight rightPanel;


	public ShowPerformances(String name) {
		
		super(false, false, true, true);
		
		showName = name;
		
		leftPanel = new ShowPerformancesLeft(this);
		rightPanel = new ShowPerformancesRight(this);
		
		this.setBackground(MainGray.brighter());
		this.setLayout(null);
		this.setBounds(0, 200, 1200, 600);
		
		this.add(leftPanel);
		this.add(rightPanel);
		leftPanel.Update(name);
		
	}
	
	public void Update(String PerformanceName) {
		
		leftPanel.Update(showName);
		rightPanel.Update(showName, PerformanceName);
		
	}
	
}
