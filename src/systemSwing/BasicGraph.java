package systemSwing;

import static util.SwingConstants.MainGray;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import util.DataPoint;

public class BasicGraph extends Panel {

	DataPoint[] dataPoints = new DataPoint[0];
	double highestVal = 0;
	int UpperBound;
	int minHeight = 10;
	String label = "N/A";
	int DateRangeFontSize = this.getWidth() / 40;

	int leftMarginX;
	int rightMarginX;

	int topMarginY;
	int labelMarginY;
	int bottomMarginY;

	int LowerOffsetX;
	int UpperOffsetX;

	int valuePointX;

	int DateRangeX;
	int DateRangeY;

	int startX;
	int endX;
	int intervalX;

	int pointRadius;

	public BasicGraph(DataPoint[] dots) {

		super(true, true, true, true);

		this.setLayout(null);

		this.swapData(dots);

	}

	public void setSize(int X, int Y) {

		this.setBounds(this.getBounds().x, this.getBounds().y, X, Y);

		leftMarginX = this.getSize().width / 20;
		rightMarginX = this.getSize().width - this.getSize().width / 20;

		topMarginY = (int) (this.getSize().height / 5.0);
		labelMarginY = (int) (topMarginY / 3.0 * 2);
		bottomMarginY = this.getSize().height - (int) (this.getSize().height / 10.0);

		startX = leftMarginX + (rightMarginX - leftMarginX) / 20;
		endX = rightMarginX - (rightMarginX - leftMarginX) / 20;
		intervalX = (int) ((endX - startX) / ((double) (dataPoints.length - 1)));

		pointRadius = 2;

		this.swapData(dataPoints);

	}

	public void setPosition(int X, int Y) {

		this.setBounds(X, Y, this.getSize().width, this.getSize().height);

	}

	public void setMinHeight(int min) {

		this.minHeight = min;

		this.swapData(dataPoints);

	}

	public int getLeftMarginX() {

		return this.leftMarginX;

	}

	public int getRightMarginX() {

		return this.rightMarginX;

	}
	
	public int getTopMarginY() {

		return this.topMarginY;

	}

	public int getBottomMarginY() {

		return this.bottomMarginY;

	}
	
	public int getLabelMarginY() {

		return this.labelMarginY;

	}

	public void swapData(DataPoint[] dots) {

		for (Component swingComp : this.getComponents()) {

			if (swingComp instanceof DataPoint) {

				this.remove(swingComp);

			}

		}

		dataPoints = dots;

		highestVal = 0;

		for (DataPoint p : dots) {

			if (p.getValue() > highestVal)
				highestVal = p.getValue();

		}

		for (DataPoint data : dataPoints) {

			this.add(data);

		}

		UpperBound = (int) Math.max(Math.round(highestVal * 1.1), minHeight);

		this.revalidate();

		this.repaint();

	}

	@Override

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		super.paint(g);

		leftMarginX = this.getSize().width / 20;
		rightMarginX = this.getSize().width - this.getSize().width / 20;

		topMarginY = (int) (this.getSize().height / 5.0);
		labelMarginY = (int) (topMarginY / 3.0 * 2);
		bottomMarginY = this.getSize().height - (int) (this.getSize().height / 10.0);

		// Write the bounds and descriptors

		int FontSize = this.getSize().width / 50;

		Font textFont = new Font(Font.SANS_SERIF, 0, FontSize);

		g2.setFont(textFont);
		LowerOffsetX = g2.getFontMetrics().charsWidth("0".toCharArray(), 0, 1);
		UpperOffsetX = g2.getFontMetrics().charsWidth(Integer.toString(UpperBound).toCharArray(), 0,
				Integer.toString(UpperBound).length());

		valuePointX = leftMarginX - leftMarginX / 4;

		g2.drawString("0", valuePointX - LowerOffsetX, bottomMarginY);
		g2.drawString(Integer.toString(UpperBound), valuePointX - UpperOffsetX,
				topMarginY + g2.getFontMetrics().getHeight());

		startX = leftMarginX + (rightMarginX - leftMarginX) / 20;
		endX = rightMarginX - (rightMarginX - leftMarginX) / 20;
		intervalX = (int) ((endX - startX) / ((double) (dataPoints.length - 1)));

		pointRadius = 2;

		for (int i = 0; i < dataPoints.length; i++) {

			int dataOffsetX = g2.getFontMetrics().charsWidth(dataPoints[i].getDescriptor().toCharArray(), 0,
					dataPoints[i].getDescriptor().length()) / 2;
			int dataOffsetY = (int) (g2.getFontMetrics().getHeight() * 1.05);

			int descX = startX + intervalX * i - dataOffsetX;
			int descY = bottomMarginY + dataOffsetY;

			g2.drawString(dataPoints[i].getDescriptor(), descX, descY);

			double dataValProp = (double) (dataPoints[i].getValue()) / UpperBound;
			int dataValHeight = (int) (dataValProp * (bottomMarginY - topMarginY));

			int bufferX = dataPoints[i].getSize().width / 2;
			int bufferY = dataPoints[i].getSize().height / 2;

			int dataValX = (startX + intervalX * i) - bufferX;
			int dataValY = bottomMarginY - dataValHeight - bufferY;

			dataPoints[i].setPosition(dataValX, dataValY);

		}

		FontSize = this.getSize().width / 30;

		textFont = new Font(Font.SANS_SERIF, 0, FontSize);

		g2.setFont(textFont);
		DateRangeX = this.getWidth() / 2 - g2.getFontMetrics().charsWidth(label.toCharArray(), 0, label.length()) / 2;
		DateRangeY = (labelMarginY) / 2;

		g2.drawString(label, DateRangeX, DateRangeY);

		// Draw the line delineating progress

		int graphLineThickness = pointRadius * 2;
		BasicStroke graphLineStroke = new BasicStroke(graphLineThickness);
		g2.setStroke(graphLineStroke);

		for (int i = 0; i < dataPoints.length - 1; i++) {

			double dataVal1Prop = (double) (dataPoints[i].getValue()) / UpperBound;
			int dataVal1Height = (int) (dataVal1Prop * (bottomMarginY - topMarginY));

			int dataVal1X = (startX + intervalX * i);
			int dataVal1Y = bottomMarginY - dataVal1Height;

			double dataVal2Prop = (double) (dataPoints[i + 1].getValue()) / UpperBound;
			int dataVal2Height = (int) (dataVal2Prop * (bottomMarginY - topMarginY));

			int dataVal2X = (startX + intervalX * (i + 1));
			int dataVal2Y = bottomMarginY - dataVal2Height;

			g2.drawLine(dataVal1X, dataVal1Y, dataVal2X, dataVal2Y);

		}

		int graphBoundsThickness = 5;
		BasicStroke graphBoundStroke = new BasicStroke(graphBoundsThickness);
		g2.setStroke(graphBoundStroke);

		// Drawing the graph bounds

		g2.drawLine(leftMarginX, bottomMarginY, leftMarginX, topMarginY);
		g2.drawLine(leftMarginX, bottomMarginY, rightMarginX, bottomMarginY);

		textFont = new Font(Font.SANS_SERIF, 0, DateRangeFontSize);

		g2.dispose();

	}

}
