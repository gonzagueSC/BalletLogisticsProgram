package SwingCards;

import javax.swing.JPanel;

import systemSwing.Button;
import systemSwing.Input;
import systemSwing.TitleLabel;
import static util.SwingConstants.*;
import control.*;
import swingConstants.*;

import databaseAccess.*;

public class CostumeMeasurementsView extends JPanel {
	private TitleLabel title;
	private Button Save;

	private TitleLabel heightLab, girthLab, waistLab, hipsLab, bustChestLab, inseamLab, sleeveLengthLab, neckLab,
			backLengthLab, shoeSizeLab;

	private Input height, girth, waist, hips, bustChest, inseam, sleeveLength, neck, backLength, shoeSize;

	String[] studentData;

	public CostumeMeasurementsView() {

		this.setBackground(MainGray);
		this.setLayout(null);

		title = new TitleLabel("Costume Measurements", TITLELABELX, TITLELABELY, TITLELABELWIDTH, TITLELABELHEIGHT,
				TITLELABELFONTSIZE, TextColor);

		heightLab = new TitleLabel("Height", INPUTPANELLEFTX, INPUTFORM5STARTY - INPUTFORM5LABELOFFSET,
				INPUTPANELINPUTWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		height = new Input(INPUTPANELLEFTX, INPUTFORM5STARTY, INPUTPANELINPUTWIDTH, INPUTFORM5INPUTHEIGHT,
				INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		girthLab = new TitleLabel("Girth", INPUTPANELRIGHTX, INPUTFORM5STARTY - INPUTFORM5LABELOFFSET,
				INPUTPANELINPUTWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		girth = new Input(INPUTPANELRIGHTX, INPUTFORM5STARTY, INPUTPANELINPUTWIDTH, INPUTFORM5INPUTHEIGHT,
				INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		waistLab = new TitleLabel("Waist", INPUTPANELLEFTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 1 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		waist = new Input(INPUTPANELLEFTX, INPUTFORM5STARTY + INPUTFORM5GAP * 1, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		hipsLab = new TitleLabel("Hips", INPUTPANELRIGHTX, INPUTFORM5STARTY + INPUTFORM5GAP * 1 - INPUTFORM5LABELOFFSET,
				INPUTPANELINPUTWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		hips = new Input(INPUTPANELRIGHTX, INPUTFORM5STARTY + INPUTFORM5GAP * 1, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		bustChestLab = new TitleLabel("Bust/Chest", INPUTPANELLEFTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 2 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		bustChest = new Input(INPUTPANELLEFTX, INPUTFORM5STARTY + INPUTFORM5GAP * 2, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		inseamLab = new TitleLabel("Inseam", INPUTPANELRIGHTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 2 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		inseam = new Input(INPUTPANELRIGHTX, INPUTFORM5STARTY + INPUTFORM5GAP * 2, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		sleeveLengthLab = new TitleLabel("Sleeve Length", INPUTPANELLEFTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 3 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		sleeveLength = new Input(INPUTPANELLEFTX, INPUTFORM5STARTY + INPUTFORM5GAP * 3, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		neckLab = new TitleLabel("Neck", INPUTPANELRIGHTX, INPUTFORM5STARTY + INPUTFORM5GAP * 3 - INPUTFORM5LABELOFFSET,
				INPUTPANELINPUTWIDTH, INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		neck = new Input(INPUTPANELRIGHTX, INPUTFORM5STARTY + INPUTFORM5GAP * 3, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		backLengthLab = new TitleLabel("Back Length", INPUTPANELLEFTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 4 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		backLength = new Input(INPUTPANELLEFTX, INPUTFORM5STARTY + INPUTFORM5GAP * 4, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		shoeSizeLab = new TitleLabel("Shoe Size", INPUTPANELRIGHTX,
				INPUTFORM5STARTY + INPUTFORM5GAP * 4 - INPUTFORM5LABELOFFSET, INPUTPANELINPUTWIDTH,
				INPUTPANELLABELHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor);
		shoeSize = new Input(INPUTPANELRIGHTX, INPUTFORM5STARTY + INPUTFORM5GAP * 4, INPUTPANELINPUTWIDTH,
				INPUTFORM5INPUTHEIGHT, INPUTFORM5INPUTFONTSIZE, TextColor, INPUTFORM5ARCRAD);

		Save = new Button("Save", null, ConfirmButtonD, ConfirmButtonX, ConfirmButtonY, ConfirmButtonColor,
				ConfirmButtonArcRad, ConfirmButtonFontSize);

		this.add(title);
		this.add(heightLab);
		this.add(height);
		this.add(girthLab);
		this.add(girth);
		this.add(waistLab);
		this.add(waist);
		this.add(hipsLab);
		this.add(hips);
		this.add(bustChestLab);
		this.add(bustChest);
		this.add(inseamLab);
		this.add(inseam);
		this.add(sleeveLengthLab);
		this.add(sleeveLength);
		this.add(neckLab);
		this.add(neck);
		this.add(backLengthLab);
		this.add(backLength);
		this.add(shoeSizeLab);
		this.add(shoeSize);
		this.add(Save);

	}

	public void Update(String name) {
		
		String[] nameSplit = name.split(" ");
		
		String studentFullName = nameSplit[0] + " " + nameSplit[1];
		
		String studentBirthDate = nameSplit[2];

		studentData = StudentsModule.getStudentInfo(studentFullName, studentBirthDate);

		if (!studentData[13].equals("-1"))
			height.setText(studentData[13]);
		if (!studentData[14].equals("-1"))
			girth.setText(studentData[14]);
		if (!studentData[15].equals("-1"))
			waist.setText(studentData[15]);
		if (!studentData[16].equals("-1"))
			hips.setText(studentData[16]);
		if (!studentData[17].equals("-1"))
			bustChest.setText(studentData[17]);
		if (!studentData[18].equals("-1"))
			inseam.setText(studentData[18]);
		if (!studentData[19].equals("-1"))
			sleeveLength.setText(studentData[19]);
		if (!studentData[20].equals("-1"))
			neck.setText(studentData[20]);
		if (!studentData[21].equals("-1"))
			backLength.setText(studentData[21]);
		if (!studentData[22].equals("-1"))
			shoeSize.setText(studentData[22]);
		
		this.remove(Save);
		
		Save = new Button("Save", null, ConfirmButtonD, ConfirmButtonX, ConfirmButtonY, ConfirmButtonColor,
				ConfirmButtonArcRad, ConfirmButtonFontSize);

		Save.addActionListener((e -> {

			if (height.getText().split(" ").length > 0 && height.getText().split(" ")[0] != null
					&& !height.getText().split(" ")[0].isBlank() && height.getText().split(" ").length < 2) {

				try {

					String accBal = height.getText();
					Double accBalance = Double.parseDouble(accBal);
					studentData[13] = height.getText();

				} catch (Exception ex) {

					PromptsService.FailurePrompt("Please use only Numbers for measurements");
					return;

				}

			} else {

				studentData[13] = "-1";

			}
			if (girth.getText().split(" ").length > 0 && girth.getText().split(" ")[0] != null
					&& !girth.getText().split(" ")[0].isBlank() && girth.getText().split(" ").length < 2) {

				try {

					String accBal = girth.getText();
					Double accBalance = Double.parseDouble(accBal);
					studentData[14] = girth.getText();

				} catch (Exception ex) {

					PromptsService.FailurePrompt("Please use only Numbers for measurements");
					return;

				}

			} else {

				studentData[14] = "-1";

			}
			if (waist.getText().split(" ").length > 0 && waist.getText().split(" ")[0] != null
					&& !waist.getText().split(" ")[0].isBlank() && waist.getText().split(" ").length < 2) {
				try {
					String val = waist.getText();
					Double.parseDouble(val);
					studentData[15] = val;
				} catch (Exception ex) {
					PromptsService.FailurePrompt("Please use only numbers for measurements.");
					return;
				}
			} else {
				studentData[15] = "-1";
			}

			if (hips.getText().split(" ").length > 0 && hips.getText().split(" ")[0] != null
					&& !hips.getText().split(" ")[0].isBlank() && hips.getText().split(" ").length < 2) {
				try {
					String val = hips.getText();
					Double.parseDouble(val);
					studentData[16] = val;
				} catch (Exception ex) {
					PromptsService.FailurePrompt("Please use only numbers for measurements.");
					return;
				}
			} else {
				studentData[16] = "-1";
			}

			if (bustChest.getText().split(" ").length > 0 && bustChest.getText().split(" ")[0] != null
					&& !bustChest.getText().split(" ")[0].isBlank() && bustChest.getText().split(" ").length < 2) {
				try {
					String val = bustChest.getText();
					Double.parseDouble(val);
					studentData[17] = val;
				} catch (Exception ex) {
					PromptsService.FailurePrompt("Please use only numbers for measurements.");
					return;
				}
			} else {
				studentData[17] = "-1";
			}

			if (inseam.getText().split(" ").length > 0 && inseam.getText().split(" ")[0] != null
					&& !inseam.getText().split(" ")[0].isBlank() && inseam.getText().split(" ").length < 2) {
				try {
					String val = inseam.getText();
					Double.parseDouble(val);
					studentData[18] = val;
				} catch (Exception ex) {
					PromptsService.FailurePrompt("Please use only numbers for measurements.");
					return;
				}
			} else {
				studentData[18] = "-1";
			}

			if (sleeveLength.getText().split(" ").length > 0 && sleeveLength.getText().split(" ")[0] != null
					&& !sleeveLength.getText().split(" ")[0].isBlank()
					&& sleeveLength.getText().split(" ").length < 2) {
				try {
					String val = sleeveLength.getText();
					Double.parseDouble(val);
					studentData[19] = val;
				} catch (Exception ex) {
					PromptsService.FailurePrompt("Please use only numbers for measurements.");
					return;
				}
			} else {
				studentData[19] = "-1";
			}

			if (neck.getText().split(" ").length > 0 && neck.getText().split(" ")[0] != null
					&& !neck.getText().split(" ")[0].isBlank() && neck.getText().split(" ").length < 2) {
				try {
					String val = neck.getText();
					Double.parseDouble(val);
					studentData[20] = val;
				} catch (Exception ex) {
					PromptsService.FailurePrompt("Please use only numbers for measurements.");
					return;
				}
			} else {
				studentData[20] = "-1";
			}

			if (backLength.getText().split(" ").length > 0 && backLength.getText().split(" ")[0] != null
					&& !backLength.getText().split(" ")[0].isBlank() && backLength.getText().split(" ").length < 2) {
				try {
					String val = backLength.getText();
					Double.parseDouble(val);
					studentData[21] = val;
				} catch (Exception ex) {
					PromptsService.FailurePrompt("Please use only numbers for measurements.");
					return;
				}
			} else {
				studentData[21] = "-1";
			}

			if (shoeSize.getText().split(" ").length > 0 && shoeSize.getText().split(" ")[0] != null
					&& !shoeSize.getText().split(" ")[0].isBlank() && shoeSize.getText().split(" ").length < 2) {
				try {
					String val = shoeSize.getText();
					Double.parseDouble(val);
					studentData[22] = val;
				} catch (Exception ex) {
					PromptsService.FailurePrompt("Please use only numbers for measurements.");
					return;
				}
			} else {
				studentData[22] = "-1";
			}

			StudentsModule.writeToFile(studentFullName, studentBirthDate, studentData);
			Main.Router.showView(ViewConstants.INDIVIDUAL_STUDENT_VIEW);

		}));
		
		this.add(Save);

		this.revalidate();
		this.repaint();

	}

}
