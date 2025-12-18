package control;

import java.awt.*;

import javax.swing.JPanel;

import swingConstants.ViewConstants;

public class Router {

	private final CardLayout cardLayout;
	private final JPanel deckPanel;
	private final ViewFactory viewFactory;

	public Router(CardLayout cardLayout, JPanel deckPanel, ViewFactory viewFactory) {

		this.cardLayout = cardLayout;
		this.deckPanel = deckPanel;
		this.viewFactory = viewFactory;

	}

	public void showView(String viewName) {
		
		viewFactory.LoadView(viewName);

	}

	public void showDynamicView(String viewName) {
		
		viewFactory.loadDynamicView(viewName);

	}

	public void showDynamicView(String viewName, String param1) {
		
		viewFactory.loadDynamicView(viewName, param1);

	}

	public void showDynamicView(String viewName, String param1, String param2) {
		
		viewFactory.loadDynamicView(viewName, param1, param2);

	}

	public void showDynamicView(String viewName, String param1, String param2, String param3) {
		
		viewFactory.loadDynamicView(viewName, param1, param2, param3);

	}
	
	public void showStudentDirectoryView(int page, String[] filters) {
		
		viewFactory.loadStudentDirectory(page, filters);
		
	}

	public void displayView(String viewName) {

		cardLayout.show(deckPanel, viewName);

	}

	public void setViewToPanel(String viewName, Object View) {

		deckPanel.add(viewName, (Component) View);

	}
	
	public void showLoadingScreen() {
		
		cardLayout.show(deckPanel, ViewConstants.LOADING_SCREEN);
		
	}

}
