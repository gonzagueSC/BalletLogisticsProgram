package SwingCards;

import java.awt.Dimension;
import java.io.File;

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

public class NewCastDialog extends JDialog {
	
	private String productionName;

	public NewCastDialog(JFrame parent, String name) {
		
		super(parent, "", true);
		
		productionName = name;

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.getContentPane().setBackground(MainPurple);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		FilterPanel NewCastDial = new FilterPanel();

		TitleLabel title = new TitleLabel("New Cast", 50, 20, 700, 30, 30, TextColor);

		TitleLabel CastNameLabel = new TitleLabel("Cast Name", 75, 130, 650, 30, 20, TextColor);
		Input CastName = new Input(75, 160, 650, 30, 20, TextColor, 15);
		
		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		Button Save = new Button("Save", (e -> {

			String consoleData = CastName.getText();

			if (consoleData.isBlank()) {
				PromptsService.FailurePrompt("Please do not leave Cast Name Empty", this);
				return;
			}
			File ShowFolder = new File(Globals.ProductionsFolder, productionName);
			File Cast = new File(ShowFolder, "Casts");
			try {

				ProductionsModule.checkForCast(Cast, consoleData);

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Cast already Exists", this);
				return;

			}

			try {
				DatabaseCore.writeToDatabase(Cast, consoleData);
				ProductionsModule.UpdateProductions();

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Some other error occured", this);
				return;

			}

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

			Main.Router.showDynamicView(ViewConstants.SHOW_VIEW, productionName);
			
		}), new Dimension(300, 50), 250, 305, TextColor, 50, 24);

		NewCastDial.setLayout(null);
		NewCastDial.add(title);

		NewCastDial.add(CastNameLabel);
		NewCastDial.add(CastName);
		NewCastDial.add(Save);
		NewCastDial.add(Cancel);

		this.add(NewCastDial);
		this.setSize(800, 400);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
