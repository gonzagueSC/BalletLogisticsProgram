package Entities.ObjectEntities;

import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;
import Core.Databases.ListRegistry;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

import static Core.Utilities.DataConstants.*;

public class ProductionCostumesRegistry extends ListRegistry {
	
	public static final EntityField[] ENTITY_FIELDS = {NAME, ID, ROLE, CAST};
	public static final String RegistryName = "Costumes";
	public static final String format = "COSTUME-%04d";

	public ProductionCostumesRegistry( File Production) throws AppWarning, IllegalClassFormatException, IOException {
		
		File Registry = new File(Production, RegistryName);
		
		super(Registry, new EntityField[0], format);
	
	}
	
	public void addRole(String role) throws AppWarning, IOException {
	
		this.createNewEntry(role);
	
	}
	
	public void editRole(String roleName, String newRoleName) throws AppWarning, IOException {
		
		if (this.complexEntityData.containsKey(roleName)) {
			
			String ID = this.createBuiltListEntry(newRoleName, this.getRawMapForID(roleName));
			
			this.deleteRegister(roleName);
			
		}
		
	}
	
	public void deleteRole(String roleName) {
		
		if (this.checkForRegister(ENTRY_NAME, roleName)) {
			
			this.deleteRegister(roleName);
			
		}
		
	}
	
	public void addCostume(String roleName, String studentID, String CostumeID, String cast) throws AppWarning, IOException {
		
		this.ValidateID(roleName);
		
		this.addEntry(roleName, studentID, CostumeID + DELIMITER + cast);
	
	}
	
	public void removeCostume(String roleName, String studentID) throws AppWarning, IOException {
	
		this.ValidateID(roleName);
		
		this.removeEntry(roleName, studentID);
		this.addEntry(roleName, studentID, null);
	
	}
	
	public void editCostume(String roleName, String studentID, String CostumeID, String cast) throws AppWarning, IOException {
		
		this.addCostume(roleName, studentID, CostumeID, cast);
		
	}
	
	public void removeStudentFromRole(String roleName, String studentID) throws AppWarning, IOException {
		
		this.ValidateID(roleName);
	
		this.removeEntry(roleName, studentID);
	
	}
	
	public void addStudentToRole(String roleName, String studentID) throws AppWarning, IOException {
		
		this.ValidateID(roleName);
		
		this.addEntry(roleName, studentID, null);
		
	}
	
	public void ValidateID(String ID) throws AppWarning {
		
		if (!this.checkForRegister(ENTRY_NAME, ID))
			throw new AppWarning("Could not find that role");
	
	}

}
