package Core;

import java.io.*;
import java.lang.instrument.IllegalClassFormatException;
import java.util.*;

import static Core.DataConstants.*;

public abstract class DBEntity {
	
	private final DB database;
	protected final File file;
	private String dataType;
	
	protected FileChecker fileChecker;
	protected String[] lines;
	protected Map<String, String> entityData;
	
	public DBEntity ( File file ) throws IOException, IllegalClassFormatException {
		
		this.file = file;
		this.database = new DB(this.file);
		
		initializeComponents();
		
	}
	
	public DBEntity ( DB database ) throws IOException, IllegalClassFormatException {
		
		this.database = database;
		this.file = database.getFile();
		collectData();
		
		initializeComponents();
		
	}
	
	protected void setDataType ( String dataType ) throws IllegalClassFormatException, IOException {
		
		this.dataType = dataType;
		
		database.setFileType(dataType);
		
		collectData();
		
	}
	
	protected void collectData () throws IOException, IllegalClassFormatException {

		if (dataType.equals(JSON_TYPE)) {
			
			this.entityData = database.interpretJSON();
			
		} else if (dataType.equals(TEXT_TYPE)) {
			
			this.lines = fileChecker.getLines();
			fileChecker.updateLines();
			
		} else {
			
			throw new IllegalArgumentException("Unknown File Type Presented: " + dataType + ". Error " +
				   "Originated in DBEntity");
			
		}
		
	}
	
	protected void addToFile ( String key, String value ) throws IOException, IllegalClassFormatException {
		
		if (dataType.equals(JSON_TYPE)) {
			
			database.appendToJSON(key, value);
			
		} else {
			
			throw new IllegalClassFormatException("Cannot provide 2 values to addToFile for a non JSON object");
			
		}
		
	}
	
	protected void addToFile ( String value ) throws IOException, IllegalClassFormatException {
		
		if (dataType.equals(TEXT_TYPE)) {
			
			database.appendToRAW(value);
			fileChecker.updateLines();
			
		} else {
			
			throw new IllegalClassFormatException("Cannot provide more than one value to addToFile for a non RAW " +
				   "object");
			
		}
		
	}
	
	protected void editFile ( String key, String newValue ) throws IllegalClassFormatException, IOException {
		
		this.addToFile(key, newValue);
		
	}
	
	protected void editFileLine ( String oldLine, String newLine ) throws IOException, IllegalClassFormatException {
		
		if (dataType.equals(TEXT_TYPE)) {
			
			fileChecker.editLine(oldLine, newLine);
			fileChecker.updateLines();
			
		} else {
			
			throw new IllegalClassFormatException("Cannot run editFileLine for a non RAW object");
			
		}
		
	}
	
	protected void removeLine(String line) throws IOException, IllegalClassFormatException {
		
		if (dataType.equals(TEXT_TYPE)) {
			
			fileChecker.removeLine(line);
			fileChecker.updateLines();
			
		} else {
			
			throw new IllegalClassFormatException("Cannot run removeLine for a non RAW object");
			
		}
		
	}
	
	protected abstract void initializeComponents () throws IllegalClassFormatException, IOException;
	
	protected void save () throws IOException {
		
		if (dataType.equals(JSON_TYPE)) {
			
			database.writeJSON(entityData);
			
		} else if (dataType.equals(TEXT_TYPE)) {
			
			database.writeRAW(lines);
			fileChecker.updateLines();
			
		} else {
			
			throw new IllegalArgumentException("Unknown File Type Presented: " + dataType + ". Error " +
				   "Originated in DBEntity");
			
		}
		
	}
	
}
