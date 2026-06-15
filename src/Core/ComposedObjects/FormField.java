package Core.ComposedObjects;

import static Core.Utilities.SwingConstants.*;

import Core.Swing.*;
import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;
import Core.Utilities.TypeController;

public class FormField extends View {
	
	EntityField field;
	
	Label label;
	TextField textField;
	StandaloneCheckbox checkbox;
	
	public FormField ( UIComponent<?> parent, EntityField field, String value ) throws AppWarning {
		
		int[] X = { FIELD_PADDING, FIELD_WIDTH, FIELD_PADDING };
		int[] Y = { FIELD_PADDING, FIELD_TITLE_HEIGHT, FIELD_HEIGHT, FIELD_PADDING };
		
		super(parent, X, Y);
		
		this.field = field;
		
		label = new Label(this);
		
		label.setText(field.getName());
		
		if ( field.getFieldType() != Boolean.class ) {
			textField = new TextField(this);
			
			textField.setText(value);
			
			this.add(textField, 2, 3);
			
		} else {
		
			checkbox = new StandaloneCheckbox(this);
			
			this.add(checkbox, 2, 3);
		
		}
		
		this.add(label, 2, 2);
	}
	
	public boolean dataIsValid () {
		
		if (textField == null) return TypeController.getValue(String.valueOf(this.checkbox.getValue()), field.getFieldType()) != null;
		
		return TypeController.getValue(this.textField.getText(), field.getFieldType()) != null;
		
	}
	
	public String getValue () {
		
		if (textField == null) return String.valueOf(checkbox.getValue());
		
		return textField.getText();
		
	}
	
	public EntityField getField () {
		
		return field;
		
	}
	
}
