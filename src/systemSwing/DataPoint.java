package systemSwing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.JPopupMenu;

public class DataPoint extends JButton {

	private String desc;
	private int value;

	public DataPoint(String descriptor, int pointVal, String regex) {

		desc = descriptor;
		value = pointVal;

		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);

		Dimension size = new Dimension(15, 15);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		JPopupMenu popup = new JPopupMenu();
		popup.add(new JLabel(String.format(regex, pointVal)));
		popup.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		DataPoint Point = this;
		
		this.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        // Show the popup slightly above the button
		        popup.show(Point, 0, -popup.getPreferredSize().height);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        popup.setVisible(false);
		    }
		});

	}

	public int getValue() {

		return value;

	}

	public String getDescriptor() {

		return desc;

	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Change color based on button state
		if (getModel().isArmed()) {

			g2.setColor(Color.DARK_GRAY);

		} else if (getModel().isRollover()) {

			g2.setColor(Color.LIGHT_GRAY);

		} else {

			g2.setColor(Color.BLACK);

		}

		// Draw the dot (x, y, width, height)
		g2.fillOval(0, 0, getWidth(), getHeight());

		g2.dispose();
		super.paintComponent(g);

	}
	
	

}
