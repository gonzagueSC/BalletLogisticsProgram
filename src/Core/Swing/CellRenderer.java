package Core.Swing;

import javax.swing.*;
import java.awt.*;

import static Core.Utilities.SwingConstants.*;

public class CellRenderer extends JLabel implements ListCellRenderer<String> {
	
	public CellRenderer () {
		
		setOpaque(true);
		
	}
	
	@Override
	public Component getListCellRendererComponent ( JList<? extends String> list, String value, int index,
	                                                boolean isSelected, boolean cellHasFocus ) {
		
		setFont(list.getFont());
		
		setText(value);
		
		if ( isSelected ) {
			
			setBackground(selectedCellBackgroundColor);
			setForeground(selectedCellBackgroundColor);
			
		} else {
			
			setForeground(cellForegroundColor);
			setBackground(cellBackgroundColor);
			
		}
		
		return this;
		
	}
	
}