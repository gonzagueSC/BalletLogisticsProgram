package systemSwing;

import java.awt.Insets;

import javax.swing.JFrame;

import swingConstants.CoreVariables;

public class Frame extends JFrame {
	
	private Panel p;
	
	public Frame() {
		
		Insets insets = this.getInsets();
		
		this.setSize(CoreVariables.SCREENWIDTH + insets.left + insets.right, CoreVariables.SCREENHEIGHT + insets.top + insets.bottom);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.p = new Panel(false, false, false, false);
		this.add(p);
		
	}
	
	public Panel getPanel() {
		return p;
	}
	
}
