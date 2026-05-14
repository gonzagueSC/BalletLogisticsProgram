package Core.Databases;

import Core.Utilities.EntityField;
import Core.Utilities.TypeController;
import Core.Utilities.AppWarning;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.*;

import static Core.Utilities.DataConstants.*;

public abstract class SimpleRegistry extends DBEntity {
	
	protected static final String FileType = COMPLEX_JSON;
	
	protected final EntityField[] ENTITY_FIELDS;
	
	protected final String format;
	
	protected Map<EntityField, String> currentObjectValues = new HashMap<>();
	
	public SimpleRegistry ( File file, EntityField[] fields, String format ) throws IllegalClassFormatException,
		   IOException, AppWarning {
		
		DB.fileStartup(file, FileType);
		
		super(file);
		
		ENTITY_FIELDS = fields;
		
		this.format = format;
		
	}
	
	public String getNextID ( String format ) {
		
		if ( this.complexEntityData.isEmpty() ) {
			
			return String.format(format, 1);
			
		} else {
			
			HashMap<Integer, String> IDs = new HashMap<>();
			
			int maxID = 0;
			
			for ( String cast : this.complexEntityData.keySet() ) {
				
				int ID = Integer.parseInt(cast.substring(5));
				
				maxID = Math.max(maxID, ID);
				
				IDs.put(ID, cast);
				
			}
			
			return getIDValue(format, IDs, maxID);
			
		}
		
	}
	
	public synchronized String createNewEntry () {
		
		Map<String, String> allFields = new HashMap<>();
		
		for ( EntityField field : ENTITY_FIELDS ) {
			
			allFields.put(field.getName(), null);
			
		}
		
		String ID = getNextID(format);
		
		this.complexEntityData.put(ID, allFields);
		
		return ID;
		
	}
	
	public synchronized String createNewEntry(String ID) throws AppWarning, IOException {
		
		Map<String, String> allFields = new HashMap<>();
		
		for ( EntityField field : ENTITY_FIELDS ) {
			
			allFields.put(field.getName(), null);
			
		}
		
		this.complexEntityData.put(ID, allFields);
		
		return ID;
		
	}
	
	public synchronized String createNewEntry ( Map<EntityField, String> presetFields ) throws IOException,
		   AppWarning {
		
		String ID = createNewEntry();
		
		this.changeRegister(ID, presetFields);
		
		return ID;
		
	}
	
	public synchronized String createNewEntry ( String ID, Map<EntityField, String> presetFields ) throws IOException,
		   AppWarning {
		
		this.createNewEntry(ID);
		
		this.changeRegister(ID, presetFields);
		
		return ID;
		
	}
	
	public Map<String, Map<String, String>> getDataMap () {
		
		return this.complexEntityData;
		
	}
	
	public List<String> getAllOfField ( EntityField field ) {
		
		List<String> keys = new ArrayList<>(getDataMap().keySet());
		
		List<String> names = new ArrayList<>();
		
		for ( String key : keys ) {
			
			names.add(this.getFieldValue(key, field));
			
		}
		
		return names;
		
	}
	
	public Map<EntityField, String> getMapForID ( String ID ) {
		
		currentObjectValues = new HashMap<>();
		
		for ( EntityField field : ENTITY_FIELDS ) {
			
			currentObjectValues.put(field, this.complexEntityData.get(ID).get(field.getName()));
			
		}
		
		return this.currentObjectValues;
		
	}
	
