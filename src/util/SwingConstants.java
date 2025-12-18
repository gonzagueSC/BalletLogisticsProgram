package util;

import java.awt.Color;
import java.awt.Dimension;

import swingConstants.*;

public class SwingConstants {

	// SWING DIMENSIONS
	
	//SUBSECTION: SHOW CENTER ADD BUTTON
	public static final int ADDBUTTONX = 900;
	public static final int ADDBUTTONY = 150;
	public static final int ADDBUTTONWIDTH = 270;
	public static final int ADDBUTTONHEIGHT = 70;
	public static final int ADDBUTTONFONTSIZE = 18;
	public static final int ADDBUTTONARCRAD = 50;
	
	//SUBSECTION: SHOWS TAB SYSTEM SIZES
	public static final int TABSTARTX = 10;
	public static final int TABY = 150;
	public static final int TABWIDTH = 200;
	public static final int TABHEIGHT = 70;
	public static final int TABFONTSIZE = 18;
	public static final int TABARCRAD = 50;
	public static final int TABGAP = 25;
	
	//SUBSECTION BOTTOMADD
	
	public static final int BOTTOMADDWIDTH = 190;
	public static final int BOTTOMADDHEIGHT = 50;
	public static final int BOTTOMADDGAP = 15;
	public static final int BOTTOMADDLEFTX = 10;
	public static final int BOTTOMADDRIGHTX = BOTTOMADDLEFTX + BOTTOMADDWIDTH + BOTTOMADDGAP;
	public static final Dimension BOTTOMADDDim = new Dimension(BOTTOMADDWIDTH, BOTTOMADDHEIGHT);
	public static final int BOTTOMADDY = 510;
	public static final int BOTTOMADDARCRAD = 50;
	public static final int BOTTOMADDFONTSIZE = 18;
	
	

	//SUBSECTION: PROMPT
	public static final int PROMPTWIDTH = 1000;
	public static final int PROMPTHEIGHT = 150;
	public static final int PROMPTFONTSIZE = 40;
	public static final int PROMPTX = (CoreVariables.SCREENWIDTH - PROMPTWIDTH)/2;
	
	//SUBSECTION: TITLE LABEL
	public static final int TITLELABELX = 100;
	public static final int TITLELABELY = 25;
	public static final int TITLELABELWIDTH = CoreVariables.SCREENWIDTH - 2 * TITLELABELX;
	public static final int TITLELABELHEIGHT = 100;
	public static final int TITLELABELFONTSIZE = 70;
	
	//SUBSECTION: CHANGE BUTTON - BUTTON FOR CHANGING TO ADMIN
	public static final Dimension ChangeButtonD = new Dimension(100, 70);
	public static final int ChangeButtonX = 1070;
	public static final int LeftChangeButtonX = 30;
	public static final int ChangeButtonY = 30;
	public static final int ChangeButtonArcRad = 70;
	public static final int ChangeButtonFontSize = 18;
	
	//SUBSECTION: BACK BUTTON - BUTTON FOR CHANGING SCREENS (GENERALLY BACKWARDS)
	public static final Dimension BACKBUTTOND = new Dimension(100, 70);
	public static final int BACKBUTTONX = 30;
	public static final int BACKBUTTONY = 30;
	public static final int BACKBUTTONARCRAD = 70;
	public static final int BACKBUTTONFONTSIZE = 18;
	
	//SUBSECTION: CONFIRM BUTTON - BUTTON AT THE BOTTOM OF THE SCREEN TO CONFIRM. USED OFTEN
	public static final int ConfirmButtonX = 450;
	public static final int ConfirmButtonY = 650;
	public static final Dimension ConfirmButtonD = new Dimension(CoreVariables.SCREENWIDTH - 2 * ConfirmButtonX, 100);
	public static final int ConfirmButtonFontSize = 27;
	public static final int ConfirmButtonArcRad = 85;
	
	//MINI SECTION: PROMPT
	public static final int RegPromptY = 410;	
	
	//SUBSECTION: LOGINSIGNUP VARIABLES - THE TWO TEXT INPUTS ALONG WITH THEIR LABELS IN THE BASIC SET-UP
	public static final int LOGINSIGNUPX = 350;
	public static final int LOGINSIGNUPWIDTH = 500;
	public static final int LOGINSIGNUPINPUTHEIGHT = 75;
	public static final int LOGINSIGNUPINPUTFONTSIZE = 25;
	public static final int LOGINSIGNUPINPUTARCRAD = 50;
	public static final int LOGINSIGNUPLABELHEIGHT = 50;
	public static final int LOGINSIGNUPLABELFONTSIZE = 30;
	public static final int LOGINSIGNUPLABELOFFSET = 55;
	public static final int LOGINSIGNUPTOPINPUTY = 250;
	public static final int LOGINSIGNUPTOPLABELY = LOGINSIGNUPTOPINPUTY - LOGINSIGNUPLABELOFFSET;
	public static final int LOGINSIGNUPBOTTOMINPUTY = 420;
	public static final int LOGINSIGNUPBOTTOMLABELY = LOGINSIGNUPBOTTOMINPUTY - LOGINSIGNUPLABELOFFSET;
	
