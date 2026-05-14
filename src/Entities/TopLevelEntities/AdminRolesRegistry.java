package Entities.TopLevelEntities;

import Core.Databases.ListRegistry;
import Core.Databases.SimpleRegistry;
import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;
import Core.Utilities.FilePaths;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Core.Utilities.DataConstants.*;

public class AdminRolesRegistry extends SimpleRegistry {
	
	public static final String format = "ADMIN-ROLE-%04d";
	
	public static final File REGISTRY = FilePaths.AdminRoles;
	
	public static EntityField[] fields = new EntityField[Permissions.length + 1];
	
	public AdminRolesRegistry () throws AppWarning, IllegalClassFormatException, IOException {
		
		fields[0] = NAME;
		
		for (int i = 1; i < fields.length; i++) {
			
			fields[i] = Permissions[i-1];
			
		}
		
		super(REGISTRY, fields, format);
		
	}
	
	public List<String> getAllRoles () { return this.getAllOfField(ListRegistry.ENTRY_NAME); }
	
	public String getRoleID ( String roleName ) throws AppWarning {
		
		return this.getID(ListRegistry.ENTRY_NAME, roleName);
	}
	
	public Map<String, String> getRoleData ( String roleName ) throws AppWarning { return getRawMapForID(getRoleID(roleName)); }
	
	public void editRoleData ( String ID, EntityField field, String newValue ) throws AppWarning, IOException { this.changeRegisterValue(ID, field, newValue, false); }
	
	public void deleteRole ( String roleID ) { this.deleteRegister(roleID); }
	
	public String createNewRole ( String roleName ) throws AppWarning, IOException {
		
		HashMap<EntityField, String> newRole = new HashMap<>();
		newRole.put(NAME, roleName);
		
		return this.createNewEntry(newRole);
		
	}
	
	public boolean hasPermission(String ID, EntityField permissionField) throws AppWarning {
	
		List<EntityField> permissions = new ArrayList<>(List.of(Permissions));
		
		if (!permissions.contains(permissionField)) throw new AppWarning("Can only check permission for a " +
			   "permission field");
		
		return this.getFieldValue(ID, permissionField);
	
	}
	
}
