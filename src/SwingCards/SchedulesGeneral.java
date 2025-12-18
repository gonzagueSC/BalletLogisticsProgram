package SwingCards;

import static util.SwingConstants.*;

import javax.swing.BorderFactory;

import systemSwing.MovableCalendarPanel;
import systemSwing.Panel;

public class SchedulesGeneral extends Panel {

	public SchedulesGeneral() {

		super(false, false, true, true);

		this.setBackground(MainGray.brighter());
		this.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, SecondaryPurple.darker().darker().darker()));
		this.setLayout(null);
		this.setBounds(0, 200, 1200, 600);
		
		MovableCalendarPanel viewPanel = new MovableCalendarPanel(1190, 520);
		viewPanel.setLocation(5, 20);
		this.add(viewPanel);

	}

}
