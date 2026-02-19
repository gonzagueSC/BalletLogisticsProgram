package systemSwing;

import javax.swing.JPanel;

import swingConstants.CoreVariables;

import static util.SwingConstants.*;

import java.awt.*;
import java.awt.geom.Path2D;

public class Panel extends JPanel {

	private int cornerRadius = 70;
	private boolean TopRight = true;
	private boolean TopLeft = true;
	private boolean BottomRight = true;
	private boolean BottomLeft = true;

	public Panel() {

		this.setBackground(MainGray);
		this.setLayout(null);
		this.setOpaque(false);

	}

	public Panel(boolean roundTopRight, boolean roundTopLeft, boolean roundBottomRight, boolean roundBottomLeft) {

		TopRight = roundTopRight;
		TopLeft = roundTopLeft;
		BottomRight = roundBottomRight;
		BottomLeft = roundBottomLeft;
		this.setBackground(MainGray);
		this.setLayout(null);
		this.setOpaque(false);

	}
	
	public Panel(LayoutManager borderLayout) {
		
		this.setBackground(MainGray);
		this.setLayout(null);
		this.setOpaque(false);
		this.setLayout(borderLayout);
		
	}

	@Override
    public void update(Graphics g) {
        paint(g);
    }

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g.create();
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(this.getBackground());

		int w = getWidth();
		int h = getHeight();
		int r = cornerRadius;

		Path2D.Double path = new Path2D.Double();

		if (TopLeft) {
			path.moveTo(0, r);
			path.quadTo(0, 0, r, 0);
		} else {
			path.moveTo(0, 0);
		}

		if (TopRight) {
			path.lineTo(w - r, 0);
			path.quadTo(w, 0, w, r);
		} else {
			path.lineTo(w, 0);
		}

		if (BottomRight) {
			path.lineTo(w, h - r);
			path.quadTo(w, h, w - r, h);
		} else {
			path.lineTo(w, h);
		}

		if (BottomLeft) {
			path.lineTo(r, h);
			path.quadTo(0, h, 0, h - r);
		} else {
			path.lineTo(0, h);
		}

		path.closePath();

		g2.fill(path);

		g2.dispose();
		
		super.paintComponent(g);
	}

}
