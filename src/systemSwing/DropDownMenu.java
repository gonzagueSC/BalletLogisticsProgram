package systemSwing;

import java.awt.*;

import javax.swing.*;

import static util.SwingConstants.*;

public class DropDownMenu extends JComboBox<String> {

	int desiredWidth = 0;
	int desiredHeight = 0;

	public DropDownMenu(String[] data) {

		super(data);
		this.setRenderer(
				new DropDownCellRenderer(TextColor, this.getForeground(), TextColor.darker(), SecondaryPurple));
		
		this.setMaximumRowCount(30);

	}

	public void SetPos(int X, int Y) {

		this.setLocation(X, Y);

	}

	public void SetDim(int width, int height) {

		this.setSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.desiredWidth = width;
		this.desiredHeight = height;
		this.revalidate();
		this.repaint();

	}

	public void SetColors(Color background, Color foreground) {

		this.setBackground(background);
		this.setForeground(foreground);

	}

	@Override
	public Dimension getPreferredSize() {

		Dimension prefSize = super.getPreferredSize();

		if (desiredHeight > 0) {

			return new Dimension(prefSize.width, desiredHeight);

		}

		return prefSize;

	}

	@Override
	public Dimension getMinimumSize() {

		if (desiredHeight > 0) {

			return new Dimension(super.getMinimumSize().width, desiredHeight);

		}

		return super.getMinimumSize();

	}

	@Override
	public Dimension getMaximumSize() {

		if (desiredHeight > 0) {

			return new Dimension(super.getMaximumSize().width, desiredHeight);

		}

		return super.getMaximumSize();

	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(getBackground());

		g2.fillRoundRect(0, 0, getWidth(), getHeight(), getWidth() / 10, getWidth() / 10);

		g2.setColor(getBackground().darker());

		g2.fillRoundRect(getWidth() - getHeight() + getHeight() / 7, getHeight() / 7, getHeight() - getHeight() * 2 / 7,
				getHeight() - getHeight() * 2 / 7, getHeight() * 2 / 7, getHeight() * 2 / 7);

		g2.setColor(getBackground());

		float thickness = 3.0f; // 5 pixels thick
		g2.setStroke(new BasicStroke(thickness));

		int[] xPoints = { (int) (getWidth() - getHeight() + getHeight() / 7.0 + (getHeight() - getHeight() * 2.0 / 7.0) / 4.0),
				(int) (getWidth() - getHeight() + getHeight() / 7.0 + (getHeight() - getHeight() * 2.0 / 7.0) / 2.0),
				(int) (getWidth() - getHeight() + getHeight() / 7.0 + (getHeight() - getHeight() * 2.0 / 7.0) * 3.0 / 4) };
		int[] yPoints = { (int) (getHeight() / 7.0 + (getHeight() - getHeight() * 2.0 / 7.0) / 4),
				(int) (getHeight() / 7.0 + (getHeight() - getHeight() * 2.0 / 7.0) * 3.0 / 4.0),
				(int) (getHeight() / 7.0 + (getHeight() - getHeight() * 2.0 / 7.0) / 4.0) };

		g2.drawPolygon(xPoints, yPoints, 3);

		g2.setColor(this.getForeground());

		FontMetrics fm = g.getFontMetrics(this.getFont());

		// The width of the text in pixels
		int textWidth = fm.stringWidth(this.getSelectedItem().toString());
		// The ascent is the height above the baseline (needed for vertical centering)
		int textAscent = fm.getAscent();

		// Calculate centered x and y coordinates
		int x = (int) ((getWidth() - textWidth) / 2.0);
		// (height / 2) is the center of the component
		// (textAscent / 2) adjusts the position so the text's center aligns with the
		// component's center
		int y = (int) ((getHeight() / 2.0) + (textAscent / 2.0));

		// Draw the text
		g.drawString(this.getSelectedItem().toString(), x, y);

		// super.paintComponent(g);
		g2.dispose();

	}

	@Override
	public void paint(Graphics g) {

		paintComponent(g);

	}

}

class DropDownCellRenderer extends JLabel implements ListCellRenderer<String> {

	private Color background;
	private Color foreground;
	private Color selectionBackground;
	private Color selectionForeground;

	public DropDownCellRenderer(Color background, Color foreground, Color selectionBackground,
			Color selectionForeground) {

		this.background = background;
		this.foreground = foreground;
		this.selectionBackground = selectionBackground;
		this.selectionForeground = selectionForeground;
		setOpaque(true);

	}

	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
			boolean isSelected, boolean cellHasFocus) {

		setFont(list.getFont());

		setText(value);

		if (isSelected) {

			setBackground(background);
			setForeground(selectionForeground);

		} else {

			setForeground(background);
			setBackground(selectionForeground);

		}

		return this;

	}

}