package Core.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TextField extends RatioComponent<JTextField> {
	
	String placeholder = "";
	
	public TextField ( UIComponent<?> parent ) {
		
		super(parent);
		
		this.getComponent().addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained( FocusEvent e) {
				
				// When the user clicks into the box
				onFocusGain();
				
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				
				// When the user clicks out of the box
				onFocusLost();
				
			}
			
		});
		
	}
	
	@Override
	public void stylize () {
	
	}
	
	@Override
	protected JTextField createCustomObject () {
		
		return new JTextField() {
			
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
	
	public void setPlaceholder ( String string ) {
		
		this.placeholder = string;
		onFocusGain();
		onFocusLost();
		
	}
	
	public void setText (String text) {
		
		this.getComponent().requestFocus();
		
		onFocusLost();
		
		this.getComponent().setText(text);
		
	}
	
	public String getText () {
		
		return this.getComponent().getText();
		
	}
	
	public void onFocusGain () {
		
		if ( this.getComponent().getText().equals(placeholder) ) {
			
			this.getComponent().setText(""); // Clear the placeholder text
			this.getComponent().setForeground(Color.black); // Switch to default text color
			
		}
		
	}
	
	public void onFocusLost () {
		
		if ( this.getComponent().getText().isBlank() ) {
			
			this.getComponent().setText(placeholder); // Restore the placeholder text
			this.getComponent().setForeground(Color.gray); // Switch back to gray color
			
		}
		
	}
	
}
