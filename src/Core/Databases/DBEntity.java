package Core.Databases;

import Core.Utilities.FileChecker;
import Core.Utilities.AppWarning;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.instrument.IllegalClassFormatException;
import java.util.*;

import static Core.Utilities.DataConstants.*;

public abstract class DBEntity {
	
	private final DB database;
	protected final File file;
	private String dataType;
	
	protected FileChecker fileChecker;
	protected String[] lines;
	protected Map<String, String> entityData;
	protected Map<String, Map<String,String>> complexEntityData;
	
	public DBEntity ( File file ) throws IOException, IllegalClassFormatException, AppWarning {
		
		this.file = file;
		this.database = new DB(this.file);
		
		initializeComponents();
		
	}
	
	@SuppressWarnings("unused")
	public DBEntity ( DB database ) throws IOException, IllegalClassFormatException, AppWarning {
		
		this.database = database;
		this.file = database.getFile();
		collectData();
		
		initializeComponents();
		
	}
	
	protected void setDataType ( String dataType ) throws IllegalClassFormatException, IOException, AppWarning {
		
		this.dataType = dataType;
		
		database.setFileType(dataType);
		
		collectData();
		
	}
	
	protected void collectData () throws IOException, IllegalClassFormatException, AppWarning {

		if (dataType.equals(JSON_TYPE)) {
			
			this.entityData = database.interpretJSON();
			
		} else if (dataType.equals(TEXT_TYPE)) {
			
			this.lines = fileChecker.getLines();
			fileChecker.updateLines();
			
		} else if ( dataType.equals(COMPLEX_JSON)) {
		
			this.complexEntityData = database.interpretComplexJSON();
		
		}else {
			
			throw new IllegalArgumentException("Unknown File Type Presented: " + dataType + ". Error " +
				   "Originated in DBEntity");
			
		}
		
	}
	
	protected void addToFile ( String key, String value ) throws IOException, IllegalClassFormatException, AppWarning {
		
		if (dataType.equals(JSON_TYPE)) {
			
			database.appendToJSON(key, value);
			
		} else {
			
			throw new IllegalClassFormatException("Cannot provide 2 values to addToFile for a non JSON object");
			
		}
		
	}
	
	protected void addToFile ( String value ) throws IOException, IllegalClassFormatException, AppWarning {
		
		if (dataType.equals(TEXT_TYPE)) {
			
			database.appendToRAW(value);
			fileChecker.updateLines();
			
		} else {
			
			throw new IllegalClassFormatException("Cannot provide more than one value to addToFile for a non RAW " +
				   "object");
			
		}
		
	}
	
	@SuppressWarnings("unused")
	protected void editFile ( String key, String newValue ) throws IllegalClassFormatException, IOException, AppWarning {
		
		this.addToFile(key, newValue);
		
	}
	
	@SuppressWarnings("unused")
	protected void editFileLine ( String oldLine, String newLine ) throws IOException, IllegalClassFormatException, AppWarning {
		
		if (dataType.equals(TEXT_TYPE)) {
			
			fileChecker.editLine(oldLine, newLine);
			fileChecker.updateLines();
			
		} else {
			
			throw new IllegalClassFormatException("Cannot run editFileLine for a non RAW object");
			
		}
		
	}
	
	protected void removeLine(String line) throws IOException, IllegalClassFormatException, AppWarning {
		
		if (dataType.equals(TEXT_TYPE)) {
			
			fileChecker.removeLine(line);
			fileChecker.updateLines();
			
		} else {
			
			throw new IllegalClassFormatException("Cannot run removeLine for a non RAW object");
			
		}
		
	}
	
	protected abstract void initializeComponents () throws IllegalClassFormatException, IOException, AppWarning;
	
	protected void save () throws IOException, AppWarning {
		
		switch ( dataType ) {
			case JSON_TYPE -> database.writeJSON(entityData);
			case COMPLEX_JSON -> database.writeComplexJSON(complexEntityData);
			case TEXT_TYPE -> {
				
				database.writeRAW(lines);
				fileChecker.updateLines();
			}
			default ->
				   throw new IllegalArgumentException("Unknown File Type Presented: " + dataType + ". Error " + "Originated in DBEntity");
		}
		
	}
	
	
	@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
	public void killFile() {
		
		file.delete();
		
	}
	
	@SuppressWarnings("All")
	public String readArrayNaturally ( Object[] array ) {
		
		String result = "";
		
		for (int i = 0; i < array.length; i++) {
			
			result += array[i].toString() + ((i < array.length - 1) ? ", " : (i == array.length - 1) ? ", and " : "");
			
		}
		
		return result;
		
	}
	
	@SuppressWarnings("All")
	public static String readArray(Object[] array) {
		
		String result = "";
		
		for (int i = 0; i < array.length; i++) {
			
			result += array[i].toString() + ((i != array.length - 1) ? ", " : "");
			
		}
		
		return result;
		
	}
	
	@NotNull
	protected static String getIDValue ( String format, HashMap<Integer, String> IDs, int maxID ) {
		
		if (maxID > IDs.size()) {
			
			for (int i = 1; i <= IDs.size(); i++) {
				
				if (!IDs.containsKey(i)) {
					
					return String.format(format, i);
					
				}
				
			}
			
		}
		
		return String.format(format, maxID + 1);
	}
	
}
