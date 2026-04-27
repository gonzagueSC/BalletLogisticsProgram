package Entities;

import Core.RegistryEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Core.DataConstants.JSON_TYPE;

public class RehearsalsRegistryEntity extends RegistryEntity {
	
	@SuppressWarnings("unused")
	public static final String FileType = JSON_TYPE;
	public static final String RegistryName = "Rehearsals";
	public static final String format = "REHEARSAL-%04d";
	
	public RehearsalsRegistryEntity ( File production ) throws IllegalClassFormatException, IOException {
		
		File Registry = new File(production, RegistryName);
		
		super(Registry, CastEntity.class);
		
	}
	
	public Map<String, String> getRehearsals () {
		
		return this.entityData;
		
	}
	
	public List<String> getRehearsalsNames () {
		
		return new ArrayList<>(getRehearsals().keySet());
		
	}
	
	public String getNextRehearsalID () {
		
		return this.getNextID(format);
		
	}
	
	public void addRehearsal ( String rehearsalName, String timeStart, String timeEnd, String date ) throws IllegalClassFormatException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		this.addToFile(rehearsalName + ", " + timeStart + " - " + timeEnd + ", " + date, getNextRehearsalID());
		
		this.save();
		
		RehearsalEntity rehearsal =
                  this.getEntity(getRehearsals().get(rehearsalName + ", " + timeStart + ", " + timeEnd + ", " + date)
                          , true);
		
		rehearsal.setUpRehearsal(rehearsalName, date, timeStart, timeEnd);
		
	}
	
	@SuppressWarnings("unused")
	public void changeRehearsalName ( RehearsalEntity rehearsal, String newName ) throws IOException {
		
		String RehearsalFullName = rehearsal.getFullRehearsalName();
		
		RehearsalFullName =
                  RehearsalFullName.replaceFirst(rehearsal.getFieldValue(rehearsal.getFieldValue(RehearsalEntity.rehearsalName)), newName);
		
		rehearsal.setRehearsalName(newName);
		
		getRehearsals().put(RehearsalFullName, getRehearsals().get(rehearsal.getFullRehearsalName()));
		getRehearsals().remove(rehearsal.getFullRehearsalName());
		
		this.save();
		
	}
	
	@SuppressWarnings("unused")
	public void changeRehearsalStart ( RehearsalEntity rehearsal, String newStartTime) throws IOException {
		
		String RehearsalFullName = rehearsal.getFullRehearsalName();
		
		if (newStartTime == null) {
			throw new IllegalArgumentException("New start time cannot be null");
		}
		
		RehearsalFullName =
			   RehearsalFullName.replaceFirst(rehearsal.getFieldValue(rehearsal.getFieldValue(RehearsalEntity.rehearsalStartTime))
				      , newStartTime);
		
		rehearsal.setRehearsalStartTime(newStartTime);
		
		getRehearsals().put(RehearsalFullName, getRehearsals().get(rehearsal.getFullRehearsalName()));
		getRehearsals().remove(rehearsal.getFullRehearsalName());
		
		this.save();
	
	}
	
	@SuppressWarnings("unused")
	public void changeRehearsalEnd ( RehearsalEntity rehearsal, String newEndTime) throws IOException {
		
		String RehearsalFullName = rehearsal.getFullRehearsalName();
		
		if (newEndTime == null) {
			throw new IllegalArgumentException("New End time cannot be null");
		}
		
		RehearsalFullName =
			   RehearsalFullName.replaceFirst(rehearsal.getFieldValue(rehearsal.getFieldValue(RehearsalEntity.rehearsalEndTime))
					 , newEndTime);
		
		rehearsal.setRehearsalEndTime(newEndTime);
		
		getRehearsals().put(RehearsalFullName, getRehearsals().get(rehearsal.getFullRehearsalName()));
		getRehearsals().remove(rehearsal.getFullRehearsalName());
		
		this.save();
		
	}
	
	@SuppressWarnings("unused")
	public void changeRehearsalDate ( RehearsalEntity rehearsal, String newDate) throws IOException {
		
		String RehearsalFullName = rehearsal.getFullRehearsalName();
		
		RehearsalFullName =
			   RehearsalFullName.replaceFirst(rehearsal.getFieldValue(rehearsal.getFieldValue(RehearsalEntity.rehearsalDate))
					 , newDate);
		
		rehearsal.setRehearsalDate(newDate);
		
		getRehearsals().put(RehearsalFullName, getRehearsals().get(rehearsal.getFullRehearsalName()));
		getRehearsals().remove(rehearsal.getFullRehearsalName());
		
		this.save();
	}
	
	public void removeRehearsal ( RehearsalEntity rehearsal ) {
		
		getRehearsals().remove(rehearsal.getFullRehearsalName());
		rehearsal.killFile();
		
	}
	
	public RehearsalEntity getRehearsal ( String rehearsalName, String timeStart, String timeEnd, String date ) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		
		if ( rehearsalName == null || timeStart == null || timeEnd == null || date == null ) {
			throw new IllegalArgumentException("Rehearsal details cannot be null");
		}
		
		String fullRehearsalName = rehearsalName + ", " + timeStart + ", " + timeEnd + ", " + date;
		
		if ( !getRehearsals().containsKey(rehearsalName) ) {
			throw new IllegalArgumentException("Rehearsal not found");
		}
		
		return this.getEntity(getRehearsals().get(fullRehearsalName), true);
		
	}
	
	public void changeRole(String oldRole, String newRole) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
	
		List<String> rehearsals = getRehearsalsNames();
		
		for (String rehearsal: rehearsals) {
		
			RehearsalEntity rehearsalEntity = this.getEntity(rehearsal, true);
			rehearsalEntity.editRole(oldRole, newRole);
		
		}
	
	}
	
	public void removeRole(String role) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
	
		List<String> rehearsals = getRehearsalsNames();
		
		for (String rehearsal: rehearsals) {
			
			RehearsalEntity rehearsalEntity = this.getEntity(rehearsal, true);
			rehearsalEntity.removeRole(role);
			
		}
	
	}
	
}