	public Map<String, String> getRawMapForID ( String ID ) throws AppWarning {
		
		if ( !this.complexEntityData.containsKey(ID) ) throw new AppWarning("No such ID exists");
		
		return this.complexEntityData.get(ID);
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getFieldValue ( String ID, EntityField field ) {
		
		String rawValue = getMapForID(ID).get(field);
		
		if ( rawValue == null ) return null;
		
		Class<T> targetType = (Class<T>) field.getFieldType();
		
		return TypeController.getValue(rawValue, targetType);
		
	}
	
	@SuppressWarnings("unused")
	public synchronized void changeFullRegister ( String ID, Map<EntityField, String> newRegister ) throws IOException, AppWarning {
		
		if ( !this.complexEntityData.containsKey(ID) )
			
			throw new AppWarning("ID does not exist within the registry");
		
		this.changeRegister(ID, newRegister);
		
	}
	
	public synchronized void changeRegister ( String ID, Map<EntityField, String> newRegisterValues ) throws IOException, AppWarning {
		
		for ( EntityField field : ENTITY_FIELDS ) {
			
			if ( newRegisterValues.containsKey(field) ) {
				
				changeRegisterValue(ID, field, newRegisterValues.get(field), true);
				
			}
			
		}
		
		this.save();
		
	}
	
	public synchronized void changeRegisterValue ( String ID, EntityField field, Object newValue, boolean bulk ) throws IOException, AppWarning {
		
		if ( field == null ) throw new IllegalArgumentException("Cannot save a null field");
		if ( field.isImmutable() && getFieldValue(ID, field) != null )
			throw new IllegalArgumentException("Cannot save over " + "and immutable value " + "if " + "previously " + "assigned a value");
		if ( field.isNotNull() && newValue == null && field.getDefaultValue() == null)
			throw new AppWarning("Cannot assign a null value to a field defined as Not Null");
		
		String parsedValue = TypeController.checkParseability(newValue, field.getFieldType());
		
		if ( parsedValue != null ) {
			
			this.complexEntityData.get(ID).put(field.getName(), parsedValue);
			
		} else {
			
			throw new IllegalArgumentException("Argument was not of the same type as the field");
			
		}
		
		if ( !bulk ) this.save();
		
	}
	
	public synchronized void deleteRegister ( String ID ) {
		
		this.complexEntityData.remove(ID);
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException, AppWarning {
		
		setDataType(FileType);
		
	}
	
	public void ValidateID ( String ID ) throws AppWarning {
		
		if ( !complexEntityData.containsKey(ID) ) throw new AppWarning("ID does not exist in ListRegistry");
		
	}
	
	/**
	 * gets the ID of the first Register that matches the givenValues
	 *
	 * @param givenValues the values to match against
	 * @return the ID of the first Register that matches the givenValues, or "-1" if no match is found
	 */
	public String getID ( Map<EntityField, String> givenValues ) throws AppWarning {
		
		ArrayList<String> baseKeys = new ArrayList<>(this.complexEntityData.keySet());
		
		ArrayList<EntityField> givenKeys = new ArrayList<>(givenValues.keySet());
		
		for ( String key : baseKeys ) {
			
			boolean allMatch = true;
			
			Map<EntityField, String> registerKeys = this.getMapForID(key);
			
			for ( EntityField givenKey : givenKeys ) {
				
				if ( !registerKeys.containsKey(givenKey) || !this.getFieldValue(key, givenKey).equals(givenValues.get(givenKey)) ) {
					
					allMatch = false;
					break;
					
				}
				
			}
			
			if ( allMatch ) {
				
				return key;
				
			}
			
			
		}
		
		return null;
		
	}
	
	public String getID ( EntityField field, String value ) throws AppWarning {
		
		HashMap<EntityField, String> givenValues = new HashMap<>();
		
		givenValues.put(field, value);
		
		return this.getID(givenValues);
		
	}
	
	public boolean checkForRegister ( EntityField field, String value ) {
		
		for ( String key : this.complexEntityData.keySet() ) {
			
			String foundValue = this.getFieldValue(key, field);
			
			if ( foundValue != null && foundValue.equals(value) ) return true;
			
		}
		
		return false;
		
	}
	
	public void swapRegisters ( String ID1, String ID2 ) throws AppWarning, IOException {
		
		HashMap<String, String> temp = new HashMap<>(this.complexEntityData.get(ID1));
		
		this.complexEntityData.put(ID1, this.complexEntityData.get(ID2));
		this.complexEntityData.put(ID2, temp);
		
		this.save();
		
	}
	
	public int getNumberFromID ( String ID ) {
		
		String[] parts = ID.split("\\D+");
		
		if ( parts.length == 0 ) return -1;
		
		return Integer.parseInt(parts[0]);
		
	}
	
	public String getIDFromNumber(int ID) {
		
		return String.format(format, ID);
		
	}
	
	public void swap(String ID, int change) throws AppWarning, IOException {
		
		ValidateID(ID);
		
		int IDValue = getNumberFromID(ID);
		
		if ( IDValue == 0 ) return;
		
		String ID2 = String.format(format, IDValue + change);
		
		if ( !this.complexEntityData.containsKey(ID2) ) {
			
			this.complexEntityData.put(ID2, this.getRawMapForID(ID));
			
			this.deleteRegister(ID);
			
			return;
			
		}
		
		swapRegisters(ID, ID2);
		
	}
	
	public void swapUp ( String ID ) throws AppWarning, IOException {
		
		swap(ID, -1);
		
	}
	
	public void swapDown ( String ID ) throws AppWarning, IOException {
		
		swap(ID, 1);
		
	}
	
	public boolean hasPriority( String IDToCheck, String ReferenceID) throws AppWarning {
	
		ValidateID(IDToCheck);
		ValidateID(ReferenceID);
		
		return (getNumberFromID(IDToCheck) < getNumberFromID(ReferenceID));
	
	}
	
	public LinkedHashMap<String, Map<EntityField, String>> getOrderedData() {
		
		LinkedHashMap<String, Map<EntityField, String>> orderedData = new LinkedHashMap<>();
		
		int currentItem = 1;
		
		for (int i = 0; i < this.complexEntityData.size(); i++) {
			
			if (this.complexEntityData.containsKey(String.format(format, currentItem))) {
				
				orderedData.put(String.format(format, currentItem), this.getMapForID(String.format(format, currentItem)));
				
				currentItem++;
				
			}
			
		}
		
		return orderedData;
	
	}
	
}
