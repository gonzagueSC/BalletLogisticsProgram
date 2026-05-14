package control;

import java.awt.CardLayout;

import javax.swing.JPanel;

import Core.Databases.EntityMapper;
import databaseAccess.*;
import systemSwing.*;
import swingConstants.*;

public class Main {

	// CONTROL VARIABLES
	public static Panel panel;
	public static Frame frame;
	public static Input input;
	public static CardLayout cardLayout;
	public static JPanel deckPanel;
	public static ViewFactory viewFactory;
	public static Router Router;

	public static void main(String[] args) {

		cardLayout = new CardLayout();
		deckPanel = new NFDeckPanel(cardLayout);

		viewFactory = new ViewFactory();
		Router = new Router(cardLayout, deckPanel, viewFactory);

		deckPanel.setBounds(0, 0, CoreVariables.SCREENWIDTH, CoreVariables.SCREENHEIGHT);

		DatabaseCore.Update();
		
		EntityMapper.getInstance();

		viewFactory.setUp();

		if (SystemSettingsModule.getAllOfSystemDetail(SystemSettingsModule.ADMINS).length > 0) {

			Router.showView(ViewConstants.MAIN_MENU);

		} else {
			
			Router.showDynamicView(ViewConstants.ADMIN_CREATION_VIEW);
			
		}

		frame = new Frame();
		panel = frame.getPanel();

		panel.add(deckPanel);

		frame.setVisible(true);
		

	}

}
