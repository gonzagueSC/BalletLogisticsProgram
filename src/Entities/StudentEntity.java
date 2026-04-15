package Entities;

import Core.*;
import org.jetbrains.annotations.NotNull;

import static Core.DataConstants.*;

import java.io.*;
import java.lang.instrument.IllegalClassFormatException;
import java.util.*;

public class StudentEntity extends JSONObjectEntity {
	
	static final File folder = FilePaths.StudentsFolder;
	static final EntityField firstName = new EntityField("first_name", true, String.class);
	static final EntityField lastName = new EntityField("last_name", true, String.class);
	static final EntityField dateOfBirth = new EntityField("date_of_birth", true, String.class);
	static final EntityField level = new EntityField("level", true, String.class);
	static final EntityField email = new EntityField("email", true, String.class);
	static final EntityField phoneNumber = new EntityField("phone_number", true, String.class);
	static final EntityField medicalConditions = new EntityField("medical_conditions", true, String.class);
	static final EntityField gender = new EntityField("gender", true, String.class);
	static final EntityField dateJoined = new EntityField("date_joined", false, String.class);
	static final EntityField address = new EntityField("address", true, String.class);
	static final EntityField activityStatus = new EntityField("activity_status", true, String.class);
	static final EntityField tuitionPlan = new EntityField("tuition_plan", true, String.class);
	static final EntityField accountBalance = new EntityField("account_balance", true, Double.class);
	static final EntityField height = new EntityField("height", true, Double.class);
	static final EntityField girth = new EntityField("girth", true, Double.class);
	static final EntityField waist = new EntityField("waist", true, Double.class);
	static final EntityField hips = new EntityField("hips", true, Double.class);
	static final EntityField bustChest = new EntityField("bust_chest", true, Double.class);
	static final EntityField inseam = new EntityField("inseam", true, Double.class);
	static final EntityField sleeveLength = new EntityField("sleeve_length", true, Double.class);
	static final EntityField neck = new EntityField("neck", true, Double.class);
	static final EntityField backLength = new EntityField("back_length", true, Double.class);
	static final EntityField shoeSize = new EntityField("shoe_size", true, Double.class);
	static final EntityField classesPerWeek = new EntityField("classes_per_week", true, Integer.class);
	static final EntityField log = new EntityField("audit_log", true, String.class);
	
	public StudentEntity ( String studentID ) throws IOException, IllegalClassFormatException {
		
		super(studentID, folder);
		
		ENTITY_FIELDS = new EntityField[]{firstName, lastName, dateOfBirth, level, email, phoneNumber,
			   medicalConditions, gender, dateJoined, address, activityStatus, tuitionPlan, accountBalance, height,
			   girth, waist, hips, bustChest, inseam, sleeveLength, neck, backLength, shoeSize, classesPerWeek,
			   log};
		
		initializeComponents();
		
	}
	
	public Map<String, String> getStudentData () {
		
		return this.entityData;
		
	}
	
	@Override
	public void saveFields ( @NotNull Map<EntityField, String> fieldToSaveMap ) throws IllegalClassFormatException, IOException {
		
		if (fieldToSaveMap.containsKey(firstName) || fieldToSaveMap.containsKey(lastName) || fieldToSaveMap.containsKey(dateOfBirth)) {
			
			String oldDisplay =
				   this.getFieldValue(firstName) + " " + this.getFieldValue(lastName) + " " + this.getFieldValue(dateOfBirth);
			String newDisplay =
				   ((fieldToSaveMap.containsKey(firstName)) ? fieldToSaveMap.get(firstName) : this.getFieldValue(firstName))
					      + " " +
			        ((fieldToSaveMap.containsKey(lastName)) ? fieldToSaveMap.get(lastName) : this.getFieldValue(lastName))
					      + " " +
			        ((fieldToSaveMap.containsKey(dateOfBirth)) ? fieldToSaveMap.get(dateOfBirth) : this.getFieldValue(dateOfBirth));
			EntityMapper.getInstance().getEntity(StudentsRegistryEntity.class).changeStudentName(oldDisplay, newDisplay);
			
		}
		
		super.saveFields(fieldToSaveMap);
	}
	
}
