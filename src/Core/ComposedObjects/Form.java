package Core.ComposedObjects;

import Core.Swing.UIComponent;
import Core.Swing.View;
import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;

import java.util.ArrayList;

import static Core.Utilities.SwingConstants.*;

public class Form extends View {
	
	View generalFormView;
	View formContentView;
	int fieldsUsed = 0;
	int totalFields;
	
	public Form ( int fields, UIComponent<?> parent ) {
		
		int[] X = { FORM_PADDING, FORM_WIDTH, FORM_PADDING };
		
		int[] Y = { DEFAULT_TITLE, FORM_HEIGHT, FORM_PADDING };
		
		super(parent, X, Y);
		
		int[] generalFormX = { 1 };
		int[] generalFormY = { FORM_CONTENT_HEIGHT, FORM_SUBMIT_BUTTON_HEIGHT };
		
		generalFormView = new View(this, generalFormX, generalFormY);
		
		totalFields = fields;
		
		int sizeX = 5;
		int sizeY = 5;
		
		boolean notMax = true;
		
		while ( notMax ) {
			
			if ( (sizeX - 1) * sizeY > totalFields ) {
				
				sizeX--;
				
			} else if ( sizeX * (sizeY - 1) > totalFields ) {
				
				sizeY--;
				
			} else {
				
				notMax = false;
				
			}
		}
		
		int[] formX = new int[sizeX];
		
		int[] formY = new int[sizeY];
		
		for (int i = 0; i < Math.max(sizeX, sizeY); i++) {
			
			if (i < sizeX) formX[i] = 1;
			if (i< sizeY) formY[i] = 1;
			
		}
		
		formContentView = new View(this, formX, formY);
		
	}
	
	public void addField ( EntityField field, String value ) throws AppWarning {
		
		if ( fieldsUsed >= totalFields ) throw new AppWarning("Fields limit reached");
		
		int nextX = ++fieldsUsed%formContentView.getXLength();
		int nextY = fieldsUsed/formContentView.getYLength();
		
		FormField formField = new FormField(this, field, value);
		
		formContentView.add(formField, nextX, nextY);
		
	}
	
	public void addField ( EntityField field ) throws AppWarning {
		
		this.addField(field, "");
		
	}
	
}
