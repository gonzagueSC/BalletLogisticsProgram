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

public class LevelsRegistry extends SimpleRegistry {
	
	protected static final String format = "NAME-%04d";
	
	public static final EntityField[] ENTITY_FIELDS = {
		   NAME, ATTENDANCE_HOURS, REQUIRED_HOURS, COLOR, PRICE, LOCATION
	};
	
	private static final File LEVELS_REGISTRY = FilePaths.Levels;
	
	public LevelsRegistry () throws AppWarning, IllegalClassFormatException, IOException {
		
		super(LEVELS_REGISTRY, ENTITY_FIELDS, format);
		
	}
	
	public String createLevel ( String newLevelName, int hoursPerWeek, int requiredHours, String color,
	                          double classPrice) throws AppWarning, IOException {
		
		HashMap<EntityField, String> levelData = new HashMap<EntityField, String>();
		
		levelData.put(NAME, newLevelName);
		levelData.put(ATTENDANCE_HOURS, String.valueOf(hoursPerWeek));
		levelData.put(REQUIRED_HOURS, String.valueOf(requiredHours));
		levelData.put(COLOR, color);
		levelData.put(PRICE, String.valueOf(classPrice));
		
		return this.createNewEntry(levelData);
		
	}
	
	public void createLevel ( String newLevelName, int hoursPerWeek, int requiredHours, String color,
	                          double classPrice, String studio) throws AppWarning, IOException {
		
		String ID = this.createLevel(newLevelName, hoursPerWeek, requiredHours, color, classPrice);
		
		this.changeRegisterValue(ID, LOCATION, studio, false);
		
	}
	
	public boolean checkForLevel ( String levelSearchName ) { return checkForRegister(NAME, levelSearchName); }
	
	public Map<String, Map<String, String>> getAllLevels () { return this.getDataMap(); }
	
	public List<String> getAllLevelNames () { return this.getAllOfField(NAME); }
	
	public String getLevelID ( String levelSearchName ) throws AppWarning { return this.getID(NAME,
		   levelSearchName); }
	
	public Map<EntityField, String> getLevelData ( String levelSearchName ) throws AppWarning { return getMapForID(getLevelID(levelSearchName)); }
	
	public void deleteLevel ( String levelID ) throws AppWarning, IOException {
		
		int IDValue = this.getNumberFromID(levelID);
		
		if (!this.complexEntityData.containsKey(String.format(format, IDValue + 1))) return;
		
		while (this.complexEntityData.containsKey(String.format(format, IDValue + 1))) {
			
			this.swapDown(String.format(format, IDValue));
			
			IDValue++;
			
		}
		
		this.deleteRegister(levelID);
		
	}
	
	public void editLevelData(String ID, EntityField field, String newValue) throws AppWarning, IOException {
		
		this.changeRegisterValue(ID, field, newValue, false);
		
	}
	
}
