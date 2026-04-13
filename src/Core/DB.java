package Core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.instrument.IllegalClassFormatException;
import java.util.*;

import static Core.DataConstants.*;

public class DB {
	
	private final File file;
	private BufferedReader fileReader;
	private BufferedWriter fileWriter;
	private final String fileType;
	
	public DB ( File file ) throws IOException {
		
		this.file = file;
		FileReader baseDataReader = new FileReader(file);
		fileReader = new BufferedReader(baseDataReader);
		
		try {
			fileType = (fileReader.readLine().substring(5));
			
		} finally {fileReader.close();}
		
	}
	
	public File getFile () {
		
		return file;
		
	}
	
	public String getFileType () {
		
		return fileType;
		
	}
	
	public BufferedReader openReader () throws IOException {
		
		FileReader baseDataReader = new FileReader(file);
		return new BufferedReader(baseDataReader);
		
	}
	
	public BufferedWriter openAppendWriter () throws IOException {
		
		FileWriter baseDataWriter = new FileWriter(file, true);
		return new BufferedWriter(baseDataWriter);
		
	}
	
	public BufferedWriter openOverWriter () throws IOException {
		
		FileWriter baseDataWriter = new FileWriter(file);
		BufferedWriter dataWriter = new BufferedWriter(baseDataWriter);
		dataWriter.write(IDENTIFIER + fileType + "\n");
		return dataWriter;
		
	}
	
	public Object getLines () throws IOException, IllegalArgumentException {
		
		try {
			
			fileReader = openReader();
			
			fileReader.readLine(); //IGNORE FILE TYPE INDICATOR
			
			Object result;
			
			if (fileType.equals(JSON_TYPE)) {
				
				result = fileReader.readAllAsString();
				
				fileReader.close();
				
			} else if (fileType.equals(TEXT_TYPE)) {
				
				result = fileReader.readAllLines();
				
				fileReader.close();
				
			} else {
				
				throw new IllegalArgumentException("Unknown File Type Presented: " + fileType + ". Error " +
					   "Originated in DB");
				
			}
			
			return result;
			
		} finally {fileReader.close();}
		
	}
	
	public Map<String, String> interpretJSON () throws IllegalClassFormatException, IOException {
		
		if (!fileType.equals(JSON_TYPE)) {
			
			throw new IllegalClassFormatException("Cannot access a JSON Map for a non JSON Entity");
			
		} else {
			
			ObjectMapper mapper = new ObjectMapper();
			
			return mapper.readValue((String) this.getLines(), new TypeReference<Map<String, String>>() {});
			
		}
		
	}
	
	public void appendToJSON ( String key, String value ) throws IOException {
		
		String currentJSON = (String) getLines();
		
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, String> json = mapper.readValue(currentJSON, new TypeReference<Map<String, String>>() {});
		
		json.put(key, value);
		
		String jsonResult = mapper.writeValueAsString(json);
		
		fileWriter = openOverWriter();
		
		try {
			
			fileWriter.write(jsonResult);
			
		} finally {fileWriter.close();}
		
		
	}
	
	public void appendToRAW ( String appending ) throws IOException {
		
		fileWriter = openAppendWriter();
		
		try {
			
			fileWriter.write(encodeForFile(appending) + "\n");
			
		} finally {fileWriter.close();}
		
	}
	
	public void writeJSON ( Map<String, String> jsonData ) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonResult = mapper.writeValueAsString(jsonData);
		
		fileWriter = openOverWriter();
		
		try {
			
			fileWriter.write(jsonResult);
			
		} finally {fileWriter.close();}
		
	}
	
	public void writeRAW ( String[] file ) throws IOException {
		
		fileWriter = openOverWriter();
		
		try {
			
			for (String line : file) {
				
				fileWriter.write(encodeForFile(line) + "\n");
				
			}
			
		} finally {fileWriter.close();}
		
	}
	
	public String encodeForFile ( String input ) {
		if (input == null)
			return null;
		return input.replace("\\", "\\\\")  // Must escape backslashes first!
			   .replace("\n", "\\n")    // Encode newlines
			   .replace("\r", "\\r")    // Encode carriage returns
			   .replace("\t", "\\t")    // Encode tabs
			   .replace("\"", "\\\"");  // Encode double quotes
	}
	
	public static File retrieveFile ( String path ) {
		
		return new File(path);
		
	}
	
	public static boolean fileExists ( File file ) {
		
		return file.exists();
		
	}
	
	public static void createFile ( File file, String dataType ) throws IOException {
		
		boolean fileExists = !file.createNewFile();
		
		if (!fileExists) {
			FileWriter fileWriter = new FileWriter(file);
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				bufferedWriter.write(IDENTIFIER + dataType + "\n");
			}
		}
		
	}
	
	public static void fileStartup ( File file, String dataType ) throws IOException {
		
		if (!fileExists(file)) {
			
			createFile(file, dataType);
			
		}
		
	}
	
	public static void moveFile(File file, String newPath) {
	
	
	
	}
	
}
