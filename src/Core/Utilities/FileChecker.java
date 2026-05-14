package Core.Utilities;
import Core.Databases.DB;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import static Core.Utilities.DataConstants.*;

public class FileChecker {
	
	private String[] lines;
	private final DB currentFile;
	
	public FileChecker( DB database ) throws IOException, AppWarning {
		
		currentFile = database;
		if (currentFile.getFileType().equals(TEXT_TYPE)) {
			
			lines = getLines();
			
		} else if (currentFile.getFileType().equals(JSON_TYPE)) {
			
			throw new IllegalArgumentException("Cannot provide FileChecker object with a JSON file");
			
		}
	
	}
	
	public boolean lineExists ( String targetLine ) throws IOException {
		
		if (currentFile.getFileType().equals(TEXT_TYPE)) {
			
			for (String currentLine : lines) {
				
				if (currentLine.equals(targetLine)) {
					
					return true;
					
				}
				
			}
			
		} else {
			
			throw new InvalidObjectException("File was in " + currentFile.getFileType() + " format. checkForLine" + "(String line, File file) only searches RAW files");
			
		}
		
		return false;
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean lineExists ( String regex, String... args ) throws IOException, AppWarning {
		
		if (currentFile.getFileType().equals(TEXT_TYPE)) {
			
			for (String currentLine : ((List<String>) currentFile.getLines())) {
				
				String[] line = currentLine.split(Pattern.quote(regex));
				
				if (line.length < args.length && !currentLine.isBlank()) {
					
					throw new IndexOutOfBoundsException("Too many parsing arguments | line: " + Arrays.toString(line) + ", args: " + Arrays.toString(args));
					
				}
				
				if (sameArguments(line, args)) {
					
					return true;
					
				}
				
			}
			
		} else {
			
			throw new InvalidObjectException("File was in " + currentFile.getFileType() + " format. checkForLine" + "(String line, File file) only searches RAW files");
			
		}
		
		return false;
		
	}
	
	@SuppressWarnings("All")
	public boolean sameArguments ( String[] array, String[] arguments ) {
		
		// iterate through the arguments array
		for (int i = 0; i < array.length; i++) {
			
			// if the argument is not null and does not match the corresponding array
			// element, return false
			if (i < arguments.length && !array[i].equals(arguments[i])) {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
	public int getLineIndex ( String targetLine ) throws IOException {
		
		if (!lineExists(targetLine))
			return -1;
		
		for (int index = 0; index < lines.length; index++) {
			
			if (lines[index].equals(targetLine)) {
				
				return index;
				
			}
			
		}
		
		return -1;
		
	}
	
	public int getLineIndex ( String regex, String... args ) throws IOException, AppWarning {
		
		if (!lineExists(regex, args))
			return -1;
		
		for (int index = 0; index < lines.length; index++) {
			
			String currentLine = lines[index];
			
			String[] line = currentLine.split(Pattern.quote(regex));
			
			if (line.length < args.length && !currentLine.isBlank()) {
				
				throw new IndexOutOfBoundsException("Too many parsing arguments | line: " + Arrays.toString(line) + ", args: " + Arrays.toString(args));
				
			}
			
			if (sameArguments(line, args)) {
				
				return index;
				
			}
			
		}
		
		return -1;
		
	}
	
	@SuppressWarnings("unused")
	public String getLineAroundLine ( String targetLine, int direction, String regex, String... args) throws IOException, AppWarning {
		
		if (!lineExists(targetLine))
			return "N/A";
		
		int targetLineIndex = getLineIndex(regex, args);
		
		return switch (direction) {
			
			case BEFORE -> lines[targetLineIndex - 1];
			case AFTER -> lines[targetLineIndex + 1];
			default -> throw new IllegalArgumentException("getLineAroundLine direction parameter must be either " +
				   "BEFORE or AFTER");
			
		};
		
	}
	
	@SuppressWarnings("unused")
	public String getLineAroundLine ( String targetLine, int direction ) throws IOException {
		
		if (!lineExists(targetLine))
			return "N/A";
		
		int targetLineIndex = getLineIndex(targetLine);
		
		return switch (direction) {
			
			case BEFORE -> lines[targetLineIndex - 1];
			case AFTER -> lines[targetLineIndex + 1];
			default -> throw new IllegalArgumentException("getLineAroundLine direction parameter must be either " +
				   "BEFORE or AFTER");
			
		};
		
	}
	
	public void editLine(String oldLine, String newLine) throws IOException {
	
		int index = this.getLineIndex(oldLine);
		lines[index] = newLine;
		currentFile.writeRAW(lines);
	
	}
	
	public void removeLine(String line) throws IOException {
		
		ArrayList<String> newLines = new ArrayList<>(List.of(lines));
		newLines.remove(line);
		currentFile.writeRAW(newLines.toArray(new String[0]));
		
	}
	
	public static String[] getLines ( File file ) throws IOException, AppWarning {
		
		@SuppressWarnings("unchecked") String[] lines =
			   ((List<String>) (new DB(file).getLines())).toArray(new String[0]);
		
		return lines;
		
	}
	
	public String[] getLines() throws IOException, AppWarning {
		
		@SuppressWarnings("unchecked") String[] lines =
			   ((List<String>) (currentFile.getLines())).toArray(new String[0]);
		
		return lines;
		
	}
	
	public void updateLines() throws IOException, AppWarning {
		
		lines = getLines(currentFile.getFile());
		
	}
	
}
