package Core.Swing;

import javax.swing.*;
import java.awt.*;

public class Label extends RatioComponent<JLabel> {
	
	public Label ( UIComponent<?> parent ) {
		
		super(parent);
		
	}
	
	public Label ( UIComponent<?> parent, int RefX, int RefY, int RefWidth, int RefHeight ) {
		
		super(parent, RefX, RefY, RefWidth, RefHeight);
		
	}
	
	public void setText ( String text ) {
		
		getComponent().setText(text);
		
	}
	
	@Override
	public void stylize () {
		
		//TODO add final touches
		
	}
	
	@Override
	protected JLabel createCustomObject () {
		
		return new JLabel() {
			
			@Override
			public void paintComponent ( Graphics g ) {
				
				super.paintComponent(g);
				buildComponent(g);
				
			}
			
			@Override
			public boolean contains(int x, int y) {
				
				return isWithinBounds(x, y);
				
			}
			
		};
		
	}
	
	public void buildComponent ( Graphics g ) {
		
		//TODO build the painting mechanic
		
	}
	
}
