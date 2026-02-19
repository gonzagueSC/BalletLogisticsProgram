package SwingCards;

import static util.SwingConstants.BACKBUTTONARCRAD;
import static util.SwingConstants.BACKBUTTOND;
import static util.SwingConstants.BACKBUTTONFONTSIZE;
import static util.SwingConstants.BACKBUTTONX;
import static util.SwingConstants.BACKBUTTONY;
import static util.SwingConstants.MainGray;
import static util.SwingConstants.TextColor;

import systemSwing.AttendanceGraph;
import systemSwing.Button;
import systemSwing.DataWindow;
import systemSwing.Panel;
import java.time.LocalDateTime;
import java.time.temporal.*;

import control.Main;
import swingConstants.ViewConstants;

public class AdminAttendanceDataView extends Panel {

	private String currentDay;
	private AttendanceGraph attendanceGraph;
	private DataWindow statsWindow;

	public AdminAttendanceDataView() {

		super(false, false, true, true);

		this.setBackground(MainGray.brighter());
		this.setLayout(null);
		this.setBounds(0, 200, 1200, 800);
		
		Button MainMenu = new Button("Back", (e -> Main.Router.showDynamicView(ViewConstants.INDIVIDUAL_STUDENT_VIEW)), BACKBUTTOND, BACKBUTTONX, BACKBUTTONY,
				TextColor, BACKBUTTONARCRAD, BACKBUTTONFONTSIZE);
		
		this.add(MainMenu);

	}

	public void Update(String ID) {

		if (attendanceGraph != null) {

			attendanceGraph.swapData(ID);
			attendanceGraph.repaint();

		} else {

			attendanceGraph = new AttendanceGraph(this);
			attendanceGraph.swapData(ID);
			this.UpdateData();
			attendanceGraph.setPosition(30, 200);
			attendanceGraph.setSize(770, 550);
			this.add(attendanceGraph);

		}

		double Hours = attendanceGraph.getTotalHoursOverRange();
		double ComplianceScore = attendanceGraph.getComplianceScoreOverRange();

		if (statsWindow != null) {

			statsWindow.swapData("Hours", Integer.toString((int)Math.round(Hours)), "Compliance Score",
					Double.toString(ComplianceScore));
			statsWindow.repaint();

		} else {

			statsWindow = new DataWindow("Hours", Integer.toString((int)Math.round(Hours)), "Compliance Score",
					Double.toString(ComplianceScore));
			statsWindow.setPosition(860, 200);
			statsWindow.setSize(300, 550);
			this.add(statsWindow);

		}

	}

	public void UpdateData() {

		double Hours = attendanceGraph.getTotalHoursOverRange();
		double ComplianceScore = attendanceGraph.getComplianceScoreOverRange();

		if (statsWindow != null) {

			statsWindow.swapData("Hours", Integer.toString((int)Math.round(Hours)), "Compliance Score",
					Double.toString(ComplianceScore));
			statsWindow.repaint();

		} else {

			statsWindow = new DataWindow("Hours", Integer.toString((int)Math.round(Hours)), "Compliance Score",
					Double.toString(ComplianceScore));
			statsWindow.setPosition(860, 200);
			statsWindow.setSize(300, 550);
			this.add(statsWindow);

		}

	}

}
