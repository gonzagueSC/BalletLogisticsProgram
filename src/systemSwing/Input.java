package systemSwing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JTextField;

public class Input extends JTextField {

	private int arcradius;
	private String placeholder = "";
	private final Color placeholderColor = Color.GRAY;

	public Input(int X, int Y, int width, int height, int fontSize, Color generalBackdrop, int arcrad) {

		this.setLocation(X, Y);
		this.setSize(new Dimension(width, height));
		this.setFont(new Font(Font.DIALOG, 0, fontSize));
		this.setHorizontalAlignment(CENTER);
		this.setText(placeholder);
		this.setForeground(placeholderColor);
		this.arcradius = arcrad;

		this.setOpaque(false);
		this.setBorder(null);

		this.setBackground(generalBackdrop);
		this.setVisible(true);
		this.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {

				// When the user clicks into the box
				if (getText().equals(placeholder)) {

					setText(""); // Clear the placeholder text
					setForeground(Color.black); // Switch to default text color

				}

			}

			@Override
			public void focusLost(FocusEvent e) {

				// When the user clicks out of the box
				if (getText().isEmpty()) {

					setText(placeholder); // Restore the placeholder text
					setForeground(placeholderColor); // Switch back to gray color

				}

			}

		});

	}

	public void setPlaceholderText(String newPlaceholder) {

		if (this.getText().equals(this.placeholder)) {

			this.placeholder = newPlaceholder;
			this.setText(newPlaceholder);
			this.setForeground(this.placeholderColor);

		} else {

			this.placeholder = newPlaceholder;

		}
		this.repaint();

	}
	
	@Override
	public void setText(String text) {
		
		super.setText(text);
		this.setForeground(Color.black);
		
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

}
