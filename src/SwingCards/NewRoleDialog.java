package SwingCards;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;

import control.Main;
import control.PromptsService;
import swingConstants.ViewConstants;
import systemSwing.Button;
import systemSwing.FilterPanel;
import systemSwing.Input;
import systemSwing.TitleLabel;
import util.Globals;
import static util.SwingConstants.*;

import databaseAccess.*;

public class NewRoleDialog extends JDialog {
	
	private String productionName;

	public NewRoleDialog(JFrame parent, String name) {
		
		super(parent, "", true);
		
		productionName = name;

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.getContentPane().setBackground(MainPurple);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		FilterPanel newRoleDial = new FilterPanel();

		TitleLabel title = new TitleLabel("New Role", 50, 20, 700, 30, 30, TextColor);

		TitleLabel RoleNameLabel = new TitleLabel("Role Name", 75, 130, 650, 30, 20, TextColor);
		Input RoleName = new Input(75, 160, 650, 30, 20, TextColor, 15);
		
		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		Button Save = new Button("Save", (e -> {
			
			Button btn = (Button) e.getSource();
		    btn.setEnabled(false);

			String consoleData = RoleName.getText();

			if (consoleData.isBlank()) {
				PromptsService.FailurePrompt("Please do not leave Role Name Empty", this);
				btn.setEnabled(true);
				return;
			}
			File ShowFolder = new File(Globals.ProductionsFolder, productionName);
			File Roles = new File(ShowFolder, "Roles");
			try {

				ProductionsModule.checkForRole(Roles, consoleData);

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Role already Exists", this);
				btn.setEnabled(true);
				return;

			}

			try {
				
				ProductionsModule.addRole(productionName, consoleData);

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Some other error occured", this);
				btn.setEnabled(true);
				return;

			}

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

			Main.Router.showDynamicView(ViewConstants.SHOW_VIEW, productionName);
		}), new Dimension(300, 50), 250, 305, TextColor, 50, 24);

		newRoleDial.setLayout(null);
		newRoleDial.add(title);

		newRoleDial.add(RoleNameLabel);
		newRoleDial.add(RoleName);
		newRoleDial.add(Save);
		newRoleDial.add(Cancel);

		this.add(newRoleDial);
		this.setSize(800, 400);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
