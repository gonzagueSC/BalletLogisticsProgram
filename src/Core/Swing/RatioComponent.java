package Core.Swing;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public abstract class RatioComponent<T extends JComponent> extends UIComponent<T> {
	
	public int REF_X = -1;
	public int REF_Y = -1;
	
	public int REF_WIDTH = -1;
	public int REF_HEIGHT = -1;
	
	public int CALCULATED_X = 0;
	public int CALCULATED_Y = 0;
	
	public int CALCULATED_WIDTH = 0;
	public int CALCULATED_HEIGHT = 0;
	
	public RatioComponent (UIComponent<?> parent ) {
		
		super(parent);
		
	}
	
	public RatioComponent (UIComponent<?> parent, int RefX, int RefY, int RefWidth, int RefHeight ) {
		
		this(parent);
		
		REF_X = RefX;
		REF_Y = RefY;
		
		REF_WIDTH = RefWidth;
		REF_HEIGHT = RefHeight;
		
	}
	
	public void setInteriorPos ( int X, int Y ) {
		
		if ( REF_X > 0 ) {
			
			CALCULATED_X = component.getWidth() * X / REF_X;
			CALCULATED_Y = component.getHeight() * Y / REF_Y;
			
		}
		
	}
	
	public void setInteriorSize ( int width, int height ) {
		
		if ( REF_WIDTH > 0 ) {
			
			CALCULATED_WIDTH = component.getWidth() * width / REF_WIDTH;
			CALCULATED_HEIGHT = component.getHeight() * height / REF_HEIGHT;
			
		}
		
	}
	
	public boolean isWithinBounds(int x, int y) {
	
		return x >= CALCULATED_X &&
			   x <= CALCULATED_WIDTH + CALCULATED_WIDTH &&
			   y >= CALCULATED_Y &&
			   y <= CALCULATED_Y + CALCULATED_HEIGHT;
	
	}
	
}
