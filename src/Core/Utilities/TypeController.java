package Core.Utilities;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HexFormat;

public class TypeController {
	
	public static <T> String checkParseability ( Object inputValue, Class<T> target ) {
		
		if ( inputValue == null ) return null;
		
		String value = inputValue.toString();
		
		try {
			if ( target == Integer.class ) {
				Integer.parseInt(value);
				return value;
			} else if ( target == Double.class ) {
				Double.parseDouble(value);
				return value;
			} else if ( target == Boolean.class ) {
				if ( !value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false") ) {
					
					throw new AppWarning("boolean was not a boolean");
					
				} else {
					
					return value;
					
				}
			} else if ( target == LocalDate.class ) {
				Chronos.getStandardizedDate(value);
				return Chronos.standardizeDate(Chronos.getStandardizedDate(value));
			} else if ( target == LocalTime.class ) {
				Chronos.getStandardizedTime(value);
				return Chronos.standardizeTime(Chronos.getStandardizedTime(value));
			} else if ( target == Color.class ) {
				Color.getColor(value);
				return value;
			}
			return null;
		} catch ( Exception e ) {
			
			return null;
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getValue ( String rawValue, Class<T> targetType ) {
		
		if ( rawValue == null ) return null;
		
		try {
			if ( targetType == Integer.class ) return (T) Integer.valueOf(rawValue);
			if ( targetType == Double.class ) return (T) Double.valueOf(rawValue);
			if ( targetType == Boolean.class ) return (T) Boolean.valueOf(rawValue);
			if ( targetType == LocalDate.class ) return (T) Chronos.getStandardizedDate(rawValue);
			if ( targetType == LocalTime.class ) return (T) Chronos.getStandardizedTime(rawValue);
			if ( targetType == Color.class ) return (T) Color.getColor(rawValue);
			
			return (T) rawValue; // Default to String
		} catch ( Exception e ) {
			return null;
		}
	}
	
	public static <T> boolean isGreater ( String rawValue1, String rawValue2, Class<T> targetType ) throws AppWarning {
		
		if ( targetType == Integer.class ) return ((Integer)getValue(rawValue1, targetType)) > ((Integer)getValue(rawValue2,
		   targetType));
		if ( targetType == Double.class ) return ((Double) getValue(rawValue1, targetType)) > ((Double) getValue(rawValue2, targetType));
		if ( targetType == LocalDate.class || targetType == LocalTime.class) return Chronos.compareChrono(rawValue1,
			   rawValue2) < 1;
		
		return rawValue1.compareTo(rawValue2) > 0; // Default to String
		
	}
	
	public static String hash ( String input ) throws NoSuchAlgorithmException {
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		
		return HexFormat.of().formatHex(bytes);
		
	}
	
}
