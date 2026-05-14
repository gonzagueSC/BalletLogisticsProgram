package Core.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Organizer {
	
	public static final String ASCENDING = "ASC";
	public static final String DESCENDING = "DESC";
	public static final String DEFAULT_SORT_METHOD = ASCENDING;
	
	public static final String LESS_THAN_INCLUSIVE = "LTI";
	public static final String GREATER_THAN_INCLUSIVE = "GTI";
	public static final String LESS_THAN_EXCLUSIVE = "LTE";
	public static final String GREATER_THAN_EXCLUSIVE = "GTE";
	public static final String EXACT = "EXACT";
	
	
	public static <T> List<T> simpleSort ( List<T> list, String sortMethod ) throws AppWarning {
		
		if ( sortMethod.equals(ASCENDING) ) {
			
			return list;
			
		} else if ( sortMethod.equals(DESCENDING) ) {
			
			return list.reversed();
			
		} else {
			
			throw new AppWarning("Unrecognized Search method");
			
		}
		
	}
	
	public static List<Object> sort ( Map<Object, String> originalData, EntityField sortField, String sortMethod ) throws AppWarning {
		
		ArrayList<Object> orderedKeys = new ArrayList<>();
		Class<?> targetType = sortField.getFieldType();
		
		for ( Object key : originalData.keySet() ) {
			
			if ( orderedKeys.isEmpty() ) {
				
				orderedKeys.add(key);
				
			} else {
				
				String currentData = originalData.get(key);
				
				int start = 0;
				int end = orderedKeys.size();
				int index = start + (end - start) / 2;
				
				while ( (end - start) > 0 ) {
					
					if ( TypeController.isGreater(originalData.get(orderedKeys.get(index)), currentData,
						   targetType) ) {
						
						end = index;
						
					} else if ( TypeController.isGreater(currentData, originalData.get(orderedKeys.get(index)),
						   targetType) ) {
						
						start = index + 1;
						
					} else {
						
						break;
						
					}
					
					index = start + (end - start) / 2;
					
				}
				
				orderedKeys.add(index, key);
				
				
			}
			
		}
		
		return simpleSort(orderedKeys, sortMethod);
		
	}
	
	public static List<Object> sort ( Map<Object, String> originalData, EntityField sortField ) throws AppWarning {
		
		return sort(originalData, sortField, DEFAULT_SORT_METHOD);
		
	}
	
	public static Map<Object, String> staticSort ( Map<Object, String> originalData, EntityField sortField,
	                                               String sortMethod ) throws AppWarning {
		
		List<Object> simpleList = sort(originalData, sortField, sortMethod);
		Map<Object, String> rebuiltMap = new HashMap<>();
		
		for ( Object key : simpleList ) {
			
			rebuiltMap.put(key, originalData.get(key));
			
		}
		
		return rebuiltMap;
		
	}
	
	public static Map<Object, String> staticSort ( Map<Object, String> originalData, EntityField sortField ) throws AppWarning {
		
		return staticSort(originalData, sortField, DEFAULT_SORT_METHOD);
		
	}
	
	public static String[] sort ( String[] originalData, EntityField sortField, String sortMethod ) throws AppWarning {
		
		ArrayList<String> orderedData = new ArrayList<>();
		Class<?> targetType = sortField.getFieldType();
		
		for ( String currentData : originalData ) {
			
			if ( orderedData.isEmpty() ) {
				
				orderedData.add(currentData);
				
			} else {
				
				int start = 0;
				int end = orderedData.size();
				int index = start + (end - start) / 2;
				
				while ( (end - start) > 0 ) {
					
					if ( TypeController.isGreater(originalData[index], currentData, targetType) ) {
						
						end = index;
						
					} else if ( TypeController.isGreater(currentData, originalData[index], targetType) ) {
						
						start = index + 1;
						
					} else {
						
						break;
						
					}
					
					index = start + (end - start) / 2;
					
				}
				
				orderedData.add(index, currentData);
				
				
			}
			
		}
		
		return simpleSort(orderedData, sortMethod).toArray(new String[0]);
		
	}
	
	public static String[] sort ( String[] originalData, EntityField sortField ) throws AppWarning {
		
		return sort(originalData, sortField, DEFAULT_SORT_METHOD);
		
	}
	
	public static Map<Object, String> staticFilter ( Map<Object, String> originalData, String reference,
	                                                 EntityField filterField, String filterMethod ) throws AppWarning {
		
		Map<Object, String> filteredMap = new HashMap<>();
		
		Class<?> targetType = filterField.getFieldType();
		
		for ( Object key : originalData.keySet() ) {
			
			switch ( filterMethod ) {
				
				case LESS_THAN_EXCLUSIVE -> {
					
					if ( TypeController.isGreater(reference, originalData.get(key), targetType) )
						filteredMap.put(key, originalData.get(key));
					
				}
				case LESS_THAN_INCLUSIVE -> {
					
					if ( !TypeController.isGreater(originalData.get(key), reference, targetType) )
						filteredMap.put(key, originalData.get(key));
					
				}
				case GREATER_THAN_EXCLUSIVE -> {
					
					if ( TypeController.isGreater(originalData.get(key), reference, targetType) )
						filteredMap.put(key, originalData.get(key));
					
				}
				case GREATER_THAN_INCLUSIVE -> {
					
					if ( !TypeController.isGreater(reference, originalData.get(key), targetType) )
						filteredMap.put(key, originalData.get(key));
					
				}
				case EXACT -> {
					
					if ( !TypeController.isGreater(reference, originalData.get(key), targetType) && !TypeController.isGreater(originalData.get(key), reference, targetType))
						filteredMap.put(key, originalData.get(key));
						
					
				}
				default -> throw new AppWarning("filter Method does not Exist");
				
			}
			
		}
		
		return filteredMap;
		
	}
	
	public static Map<Object, String> deepFilter ( Map<Object, String> originalData, String reference,
	                                                 EntityField[] filterFields, String[] filterMethods ) throws AppWarning {
		
		if (filterFields.length != filterMethods.length)
			throw new AppWarning("filterFields does not match filterMethods");
		
		Map<Object, String> filteredMap = originalData;
		
		for (int i = 0; i < filterFields.length; i++) {
		
			filteredMap = staticFilter(originalData, reference, filterFields[i], filterMethods[i]);
		
		}
		
		return filteredMap;
		
	}
	
}
