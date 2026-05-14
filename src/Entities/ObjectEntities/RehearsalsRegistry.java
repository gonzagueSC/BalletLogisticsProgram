package Entities.ObjectEntities;

import Core.Databases.SimpleRegistry;
import Core.Utilities.AppError;
import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static Core.Utilities.DataConstants.*;

public class RehearsalsRegistry extends SimpleRegistry {
	
	public static final String RegistryName = "Rehearsals";
	public static final String format = "REHEARSAL-%04d";
	
	public static final EntityField[] ENTITY_FIELDS = { NAME, DATE, START_TIME, END_TIME, LOCATION, MISC, CAST, TEACHER };
	
	public RehearsalsRegistry ( File Production ) throws IllegalClassFormatException, IOException, AppWarning {
		
		File Registry = new File(Production, RegistryName);
		
		super(Registry, ENTITY_FIELDS, format);
		
	}
	
	public Map<String, Map<String, String>> getRehearsals () {
		
		return this.getDataMap();
		
	}
	
	public void setUpRehearsal ( String rehearsalNameString, String rehearsalDateString,
	                             String rehearsalStartTimeString, String rehearsalEndTimeString ) throws IOException, AppWarning {
		
		Map<EntityField, String> fields = new HashMap<>();
		
		fields.put(NAME, rehearsalNameString);
		fields.put(DATE, rehearsalDateString);
		fields.put(START_TIME, rehearsalStartTimeString);
		fields.put(END_TIME, rehearsalEndTimeString);
		
		this.createNewEntry(fields);
		
	}
	
	public void addRehearsalCast ( String ID, String castToAdd ) throws IOException, AppError, AppWarning {
		
		String currentRoles = this.getFieldValue(ID, CAST);
		
		if ( currentRoles == null ) {
			
			currentRoles = "";
			
		}
		
		if ( currentRoles.contains(castToAdd) )
			throw new AppError(new AppWarning("Tried to add the same role twice"));
		
		currentRoles += ((!currentRoles.isBlank()) ? ", " : "") + castToAdd;
		
		this.changeRegisterValue(ID, CAST, currentRoles, false);
		
	}
	
	public void removeRehearsalCast ( String ID, String castToRemove ) throws IOException, AppError, AppWarning {
		
		String currentRoles = this.getFieldValue(ID, CAST);
		
		if ( !currentRoles.contains(castToRemove) )
			throw new AppError(new AppWarning("Tried to un-cast a non-casted role"));
		
		String regex = "(, " + castToRemove + "|" + castToRemove + ", |" + castToRemove + ")";
		
		currentRoles = currentRoles.replaceFirst(regex, "");
		
		this.changeRegisterValue(ID, CAST, (currentRoles.isBlank()) ? null : currentRoles, false);
		
	}
	
	public void removeRole ( String ID, String roleToRemove ) throws IOException, AppError, AppWarning {
		
		removeRehearsalCast(ID, roleToRemove);
		
	}
	
	public void editRole ( String ID, String oldRole, String newRole ) throws IOException, AppError, AppWarning {
		
		removeRehearsalCast(ID, oldRole);
		addRehearsalCast(ID, newRole);
		
	}
	
	public void changeRole ( String oldRole, String newRole ) throws IOException, AppError, AppWarning {
		
		List<String> keys = new ArrayList<>(getRehearsals().keySet());
		
		for ( String key : keys ) {
			
			editRole(key, oldRole, newRole);
			
		}
		
	}
	
	public void removeRole ( String roleToRemove ) throws IOException, AppError, AppWarning {
		
		List<String> keys = new ArrayList<>(getRehearsals().keySet());
		
		for ( String key : keys ) {
			
			removeRole(key, roleToRemove);
			
		}
		
	}
	
	@SuppressWarnings("unused")
	public List<String> getRehearsalNames () {
		
		return getAllOfField(NAME);
		
	}
	
	public void removeRehearsal ( String ID ) {
		
		this.deleteRegister(ID);
		
	}
	
	public void removeRehearsal ( Map<EntityField, String> rehearsal ) {
		
		List<String> keys = new ArrayList<>(getRehearsals().keySet());
		
		for ( String key : keys ) {
			
			if ( getMapForID(key).equals(rehearsal) ) {
				
				removeRehearsal(key);
				
			}
			
		}
		
	}
	
	public Map<EntityField, String> getRehearsal ( String rehearsalName, String timeStart, String timeEnd,
	                                               String date ) throws AppWarning {
		
		Map<EntityField, String> fields = new HashMap<>();
		
		fields.put(NAME, rehearsalName);
		fields.put(START_TIME, timeStart);
		fields.put(END_TIME, timeEnd);
		fields.put(DATE, date);
		
		String ID = this.getID(fields);
		
		if (ID.equals("-1"))
			throw new AppWarning("Could not find Rehearsal with those specifications");
			
		return getMapForID(ID);
		
	}
	
}
