package Entities;

import Core.DBEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.List;

import static Core.DataConstants.*;

public class RolesEntity extends DBEntity {
	
	public static String dataType = TEXT_TYPE;
	File productionFolder;
	
	private static final String filePath = "Roles";
	
	public RolesEntity ( File folder ) throws IOException, IllegalClassFormatException {
		
		super(new File(folder, filePath));
		this.productionFolder = folder;
		
	}
	
	public void addRole ( String roleName ) throws IllegalClassFormatException, IOException {
		
		this.addToFile(roleName);
		this.save();
		
	}
	
	public void removeRole ( String roleName ) throws IllegalClassFormatException, IOException {
		
		this.removeLine(roleName);
		this.save();
		
	}
	
	@SuppressWarnings("unused")
	public boolean roleExists (String roleName) throws IOException {
		
		return this.fileChecker.lineExists(roleName);
		
	}
	
	public List<String> getAllRoles() throws IOException {
		
		return List.of(this.fileChecker.getLines());
		
	}
	
	public void changeRole(String oldRole, String newRole) throws IOException {
	
		this.fileChecker.editLine(oldRole, newRole);
	
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		
		setDataType(dataType);
		
	}
	
}
