package Entities;

import Core.DB;
import Core.EntityField;
import Core.FilePaths;
import Core.JSONObjectEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

public class ConfigEntity extends JSONObjectEntity {
	
	public static final String filePath = "config";
	public static final File folder = FilePaths.configFolder;
	public static final EntityField lastStudentID = new EntityField("last_student_id", true, Integer.class);
	
	public ConfigEntity () throws IOException, IllegalClassFormatException {
		
		super(filePath, folder);
		
		ENTITY_FIELDS = new EntityField[]{lastStudentID};
		
		initializeComponents();
		
	}
	
	public String returnNextStudentID () throws IOException {
		
		int lastID = getFieldValue(lastStudentID);
		int nextID = lastID + 1;
		this.saveField(lastStudentID, String.valueOf(nextID));
		
		this.save();
		
		return String.format("STUDENT-%05d", lastID);
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		super.initializeComponents();
		
		if (this.getFieldValue(lastStudentID) == null) {
			
			this.saveField(lastStudentID, "0");
			
		}
	}
	
}
