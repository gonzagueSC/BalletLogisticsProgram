package systemSwing;

import util.Globals;

import static util.SwingConstants.*;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import control.PromptsService;

import databaseAccess.*;

public class MovableCalendarPanel extends WeekCalendarPanel {

	private String weekMonday;
	
	private boolean isActivated = true;
	
	Timer debounceTimer;

	public MovableCalendarPanel(int width, int height) {

		super(width, height, height / 10);

		Button forward = new Button("→", (e -> Update(SchedulesModule.jumpWeek(weekMonday))),
				new Dimension(width / 15, height / 10), width - this.MARGINX - width / 15,
				this.MARGINY + height / 3 - height / 7, TextColor, width / 35, width / 60);
		Button backward = new Button("←", (e -> Update(SchedulesModule.jumpBackWeek(weekMonday))),
				new Dimension(width / 15, height / 10), this.MARGINX, this.MARGINY + height / 3 - height / 7, TextColor,
				width / 35, width / 60);
		Input DateJump = new Input(width / 2 - width / 6, this.MARGINY + height / 3 - height / 7, width / 4,
				height / 10, width / 60, TextColor, width / 35);
		DateJump.setPlaceholderText("MM/dd/yyyy");
		Button JumpToDate = new Button("Jump To Date", (e -> {

			if (DateJump.getText().isBlank()) {

				PromptsService.FailurePrompt("Please don't leave date empty", (JDialog) this.getParent());
				return;

			}

			try {

				DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				LocalDate day = LocalDate.parse(DateJump.getText(), format);
				this.Update(day.toString());

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Please format date as MM/dd/yyyy");
				return;

			}

		}), new Dimension(width / 5, height / 10), width / 2 + width / 12, this.MARGINY + height / 3 - height / 7,
				TerciaryColor, width / 35, width / 60);

		this.Update(LocalDate.now().toString());

		this.add(JumpToDate);
		this.add(DateJump);
		this.add(forward);
		this.add(backward);
		
		ActionListener taskPerformer = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent evt) {
		        debounceTimer.stop(); 
		        MovableCalendarPanel.this.setEnabled(true);
		    }
		};
		debounceTimer = new Timer(20, taskPerformer);
		debounceTimer.setRepeats(false);
		

		this.addMouseWheelListener(new MouseWheelListener() {
			
			

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
				debounceTimer.restart();

				if (isActivated) {

					// Check for horizontal scrolling (common on touchpads/Magic Mouse)
					if (e.isShiftDown() || e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {

						// Check direction: Negative value for unitsToScroll often means left/up
						int units = e.getUnitsToScroll();

						if (units != 0) {
							
							setEnabled(false);

							if (units < 0) {

								Update(SchedulesModule.jumpBackWeek(weekMonday));

							} else {

								Update(SchedulesModule.jumpWeek(weekMonday));

							}
							
						}

					}

				}

			}

		});

	}
	
	public void setEnabled(boolean active) {

		isActivated = active;

	}

	public MovableCalendarPanel(int width, int height, Color weekBoxColor) {

		super(width, height, height / 10, weekBoxColor);

		Button forward = new Button("→", (e -> Update(SchedulesModule.jumpWeek(weekMonday))),
				new Dimension(width / 15, height / 10), width - this.MARGINX - width / 15,
				this.MARGINY + height / 3 - height / 7, TextColor, width / 35, width / 60);
		Button backward = new Button("←", (e -> Update(SchedulesModule.jumpBackWeek(weekMonday))),
				new Dimension(width / 15, height / 10), this.MARGINX, this.MARGINY + height / 3 - height / 7, TextColor,
				width / 35, width / 60);

		this.Update(LocalDate.now().toString());

		this.add(forward);
		this.add(backward);

	}

	public void Update(String date) {

		this.weekMonday = SchedulesModule.getMonday(date);

		String[] weekdates = SchedulesModule.getWeekDatesFromDate(date);
		WeekDay[] weekButtons = this.getWeek();

		for (int i = 0; i < weekdates.length; i++) {

			weekButtons[i].setText(DaysOfTheWeek[i] + ": " + weekdates[i].split("-")[2]);
			weekButtons[i].setEvents(SchedulesModule.getScheduleByDate(weekdates[i]));
			weekButtons[i].setHorizontalAlignment(JLabel.LEFT);
			weekButtons[i].setVerticalAlignment(JLabel.TOP);
			weekButtons[i].revalidate();
			weekButtons[i].repaint();

		}

		this.WeekLabel.setText("Week of " + Globals.Months[Integer.parseInt(weekMonday.split("-")[1]) - 1] + " "
				+ weekMonday.split("-")[2] + ", " + weekMonday.split("-")[0]);

		this.revalidate();
		this.repaint();

	}

}
