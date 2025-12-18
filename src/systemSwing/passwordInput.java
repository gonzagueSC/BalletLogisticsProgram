package systemSwing;

import java.awt.*;

import javax.swing.JPasswordField;

public class passwordInput extends JPasswordField {

	private int arcradius;

	public passwordInput(int X, int Y, int width, int height, int fontSize, Color generalBackdrop, int arcrad) {

		this.setLocation(X, Y);
		this.setSize(new Dimension(width, height));
		this.setFont(new Font(Font.DIALOG, 0, fontSize));
		this.setHorizontalAlignment(CENTER);
		this.arcradius = arcrad;

		this.setOpaque(false);
		this.setBorder(null);

		this.setBackground(generalBackdrop);
		this.setVisible(true);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcradius, arcradius);

		super.paintComponent(g);

	}

	@Override
	public void update(Graphics g) {

		paint(g);

	}

	public String getPasswordAsString() {
		
		char[] passwordChars = this.getPassword();
		String password = new String(passwordChars);
		
		java.util.Arrays.fill(passwordChars, '0');
		
		return password;
		
	}
	
	@Override
	public String getText() {
		
		return getPasswordAsString();
		
	}
	
}
