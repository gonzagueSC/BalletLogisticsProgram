package util;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class LayoutUtility {
	
	public static void applyUniformMargin(JPanel panel, int margin) {
        // Create the border using the standard BorderFactory
        Border marginBorder = BorderFactory.createEmptyBorder(margin, margin, margin, margin);
        
        // Check if the panel already has a border
        Border oldBorder = panel.getBorder();
        
        if (oldBorder != null) {
            // If it does, combine the old border with the new margin
            panel.setBorder(BorderFactory.createCompoundBorder(marginBorder, oldBorder));
        } else {
            // Otherwise, just apply the margin border
            panel.setBorder(marginBorder);
        }
    }
	
}
