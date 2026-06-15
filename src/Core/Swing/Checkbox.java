package Core.Swing;

import javax.swing.*;
import java.awt.*;

public class Checkbox extends RatioComponent<JCheckBox> {
	
	public Checkbox ( UIComponent<?> parent ) {
	
		super(parent);
	
	}
	
	public Checkbox ( UIComponent<?> parent, int RefX, int RefY, int RefWidth, int RefHeight ) {
		
		super(parent, RefX, RefY, RefWidth, RefHeight);
		
	}
	
	public void setText ( String text ) {
		
		this.getComponent().setText(text);
		
	}
	
	@Override
	public void stylize () {
		
		//TODO later
	
	}
	
	@Override
	protected JCheckBox createCustomObject () {
		
		return new JCheckBox() {
			
			@Override
			public void paintComponent ( Graphics g ) {
				
				super.paintComponent(g);
				buildComponent(g);
				
			}
			
			@Override
			public boolean contains ( int x, int y ) {
				
				return isWithinBounds(x, y);
				
			}
			
		};
	}
	
	@Override
	public void buildComponent ( Graphics g ) {
		
		//TODO Later
	
	}

	public String getText () {
		
		return this.getComponent().getText();
		
	}
	
	public boolean getValue() {
		
		return this.getComponent().isSelected();
		
	}
	
	public boolean getSelected () {
		
		return this.getComponent().isSelected();
		
	}
	
}
