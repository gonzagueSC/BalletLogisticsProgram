package systemSwing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import databaseAccess.DatabaseCore;
import util.DataPoint;
import util.Globals;

public class AttendanceGraph extends BasicGraph {

	private String currentDay;
	private ChronoUnit currentScope;
	private String currentID;
	private Button year, month, week, forward, back;

	int buttonIntervals = 10;
	int buttonWidth = (this.getWidth() - buttonIntervals * 6) / 5;

	ChronoUnit[] times = { ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.WEEKS };

	public AttendanceGraph(String date, ChronoUnit scope, String ID) {

		super(new DataPoint[0]);

		currentScope = scope;

		currentID = ID;

		currentDay = date;

		this.setSize(0, 0);

		swapData(date, scope, ID);

		this.revalidate();
		this.repaint();

	}
	
	@Override
	public void setSize(int X, int Y) {
		
		super.setSize(X, Y);
		this.repaint();
		
		if (back != null) this.remove(back);
		if (year != null) this.remove(year);
		if (month != null) this.remove(month);
		if (week != null) this.remove(week);
		if (forward != null) this.remove(forward);
		
		buttonWidth = (this.getWidth() - buttonIntervals * 6) / 5;
		
		int buttonX = buttonIntervals;
		int buttonHeight = (int) ((this.getTopMarginY() - this.getLabelMarginY()) * 0.9);
		int buttonY = (int) (this.getLabelMarginY() + (this.getTopMarginY() - this.getLabelMarginY()) / 20.0);

		int index = switch (currentScope) {

		case ChronoUnit.YEARS -> 0;
		case ChronoUnit.MONTHS -> 1;
		case ChronoUnit.WEEKS -> 2;

		default -> throw new IllegalArgumentException("Unexpected value: " + currentScope.name());

		};

		back = new Button("←", (e -> {

			try {
				
				System.out.println(times[index].toString() + "back started");

				swapData(DatabaseCore.isValidDateTime(currentDay).minus(1, times[index]).toString(), currentScope, currentID);
				
				System.out.println("Finished move. New date: " + DatabaseCore.isValidDateTime(currentDay).toLocalDate().toString());

			} catch (Exception e1) {

				e1.printStackTrace();

			}

		}), new Dimension(buttonWidth, buttonHeight), buttonX, buttonY, this.getBackground().darker(), buttonHeight / 5,
				buttonHeight / 2);
		this.add(back);

		buttonX += buttonWidth + buttonIntervals;

		year = new Button("Year", (e -> {

			try {

				swapData(currentDay, ChronoUnit.YEARS, currentID);

			} catch (Exception e1) {

				e1.printStackTrace();

			}

		}), new Dimension(buttonWidth, buttonHeight), buttonX, buttonY, this.getBackground().darker(), buttonHeight / 5,
				buttonHeight / 2);
		this.add(year);

		buttonX += buttonWidth + buttonIntervals;

		month = new Button("Month", (e -> {

			try {

				swapData(currentDay, ChronoUnit.MONTHS, currentID);

			} catch (Exception e1) {

				e1.printStackTrace();

			}

		}), new Dimension(buttonWidth, buttonHeight), buttonX, buttonY, this.getBackground().darker(), buttonHeight / 5,
				buttonHeight / 2);
		this.add(month);

		buttonX += buttonWidth + buttonIntervals;

		week = new Button("Week", (e -> {

			try {

				swapData(currentDay, ChronoUnit.WEEKS, currentID);

			} catch (Exception e1) {

				e1.printStackTrace();

			}

		}), new Dimension(buttonWidth, buttonHeight), buttonX, buttonY, this.getBackground().darker(), buttonHeight / 5,
				buttonHeight / 2);
		this.add(week);

		buttonX += buttonWidth + buttonIntervals;

		forward = new Button("→", (e -> {

			try {

				swapData(DatabaseCore.isValidDateTime(currentDay).plus(1, times[index]).toString(), currentScope, currentID);

			} catch (Exception e1) {

				e1.printStackTrace();

			}

		}), new Dimension(buttonWidth, buttonHeight), buttonX, buttonY, this.getBackground().darker(), buttonHeight / 5,
				buttonHeight / 2);
		this.add(forward);
		
		this.revalidate();
		this.repaint();
		
	}

	public void swapData(String date, ChronoUnit scope, String ID) {

		currentScope = scope;

		currentID = ID;

		currentDay = date;

		switch (scope) {

		case ChronoUnit.YEARS:
			this.setMinHeight(40);
			break;

		case ChronoUnit.MONTHS:
			this.setMinHeight(10);
			break;

		case ChronoUnit.WEEKS:
			this.setMinHeight(3);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + scope.name());

		}

		try {

			this.label = switch (scope) {

			case ChronoUnit.YEARS:
				yield "Year of " + DatabaseCore.isValidDateTime(date).getYear();

			case ChronoUnit.MONTHS:
				yield Globals.Months[DatabaseCore.isValidDateTime(date).getMonthValue() - 1] + " "
						+ DatabaseCore.isValidDateTime(date).getYear();

			case ChronoUnit.WEEKS:
				yield DatabaseCore.isValidDateTime(date).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
						.toLocalDate().toString() + " - "
						+ DatabaseCore.isValidDateTime(date).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
								.toLocalDate().toString();

			default:
				throw new IllegalArgumentException("Unexpected value: " + scope.name());

			};

			DataPoint[] attendanceHourData = switch (scope) {

			case ChronoUnit.YEARS -> databaseAccess.AttendanceModule.getYearData(date, ID);
			case ChronoUnit.MONTHS -> databaseAccess.AttendanceModule.getMonthData(date, ID);
			case ChronoUnit.WEEKS -> databaseAccess.AttendanceModule.getWeekData(date, ID);

			default -> throw new IllegalArgumentException("Unexpected value: " + scope.name());

			};

			for (int i = 0; i < attendanceHourData.length; i++) {

				DataPoint data = attendanceHourData[i];

				LocalDateTime firstOfScope = switch (scope) {

				case ChronoUnit.YEARS ->
					databaseAccess.DatabaseCore.isValidDateTime(currentDay).with(TemporalAdjusters.firstDayOfYear());
				case ChronoUnit.MONTHS ->
					databaseAccess.DatabaseCore.isValidDateTime(currentDay).with(TemporalAdjusters.firstDayOfMonth());
				case ChronoUnit.WEEKS -> databaseAccess.DatabaseCore.isValidDateTime(currentDay)
						.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

				default -> throw new IllegalArgumentException("Unexpected value: " + scope);

				};

				int index = switch (scope) {

				case ChronoUnit.YEARS -> 1;
				case ChronoUnit.MONTHS -> 2;
				case ChronoUnit.WEEKS -> 0;

				default -> throw new IllegalArgumentException("Unexpected value: " + scope.name());

				};

				LocalDateTime startOfDataPoint = firstOfScope.plus(i, times[index]);

				if (index != 0)
					data.addActionListener((e -> this.swapData(startOfDataPoint.toString(), times[index], ID)));

			}

			super.swapData(attendanceHourData);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
