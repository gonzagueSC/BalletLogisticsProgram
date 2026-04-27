package Entities;

import Core.*;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.List;
import java.util.Map;

import static Core.DataConstants.JSON_TYPE;

public class PerformanceEntity extends DBEntity {
	
	public static String dataType = JSON_TYPE;
	File productionFolder;
	
	public Map<String, String> RolesMap() {
		
		return this.entityData;
		
	}
	
	public PerformanceEntity ( String filePath, File folder) throws IOException, IllegalClassFormatException {
		
		DB.fileStartup(new File(folder, filePath), dataType);
		
		super(new File(folder, filePath));
		this.productionFolder = folder;

		if (!RolesMap().containsKey(DataConstants.START_TIME))
			RolesMap().put(DataConstants.START_TIME, null);

		if (!RolesMap().containsKey(DataConstants.END_TIME))
			RolesMap().put(DataConstants.END_TIME, null);

		if (!RolesMap().containsKey(DataConstants.DATE))
			RolesMap().put(DataConstants.DATE, null);
		
	}

	@SuppressWarnings("unused")
	public void setStartTime(String time) {

		RolesMap().put(DataConstants.START_TIME, time);

	}
	
	@SuppressWarnings("unused")
	public void setEndTime(String time) {

		RolesMap().put(DataConstants.END_TIME, time);

	}

	public void setDate(String date) {

		RolesMap().put(DataConstants.DATE, date);

	}
	
	public void updateRoles( List<String> roles) throws IOException {
	
		for (String role : roles) {

			if (!RolesMap().containsKey(role))
				RolesMap().put(role, null);

		}

		if (roles.size() != RolesMap().size()) {

			for (String key: RolesMap().keySet()) {

				if (!roles.contains(key))
					RolesMap().remove(key);

			}

		}

		this.save();
	
	}
	
	public void editRole(String oldRole, String newRole) throws IOException {
		
		if (!RolesMap().containsKey(oldRole))
			throw new IllegalArgumentException("Role does not exist in performance");
		
		RolesMap().put(newRole, RolesMap().get(oldRole));
		RolesMap().remove(oldRole);
		
		this.save();
		
	}
	
	public void removeRole(String role) throws IOException {
		
		if (!RolesMap().containsKey(role))
			throw new IllegalArgumentException("Role does not exist in performance");
		
		RolesMap().remove(role);
		
		this.save();
		
	}
	
	public void removeCast(String cast) throws IOException {
		
		for (String key: RolesMap().keySet()) {
		
			if (RolesMap().get(key).contains(cast)) {
				
				String currentCast = RolesMap().get(key);
				
				String regex = "(, " + cast + "|" + cast + ", |" + cast + ")";
				
				currentCast = currentCast.replaceAll(regex, "");
				
				if (currentCast.isBlank()) currentCast = null;
				
				RolesMap().put(key, currentCast);
				
			}
		
		}
		
		this.save();
	}
	
	@SuppressWarnings("unused")
	public void addCast(String role, String cast) throws IOException {
		
		if (!RolesMap().containsKey(role))
			throw new AppError(new AppWarning("Tried to add a cast to a non-existing role"));
		
		if (RolesMap().get(role).contains(cast))
			throw new AppError(new AppWarning("Tried to add a cast to a role that already has it"));
		
		RolesMap().put(role, cast);
		
		this.save();
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		
		setDataType(dataType);
		
	}

}
