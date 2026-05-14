package Entities.TopLevelEntities;

import Core.Databases.JSONObjectEntity;
import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;
import Core.Utilities.FilePaths;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import static Core.Utilities.DataConstants.*;

public class ConfigEntity extends JSONObjectEntity {
	
	public static final String filePath = "config";
	public static final File folder = FilePaths.configFolder;
	
	public ConfigEntity () throws IOException, IllegalClassFormatException, AppWarning {
		
		super(filePath, folder);
		
		ENTITY_FIELDS = new EntityField[]{ LAST_STUDENT_ID };
		
		initializeComponents();
		
	}
	
	public String returnNextStudentID () throws IOException, AppWarning {
		
		int lastID = getFieldValue(LAST_STUDENT_ID);
		int nextID = lastID + 1;
		this.saveField(LAST_STUDENT_ID, String.valueOf(nextID), false);
		
		this.save();
		
		return String.format("STUDENT-%05d", lastID);
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException, AppWarning {
		super.initializeComponents();
		
		if (this.getFieldValue(LAST_STUDENT_ID) == null) {
			
			this.saveField(LAST_STUDENT_ID, "0", false);
			
		}
	}
	
}
