package Entities.TopLevelEntities;

import Core.Databases.SimpleRegistry;
import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;
import Core.Utilities.FilePaths;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static Core.Utilities.DataConstants.*;

public class StudiosRegistry extends SimpleRegistry {
	
	protected static final String format = "STUDIO-%04d";

	public static final EntityField[] ENTITY_FIELDS = { NAME, PRICE };
	
	private static final File STUDIOS_REGISTRY = FilePaths.Studios;
	
	public StudiosRegistry () throws AppWarning, IllegalClassFormatException, IOException {
		
		super(STUDIOS_REGISTRY, ENTITY_FIELDS, format);
		
	}
	
	public String createStudio ( String newStudioName, int rentalRate ) throws AppWarning, IOException {
		
		String ID = createStudio(newStudioName);
		
		this.changeRegisterValue(ID, PRICE, rentalRate, false);
		
		return ID;
		
	}
	
	public String createStudio ( String newStudioName ) throws AppWarning, IOException {
		
		HashMap<EntityField, String> studioData = new HashMap<EntityField, String>();
		
		studioData.put(NAME, newStudioName);
		
		return this.createNewEntry(studioData);
		
	}
	
	public boolean checkForStudio ( String studioSearchName ) {
		
		return checkForRegister(NAME, studioSearchName);
	}
	
	public Map<String, Map<String, String>> getAllStudios () { return this.getDataMap(); }
	
	public List<String> getAllStudioNames () { return this.getAllOfField(NAME); }
	
	public String getStudioID ( String studioSearchName ) throws AppWarning {
		
		return this.getID(NAME, studioSearchName);
	}
	
	public Map<EntityField, String> getStudioData ( String studioSearchName ) throws AppWarning { return getMapForID(getStudioID(studioSearchName)); }
	
	public void deleteStudio ( String studioID ) throws AppWarning, IOException { this.deleteRegister(studioID); }
	
	public void editStudioData ( String ID, EntityField field, String newValue ) throws AppWarning, IOException { this.changeRegisterValue(ID, field, newValue, false); }
	
}
