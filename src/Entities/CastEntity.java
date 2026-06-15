package Entities;

import Core.Databases.DB;
import Core.Databases.DBEntity;
import Core.Utilities.AppError;
import Core.Utilities.AppWarning;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.List;
import java.util.Map;

import static Core.Utilities.DataConstants.*;

public class CastEntity extends DBEntity {
	
	public static String dataType = JSON_TYPE;
	File productionFolder;
	
	public CastEntity ( String filePath, File folder) throws IOException, IllegalClassFormatException, AppWarning {
		
		DB.fileStartup(new File(folder, filePath), dataType);
		super(new File(folder, filePath));
		this.productionFolder = folder;
		
	}
	
	public Map<String, String> CastingMap() {
		
		return this.entityData;
		
	}
	
	@SuppressWarnings("unused")
	public void castStudent(String studentID, String role) throws IOException, AppWarning, AppError {

		if (!CastingMap().containsKey(role)) {
			throw new AppWarning("Tried to cast a student to a non-existing role");
		}



		String currentCasting = CastingMap().get(role);

		if (currentCasting == null) {

			currentCasting = "";

		}
		
		if (currentCasting.contains(studentID))
			throw new AppError( new AppWarning("Tried to cast the same student twice"));
		
		currentCasting += ((!currentCasting.isBlank())?", ":"") + studentID;
	
		CastingMap().put(role, currentCasting);
		
		this.save();
	
	}
	
	@SuppressWarnings("unused")
	public void removeStudentFromCast(String studentID, String role) throws IOException, AppError, AppWarning {
		
		String currentCasting = CastingMap().get(role);
		
		if (!currentCasting.contains(studentID))
			throw new AppError( new AppWarning("Tried to un-cast a non-casted student"));
		
		String regex = "(, " + studentID + "|" + studentID + ", |" + studentID + ")";
		
		currentCasting = currentCasting.replaceFirst(regex, "");
		
		CastingMap().put(role, currentCasting);
		
		this.save();
	
	}
	
	public void removeRole(String roleName) throws IOException, AppWarning {
		
		CastingMap().remove(roleName);
		
		save();
	
	}
	
	@SuppressWarnings("unused")
	public Map<String, String> getAllRoles() {
		
		return this.CastingMap();
		
	}

	public void updateRoles( List<String> roles) throws IOException, AppWarning {

		for (String role : roles) {

			if (!CastingMap().containsKey(role))
				CastingMap().put(role, null);

		}

		if (roles.size() != CastingMap().size()) {

			for (String key: CastingMap().keySet()) {

				if (!roles.contains(key))
					removeRole(key);

			}

		}

		this.save();

	}
	
	public void changeRoleName(String oldRole, String newRole) throws IOException, AppWarning {
	
		if (CastingMap().containsKey(oldRole)) {
			
			CastingMap().put(newRole, CastingMap().get(oldRole));
			
			CastingMap().remove(oldRole);
			
		}
		
		this.save();
	
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException, AppWarning {
	
		setDataType(dataType);
	
	}
	
}
