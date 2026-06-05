package Core.Swing;

import Core.Utilities.Action;
import Core.Utilities.ButtonRouter;

import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;

public class Button extends RatioComponent<JButton> {
	
	private Object payload;
	
	private Action action;
	
	public Button ( UIComponent<?> parent ) {
		
		super(parent);
		
	}
	
	public Button ( UIComponent<?> parent, int RefX, int RefY, int RefWidth, int RefHeight ) {
		
		super(parent, RefX, RefY, RefWidth, RefHeight);
		
	}
	
	@Override
	public void stylize () {
		
		//TODO soon
		
	}
	
	public void setText ( String text ) {
		
		getComponent().setText(text);
		
	}
	
	public void setAction ( Object payload, Action action ) {
		
		this.payload = payload;
		this.action = action;
		
		for ( ActionListener actionListener : component.getActionListeners() ) {
			
			component.removeActionListener(actionListener);
			
		}
		
		this.component.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed ( ActionEvent e ) {
				
				ButtonRouter.directButton(payload, action);
			
			}
		});
		
	}
	
	@Override
	protected JButton createCustomObject () {
		
		return new JButton() {
			
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
		
		//TODO soon
		
	}
	
}
