package SwingCards;

import static util.SwingConstants.MainGray;

import systemSwing.AttendanceGraph;
import systemSwing.BasicGraph;
import systemSwing.Panel;
import util.DataPoint;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

public class AdminAttendanceDataView extends Panel {

	private String currentDay;
	private AttendanceGraph attendanceGraph;

	public AdminAttendanceDataView() {

		super(false, false, true, true);

		this.setBackground(MainGray.brighter());
		this.setLayout(null);
		this.setBounds(0, 200, 1200, 600);

	}

	public void Update(String date, ChronoUnit scope, String ID) {

		currentDay = date;

		if (attendanceGraph != null) {

			attendanceGraph.swapData(date, scope, ID);
			attendanceGraph.repaint();

		} else {
			
			attendanceGraph = new AttendanceGraph(date, scope, ID);
			attendanceGraph.setPosition(100, 100);
			attendanceGraph.setSize(900, 600);
			this.add(attendanceGraph);
			
		}

	}

	public void Update(String ID) {

		Update(LocalDateTime.now().toString(), ChronoUnit.MONTHS, ID);

	}

}
