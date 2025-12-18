package systemSwing;

import javax.swing.JFrame;
import static util.SwingConstants.*;

public class FilterFrame extends JFrame {
	
	FilterPanel p;
	public FilterFrame() {
		this.getContentPane().setBackground(MainPurple);
		this.setSize(800, 533);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.p = new FilterPanel();
		
		this.add(p);
	}
	
	public FilterPanel getPanel() {
		
		return p;
	
	}
	
}
