package SwingCards;

import static util.SwingConstants.*;

import systemSwing.Panel;

public class ShowRolesAndCasting extends Panel {
	
	
	private String showName;
	private ShowRolesAndCastingLeft leftPanel;
	private ShowRolesAndCastingRight rightPanel;


	public ShowRolesAndCasting(String name) {
		
		super(false, false, true, true);
		
		showName = name;
		
		leftPanel = new ShowRolesAndCastingLeft(this);
		rightPanel = new ShowRolesAndCastingRight(this);
		
		this.setBackground(MainGray.brighter());
		this.setLayout(null);
		this.setBounds(0, 200, 1200, 600);
		
		this.add(leftPanel);
		this.add(rightPanel);
		leftPanel.Update(name, "N/A");
		
	}
	
	public void Update(String showName, String roleName) {
		
		this.showName = showName;
		
		leftPanel.Update(showName, roleName);
		rightPanel.Update(showName, roleName);
		
	}
	
}
