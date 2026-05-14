package Entities.TopLevelEntities;

import Core.Databases.SimpleRegistry;
import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;
import Core.Utilities.FilePaths;
import Core.Utilities.TypeController;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Core.Utilities.DataConstants.*;

public class AdminRegistry extends SimpleRegistry {
	
	protected static final String format = "ADMIN-%04d";
	
	public static final EntityField[] ENTITY_FIELDS = {
		   USERNAME, PASSWORD, IS_ACTIVE, ADMIN_ROLE, EMAIL, DATE_JOINED
	};
	
	private static final File ADMINS_REGISTRY = FilePaths.AdminUsersAndPassword;
	
	public AdminRegistry () throws AppWarning, IllegalClassFormatException, IOException {
		
		super(ADMINS_REGISTRY, ENTITY_FIELDS, format);
		
	}
	
	public String createAdmin ( String newAdminName, String password ) throws AppWarning, IOException,
		   NoSuchAlgorithmException {
		
		String ID = createAdmin(newAdminName);
		
		this.changeRegisterValue(ID, PASSWORD, TypeController.hash(password), false);
		this.changeRegisterValue(ID, DATE_JOINED, LocalDate.now().toString(), false);
		
		return ID;
		
	}
	
	public String createAdmin ( String newAdminName ) throws AppWarning, IOException {
		
		HashMap<EntityField, String> adminData = new HashMap<EntityField, String>();
		
		adminData.put(USERNAME, newAdminName);
		
		return this.createNewEntry(adminData);
		
	}
	
	public boolean checkForAdmin ( String adminSearchName ) {
		
		return checkForRegister(USERNAME, adminSearchName);
		
	}
	
	public boolean isValidAdmin ( String adminSearchName, String adminSearchPassword ) throws AppWarning {
		
		HashMap<EntityField, String> searchMap = new HashMap<>();
		
		searchMap.put(USERNAME, adminSearchName);
		searchMap.put(PASSWORD, adminSearchPassword);
		
		return this.getID(searchMap) != null;
		
	}
	
	public Map<String, Map<String, String>> getAllAdmins () { return this.getDataMap(); }
	
	public List<String> getAllAdminNames () { return this.getAllOfField(USERNAME); }
	
	public String getAdminID ( String adminSearchName ) throws AppWarning {
		
		return this.getID(USERNAME, adminSearchName);
		
	}
	
	public Map<EntityField, String> getAdminData ( String adminSearchName ) throws AppWarning { return getMapForID(getAdminID(adminSearchName)); }
	
	public void disactivateAdmin ( String adminID ) throws AppWarning, IOException, NoSuchAlgorithmException {
		
		this.editAdminData(adminID, IS_ACTIVE, false);
	}
	
	public void activateAdmin ( String adminID ) throws AppWarning, IOException, NoSuchAlgorithmException { this.editAdminData(adminID, IS_ACTIVE, true); }
	
	public boolean isAdminActive ( String adminID ) throws AppWarning {
		
		return this.getFieldValue(adminID, IS_ACTIVE);
	}
	
	public void editAdminData ( String ID, EntityField field, Object newValue ) throws AppWarning, IOException,
		   NoSuchAlgorithmException {
		
		if ( field == PASSWORD ) {
			
			this.changeRegisterValue(ID, field, TypeController.hash(newValue.toString()), false);
			return;
			
		}
		
		this.changeRegisterValue(ID, field, newValue, false);
	}
	
}
