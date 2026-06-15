package Core.Swing;

import javax.swing.*;
import java.awt.*;

public class DropdownMenu extends RatioComponent<JComboBox<String>> {
	
	public DropdownMenu ( UIComponent<?> parent ) {
		
		super(parent);
		
	}
	
	public DropdownMenu ( UIComponent<?> parent, int RefX, int RefY, int RefWidth, int RefHeight ) {
		
		super(parent, RefX, RefY, RefWidth, RefHeight);
		
	}
	
	public void addList ( String[] items ) {
		
		for ( String string : items ) {
			
			this.getComponent().addItem(string);
			
		}
		
	}
	
	@Override
	public void stylize () {
		
		//TODO
		
	}
	
	@Override
	protected JComboBox<String> createCustomObject () {
		
		JComboBox<String> dropdownMenu = new JComboBox<>();
		
		dropdownMenu.setOpaque(true);
		dropdownMenu.setEditable(true);
		dropdownMenu.setRenderer(new CellRenderer());
		
		return dropdownMenu;
		
	}
	
	@Override
	public void buildComponent ( Graphics g ) {
	
		//TODO
	
	}
	
}
