package Core.Databases;

import Core.Utilities.EntityField;
import Core.Utilities.TypeController;
import Core.Utilities.AppWarning;

import java.io.*;
import java.util.*;
import java.lang.instrument.IllegalClassFormatException;

import static Core.Utilities.DataConstants.*;

abstract public class JSONObjectEntity extends DBEntity {
	
	protected static EntityField[] ENTITY_FIELDS = {};
	
	private static final String FileType = JSON_TYPE;
	
	protected Map<EntityField, String> objectValues = new HashMap<>();
	
	public JSONObjectEntity ( String filePath, File folder ) throws IOException, IllegalClassFormatException, AppWarning {
		
		File objectFile = new File(folder, filePath);
		
		DB.fileStartup(objectFile, FileType);
		
		super(objectFile);
		
		
	}
	
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException, AppWarning {
		
		this.setDataType(JSON_TYPE);
		
		setUpFile();
		
	}
	
	public Map<EntityField, String> getObjectValues() {
	
		for (EntityField field: ENTITY_FIELDS) {
			
			objectValues.put(field, this.entityData.get(field.getName()));
			
		}
		
		return  objectValues;
	
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getFieldValue ( EntityField field ) {
		
		String rawValue = this.entityData.get(field.getName());
		
		if (rawValue == null)
			return null;
		
		Class<T> targetType = (Class<T>) field.getFieldType();
		
		return TypeController.getValue(rawValue, targetType);
		
	}
	
	public Class<?> getFieldType ( EntityField field ) {
		
		for (EntityField entityField : ENTITY_FIELDS) {
			
			if (entityField.getName().equals(field.getName())) {
				
				return entityField.getFieldType();
				
			}
			
		}
		
		return null;
		
	}
	
	protected void setUpFile () throws IOException, AppWarning {
		
		for (EntityField field : ENTITY_FIELDS) {
			
			if (!this.entityData.containsKey(field.getName())) {
				
				this.entityData.put(field.getName(), null);
				
			}
			
		}
		
		this.save();
		
	}
	
	@SuppressWarnings("unused")
	public <T> T getFieldValue ( String fieldName, Class<T> targetType ) {
		String rawValue = this.entityData.get(fieldName);
		
		return TypeController.getValue(rawValue, targetType);
		
	}
	
	public void saveFields ( Map<EntityField, String> fieldToSaveMap ) throws IllegalClassFormatException, IOException, AppWarning {
		
		for (EntityField field : ENTITY_FIELDS) {
			
			if (fieldToSaveMap.containsKey(field)) {
				
				saveField(field, fieldToSaveMap.get(field), true);
				
			}
			
		}
		
		this.save();
		
	}
	
	public void saveField ( EntityField field, String valueToSave, boolean bulk ) throws IOException, AppWarning {
		
		if (field == null)
			throw new IllegalArgumentException("Cannot save a null field");
		if ( field.isImmutable() && getFieldValue(field) != null)
			throw new IllegalArgumentException("Cannot save over " + "and immutable value " + "if " + "previously " +
				   "assigned a value");
		
		String parsedValue = TypeController.checkParseability(valueToSave, field.getFieldType());
		
		if (parsedValue != null) {
			
			this.entityData.put(field.getName(), parsedValue);
			
		} else {
		
			throw new IllegalArgumentException("Argument was not of the same type as the field");
		
		}
		
		if (!bulk)
			this.save();
		
	}
	
}
