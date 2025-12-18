package systemSwing;

import java.awt.event.ActionListener;
import java.time.LocalDate;

import static util.SwingConstants.*;

import databaseAccess.*;

public class SingleSelectMovableCalendar extends MovableCalendarPanel {

	private String selectedDate;

	public SingleSelectMovableCalendar(int width, int height) {

		super(width, height);
		this.startUp();

	}

	public void UpdateSelected(String Date) {

		super.Update(Date);
		this.selectedDate = Date;

		String[] weekdates = SchedulesModule.getWeekDatesFromDate(Date);
		WeekDay[] weekButtons = this.getWeek();

		for (int i = 0; i < weekButtons.length; i++) {

			int finalI = i;
			
			for (ActionListener actionList: weekButtons[i].getActionListeners()) {
				
				weekButtons[i].removeActionListener(actionList);
				
			}

			weekButtons[i].addActionListener((e -> UpdateSelected(weekdates[finalI])));

			if (this.selectedDate.equals(weekdates[i])) {

				weekButtons[i].setBackground(SecondaryPurple.darker());

			} else {

				weekButtons[i].setBackground(weekButtons[i].MainColor);

			}

		}

	}

	public void startUp() {

		super.Update(LocalDate.now().toString());
		this.selectedDate = "";

		String[] weekdates = SchedulesModule.getWeekDatesFromDate(LocalDate.now().toString());
		WeekDay[] weekButtons = this.getWeek();

		for (int i = 0; i < weekButtons.length; i++) {

			int finalI = i;

			weekButtons[i].addActionListener((e -> UpdateSelected(weekdates[finalI])));

		}

	}
	
	public String getSelectedDate() {
		
		return this.selectedDate;
		
	}

}
