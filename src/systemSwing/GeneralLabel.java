package systemSwing;

import java.awt.*;

import javax.swing.JLabel;

public class GeneralLabel extends JLabel {

	public GeneralLabel(String s, int X, int Y, int width, int height, int fontSize, Color c) {

		this.setLocation(X, Y);
		this.setSize(new Dimension(width, height));
		this.setText(s);
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
		this.setForeground(c);
		this.setBackground(null);
		this.setFont(new Font(Font.DIALOG, Font.BOLD, fontSize));
		this.setVisible(true);

	}

}
