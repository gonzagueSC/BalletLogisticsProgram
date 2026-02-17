package systemSwing;

import static util.SwingConstants.MainGray;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import util.DataPoint;

public class BasicGraph extends Panel {

	DataPoint[] dataPoints;
	int highestVal = 0;
	int UpperBound;

	public BasicGraph(DataPoint[] dots) {

		super(true, true, true, true);

		dataPoints = dots;

		for (DataPoint p : dots) {

			if (p.getValue() > highestVal)
				highestVal = p.getValue();

		}

		UpperBound = (int) (highestVal * 1.05);

		this.setBackground(MainGray);
		this.setLayout(null);

	}

	public void setSize(int X, int Y) {

		this.setBounds(this.getBounds().x, this.getBounds().y, X, Y);

	}

	public void setPosition(int X, int Y) {

		this.setBounds(X, Y, this.getSize().width, this.getSize().height);

	}

	public void swapData(DataPoint[] dots) {

		dataPoints = dots;

		highestVal = 0;

		for (DataPoint p : dots) {

			if (p.getValue() > highestVal)
				highestVal = p.getValue();

		}

		UpperBound = (int) (highestVal * 1.05);

	}

	@Override

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		super.paint(g);

		int leftMarginX = this.getSize().width / 20;
		int rightMarginX = this.getSize().width - this.getSize().width / 20;

		int topMarginY = this.getSize().height / 20;
		int bottomMarginY = this.getSize().height - (int) (this.getSize().height / 20.0 * 3);

		// Write the bounds and descriptors

		int FontSize = this.getSize().width / 20;

		Font textFont = new Font(Font.SANS_SERIF, 0, FontSize);

		g2.setFont(textFont);
		int LowerOffsetX = g2.getFontMetrics().charsWidth("0".toCharArray(), 0, 1) / 2;
		int UpperOffsetX = g2.getFontMetrics().charsWidth(Integer.toString(UpperBound).toCharArray(), 0,
				Integer.toString(UpperBound).length()) / 2;

		int valuePointX = leftMarginX - leftMarginX / 4;

		g2.drawString("0", valuePointX - LowerOffsetX, bottomMarginY);
		g2.drawString(Integer.toString(UpperBound), valuePointX - UpperOffsetX, topMarginY);

		int startX = leftMarginX + (rightMarginX - leftMarginX) / 20;
		int endX = rightMarginX - (rightMarginX - leftMarginX) / 20;
		int intervalX = (rightMarginX - leftMarginX) / dataPoints.length;

		int pointRadius = 3;

		for (int i = 0; i < dataPoints.length; i++) {

			int dataOffsetX = g2.getFontMetrics().charsWidth(dataPoints[i].getDescriptor().toCharArray(), 0,
					dataPoints[i].getDescriptor().length()) / 2;
			int dataOffsetY = (int) (g2.getFontMetrics().getHeight() * 1.05);

			int descX = startX + intervalX * i - dataOffsetX;
			int descY = bottomMarginY + dataOffsetY;

			g2.drawString(dataPoints[i].getDescriptor(), descX, descY);

			double dataValProp = (double) (dataPoints[i].getValue()) / UpperBound;
			int dataValHeight = (int) (dataValProp * (bottomMarginY - topMarginY));

			int dataValX = (startX + intervalX * i) - pointRadius;
			int dataValY = bottomMarginY - dataValHeight - pointRadius;

			g.drawOval(dataValX, dataValY, pointRadius * 2, pointRadius * 2);

		}
		
		//Draw the line delineating progress

		int graphLineThickness = pointRadius * 2;
		BasicStroke graphLineStroke = new BasicStroke(graphLineThickness);
		g2.setStroke(graphLineStroke);
		
		for (int i = 0; i < dataPoints.length - 1; i++) {
			
			double dataVal1Prop = (double) (dataPoints[i].getValue()) / UpperBound;
			int dataVal1Height = (int) (dataVal1Prop * (bottomMarginY - topMarginY));

			int dataVal1X = (startX + intervalX * i) - pointRadius;
			int dataVal1Y = bottomMarginY - dataVal1Height - pointRadius;
			
			double dataVal2Prop = (double) (dataPoints[i].getValue()) / UpperBound;
			int dataVal2Height = (int) (dataVal2Prop * (bottomMarginY - topMarginY));

			int dataVal2X = (startX + intervalX * i) - pointRadius;
			int dataVal2Y = bottomMarginY - dataVal2Height - pointRadius;
			
			g2.drawLine(dataVal1X, dataVal1Y, dataVal2X, dataVal2Y);

			
		}

		int graphBoundsThickness = 5;
		BasicStroke graphBoundStroke = new BasicStroke(graphBoundsThickness);
		g2.setStroke(graphBoundStroke);

		// Drawing the graph bounds

		g2.drawLine(leftMarginX, bottomMarginY, leftMarginX, topMarginY);
		g2.drawLine(leftMarginX, bottomMarginY, rightMarginX, bottomMarginY);

	}

}
