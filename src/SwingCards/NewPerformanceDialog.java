package SwingCards;

import java.awt.Dimension;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JDialog;
import javax.swing.JFrame;

import control.Main;
import control.PromptsService;
import databaseAccess.DatabaseCore;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.FilterPanel;
import systemSwing.Input;
import systemSwing.TitleLabel;
import util.Globals;
import static util.SwingConstants.*;

import databaseAccess.*;

public class NewPerformanceDialog extends JDialog {
	
	private String productionName;

	public NewPerformanceDialog(JFrame parent, ShowPerformancesLeft panel, String name) {
		
		super(parent, "", true);
		
		productionName = name;

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.getContentPane().setBackground(MainPurple);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		FilterPanel NewPerformanceDial = new FilterPanel();

		TitleLabel title = new TitleLabel("New Performance", 50, 20, 700, 30, 30, TextColor);

		TitleLabel PerformanceNameLabel = new TitleLabel("Performance Name", 75, 100, 650, 30, 20, TextColor);
		Input PerformanceName = new Input(75, 130, 650, 30, 20, TextColor, 15);
		TitleLabel DateTimeLabel = new TitleLabel("Performance Date", 75, 200, 650, 30, 20, TextColor);
		Input DateTime = new Input(75, 230, 650, 30, 20, TextColor, 15);
		DateTime.setPlaceholderText("MM/dd/yyyy hh:mm a");
		
		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		Button Save = new Button("Save", (e -> {

			String consoleData = PerformanceName.getText();
			String dateData = DateTime.getText();

			if (consoleData.isBlank()) {
				PromptsService.FailurePrompt("Please do not leave Performance Name Empty", this);
				return;
			}
			
			if (dateData.isBlank()) {
				
				PromptsService.FailurePrompt("Please do not leave Date Empty", this);
				return;
				
			}
			File ShowFolder = new File(Globals.ProductionsFolder, productionName);
			File Performance = new File(ShowFolder, "Performances");

			LocalDateTime PerformanceDate;
			DateTimeFormatter parser = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
			
			try {
				
				PerformanceDate = LocalDateTime.parse(dateData, parser);
				
			} catch (Exception ex) {
				
				PromptsService.FailurePrompt("Please format date as MM/dd/yyyy hh:mm AM/PM", this);
				return;
				
			}
			
			try {

				DatabaseCore.checkForSameLine(Performance, consoleData + ", " + dateData);

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Performance already Exists", this);
				return;

			}
			
			try {
				DatabaseCore.writeToDatabase(Performance, consoleData + ", " + dateData);
				ProductionsModule.UpdateProductions();
				ProductionsModule.createPerformance(productionName, consoleData);
				panel.Update(name);

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Some other error occured", this);
				return;

			}

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

			Main.Router.showDynamicView(ViewConstants.SHOW_VIEW, productionName);
		}), new Dimension(300, 50), 250, 305, TextColor, 50, 24);

		NewPerformanceDial.setLayout(null);
		NewPerformanceDial.add(title);

		NewPerformanceDial.add(PerformanceNameLabel);
		NewPerformanceDial.add(PerformanceName);
		NewPerformanceDial.add(DateTime);
		NewPerformanceDial.add(DateTimeLabel);
		NewPerformanceDial.add(Save);
		NewPerformanceDial.add(Cancel);

		this.add(NewPerformanceDial);
		this.setSize(800, 400);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
