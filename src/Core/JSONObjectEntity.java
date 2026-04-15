package Core;

import java.io.*;
import java.util.*;
import java.lang.instrument.IllegalClassFormatException;

import static Core.DataConstants.*;

abstract public class JSONObjectEntity extends DBEntity {
	
	protected static EntityField[] ENTITY_FIELDS = {};
	
	private static final String FileType = JSON_TYPE;
	
	public JSONObjectEntity ( String filePath, File folder ) throws IOException, IllegalClassFormatException {
		
		File objectFile = new File(folder, filePath);
		
		DB.fileStartup(objectFile, FileType);
		
		super(objectFile);
		
	}
	
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		
		this.setDataType(JSON_TYPE);
		
		setUpFile();
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getFieldValue ( EntityField field ) {
		
		String rawValue = this.entityData.get(field.getName());
		
		if (rawValue == null)
			return null;
		
		Class<T> targetType = (Class<T>) field.getFieldType();
		
		try {
			if (targetType == Integer.class)
				return (T) Integer.valueOf(rawValue);
			if (targetType == Double.class)
				return (T) Double.valueOf(rawValue);
			if (targetType == Boolean.class)
				return (T) Boolean.valueOf(rawValue);
			
			return (T) rawValue; // Default to String
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public Class<?> getFieldType ( EntityField field ) {
		
		for (EntityField entityField : ENTITY_FIELDS) {
			
			if (entityField.getName().equals(field.getName())) {
				
				return entityField.getFieldType();
				
			}
			
		}
		
		return null;
		
	}
	
	protected void setUpFile () throws IOException {
		
		for (EntityField field : ENTITY_FIELDS) {
			
			if (!this.entityData.containsKey(field.getName())) {
				
				this.entityData.put(field.getName(), null);
				
			}
			
		}
		
		this.save();
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getFieldValue ( String fieldName, Class<T> targetType ) {
		String rawValue = this.entityData.get(fieldName);
		
		if (rawValue == null)
			return null;
		
		try {
			if (targetType == Integer.class)
				return (T) Integer.valueOf(rawValue);
			if (targetType == Double.class)
				return (T) Double.valueOf(rawValue);
			if (targetType == Boolean.class)
				return (T) Boolean.valueOf(rawValue);
			
			return (T) rawValue; // Default to String
		} catch (Exception e) {
			return null;
		}
	}
	
	public void saveFields ( Map<EntityField, String> fieldToSaveMap ) throws IllegalClassFormatException, IOException {
		
		for (EntityField field : ENTITY_FIELDS) {
			
			if (fieldToSaveMap.containsKey(field)) {
				
				saveField(field, fieldToSaveMap.get(field));
				
			}
			
		}
		
	}
	
	public void saveField ( EntityField field, String valueToSave ) {
		
		if (field == null)
			throw new IllegalArgumentException("Cannot save an null field");
		if (!field.isMutable() && getFieldValue(field) != null)
			throw new IllegalArgumentException("Cannot save over " + "and immutable value " + "if " + "previously " +
				   "assigned a value");
		
		if (checkParseability(valueToSave, field.getFieldType())) {
			
			this.entityData.put(field.getName(), valueToSave);
			
		} else {
		
			throw new IllegalArgumentException("Argument was not of the same type as the field");
		
		}
		
	}
	
	private <T> boolean checkParseability ( String value, Class<T> target ) {
		
		try {
			if (target == Integer.class) {
				Integer.parseInt(value);
			} else if (target == Double.class) {
				Double.parseDouble(value);
			} else if (target == Boolean.class) {
				Boolean.parseBoolean(value);
			}
			return true;
		} catch (Exception e) {
			
			return false;
			
		}
		
	}
	
}
