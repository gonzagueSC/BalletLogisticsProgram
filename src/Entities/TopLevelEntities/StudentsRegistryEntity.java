package Entities.TopLevelEntities;

import Core.Databases.ComplexRegistry;
import Core.Databases.DB;
import Core.Databases.DBEntity;
import Core.Utilities.AppWarning;
import Core.Utilities.EntityField;
import Core.Utilities.FilePaths;
import Entities.ObjectEntities.StudentEntity;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import static Core.Utilities.DataConstants.*;

public class StudentsRegistryEntity extends ComplexRegistry {
	
	public static final EntityField[] ENTITY_FIELDS = {FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, LEVEL, GENDER, ACTIVITY_STATUS, TUITION_PLAN, BALANCE};
	
	Map<String, StudentEntity> loadedStudentEntities = new HashMap<>();
	
	public static Class<? extends DBEntity> ObjectType = StudentEntity.class;
	
	public static String format = "STUDENT-%04d";
	
	public StudentsRegistryEntity () throws IllegalClassFormatException, IOException, AppWarning {
		
		DB.fileStartup(FilePaths.RegisteredStudents, FileType);
		
		super(FilePaths.RegisteredStudents, ENTITY_FIELDS, format, ObjectType);
		
		initializeComponents();
		
	}
	
	@SuppressWarnings("unused")
	public StudentEntity getStudent ( String firstName, String lastName, String dateOfBirth ) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, AppWarning {
		
		return getStudent(getStudentID(firstName, lastName, dateOfBirth));
		
	}
	
	public synchronized StudentEntity getStudent ( String ID ) throws InvocationTargetException,
		   NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		return getEntity(ID);
		
	}
	
	@SuppressWarnings("unused")
	public synchronized void addStudent ( Map<EntityField, String> entityValues ) throws IllegalClassFormatException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, AppWarning {
		
		if ( !(entityValues.containsKey(FIRST_NAME) && entityValues.containsKey(LAST_NAME) && entityValues.containsKey(DATE_OF_BIRTH)) )
			throw new AppWarning("Missing crucial data to create Student");
		
		String ID = this.createNewEntry(entityValues);
		this.createEntity(ID);
		
		StudentEntity currentStudent = this.getStudent(ID);
		currentStudent.saveFields(entityValues, true);
		
	}
	
	public String getStudentID ( String firstName, String lastName, String dateOfBirth ) throws AppWarning {
		
		HashMap<EntityField, String> valuesMap = new HashMap<>();
		
		valuesMap.put(FIRST_NAME, firstName);
		valuesMap.put(LAST_NAME, lastName);
		valuesMap.put(DATE_OF_BIRTH, dateOfBirth);
		
		return this.getID(valuesMap);
		
	}
	
	public Map<String, Map<String, String>> studentsMap () {
		
		return this.getDataMap();
		
	}
	
	@SuppressWarnings("unused")
	public Map<String, StudentEntity> loadedStudents () {
		
		return this.loadedStudentEntities;
		
	}
	
	@SuppressWarnings("unused")
	public ArrayList<String> getAllStudentNames () {
		
		ArrayList<String> allNames = new ArrayList<>();
		
		for ( String key: getDataMap().keySet()) {
			
			String firstName = this.getFieldValue(key, FIRST_NAME);
			String lastName = this.getFieldValue(key, LAST_NAME);
			
			allNames.add(firstName + " " + lastName);
			
		}
		
		return allNames;
		
	}
	
	public void changeStudentValue ( String ID, EntityField field, String newValue ) throws IOException, AppWarning {
		
		
		this.changeRegisterValue(ID, field, newValue, false);
		
	}
	
	public boolean studentAlreadyExists ( String firstName, String lastName, String birthDate ) throws AppWarning {
		
		HashMap<EntityField, String> valuesMap = new HashMap<>();
		
		valuesMap.put(FIRST_NAME, firstName);
		valuesMap.put(LAST_NAME, lastName);
		valuesMap.put(DATE_OF_BIRTH, birthDate);
		
		return this.getID(valuesMap) != null;
		
	}
	
}
