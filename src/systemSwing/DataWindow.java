package systemSwing;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DataWindow extends Panel{
	
	private int centerPanelX;
	private int centerPanelY;
	private int TopDataY;
	private int BottomDataY;
	private int textFontSize;
	private String firstDataText;
	private String secondDataText;
	private String firstDataVal;
	private String secondDataVal;
	
	public DataWindow(String firstDataText, String firstDataVal, String secondDataText, String secondDataVal) {
		
		super();
		
		this.firstDataText = firstDataText;
		this.firstDataVal = firstDataVal;
		this.secondDataText = secondDataText;
		this.secondDataVal = secondDataVal;
		
		this.setSize(0, 0);
		
	}
	
	public void swapData(String firstDataText, String firstDataVal, String secondDataText, String secondDataVal) {
		
		this.firstDataText = firstDataText;
		this.firstDataVal = firstDataVal;
		this.secondDataText = secondDataText;
		this.secondDataVal = secondDataVal;
		
	}
	
	public void setSize(int X, int Y) {

		this.setBounds(this.getBounds().x, this.getBounds().y, X, Y);
		centerPanelX = (int) (X/2.0);
		centerPanelY = (int) (Y/2.0);
		TopDataY = (int) (Y / 3.0);
		BottomDataY = (int) (Y / 3.0 * 2.0);
		textFontSize = (int) (X/15.0);
		
	}

	public void setPosition(int X, int Y) {

		this.setBounds(X, Y, this.getSize().width, this.getSize().height);

	}
	
	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		super.paint(g);
		
		Font textFont = new Font(Font.SANS_SERIF, 0, textFontSize);
		
		g2.setFont(textFont);
		FontMetrics fM = g2.getFontMetrics();
		
		int OffsetY = fM.getHeight();
		
		int TextOffsetX = fM.charsWidth(firstDataText.toCharArray(), 0, firstDataText.length()) / 2;
		int ValOffsetX = fM.charsWidth(firstDataVal.toCharArray(), 0, firstDataVal.length()) / 2;
		
		int TextX = centerPanelX - TextOffsetX;
		int TextY = TopDataY - OffsetY;
		
		int ValX = centerPanelX - ValOffsetX;
		int ValY = TopDataY;
		
		g2.drawString(firstDataText, TextX, TextY);
		g2.drawString(firstDataVal, ValX, ValY);
		
		TextOffsetX = fM.charsWidth(secondDataText.toCharArray(), 0, secondDataText.length()) / 2;
		ValOffsetX = fM.charsWidth(secondDataVal.toCharArray(), 0, secondDataVal.length()) / 2;
		
		TextX = centerPanelX - TextOffsetX;
		TextY = BottomDataY - OffsetY;
		
		ValX = centerPanelX - ValOffsetX;
		ValY = BottomDataY;
		
		g2.drawString(secondDataText, TextX, TextY);
		g2.drawString(secondDataVal, ValX, ValY);
		
	}
	
}
