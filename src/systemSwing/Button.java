package systemSwing;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton {

	private int arcRad; 
	public Button(String s, ActionListener a, Dimension d, int X, int Y, Color c, int arcRadius, int fontSize) {
		
		this.setLocation(X, Y);
		this.setSize(d);
		this.addActionListener(a);
		this.setText(s);
		this.setFont(new Font(Font.DIALOG, 0, fontSize));
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
		arcRad = arcRadius;
		
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		
		this.setBackground(c);
		this.setVisible(true);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (getModel().isPressed()) {
			
			g2.setColor(getBackground().darker());
			
		} else if (getModel().isRollover()) {
			
			g2.setColor(getBackground().brighter());
			
		} else {
			
			g2.setColor(getBackground());
			
		}
		
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcRad, arcRad);
		super.paintComponent(g);
		g2.dispose();
		
	}
	
}
