package Entities;

import Core.*;

import static Core.DataConstants.*;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class StudentsRegistryEntity extends RegistryEntity {
	
	Map<String, StudentEntity> loadedStudentEntities = new HashMap<String, StudentEntity>();
	
	private static final Class<StudentEntity> ObjectType = StudentEntity.class;
	
	public StudentsRegistryEntity () throws IllegalClassFormatException, IOException {
		
		DB.fileStartup(FilePaths.RegisteredStudents, FileType);
		
		super(FilePaths.RegisteredStudents, ObjectType);
		
		initializeComponents();
		
	}
	
	public StudentEntity getStudent ( String firstName, String lastName, String dateOfBirth ) throws IllegalClassFormatException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		return getStudent(getStudentID(firstName, lastName, dateOfBirth));
		
	}
	
	public synchronized StudentEntity getStudent ( String ID ) throws IllegalClassFormatException, IOException,
		   InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		return getEntity(ID);
		
	}
	
	public synchronized void addStudent ( Map<EntityField, String> entityValues ) throws IllegalClassFormatException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, CloneNotSupportedException {
		
		if (!(entityValues.containsKey(StudentEntity.firstName) && entityValues.containsKey(StudentEntity.lastName) && entityValues.containsKey(StudentEntity.dateOfBirth)))
			throw new AppWarning("Missing crucial data to create Student");
		
		String firstName = entityValues.get(StudentEntity.firstName);
		String lastName = entityValues.get(StudentEntity.lastName);
		String dateOfBirth = entityValues.get(StudentEntity.dateOfBirth);
		
		if (studentAlreadyExists(firstName, lastName, dateOfBirth))
			throw new AppWarning("Student Already Exists");
		
		
		String nextID = EntityMapper.getInstance().getEntity(ConfigEntity.class).returnNextStudentID();
		String studentDisplay =
			   firstName + " " +lastName + dateOfBirth;
		studentsMap().put(studentDisplay, nextID);
		this.save();
		
		this.createEntity(nextID);
		
		StudentEntity currentStudent = this.getStudent(nextID);
		currentStudent.saveFields(entityValues);
		
	}
	
	public String getStudentID ( String firstName, String lastName, String dateOfBirth ) {
		
		String lookup = firstName + " " + lastName + " " + dateOfBirth;
		
		return studentsMap().get(lookup);
		
	}
	
	public Map<String, String> studentsMap () {
		
		return this.getData();
		
	}
	
	public Map<String, StudentEntity> loadedStudents () {
		
		return this.loadedStudentEntities;
		
	}
	
	public ArrayList<String> getAllStudentNames() {
		
		ArrayList<String> allNames = new ArrayList<String>();
	
		for (Map.Entry<String, String> entry: studentsMap().entrySet()) {
		
			String key = entry.getKey();
			String[] keyValues = key.split(" ");
			String firstName = keyValues[0];
			String lastName = keyValues[1];
			
			allNames.add(firstName + " " + lastName);
		
		}
		
		return allNames;
	
	}
	
	public void changeStudentName(String oldDisplay, String newDisplay) throws IOException {
	
		String ID = studentsMap().get(oldDisplay);
		studentsMap().remove(oldDisplay);
		studentsMap().put(newDisplay, ID);
		save();
	
	}
	
	public boolean studentAlreadyExists(String firstName, String lastName, String birthDate) {
		
		return this.studentsMap().containsKey(firstName + " " + lastName + " " + birthDate);
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		
		setDataType(FileType);
		
	}
	
}
