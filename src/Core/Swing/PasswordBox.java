package Core.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;

public class PasswordBox extends RatioComponent<JPasswordField> {
	
	String placeholder = "";
	
	public PasswordBox ( UIComponent<?> parent ) {
		
		super(parent);
		
		this.getComponent().addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained ( FocusEvent e ) {
				
				// When the user clicks into the box
				onFocusGain();
				
			}
			
			@Override
			public void focusLost ( FocusEvent e ) {
				
				// When the user clicks out of the box
				onFocusLost();
				
			}
			
		});
		
	}
	
	public void onFocusGain () {
		
		if ( Arrays.equals(this.getComponent().getPassword(), (placeholder.toCharArray())) ) {
			
			this.getComponent().setText(""); // Clear the placeholder text
			this.getComponent().setForeground(Color.black); // Switch to default text color
			
		}
		
	}
	
	public void onFocusLost () {
		
		if ( this.getComponent().getPassword().length == 0) {
			
			this.getComponent().setText(placeholder); // Restore the placeholder text
			this.getComponent().setForeground(Color.gray); // Switch back to gray color
			
		}
		
	}
	
	@Override
	public void stylize () {
	
	}
	
	@Override
	protected JPasswordField createCustomObject () {
		
		return new JPasswordField() {
			
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
	
	}
	
	
}
