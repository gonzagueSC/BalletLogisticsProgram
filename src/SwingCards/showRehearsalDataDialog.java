package SwingCards;

import static util.SwingConstants.MainPurple;
import static util.SwingConstants.TextColor;
import static util.SwingConstants.TransparentBackground;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JFrame;

import systemSwing.Button;
import systemSwing.FilterPanel;
import systemSwing.GeneralLabel;
import systemSwing.TitleLabel;

public class showRehearsalDataDialog extends JDialog {

	public showRehearsalDataDialog(JFrame parent, String rehearsal) {

		super(parent, "", true);

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.getContentPane().setBackground(MainPurple);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		FilterPanel NewCastDial = new FilterPanel();

		TitleLabel title = new TitleLabel(rehearsal.split(", ")[1], 50, 20, 700, 30, 30, TextColor);

		TitleLabel CastNameLabel = new TitleLabel("Production Name", 75, 70, 650, 30, 20, TextColor);
		GeneralLabel CastName = new GeneralLabel(rehearsal.split(", ")[0], 75, 100, 650, 30, 20, TextColor);

		TitleLabel DateLabel = new TitleLabel("Date", 75, 140, 650, 30, 20, TextColor);
		GeneralLabel Date = new GeneralLabel(rehearsal.split(", ")[2] + ": " + rehearsal.split(", ")[3] + " - " + rehearsal.split(", ")[4], 75, 170, 650, 30, 20, TextColor);

		TitleLabel StudioAndTeacherLabel = new TitleLabel("Studio - Teacher", 75, 210, 650, 30, 20, TextColor);
		GeneralLabel StudioAndTeacher = new GeneralLabel(rehearsal.split(", ")[5] + " - " + rehearsal.split(", ")[6],
				75, 240, 650, 30, 20, TextColor);

		TitleLabel RolesLabel = new TitleLabel("Roles", 75, 280, 650, 30, 20, TextColor);
		GeneralLabel Roles = new GeneralLabel(Arrays.toString(rehearsal.split(", ")[7].split("\\|")), 75, 310, 650, 30,
				20, TextColor);

		Button Save = new Button("Close", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(300, 50), 250, 405, TextColor, 50, 24);

		NewCastDial.setLayout(null);
		NewCastDial.add(title);

		NewCastDial.add(CastNameLabel);
		NewCastDial.add(CastName);
		NewCastDial.add(DateLabel);
		NewCastDial.add(Date);
		NewCastDial.add(StudioAndTeacherLabel);
		NewCastDial.add(StudioAndTeacher);
		NewCastDial.add(RolesLabel);
		NewCastDial.add(Roles);
		NewCastDial.add(Save);

		this.add(NewCastDial);
		this.setSize(800, 500);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
