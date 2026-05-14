package Entities.ObjectEntities;

import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;
import Core.Databases.ListRegistry;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static Core.Utilities.DataConstants.*;

public class PerformanceRegistry extends ListRegistry {
	
	public static final String RegistryName = "Performances";
	public static final String format = "PERFORMANCE-%04d";

	public PerformanceRegistry( File Production) throws IllegalClassFormatException, IOException, AppWarning {
		
		File Registry = new File(Production, RegistryName);
		
		EntityField[] fields = { START_TIME, DATE };
		
		super(Registry, fields, format);
	
	}
	
	public List<String> getAllPerformanceNames() {
	
		return this.getAllOfField(ENTRY_NAME);
	
	}
	
	@SuppressWarnings("unused")
	public String getPerformanceID(String performanceName, String date) throws AppWarning {
		
		Map<EntityField, String> performanceNameCheck = new HashMap<>();
		
		performanceNameCheck.put(ENTRY_NAME, performanceName);
		performanceNameCheck.put(DATE, date);
		
		return this.getID(performanceNameCheck);
		
	}
	
	public String getPerformanceID(String performanceName) throws AppWarning {
		
		Map<EntityField, String> performanceNameCheck = new HashMap<>();
		
		performanceNameCheck.put(ENTRY_NAME, performanceName);
		
		return this.getID(performanceNameCheck);
		
	}
	
	public String addPerformance(String performanceName, String date) throws AppWarning, IOException {
		
		String ID = this.createNewEntry();
		
		this.changeRegisterValue(ID, ENTRY_NAME, performanceName, false);
		this.changeRegisterValue(ID, DATE, date, false);
		
		return ID;
		
	}
	
	public String addPerformance(String performanceName) throws IOException, AppWarning {
		
		String ID = this.createNewEntry();
		
		this.changeRegisterValue(ID, ListRegistry.ENTRY_NAME, performanceName, false);
		
		return ID;
	
	}
	
	public void removePerformance(String performanceName) throws AppWarning {
		
		this.deleteRegister(this.getPerformanceID(performanceName));
		
	}
	
	public void loadRoles(String ID, List<String> roles) throws AppWarning, IOException {
		
		ValidateID(ID);
		
		for (String role: roles) {
		
			addRole(ID, role);
			
		}
		
	}
	
	public void assignCast ( String ID, String roleName, String cast) throws AppWarning, IOException {
	
		ValidateID(ID);
		
		this.addEntry(ID, roleName, cast);
	
	}
	
	public void removeCasts(String cast) throws AppWarning, IOException {
		
		for (String key: this.complexEntityData.keySet()) {
			
			removeCast(key, cast);
			
		}
	
	}
	
	public void removeCast(String ID, String cast) throws AppWarning, IOException {
	
		for (String key: this.getRawMapForID(ID).keySet()) {
		
			if (this.getRawMapForID(ID).get(key).equals(cast)) {
				
				assignCast(ID, key, null);
				
			}
		
		}
	
	}
	
	public void addRoles(String roleName) throws AppWarning, IOException {
		
		for (String key: this.complexEntityData.keySet()) {
		
			if (!this.getRawMapForID(key).containsKey(roleName)) {
				
				addRole(key, roleName);
				
			}
		
		}
	
	}
	
	public void addRole(String ID, String roleName) throws AppWarning, IOException {
		
		ValidateID(ID);
		
		this.assignCast(ID, roleName, null);
		
	}
	
	public void editRole(String oldRoleName, String newRoleName) throws AppWarning, IOException {
		
		for (String key: this.complexEntityData.keySet()) {
			
			if (this.getRawMapForID(key).containsKey(oldRoleName)) {
				
				editRole(key, oldRoleName, newRoleName);
				
			}
			
		}
		
	}
	
	public void editRole(String ID, String oldRoleName, String newRoleName) throws AppWarning, IOException {
		
		ValidateID(ID);
		
		this.assignCast(ID, newRoleName, this.getRawMapForID(ID).get(oldRoleName));
		this.removeEntry(ID, oldRoleName);
		
	}
	
	public void removeRole(String roleName) throws AppWarning, IOException {
		
		for (String key: this.complexEntityData.keySet()) {
			
			if (this.getRawMapForID(key).containsKey(roleName)) {
				
				removeRole(key, roleName);
				
			}
			
		}
		
	}
	
	public void removeRole(String ID, String roleName) throws AppWarning, IOException {
		
		ValidateID(ID);
		
		this.removeEntry(ID, roleName);
		
	}

}
