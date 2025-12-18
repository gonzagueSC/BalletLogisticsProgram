package systemSwing;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import static util.SwingConstants.*;

public class WeekDay extends Button {

	private GeneralLabel[] Events;
	private int LabelHeight;
	private int StartHeight;
	private GeneralLabel title;
	Color MainColor;
	private int width;
	private int height;
	private int fontSize;

	public WeekDay(String s, ActionListener a, Dimension d, int X, int Y, Color c, int arcRadius) {

		super("", a, d, X, Y, c, arcRadius, d.width / 13);

		fontSize = d.width / 13;
		
		this.width = d.width;
		this.height = d.height;
		
		int div = 5;

		title = new GeneralLabel(s, d.width / (div * 2), arcRadius / 8, d.width - d.width / div, fontSize + 5, fontSize, Color.black);
		title.setHorizontalAlignment(LEFT);
		this.add(title);

		MainColor = c;
		this.setHorizontalAlignment(LEFT);
		this.setVerticalAlignment(TOP);
		Events = new GeneralLabel[0];
		this.LabelHeight = fontSize + 5;
		this.StartHeight = fontSize + 10;
		this.setLayout(null);

	}

	@Override
	public void setText(String text) {

		if (title != null)

			this.title.setText(text);

	}

	public void setEvents(String[] events) {

		this.removeAll();

		this.Events = new GeneralLabel[events.length];

		int MaxLabels = this.getHeight() / LabelHeight - 1;
		
		this.add(title);

		for (int i = 0; i < events.length && i < MaxLabels; i++) {

			if (i != MaxLabels - 1) {

				GeneralLabel event = new GeneralLabel(events[i].split(", ")[0] + ": " + events[i].split(", ")[2],
						this.getWidth() / 16, StartHeight + LabelHeight * i, this.getWidth() - this.getWidth() / 8,
						LabelHeight, this.getFont().getSize(), TerciaryColor);
				event.setHorizontalAlignment(JLabel.LEFT);

				this.add(event);
				Events[i] = event;

			} else {

				GeneralLabel warning = new GeneralLabel("And " + (MaxLabels - i) + " others...", 0,
						StartHeight + LabelHeight * i, this.getWidth(), LabelHeight, this.getFont().getSize(),
						SecondaryPurple);
				warning.setHorizontalAlignment(JLabel.LEFT);
				this.add(warning);
				Events[i] = warning;

			}

		}

		this.revalidate();
		this.repaint();

	}

}
