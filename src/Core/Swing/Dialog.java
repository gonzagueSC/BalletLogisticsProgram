package Core.Swing;

import javax.swing.*;
import java.awt.*;

import static util.SwingConstants.TransparentBackground;

public class Dialog {
	
	protected final JDialog dialog;
	
	public Dialog(UIComponent<?> parent, String title, UIComponent<?> content) {
		
		Window parentWindow = SwingUtilities.getWindowAncestor(parent.getComponent());
		
		this.dialog = new JDialog(parentWindow, title, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		
		this.dialog.setContentPane((Container) content.getComponent());
		this.dialog.pack();
		this.dialog.setLocationRelativeTo(parentWindow);
		
		stylize();
	
	}

	public void stylize () {
		
		dialog.setUndecorated(true);
		dialog.setBackground(TransparentBackground);
		dialog.getRootPane().setBackground(TransparentBackground);
	
	}
	
	public void show() {
		
		dialog.setVisible(true);
		
	}

}
