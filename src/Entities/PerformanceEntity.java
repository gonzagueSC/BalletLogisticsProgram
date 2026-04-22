package Entities;

import Core.DB;
import Core.DBEntity;
import Core.JSONObjectEntity;

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
		
	}
	
	public void updateRoles( List<String> roles) {
	
	
	
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		
		setDataType(dataType);
		
	}

}
