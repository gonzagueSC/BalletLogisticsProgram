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

public class NewTeacherDialog extends JDialog {

	public NewTeacherDialog(JFrame parent) {

		super(parent, "", true);

		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.getContentPane().setBackground(MainPurple);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		FilterPanel newTeacherDial = new FilterPanel();

		TitleLabel title = new TitleLabel("New Teacher", 50, 20, 700, 30, 30, TextColor);

		TitleLabel TeacherNameLabel = new TitleLabel("Teacher Name", 75, 130, 650, 30, 20, TextColor);
		Input TeacherName = new Input(75, 160, 650, 30, 20, TextColor, 15);

		Button Cancel = new Button("Cancel", (e -> {

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.dispose();

		}), new Dimension(150, 50), 50, 10, TextColor, 25, ChangeButtonFontSize);

		Button Save = new Button("Save", (e -> {

			Button btn = (Button) e.getSource();
			btn.setEnabled(false);

			String consoleData = TeacherName.getText();

			if (consoleData.isBlank()) {

				PromptsService.FailurePrompt("Please do not leave Teacher Name Empty", this);
				btn.setEnabled(true);
				return;

			}

			boolean check = true;

			try {

				check = SystemSettingsModule.checkForSystemDetail(SystemSettingsModule.TEACHERS, consoleData);

			} catch (Exception ex) {

				PromptsService.FailurePrompt("Some other Error Ocurred", this);
				btn.setEnabled(true);
				return;

			}

			try {

				if (!check) {

					SystemSettingsModule.addSystemDetail(SystemSettingsModule.TEACHERS, consoleData);

				} else {

					PromptsService.FailurePrompt("Teacher already Exists", this);
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
			Main.Router.showDynamicView(ViewConstants.TEACHER_MANAGER_VIEW);

		}), new Dimension(300, 50), 250, 305, TextColor, 50, 24);

		newTeacherDial.setLayout(null);
		newTeacherDial.add(title);

		newTeacherDial.add(TeacherNameLabel);
		newTeacherDial.add(TeacherName);
		newTeacherDial.add(Save);
		newTeacherDial.add(Cancel);

		this.add(newTeacherDial);
		this.setSize(800, 400);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);

	}

}
