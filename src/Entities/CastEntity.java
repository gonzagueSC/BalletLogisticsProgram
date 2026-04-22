package Entities;

import Core.*;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static Core.DataConstants.*;

public class CastEntity extends DBEntity {
	
	public static String dataType = JSON_TYPE;
	File productionFolder;
	
	public CastEntity ( String filePath, File folder) throws IOException, IllegalClassFormatException {
		
		DB.fileStartup(new File(folder, filePath), dataType);
		super(new File(folder, filePath));
		this.productionFolder = folder;
		
	}
	
	public Map<String, String> CastingMap() {
		
		return this.entityData;
		
	}
	
	public void castStudent(String studentID, String role) throws IOException {
		
		//TODO make sure that the role exists
 	
		if (!CastingMap().containsKey(role)) {
			CastingMap().put(role, Arrays.deepToString(new String[]{studentID}));
			this.save();
			return;
		}
		
		String currentCasting = CastingMap().get(role);
		
		if (currentCasting.contains(studentID))
			throw new AppError( new AppWarning("Tried to cast the same student twice"));
		
		currentCasting += ", " + studentID;
	
		CastingMap().put(role, currentCasting);
		
		this.save();
	
	}
	
	public void removeStudentFromCast(String studentID, String role) throws IOException {
		
		String currentCasting = CastingMap().get(role);
		
		if (!currentCasting.contains(studentID))
			throw new AppError( new AppWarning("Tried to un-cast a non-casted student"));
		
		String regex = "(, " + studentID + "|" + studentID + ", |" + studentID + ")";
		
		currentCasting = currentCasting.replaceFirst(regex, "");
		
		CastingMap().put(role, currentCasting);
		
		this.save();
	
	}
	
	public void removeRole(String roleName) throws IOException {
	
		if (CastingMap().containsKey(roleName))
			CastingMap().remove(roleName);
		
		save();
	
	}
	
	public Map<String, String> getAllRoles() {
		
		return this.CastingMap();
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
	
		setDataType(dataType);
	
	}
	
}
