package SwingCards;

import java.awt.Dimension;
import java.time.LocalDate;

import javax.swing.JDialog;
import javax.swing.JFrame;

import control.PromptsService;
import systemSwing.Button;
import systemSwing.FilterPanel;
import systemSwing.Input;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;

import databaseAccess.*;

public class SchedulesClassesPauseDialog extends JDialog {

	public SchedulesClassesPauseDialog(JFrame parent, SchedulesClasses panel, String classString) {

		super(parent, "", true);

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.getContentPane().setBackground(MainPurple);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		FilterPanel SchedulesClassesPauseDial = new FilterPanel();

		TitleLabel title = new TitleLabel("Pause Class", 50, 20, 700, 30, 30, TextColor);

		TitleLabel ProductionNameLabel = new TitleLabel("Start Date", 75, 100, 650, 30, 20, TextColor);
		Input ProductionName = new Input(75, 130, 650, 30, 20, TextColor, 15);
		ProductionName.setPlaceholderText("MM/dd/yyyy");

		TitleLabel EndDateLabel = new TitleLabel("End Date", 75, 170, 650, 30, 20, TextColor);
		Input EndDate = new Input(75, 200, 650, 30, 20, TextColor, 15);
		EndDate.setPlaceholderText("MM/dd/yyyy");

		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		Button Save = new Button("Save", (e -> {

			String consoleData = ProductionName.getText();

			try {
				
				LocalDate startDate = LocalDate.now();
				
				if (!consoleData.isBlank()) {
					
					startDate = DatabaseCore.isValidDate(consoleData);
					
				}

				LocalDate endDate = DatabaseCore.isValidDate(EndDate.getText());
				
				SchedulesModule.pauseClass(classString, startDate.toString(), endDate.toString());

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Please format date as: MM/dd/yyyy", this);
				return;

			}

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();
			
			panel.Update();
		}), new Dimension(300, 50), 250, 305, TextColor, 50, 24);

		SchedulesClassesPauseDial.setLayout(null);
		SchedulesClassesPauseDial.add(title);

		SchedulesClassesPauseDial.add(ProductionNameLabel);
		SchedulesClassesPauseDial.add(ProductionName);
		SchedulesClassesPauseDial.add(EndDateLabel);
		SchedulesClassesPauseDial.add(EndDate);
		SchedulesClassesPauseDial.add(Save);
		SchedulesClassesPauseDial.add(Cancel);

		this.add(SchedulesClassesPauseDial);
		this.setSize(800, 400);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