	//SUBSECTION: BUTTON PANEL VARIABLES - PANELS FOR BUTTONS WHERE WE HAVE VARYING NUMBERS OF BUTTONS - USED OFTEN

	public static final Dimension BUTTONPANELBUTTONDIM = new Dimension(400, 100);
	public static final int BUTTONPANELLEFTX = 175;
	public static final int BUTTONPANELRIGHTX = 625;
	public static final int BUTTONPANELCENTERX = (CoreVariables.SCREENWIDTH - BUTTONPANELBUTTONDIM.width)/2;
	public static final int BUTTONPANELARCRAD = 50;
	public static final int BUTTONPANELFONTSIZE = 20;
	
		//SUB SUBSECTION: BUTTON PANEL FOR 3 OR 4 BUTTONS
	public static final int BUTTONFORM1STARTY = 250;
	public static final int BUTTONFORM1GAP = 150;
	
		//SUB SUBSECTION: BUTTON PANEL FOR 5 OR 6 BUTTONS
	public static final int BUTTONFORM2STARTY = 250;
	public static final int BUTTONFORM2GAP = 150;
	
	//SUBSECTION: INPUT PANEL VARIABLES - PANELS FOR MULTIPLE INPUTS - USED OFTEN
	
	public static final int INPUTPANELINPUTWIDTH = 400;
	public static final int INPUTPANELLEFTX = 175;
	public static final int INPUTPANELRIGHTX = 625;
	public static final int INPUTPANELCENTERX = (CoreVariables.SCREENWIDTH - INPUTPANELINPUTWIDTH)/2;
	public static final int INPUTPANELLABELHEIGHT = 50;
	public static final int INPUTPANELPROMPTY = 510;
	
		//SUB SUBSECTION: INPUT PANELS WITH 1 OR 2 INPUT
	public static final int INPUTFORM1ARCRAD = 40;
	public static final int INPUTFORM1INPUTFONTSIZE = 35;
	public static final int INPUTFORM1INPUTHEIGHT = 100;
	public static final int INPUTFORM1LABELOFFSET = 80;
	public static final int INPUTFORM1STARTY = 290;
	
		//SUB SUBSECTION: INPUT PANELS WITH 3 OR 4 INPUTS
	public static final int INPUTFORM2ARCRAD = 38;
	public static final int INPUTFORM2INPUTFONTSIZE = 30;
	public static final int INPUTFORM2INPUTHEIGHT = 75;
	public static final int INPUTFORM2LABELOFFSET = 45;
	public static final int INPUTFORM2STARTY = 290;
	public static final int INPUTFORM2GAP = 150;
		
		//SUB SUBSECTION: INPUT PANELS WITH 5 OR 6 INPUTS
	public static final int INPUTFORM3ARCRAD = 38;
	public static final int INPUTFORM3INPUTFONTSIZE = 30;
	public static final int INPUTFORM3INPUTHEIGHT = 75;
	public static final int INPUTFORM3LABELOFFSET = 45;
	public static final int INPUTFORM3STARTY = 175;
	public static final int INPUTFORM3GAP = 150;
	
		//SUB SUBSECTION: INPUT PANEL WITH 7 OR 8 INPUTS
	public static final int INPUTFORM4ARCRAD = 36;
	public static final int INPUTFORM4INPUTFONTSIZE = 26;
	public static final int INPUTFORM4INPUTHEIGHT = 65;
	public static final int INPUTFORM4LABELOFFSET = 35;
	public static final int INPUTFORM4STARTY = 290;
	public static final int INPUTFORM4GAP = 120;
	
		//SUB SUBSECTION: INPUT PANEL WITH 9 OR 10 INPUTS
	public static final int INPUTFORM5ARCRAD = 36;
	public static final int INPUTFORM5INPUTFONTSIZE = 18;
	public static final int INPUTFORM5INPUTHEIGHT = 47;
	public static final int INPUTFORM5LABELOFFSET = 40;
	public static final int INPUTFORM5STARTY = 170;
	public static final int INPUTFORM5GAP = 87;
	public static final int INPUTFORM5PROMPTY = 530;
	
	// COLOR VARIABLES
	public static final Color MainGray = Color.decode("#7A4F69");
	public static final Color PromptMain = new Color(136, 132, 255, 220).darker();
	public static final Color MainPurple = Color.decode("#7A4F69");
	public static final Color SecondaryPurple = Color.decode("#8884FF");
	public static final Color TerciaryColor = Color.decode("#D7BCE8");
	public static final Color ConfirmButtonColor = Color.decode("#E8CEE4");
	public static final Color RedColor = Color.decode("#FF331F");
	public static final Color GreenColor = Color.decode("#0CCA4A");
	public static final Color TextColor = Color.decode("#DBFCFF");
	public static final Color TransparentBackground = new Color(0, 0, 0, 0);

	// STUDENT DIRECTORY VARIABLES
	public static final int Columns = 3;
	public static final int Rows = 5;
	public static final int PageTotal = Columns * Rows;
	public static final int PaddingX = 30;
	public static final int PaddingY = 40;
	public static final int StartY = 220;
	public static final int MarginX = 50;
	public static final int MarginY = 150;
	public static final int StudentArcRad = 40;
	
}
