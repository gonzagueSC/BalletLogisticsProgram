package systemSwing;

import java.awt.*;

import javax.swing.JCheckBox;

public class Checkbox extends JCheckBox {

	public Checkbox(String s, Dimension d, int X, int Y, Color c, int fontSize) {
		
		this.setLocation(X, Y);
		this.setSize(d);
		this.setText(s);
		this.setFont(new Font(Font.DIALOG, 0, fontSize));
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
		
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		
		this.setBackground(c);
		this.setForeground(c);
		this.setVisible(true);
		
	}
	
}
