package Core.Swing;

import Core.Utilities.AppWarning;
import swingConstants.CoreVariables;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
	
	View currentView;

	public Frame() {
		
		Insets insets = this.getInsets();
		
		this.setSize(CoreVariables.SCREENWIDTH + insets.left + insets.right, CoreVariables.SCREENHEIGHT + insets.top + insets.bottom);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	
	}
	
	public View getCurrentView() {
		
		return currentView;
		
	}
	
	public void changeFrameSize(int width, int height) throws AppWarning {
	
		this.setSize(width, height);
		this.getCurrentView().setSize(width, height);
		this.getCurrentView().Update();
	
	}
	
	public void changeView (View newView) {
	
		this.removeAll();
		
		this.currentView = newView;
		this.add(newView.getComponent());
		
	}

}
