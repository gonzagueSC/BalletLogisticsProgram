package Entities;

import Core.DBEntity;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.util.List;

import static Core.DataConstants.TEXT_TYPE;

public class RehearsalsEntity extends DBEntity {
	
	public static String dataType = TEXT_TYPE;
	File productionFolder;
	
	private static final String filePath = "Rehearsals";
	
	public RehearsalsEntity ( File folder ) throws IOException, IllegalClassFormatException {
		
		super(new File(folder, filePath));
		this.productionFolder = folder;
		
	}
	
	public void addRehearsal ( String RehearsalName ) throws IllegalClassFormatException, IOException {
		
		this.addToFile(RehearsalName);
		this.save();
		
	}
	
	public void removeRehearsal ( String RehearsalName ) throws IllegalClassFormatException, IOException {
		
		this.removeLine(RehearsalName);
		this.save();
		
	}
	
	public boolean RehearsalExists (String RehearsalName) throws IOException {
		
		return this.fileChecker.lineExists(RehearsalName);
		
	}
	
	public List<String> getAllRehearsals() throws IOException {
		
		return List.of(this.fileChecker.getLines());
		
	}
	
	@Override
	protected void initializeComponents () throws IllegalClassFormatException, IOException {
		
		setDataType(dataType);
		
	}
	
}
