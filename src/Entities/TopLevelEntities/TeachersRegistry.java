package Entities.TopLevelEntities;

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

public class TeachersRegistry extends SimpleRegistry {
	
	protected static final String format = "TEACHER-%04d";
	
	public static final EntityField[] ENTITY_FIELDS = {
		   FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, IS_ACTIVE
	};
	
	private static final File TEACHERS_REGISTRY = FilePaths.Teachers;
	
	public TeachersRegistry () throws AppWarning, IllegalClassFormatException, IOException {
		
		super(TEACHERS_REGISTRY, ENTITY_FIELDS, format);
		
	}
	
	public String createTeacher ( String newTeacherFirstName, String newTeacherLastName ) throws AppWarning,
		   IOException {
		
		HashMap<EntityField, String> teacherData = new HashMap<EntityField, String>();
		
		teacherData.put(FIRST_NAME, newTeacherFirstName);
		teacherData.put(LAST_NAME, newTeacherLastName);
		teacherData.put(IS_ACTIVE, "true");
		
		return this.createNewEntry(teacherData);
		
	}
	
	public boolean checkForTeacher ( String teacherSearchFirstName, String teacherSearchLastName ) throws AppWarning {
		
		HashMap<EntityField, String> teacherData = new HashMap<EntityField, String>();
		
		teacherData.put(FIRST_NAME, teacherSearchFirstName);
		teacherData.put(LAST_NAME, teacherSearchLastName);
		
		return getID(teacherData) != null;
	}
	
	public Map<String, Map<String, String>> getAllTeachers () { return this.getDataMap(); }
	
	public List<String> getAllTeacherNames () {
		
		List<String> firstNames = this.getAllOfField(FIRST_NAME);
		List<String> lastNames = this.getAllOfField(LAST_NAME);
		
		List<String> names = new ArrayList<>();
		
		for ( int i = 0; i < firstNames.size(); i++ ) {
			
			names.add(firstNames.get(i) + " " + lastNames.get(i));
			
		}
		
		return names;
		
	}
	
	public String getTeacherID ( String teacherSearchFirstName, String teacherSearchLastName ) throws AppWarning {
		
		HashMap<EntityField, String> teacherData = new HashMap<EntityField, String>();
		
		teacherData.put(FIRST_NAME, teacherSearchFirstName);
		teacherData.put(LAST_NAME, teacherSearchLastName);
		
		return this.getID(teacherData);
	}
	
	public Map<EntityField, String> getTeacherData ( String teacherSearchFirstName, String teacherSearchLastName ) throws AppWarning { return getMapForID(getTeacherID(teacherSearchFirstName, teacherSearchLastName)); }
	
	public void deleteTeacher ( String teacherID ) throws AppWarning, IOException {
		
		int IDValue = this.getNumberFromID(teacherID);
		
		if ( !this.complexEntityData.containsKey(String.format(format, IDValue + 1)) ) return;
		
		while ( this.complexEntityData.containsKey(String.format(format, IDValue + 1)) ) {
			
			this.swapDown(String.format(format, IDValue));
			
			IDValue++;
			
		}
		
		this.deleteRegister(teacherID);
		
	}
	
	public void editTeacherData ( String ID, EntityField field, String newValue ) throws AppWarning, IOException {
		
		this.changeRegisterValue(ID, field, newValue, false);
		
	}
	
}
