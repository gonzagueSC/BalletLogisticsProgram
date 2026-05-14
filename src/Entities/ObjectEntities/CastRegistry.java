package Entities.ObjectEntities;

import Core.Utilities.AppError;
import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;
import Core.Databases.ListRegistry;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CastRegistry extends ListRegistry {
	
	public static final String RegistryName = "Casts";
	public static final String format = "CAST-%04d";

	public CastRegistry(File Production) throws IllegalClassFormatException, IOException, AppWarning {
		
		File Registry = new File(Production, RegistryName);
		
		super(Registry, new EntityField[0], format);
	
	}
	
	public Map<String, Map<String, String>> getCastList() {
		
		return complexEntityData;
		
	}
	
	public Map<String, String> getCast(String castName) throws AppWarning {
	
		Map<EntityField, String> castNameCheck = new HashMap<>();
		
		castNameCheck.put(ListRegistry.ENTRY_NAME, castName);
		
		String ID = this.getID(castNameCheck);
		
		if (ID.equals("-1"))
			throw new AppWarning("Could not find Cast with that name");
		
		return getCastList().get(ID);
	
	}
	
	public String getCastID(String castName) throws AppWarning {
		
		Map<EntityField, String> castNameCheck = new HashMap<>();
		
		castNameCheck.put(ListRegistry.ENTRY_NAME, castName);
		
		return this.getID(castNameCheck);
		
	}
	
	public void addCast(String castName) throws AppWarning, IOException {
	
		Map<EntityField, String> presetValues = new HashMap<>();
		
		presetValues.put(ListRegistry.ENTRY_NAME, castName);
		
		this.createNewEntry(presetValues);
	
	}
	
	public void removeCast(String castName) throws AppWarning {
		
		this.deleteRegister(this.getCastID(castName));
		
	}
	
	public List<String> getAllCastNames() {
	
		return this.getAllOfField(ListRegistry.ENTRY_NAME);
	
	}
	
	@SuppressWarnings("unused")
	public void castStudent(String ID, String role, String studentID) throws AppWarning, IOException, AppError {
		
		ValidateID(ID);
		
		if (!this.complexEntityData.get(ID).containsKey(role)) {
			throw new AppWarning("Tried to cast a student to a non-existing role");
		}
		
		
		
		String currentCasting = this.complexEntityData.get(ID).get(role);
		
		if (currentCasting == null) {
			
			currentCasting = "";
			
		}
		
		if (currentCasting.contains(studentID))
			throw new AppError( new AppWarning("Tried to cast the same student twice"));
		
		currentCasting += ((!currentCasting.isBlank())?", ":"") + studentID;
		
		this.editEntry(ID, role, currentCasting);
		
	}
	
	public void editRoleName(String oldRoleName, String newRoleName) throws AppWarning, IOException {
	
		for (String ID : this.complexEntityData.keySet()) {
			
			editRole(ID, oldRoleName, newRoleName);
			
		}
	
	}
	
	public void editRole(String ID, String oldRoleName, String newRoleName) throws AppWarning, IOException {
	
		this.addEntry(ID, newRoleName, getEntryValue(ID, oldRoleName));
		this.removeEntry(ID, oldRoleName);
	
	}
	
	public void removeRole(String roleName) throws AppWarning, IOException {
		
		for (String ID : this.complexEntityData.keySet()) {
			
			removeRole(ID, roleName);
			
		}
	
	}
	
	public void removeRole(String ID, String roleName) throws AppWarning, IOException {
		
		this.removeEntry(ID, roleName);
		
	}
	
	public void addRole(String roleName) throws AppWarning, IOException {
		
		for (String ID : this.complexEntityData.keySet()) {
			
			addRole(ID, roleName);
			
		}
	
	}
	
	public void addRole(String ID, String roleName) throws AppWarning, IOException {
		
		this.addEntry(ID, roleName, null);
		
	}

}
