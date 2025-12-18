package systemSwing;

import static util.SwingConstants.*;

import java.awt.*;


public class WeekCalendarPanel extends Panel {

	private int PANELWIDTH;
	private int PANELHEIGHT;
	int MARGINXPERC = 3;
	int MARGINYPERC = 5;
	int MARGINX;
	int MARGINY;
	int MARGINSCREENWIDTH;
	int MARGINSCREENHEIGHT;
	private int WEEKBOXWIDTH;
	private int WEEKBOXHEIGHT;
	private int WEEKBOXFONTSIZE;
	private int WEEKBOXARCRAD;
	private int WEEKBOXGAP;
	String[] DaysOfTheWeek = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
	GeneralLabel WeekLabel;
	private WeekDay[] weekDays = new WeekDay[7];

	public WeekCalendarPanel(int width, int height, int TitlePadding) {

		super(true, true, true, true);

		this.PANELWIDTH = width;
		this.PANELHEIGHT = height;

		this.MARGINX = (MARGINXPERC * PANELWIDTH) / 100;
		this.MARGINY = (MARGINYPERC * PANELHEIGHT) / 100;

		this.MARGINSCREENWIDTH = PANELWIDTH - 2 * (MARGINX);
		this.MARGINSCREENHEIGHT = PANELHEIGHT - 2 * (MARGINY);

		this.WEEKBOXGAP = this.MARGINSCREENWIDTH / 42;
		int AVAILABLE_BOX_WIDTH = this.MARGINSCREENWIDTH - (6 * this.WEEKBOXGAP);
		
		int REMAINING_PIXELS = AVAILABLE_BOX_WIDTH % 7;
		this.WEEKBOXWIDTH = AVAILABLE_BOX_WIDTH / 7;
		this.WEEKBOXHEIGHT = (this.MARGINSCREENHEIGHT - this.MARGINSCREENHEIGHT / 3);
		this.WEEKBOXFONTSIZE = this.WEEKBOXHEIGHT / 13;
		this.WEEKBOXARCRAD = this.WEEKBOXWIDTH / 4;

		int TitleLabelWidth = (this.MARGINSCREENWIDTH - this.MARGINSCREENWIDTH / 5);
		int TitleLabelHeight = this.MARGINSCREENHEIGHT / 3 - TitlePadding;
		int TitleLabelX = this.MARGINX + this.MARGINSCREENWIDTH / 10;
		int TitleLabelFontSize = TitleLabelWidth / 20;

		WeekLabel = new GeneralLabel("Week Calendar", TitleLabelX, this.MARGINY, TitleLabelWidth, TitleLabelHeight,
				TitleLabelFontSize, Color.black);
		this.add(WeekLabel);

		int BoxX = MARGINX;

		for (int i = 0; i < weekDays.length; i++) {
			
			if (i == weekDays.length - 1) {
		        BoxX += REMAINING_PIXELS;
		    }

			WeekDay weekDay = new WeekDay(DaysOfTheWeek[i], null, new Dimension(this.WEEKBOXWIDTH, this.WEEKBOXHEIGHT),
					BoxX, this.MARGINY + this.MARGINSCREENHEIGHT / 3, MainGray.brighter(), this.WEEKBOXARCRAD);
			weekDays[i] = weekDay;
			
			BoxX += this.WEEKBOXWIDTH + this.WEEKBOXGAP;
			
			this.add(weekDay);

		}

		this.setBackground(MainGray);
		this.setLayout(null);
		this.setSize(this.PANELWIDTH, this.PANELHEIGHT);

	}
	
	public WeekCalendarPanel(int width, int height, int TitlePadding, Color weekDayColor) {

		super(true, true, true, true);

		this.PANELWIDTH = width;
		this.PANELHEIGHT = height;

		this.MARGINX = (MARGINXPERC * PANELWIDTH) / 100;
		this.MARGINY = (MARGINYPERC * PANELHEIGHT) / 100;

		this.MARGINSCREENWIDTH = PANELWIDTH - 2 * (MARGINX);
		this.MARGINSCREENHEIGHT = PANELHEIGHT - 2 * (MARGINY);

		this.WEEKBOXGAP = this.MARGINSCREENWIDTH / 42;
		int AVAILABLE_BOX_WIDTH = this.MARGINSCREENWIDTH - (6 * this.WEEKBOXGAP);
		
		int REMAINING_PIXELS = AVAILABLE_BOX_WIDTH % 7;
		this.WEEKBOXWIDTH = AVAILABLE_BOX_WIDTH / 7;
		this.WEEKBOXHEIGHT = (this.MARGINSCREENHEIGHT - this.MARGINSCREENHEIGHT / 3);
		this.WEEKBOXFONTSIZE = this.WEEKBOXHEIGHT / 13;
		this.WEEKBOXARCRAD = this.WEEKBOXWIDTH / 4;

		int TitleLabelWidth = (this.MARGINSCREENWIDTH - this.MARGINSCREENWIDTH / 5);
		int TitleLabelHeight = this.MARGINSCREENHEIGHT / 3 - TitlePadding;
		int TitleLabelX = this.MARGINX + this.MARGINSCREENWIDTH / 10;
		int TitleLabelFontSize = TitleLabelHeight - TitleLabelHeight / 3;

		WeekLabel = new GeneralLabel("Week Calendar", TitleLabelX, this.MARGINY, TitleLabelWidth, TitleLabelHeight,
				TitleLabelFontSize, Color.black);
		this.add(WeekLabel);

		int BoxX = MARGINX;

		for (int i = 0; i < weekDays.length; i++) {
			
			if (i == weekDays.length - 1) {
		        BoxX += REMAINING_PIXELS;
		    }

			WeekDay weekDay = new WeekDay(DaysOfTheWeek[i], null, new Dimension(this.WEEKBOXWIDTH, this.WEEKBOXHEIGHT),
					BoxX, this.MARGINY + this.MARGINSCREENHEIGHT / 3, weekDayColor, this.WEEKBOXARCRAD);
			weekDays[i] = weekDay;
			
			BoxX += this.WEEKBOXWIDTH + this.WEEKBOXGAP;
			
			this.add(weekDay);

		}

		this.setBackground(MainGray);
		this.setLayout(null);
		this.setSize(this.PANELWIDTH, this.PANELHEIGHT);

	}

	public void setTitle(String s) {

		WeekLabel.setText(s);
		this.revalidate();
		this.repaint();

	}

	public WeekDay[] getWeek() {

		return weekDays;

	}

}
