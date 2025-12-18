package SwingCards;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;

import control.Main;
import control.PromptsService;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.FilterPanel;
import systemSwing.Input;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;

import databaseAccess.*;

public class NewLevelDialog extends JDialog {

	public NewLevelDialog(JFrame parent) {

		super(parent, "", true);

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.getContentPane().setBackground(MainPurple);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		FilterPanel newLevelDial = new FilterPanel();

		TitleLabel title = new TitleLabel("New Level", 50, 20, 700, 30, 30, TextColor);

		TitleLabel LevelNameLabel = new TitleLabel("Level Name", 75, 60, 650, 30, 20, TextColor);
		Input LevelName = new Input(75, 90, 650, 30, 20, TextColor, 15);

		TitleLabel RecommendedHoursLabel = new TitleLabel("Recommended Hours to be Promoted", 75, 135, 650, 30, 20,
				TextColor);
		Input RecommendedHours = new Input(75, 165, 650, 30, 20, TextColor, 15);

		TitleLabel RequiredClassesLabel = new TitleLabel("Required Classes Per Week", 75, 210, 650, 30, 20, TextColor);
		Input RequiredClasses = new Input(75, 240, 650, 30, 20, TextColor, 15);

		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		Button Save = new Button("Save", (e -> {

			Double RecHours = 0.0;
			Double ReqClasses = 0.0;

			Button btn = (Button) e.getSource();
			btn.setEnabled(false);

			String consoleData = LevelName.getText();

			if (consoleData.isBlank()) {

				PromptsService.FailurePrompt("Please do not leave Level Name Empty", this);
				btn.setEnabled(true);
				return;

			}

			try {

				String Number = RecommendedHours.getText();
				Double NumberValue = Double.parseDouble(Number);
				RecHours = NumberValue;

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Please use only Numbers for measurements");
				btn.setEnabled(true);
				return;

			}

			try {

				String Number = RequiredClasses.getText();
				Double NumberValue = Double.parseDouble(Number);
				ReqClasses = NumberValue;

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Please use only Numbers for measurements");
				btn.setEnabled(true);
				return;

			}

			boolean check = true;

			try {

				check = SystemSettingsModule.checkForSystemDetail(SystemSettingsModule.LEVELS, consoleData);

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Some other Error Ocurred", this);
				btn.setEnabled(true);
				return;

			}

			try {

				if (!check) {

					String levelString = consoleData + ", " + RecHours + ", " + ReqClasses;

					SystemSettingsModule.addSystemDetail(SystemSettingsModule.LEVELS, levelString);

				} else {

					PromptsService.FailurePrompt("Level already Exists", this);
					btn.setEnabled(true);
					return;

				}

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Some other error occured", this);
				btn.setEnabled(true);
				return;

			}

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();
			Main.Router.showDynamicView(ViewConstants.LEVEL_MANAGER_VIEW);

		}), new Dimension(300, 50), 250, 305, TextColor, 50, 24);

		newLevelDial.setLayout(null);
		newLevelDial.add(title);

		newLevelDial.add(LevelNameLabel);
		newLevelDial.add(LevelName);
		newLevelDial.add(Save);
		newLevelDial.add(Cancel);
		newLevelDial.add(RecommendedHours);
		newLevelDial.add(RecommendedHoursLabel);
		newLevelDial.add(RequiredClasses);
		newLevelDial.add(RequiredClassesLabel);

		this.add(newLevelDial);
		this.setSize(800, 400);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
