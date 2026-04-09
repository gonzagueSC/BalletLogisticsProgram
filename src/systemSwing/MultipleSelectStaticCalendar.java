package systemSwing;

import static util.SwingConstants.*;

import java.util.ArrayList;


public class MultipleSelectStaticCalendar extends WeekCalendarPanel {
	
	private ArrayList<String> selectedDays = new ArrayList<String>();

	public MultipleSelectStaticCalendar(int width, int height) {

		super(width, height, 0);
		this.startUp();

	}
	
	public void UpdateSelected(String Date) {

		if (!selectedDays.contains(Date)) {
			selectedDays.add(Date);
		} else if (selectedDays.contains(Date)) selectedDays.remove(selectedDays.indexOf(Date));

		WeekDay[] weekButtons = this.getWeek();

		for (int i = 0; i < weekButtons.length; i++) {

			if (this.selectedDays.contains(this.DaysOfTheWeek[i])) {

				weekButtons[i].setBackground(SecondaryPurple.darker());

			} else {

				weekButtons[i].setBackground(weekButtons[i].MainColor);

			}

		}

	}
	
	public void startUp() {

		WeekDay[] weekButtons = this.getWeek();

		for (int i = 0; i < weekButtons.length; i++) {

			int finalI = i;

			weekButtons[i].addActionListener((e -> UpdateSelected(this.DaysOfTheWeek[finalI])));

		}

	}

	
	public String[] getSelectedDays() {
		
		return selectedDays.toArray(new String[0]);
		
	}

}
