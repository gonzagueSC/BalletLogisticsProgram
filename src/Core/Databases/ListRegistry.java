package Core.Databases;

import Core.Utilities.EntityField;
import Core.Utilities.AppWarning;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ListRegistry extends SimpleRegistry {
	
	public static final EntityField ENTRY_NAME = new EntityField("entry_name", String.class, true);
	
	public ListRegistry ( File file, EntityField[] fields, String format) throws IllegalClassFormatException, IOException, AppWarning {
		
		ArrayList<EntityField> fieldsList = new ArrayList<>(List.of(fields));
		fieldsList.add(ENTRY_NAME);
	
		super (file, fieldsList.toArray(new EntityField[0]), format);
	
	}
	
	@Override
	public synchronized String createNewEntry(String entryName) throws AppWarning, IOException {
	
		String ID = super.createNewEntry();
		this.changeRegisterValue(ID, ENTRY_NAME, entryName , false);
		return ID;
	
	}
	
	public void addEntry(String ID, String key, String value) throws IOException, AppWarning {
		
		ValidateID(ID);
		
		this.complexEntityData.get(ID).put(key, value);
		this.save();
	
	}
	
	public synchronized String createBuiltListEntry( String entryName, Map<String, String> entries ) throws AppWarning, IOException {
	
		String ID = createNewEntry(entryName);
		
		for (String key: entries.keySet()) {
		
			this.addEntry(ID, key, entries.get(key));
		
		}
		
		return ID;
	
	}
	
	public void removeEntry(String ID, String key) throws IOException, AppWarning {
		
		ValidateID(ID);
		
		this.complexEntityData.get(ID).remove(key);
		this.save();
		
	}
	
	@SuppressWarnings("unused")
	public void removeEntry(String ID, String key, String value) throws IOException, AppWarning {
		
		ValidateID(ID);
		
		this.complexEntityData.get(ID).remove(key, value);
		this.save();
		
	}
	
	@SuppressWarnings("unused")
	public List<String> getAllEntries(String ID) throws AppWarning {
	
		ValidateID(ID);
		
		ArrayList<String> keys = new ArrayList<>(this.complexEntityData.keySet());
		
		ArrayList<String> entries = new ArrayList<>();
		
		for (String key: keys) {
		
			for (EntityField field: ENTITY_FIELDS) {
			
				if (!key.equals(field.getName())) {
				
					entries.add(this.getRawMapForID(ID).get(key));
				
				}
			
			}
		
		}
		
		return entries;
	
	}
	
	public String getEntryValue(String ID, String key) {
		
		return this.complexEntityData.get(ID).get(key);
		
	}
	
	public void editEntry(String ID, String key, String newValue) throws AppWarning, IOException {
		
		this.addEntry(ID, key, newValue);
	
	}
	
	@SuppressWarnings("unused")
	public void changeKey(String ID, String oldKey, String newKey) throws AppWarning, IOException {
		
		this.addEntry(ID, newKey, getEntryValue(ID, oldKey));
		this.removeEntry(ID, oldKey);
		
	}
	
}
