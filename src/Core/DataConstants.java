package Core;

import java.time.format.DateTimeFormatter;

public class DataConstants {
	
	public static final String IDENTIFIER = "TYPE:";

	public static final String JSON_TYPE = "JSON";
	public static final String TEXT_TYPE = "RAW";
	
	public static final int BEFORE = 1;
	public static final int AFTER = 2;
	
	public static final String[] Months = { "January", "February", "March", "April", "May", "June", "July", "August",
		   "September", "October", "November", "December" };
	
	public static final String nullValue = null;
	
	public static final DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

}
