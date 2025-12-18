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

public class NewProductionDialog extends JDialog {

	public NewProductionDialog(JFrame parent) {

		super(parent, "", true);

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.getContentPane().setBackground(MainPurple);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		FilterPanel newProductionDial = new FilterPanel();

		TitleLabel title = new TitleLabel("New Production", 50, 20, 700, 30, 30, TextColor);

		TitleLabel ProductionNameLabel = new TitleLabel("Production Name", 75, 100, 650, 30, 20, TextColor);
		Input ProductionName = new Input(75, 130, 650, 30, 20, TextColor, 15);

		TitleLabel EndDateLabel = new TitleLabel("Production End", 75, 170, 650, 30, 20, TextColor);
		Input EndDate = new Input(75, 200, 650, 30, 20, TextColor, 15);
		EndDate.setPlaceholderText("MM/dd/yyyy");

		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		Button Save = new Button("Save", (e -> {

			String consoleData = ProductionName.getText();

			if (consoleData.isBlank()) {
				PromptsService.FailurePrompt("Please do not leave Production Name Empty", this);
				return;
			}
			try {

				ProductionsModule.checkForShow(consoleData);

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Production already Exists", this);
				return;

			}

			try {

				ProductionsModule.createProduction(ProductionName.getText(), EndDate.getText());

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Please format date as: MM/dd/yyyy", this);
				return;

			}

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

			Main.Router.showDynamicView(ViewConstants.SHOW_DIRECTORY_VIEW);
		}), new Dimension(300, 50), 250, 305, TextColor, 50, 24);

		newProductionDial.setLayout(null);
		newProductionDial.add(title);

		newProductionDial.add(ProductionNameLabel);
		newProductionDial.add(ProductionName);
		newProductionDial.add(EndDateLabel);
		newProductionDial.add(EndDate);
		newProductionDial.add(Save);
		newProductionDial.add(Cancel);

		this.add(newProductionDial);
		this.setSize(800, 400);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
