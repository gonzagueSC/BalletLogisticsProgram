package SwingCards;

import static util.SwingConstants.MainGray;

import systemSwing.BasicGraph;
import systemSwing.Panel;
import util.DataPoint;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.*;

public class AdminAttendanceDataView extends Panel {

	private String currentDay;
	private BasicGraph attendanceGraph;

	public AdminAttendanceDataView() {

		super(false, false, true, true);

		this.setBackground(MainGray.brighter());
		this.setLayout(null);
		this.setBounds(0, 200, 1200, 600);

	}

	public void Update(String date, ChronoUnit scope, String ID) {

		currentDay = date;

		if (attendanceGraph != null) {

			this.remove(attendanceGraph);

		}

		try {

			DataPoint[] attendanceHourData = switch (scope) {

			case ChronoUnit.YEARS -> databaseAccess.AttendanceModule.getYearData(date, ID);
			case ChronoUnit.MONTHS -> databaseAccess.AttendanceModule.getMonthData(date, ID);
			case ChronoUnit.WEEKS -> databaseAccess.AttendanceModule.getWeekData(date, ID);

			default -> throw new IllegalArgumentException("Unexpected value: " + scope.name());

			};

			for (int i = 0; i < attendanceHourData.length; i++) {

				DataPoint data = attendanceHourData[i];

				LocalDate firstOfScope = switch (scope) {
				
				case ChronoUnit.YEARS ->
					databaseAccess.DatabaseCore.isValidDate(currentDay).with(TemporalAdjusters.firstDayOfYear());
				case ChronoUnit.MONTHS ->
					databaseAccess.DatabaseCore.isValidDate(currentDay).with(TemporalAdjusters.firstDayOfMonth());
				case ChronoUnit.WEEKS -> databaseAccess.DatabaseCore.isValidDate(currentDay)
						.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

				default -> throw new IllegalArgumentException("Unexpected value: " + scope);

				};

				ChronoUnit[] times = { ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.WEEKS };

				int index = switch (scope) {

				case ChronoUnit.YEARS -> 1;
				case ChronoUnit.MONTHS -> 2;
				case ChronoUnit.WEEKS -> 0;
				

				default -> throw new IllegalArgumentException("Unexpected value: " + scope.name());

				};
				
				LocalDate startOfDataPoint = firstOfScope;
				startOfDataPoint.plus(1, times[index]);

				if (index != 0) data.addActionListener((e -> this.Update(startOfDataPoint.toString(), times[index], ID)));

			}

			attendanceGraph = new BasicGraph(attendanceHourData);

			attendanceGraph.setPosition(100, 100);
			attendanceGraph.setSize(800, 500);

			this.add(attendanceGraph);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void Update(String ID) {

		Update(LocalDate.now().toString(), ChronoUnit.MONTHS, ID);

	}

}
