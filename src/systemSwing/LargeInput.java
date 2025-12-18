package systemSwing;

import java.awt.*;

import javax.swing.JTextPane;
import javax.swing.text.*;

public class LargeInput extends JTextPane {
	
	private int arcradius;

	public LargeInput(int X, int Y, int width, int height, int fontSize, Color generalBackdrop, int arcrad) {
		
		this.setLocation(X, Y);
		this.setSize(new Dimension(width, height));
		this.setFont(new Font(Font.DIALOG, 0, fontSize));
		
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontSize(attributes, fontSize);
		this.setParagraphAttributes(attributes, true);
		
		this.arcradius = arcrad;
		
		this.setOpaque(false);
		this.setBorder(null);
		
		this.setBackground(generalBackdrop);
		this.setVisible(true);
		
	}
	
	@Override
    public void update(Graphics g) {
        paint(g);
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcradius, arcradius);
		super.paintComponent(g);
		g2.dispose();
		
	}
	
}
