package Core.Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StandaloneCheckbox extends Checkbox{

	public StandaloneCheckbox(UIComponent<?> parent) {
		
		super(parent);
		
		this.getComponent().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed ( ActionEvent e ) {
				
				JCheckBox checkbox = (JCheckBox) e.getSource();
				
				if(checkbox.isSelected()){
					
					checkbox.setText("true");
					
				} else {
					
					checkbox.setText("false");
					
				}
				
			}
			
		});
		
	}
	
	public StandaloneCheckbox(UIComponent<?> parent, int RefX, int RefY, int RefWidth, int RefHeight) {
		
		super(parent, RefX, RefY, RefWidth, RefHeight);
		
		this.getComponent().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed ( ActionEvent e ) {
				
				JCheckBox checkbox = (JCheckBox) e.getSource();
				
				if(checkbox.isSelected()){
					
					checkbox.setText("true");
					
				} else {
					
					checkbox.setText("false");
					
				}
				
			}
			
		});
		
	}
	
}
