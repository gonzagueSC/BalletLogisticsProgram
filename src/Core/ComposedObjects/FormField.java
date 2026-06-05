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
	
	public FormField( UIComponent<?> parent , EntityField field, String value ) throws AppWarning {
	
		int[] X = {FIELD_PADDING, FIELD_WIDTH, FIELD_PADDING};
		int[] Y = {FIELD_PADDING, FIELD_TITLE_HEIGHT, FIELD_HEIGHT, FIELD_PADDING};
		
		super (parent, X, Y);
		
		this.field = field;
		
		label = new Label(this);
		
		label.setText(field.getName());
		
		textField = new TextField(this);
		
		textField.setText(value);
		
		this.add(label, 2, 2);
		this.add(textField, 2, 3);
	
	}
	
	public boolean dataIsValid() {
		
		return TypeController.getValue(this.textField.getText(), field.getFieldType()) != null;
	
	}
	
	public String getValue() {
		
		return textField.getText();
		
	}
	
	public EntityField getField() {
		
		return field;
		
	}
	
}
