package systemSwing;

import javax.swing.JFrame;

import swingConstants.CoreVariables;

public class Frame extends JFrame {
	
	private Panel p;
	
	public Frame() {
		
		this.setSize(CoreVariables.SCREENWIDTH, CoreVariables.SCREENHEIGHT);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.p = new Panel(false, false, false, false);
		this.add(p);
		
	}
	
	public Panel getPanel() {
		return p;
	}
	
}
